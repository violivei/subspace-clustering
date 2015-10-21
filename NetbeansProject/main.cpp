/* 
 * File:   main.cpp
 * Author: hans and victor
 *
 * Created on 11 de Outubro de 2010, 07:25
 */

#include "../dlib/optimization/max_cost_assignment.h"
#include "../dlib/svm.h"
#include <stdlib.h>
#include <fstream>
#include <algorithm>
#include <time.h>
#include <limits>
#include <unistd.h>
#include "MatMatrix.h"
#include "MatVector.h"
#include "Defines.h"
#include "DebugOut.h"
#include "Parameters.h"
#include "ClusteringMetrics.h"
#include "ArffData.h"
#include "ClusteringSOM.h"
#include "LARFDSSOM.h"
#include "DataDisplay.h"
#include <cstdio>
#include "mat.h"
#include <time.h>       /* time */

#include "bipartite_matching_commented.h"

using namespace std;
using namespace dlib;

int main(int argc, char** argv) {

    int seed = 0;
    dbgThreshold(1);

    LARFDSSOM som(1);
    
    som.maxNodeNumber = 5;
    som.e_b = 0.0005;
    som.e_n = 0.000001;
    //
    som.dsbeta = 0.1;
    som.epsilon_ds = 0.001;    
    som.minwd = 0.5;
    som.age_wins = 2;
    som.lp       = 0.1;//0.175;
    som.a_t = 0.975; //Limiar de atividade .95
    int epocs = 100;
    bool isSubspaceClustering = true;
    bool filterNoise = true;
    string filename = "";
    double totalAccuracy = 0.0;
    int count = 0;
    
    int c;
    while ((c = getopt(argc, argv, "f:n:t:e:g:m:s:p:w:i:l:v:r:Po")) != -1)
        switch (c) {            
            case 'f':
                filename.assign(optarg);
                break;
            case 'n':
            case 't':
                epocs = atoi(optarg);
                break;
            case 'e':                
                som.e_b = atof(optarg);
                break;
            case 'g':
                som.e_n = atof(optarg);
                break;
            case 'm':
                som.maxNodeNumber = atoi(optarg);
                break;
            case 's':
                //DS
                som.dsbeta = atof(optarg);
                break;
            case 'p':
                som.epsilon_ds = atof(optarg);
                break;   
            case 'w':
                som.minwd = atof(optarg);
                break;  
            case 'i':
                som.age_wins = atof(optarg);
                break; 
            case 'l':
                som.lp = atof(optarg);
                break;  
            case 'v':
                som.a_t = atof(optarg);
                break;    
            case 'P':
                isSubspaceClustering = true;
                break;
            case 'r':
                seed = atoi(optarg);
                break;  
            case 'o':
                filterNoise = true;
                break;  
            case '?':
                if (optopt == 'f')
                    fprintf(stderr, "Option -%c requires an argument.\n", optopt);
                else if (isprint(optopt))
                    fprintf(stderr, "Unknown option `-%c'.\n", optopt);
                else
                    fprintf(stderr,
                        "Unknown option character `\\x%x'.\n",
                        optopt);
                return 1;
        }

    if (filename == "") {
        dbgOut(0) << "option -f [filename] is required" << endl;
        return -1;
    }
        
    while(count < 10){
    
        dbgOut(1) << "Running LARFDSSOM for file: " << filename << endl;

        //srand(1);
        srand(time(NULL));
        LARFDSSOM somDef(1);
        somDef.maxNodeNumber = som.maxNodeNumber;
        somDef.e_b = som.e_b;
        somDef.e_n = som.e_n;
        somDef.dsbeta = som.dsbeta;
        somDef.epsilon_ds = som.epsilon_ds;

        somDef.minwd = som.minwd;
        somDef.age_wins = som.age_wins;
        somDef.lp = som.lp;
        somDef.a_t = som.a_t;


        SOM<DSNode> *dssom = (SOM<DSNode>*)&somDef;
        //ClusteringMeshSOM clusteringSOM(dssom);
        ClusteringMeshSOM *clusteringSOM = new ClusteringMeshSOM(dssom);
    
    
        if (clusteringSOM->readFile(filename)){

            
            somDef.groundTruthNodes = clusteringSOM->groupLabels.size();
            clusteringSOM->setIsSubspaceClustering(false);
            clusteringSOM->setFilterNoise(filterNoise);
            somDef.minwd = somDef.minwd*sqrt(clusteringSOM->getInputSize());
            somDef.age_wins = round(somDef.age_wins*clusteringSOM->getNumSamples());
                        
            typedef matrix<double, 5000, 1> sample_type;       
            vector_normalizer<sample_type> normalizer;
            std::vector<sample_type> samples;
            
            for (unsigned long i = 0; i < clusteringSOM->trainingData->rows(); ++i){
                sample_type samp;
                for (unsigned long j = 0; j < clusteringSOM->trainingData->cols(); ++j){                    
                    samp(j) = clusteringSOM->trainingData->get(i,j);    
                   // printf(" %f ", samp(i,j));
                }
                //printf("\n\n\n");
                samples.push_back(samp);
            }
            
            // let the normalizer learn the mean and standard deviation of the samples
            normalizer.train(samples);

            //printf("\n\n");
            int dataCols = clusteringSOM->trainingData->cols();
            int dataRows = clusteringSOM->trainingData->rows();
            
            clusteringSOM->trainingData = new MatMatrix<float>();
            
            for (unsigned long i = 0; i < dataRows; ++i){             
                     samples[i] = normalizer(samples[i]); 
                     MatVector<float> row;
                     for (unsigned long j = 0; j < dataCols; ++j){ 
                         //printf(" %f ", samples[i](j));
                         row.append(samples[i](j));
                     }
                     clusteringSOM->trainingData->concatRows(row);
            }
                        
            for (unsigned long i = 0; i < dataRows; ++i){
                
                for (unsigned long j = 0; j < dataCols; ++j){ 
                    //printf(" %f ", clusteringSOM->trainingData->get(i,j));
                }
                //printf("\n\n\n");
            }
            
            somDef.reset(clusteringSOM->getInputSize());
            clusteringSOM->trainSOM(epocs*clusteringSOM->getNumSamples());
            //clusteringSOM.trainSOM(3000);
            somDef.finishMapFixed();

            //if(clusteringSOM->getMeshSize() == clusteringSOM->groupLabels.size())
            {
                clusteringSOM->writeClusterResults(filename + ".results");    
                //clusteringSOM.groups.clear();
                clusteringSOM->outConfusionMatrix(filename + ".outConfusionMatrix");
                
                //dbgOut(1) << clusteringSOM->printConditionalEntropy(clusteringSOM->groups);
                //dbgOut(1) << clusteringSOM.outClassInfo() << endl;
                //fprintf(stderr, "%s", clusteringSOM->outClassInfo().c_str());
                matrix<int> cost(clusteringSOM->groupLabels.size(),clusteringSOM->groupLabels.size());
                int counter;
                
                for (int l = 0; l < clusteringSOM->groupLabels.size(); l++) { 
                    
                    for (int k = 0; k < clusteringSOM->groupLabels.size(); k++) {
                        
                        counter = 0;
                        for (int j = 0; j < clusteringSOM->groups.size(); j++) {
                            if(clusteringSOM->groupLabels[l] - 1  == clusteringSOM->groups[j]){
                                if(k  == clusteringSOM->winn[j]){
                                    counter++;
                                }
                            }
                        } 
                        cost(l,k) = counter;
                        //fprintf(stderr, " class %d and class %d equals to %d \n", clusteringSOM->groupLabels[l], k,  cost(l,k));
                    }                                   
                }

                
                // To find out the best assignment of people to jobs we just need to call this function.
                std::vector<long> assignment = max_cost_assignment(cost);
                // This prints optimal assignments:  [2, 0, 1] which indicates that we should assign
                // the person from the first row of the cost matrix to job 2, the middle row person to
                // job 0, and the bottom row person to job 1.
                for (unsigned int i = 0; i < assignment.size(); i++)
                    cout << assignment[i] << std::endl;
                
                
                std::vector<int> newArray;

                for(int x=0; x < clusteringSOM->winn.size() ;x++)
                {
                    newArray.push_back(-99999);
                }
                
                //printf(" size =  %d %d  \n", clusteringSOM->groups.size(), clusteringSOM->groupLabels.size());
               
                for (int l = 0; l < clusteringSOM->groupLabels.size(); l++) { 

                    for(int x=0; x < clusteringSOM->groups.size() ;x++)
                    {
                        //printf("  %d = %d | %d  \n", clusteringSOM->groups[x], (int)assignment[l], clusteringSOM->groupLabels[l]);
                        if(clusteringSOM->groups[x] == clusteringSOM->groupLabels[l] - 1){ 
                            //printf("  %d = %d | %d  \n", clusteringSOM->groups[x], (int)assignment[l], clusteringSOM->groupLabels[l]-1);
                            newArray[x] = assignment[l];
                        }
                    }
                }
                
                
                int hits = 0;
                for(int x=0; x < clusteringSOM->winn.size() ;x++)
                {
                    //printf("%d  %d  %d \n", clusteringSOM->winn[x], newArray[x], clusteringSOM->groups[x]);
                    if(clusteringSOM->winn[x] == newArray[x])
                    {
                        hits++;
                    }          
                }
                
                fprintf(stderr, " Temp Acc: %f \n", (double)hits/clusteringSOM->winn.size());
                //if((double)hits/clusteringSOM->winn.size() > totalAccuracy)
                    //totalAccuracy = (double)hits/clusteringSOM->winn.size();
                totalAccuracy += (double)hits/clusteringSOM->winn.size();
                count++;
            }
        }
      //fprintf(stderr, " Temp Acc: %f \n", (double)hits/clusteringSOM->winn.size());
      
    
   }
        
    //fprintf(stderr, " Acc: %f \n", totalAccuracy);
    fprintf(stderr, " Acc: %f \n", totalAccuracy/count);

}


/*
    typedef LARFDSNode TNode;
    LARFDSSOM som(m.cols()); //Cria um obj Larfdssom
    som.a_t = 0.95; //Limiar de atividade .95
    som.d_max = 100;
    som.epsilon = 0.9;
    som.ho_f = 0.10;
    som.maxNodeNumber = 6;
    //DS
    som.dsbeta = 0.001;
    som.epsilon_ds = 0.0;


    /*
    typedef GNGDSNode TNode;
    GNGDSSOM som(m.cols()); //Cria um obj 
    som.maxNodeNumber = 20;
    som.alfa = 0.5;
    som.beta = 0.0005;
    som.e_b = 0.1;
    som.e_n = 0.00000; //0.0006
    //DS
    som.dsbeta = 0.0001;
    som.epsilon_ds = 0.0;


    /*
    typedef GWRNode TNode;
    GWRSOM som(m.cols()); //Cria um obj 
    som.maxNodeNumber = 12;
    som.a_t = 0.65; //Limiar de atividade
    som.h_t = 0.45; //Limiar de disparo
    som.e_b = 0.5;
    som.e_n = 0.051;
    som.h0 = 1.2;
    som.iStrength = 1;
    som.a_b = 1.05;
    som.a_n = 1.05;
    som.tau_b = 3.33;
    som.tau_n = 3.33; 


    //*
    typedef GWRDSNode TNode;
    GWRDSSOM som(m.cols()); //Cria um obj 
    som.maxNodeNumber = 20;
    som.a_t = 0.65; //Limiar de atividade
    som.h_t = 0.45; //Limiar de disparo
    som.e_b = 0.5;
    som.e_n = 0.00001;
    som.h0 = 1.2;
    som.iStrength = 1;
    som.a_b = 1.05;
    som.a_n = 1.05;
    som.tau_b = 3.33;
    som.tau_n = 3.33;
    //DS
    som.dsbeta = 0.0001;
    som.epsilon_ds = 0.00;


    som.data = m;
    //som.initialize(m); //ou larfdssom.initializeDefault(m);

    //som.trainning(m.rows()); //Número de amostras apresentadas ao mapa

    SOM<DSNode> *drawingSOM = (SOM<DSNode>*) & som;
    CImg<unsigned char> image(500, 500, 1, 3);
    CImgDisplay disp(500, 500);


    for (int t = 0; t < m.rows()*20; t++) {
        som.trainningStep();
        som.enumerateNodes(); //Realiza uma rotulação dos nodos
        buildImage(image, *drawingSOM, m, NULL, 0, 1, true, true, &averages, &groupLabels);
        image.display(disp);
    }
    disp.close();
    /**/