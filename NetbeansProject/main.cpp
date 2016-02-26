/* 
 * File:   main.cpp
 * Author: hans and victor
 *
 * Created on 11 de Outubro de 2010, 07:25
 */

#include <armadillo>
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
#include <dirent.h>

#include "bipartite_matching_commented.h"

using namespace std;
using namespace dlib;
using namespace arma;

bool matFile = true;

/*void printTruth(const std::string &filename, ClusteringMeshSOM *clusteringSOM) {

    std::ofstream file;
    file.open(filename.c_str()); 
    std::vector<int> groupQuantity;    
    int count;
    
    for (int j = 0; j < clusteringSOM->groupLabels.size(); j++) {
        count = 0;
        for (int i = 0; i < clusteringSOM->groups.size(); i++) {
            
            if(clusteringSOM->groups[i] == clusteringSOM->groupLabels[j] - 1){
                count++;
            }            
        }
        groupQuantity.push_back(count);
    }
    
    
    file << "DIM=" << clusteringSOM->getInputSize() << ";";
    file << endl;
    
    for (int j = 0; j < clusteringSOM->groupLabels.size(); j++) {
        
        for (int k = 0; k < clusteringSOM->getInputSize(); k++) {
            file << "1 ";
        }
        
        if(groupQuantity[j] > 1){
            file << groupQuantity[j]<< " ";
        }else{
            file << groupQuantity[j];
        }
        
        count = 0;
        for (int i = 0; i < clusteringSOM->groups.size(); i++) {
            
            if(clusteringSOM->groups[i] == clusteringSOM->groupLabels[j] - 1){
                count++;
                if(groupQuantity[j] > 1){
                    if(count < groupQuantity[j])
                       file << i << " ";
                    else
                       file << i ;
                }else{
                    file << i ;
                }
                    
            }
        }
        file << endl;
    }    
    
}*/

int runMatFile(SOM<DSNode> *dssom, string dirname, LARFDSSOM *somDef, int epocs, bool filterNoise, bool random, float minwd, float age_wins) {
      
    double totalAccuracy = 0.0;
    int count = 0;
    
    DIR *dir;
    struct dirent *ent;
    if ((dir = opendir (dirname.c_str())) != NULL) {
      /* print all the files and directories within directory */
      while ((ent = readdir (dir)) != NULL) {
          
        string str = ent->d_name;  
        string filename = dirname + "/" + str;    
        
        ClusteringMeshSOM *clusteringSOM = new ClusteringMeshSOM(dssom);
    
        if (clusteringSOM->readFileMat(filename) && filename.find(".mat") != std::string::npos){
            
            printf ("%s\n", filename.c_str());
            
            somDef->groundTruthNodes = clusteringSOM->groupLabels.size();
            clusteringSOM->setIsSubspaceClustering(false);
            clusteringSOM->setFilterNoise(filterNoise);
            somDef->minwd = minwd*sqrt(clusteringSOM->getInputSize());
            somDef->age_wins = round(age_wins*clusteringSOM->getNumSamples());
           
            ArffData::rescaleCols01(*clusteringSOM->trainingData);
            //ArffData::rescaleColsSparse(*clusteringSOM->trainingData);
            //clusteringSOM->printDataByGroup(filename + ".ones");
            clusteringSOM->printData(filename + ".data");  
            somDef->reset(clusteringSOM->getInputSize());
            //printTruth(tokens[0] + ".true", clusteringSOM);
            clusteringSOM->trainSOM(epocs*clusteringSOM->getNumSamples(), random);
            //clusteringSOM->trainSOM(epocs);
            //clusteringSOM.trainSOM(3000);
            //somDef.finishMapFixed(filename);
            //somDef.finishMap(epocs*clusteringSOM->getNumSamples());
            //somDef.printDistanceMetricBtwCentroids("MOVIES.distanceBtwCentroids");
            //clusteringSOM->som->outAccuracy();
           
            
            clusteringSOM->writeClusterResults(filename + ".results", somDef->globalDimensionRelevances); 
            totalAccuracy += clusteringSOM->som->outAccuracy();
            count++;
            
            DataDisplay dataDisplay(clusteringSOM->trainingData);
            //dataDisplay.setGitter(GITTER);
            //dataDisplay.setPadding(PADDING);
            dataDisplay.display(*clusteringSOM->som);
            
            int i = 0;
            
//            matrix<int> cost(clusteringSOM->groupLabels.size(),clusteringSOM->groupLabels.size());
//            int counter;
//
//            for (int l = 0; l < clusteringSOM->groupLabels.size(); l++) { 
//
//                for (int k = 0; k < clusteringSOM->groupLabels.size(); k++) {
//
//                    counter = 0;
//                    for (int j = 0; j < clusteringSOM->groups.size(); j++) {
//                        if(clusteringSOM->groupLabels[l] == clusteringSOM->groupLabels[clusteringSOM->groups[j]]){
//                            if(k  == clusteringSOM->winn[j]){
//                                counter++;
//                            }
//                        }
//                    } 
//                    cost(l,k) = counter;
//                    fprintf(stderr, " class %d and class %d equals to %d \n", clusteringSOM->groupLabels[l], k,  cost(l,k));
//                }                                   
//            }
//
//            // To find out the best assignment of people to jobs we just need to call this function.
//            std::vector<long> assignment = max_cost_assignment(cost);
//            // This prints optimal assignments:  [2, 0, 1] which indicates that we should assign
//            // the person from the first row of the cost matrix to job 2, the middle row person to
//            // job 0, and the bottom row person to job 1.
//            for (unsigned int i = 0; i < assignment.size(); i++)
//                cout << assignment[i] << std::endl;
//
//            std::vector<int> newArray;
//
//            for(int x=0; x < clusteringSOM->winn.size() ;x++)
//            {
//                newArray.push_back(-99999);
//            }
//
//            //printf(" size =  %d %d  \n", clusteringSOM->groups.size(), clusteringSOM->groupLabels.size());
//
//            for (int l = 0; l < clusteringSOM->groupLabels.size(); l++) { 
//
//                for(int x=0; x < clusteringSOM->groups.size() ;x++)
//                {
//                    //printf("  %d = %d | %d  \n", clusteringSOM->groups[x], (int)assignment[l], clusteringSOM->groupLabels[l]);
//                    if(clusteringSOM->groupLabels[clusteringSOM->groups[x]] == clusteringSOM->groupLabels[l]){ 
//                        //printf("  %d = %d | %d  \n", clusteringSOM->groups[x], (int)assignment[l], clusteringSOM->groupLabels[l]-1);
//                        newArray[x] = assignment[l];
//                    }
//                }
//            }
//
//            std::map<int, int> accPerGroup;
//
//            int hits = 0;
//            for(int x=0; x < clusteringSOM->winn.size() ;x++)
//            {
//                //printf("%d  %d  %d \n", clusteringSOM->winn[x], newArray[x], clusteringSOM->groups[x]);
//                if(clusteringSOM->winn[x] == newArray[x])
//                {
//                    accPerGroup[newArray[x]]++; 
//                    hits++;
//                }          
//            }
//
//            fprintf(stderr, " Temp Acc: %f \n", (double)hits/clusteringSOM->winn.size());
//            //if((double)hits/clusteringSOM->winn.size() > totalAccuracy)
//                //totalAccuracy = (double)hits/clusteringSOM->winn.size();
//            totalAccuracy += (double)hits/clusteringSOM->winn.size();

            
        }
        //fprintf(stderr, " Temp Acc: %f \n", (double)hits/clusteringSOM->winn.size());
      
        //fprintf(stderr, " Acc: %f \n", totalAccuracy);
        clusteringSOM->cleanUpTrainingData();
      }
      
      fprintf(stderr, " Acc: %f \n", totalAccuracy/count);
      closedir (dir);
    } else {
      /* could not open directory */
      perror ("");
      return EXIT_FAILURE;
    }
    
}

int runArffFile(SOM<DSNode> *dssom, string filename, LARFDSSOM *somDef, int epocs, bool filterNoise, bool random, int total, float minwd, float age_wins) {
    double totalAccuracy = 0.0;
    int count = 0;

    while(count < total){
        
        ClusteringMeshSOM *clusteringSOM = new ClusteringMeshSOM(dssom);

        if (clusteringSOM->readFile(filename)){

            somDef->groundTruthNodes = clusteringSOM->groupLabels.size();
            clusteringSOM->setIsSubspaceClustering(false);
            clusteringSOM->setFilterNoise(filterNoise);
            somDef->minwd = minwd*sqrt(clusteringSOM->getInputSize());
            somDef->age_wins = round(age_wins*clusteringSOM->getNumSamples());


            //ArffData::rescaleColsSparse(*clusteringSOM->trainingData);
            //clusteringSOM->printDataByGroup(filename + ".ones");
            clusteringSOM->printData(filename + ".data");  
            somDef->reset(clusteringSOM->getInputSize());

            //printTruth(tokens[0] + ".true", clusteringSOM);

            clusteringSOM->trainSOM(epocs*clusteringSOM->getNumSamples(), random);
            //clusteringSOM->trainSOM(epocs);

            //clusteringSOM.trainSOM(3000);
            //somDef->finishMapFixed(filename);
            //somDef.finishMap(epocs*clusteringSOM->getNumSamples());

            //somDef.printDistanceMetricBtwCentroids("MOVIES.distanceBtwCentroids");
            clusteringSOM->som->outAccuracy();

            /*DataDisplay dataDisplay(clusteringSOM->trainingData);
            dataDisplay.setGitter(550);
            dataDisplay.setPadding(0);
            dataDisplay.display(*clusteringSOM->som);    */        

            clusteringSOM->writeClusterResults(filename + ".results", somDef->globalDimensionRelevances);

            totalAccuracy += clusteringSOM->som->outAccuracy();
        }
        
        count++;
    }

    fprintf(stderr, " Acc: %f \n", totalAccuracy/count);
}

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
        
    dbgOut(1) << "Running LARFDSSOM for file: " << filename << endl;

    //srand(1);
    srand(time(NULL));

    
    for(int i = 0 ; i < 1 ; i++){
        LARFDSSOM somDef(1);
        somDef.maxNodeNumber = som.maxNodeNumber;
        somDef.e_b = som.e_b;
        somDef.e_n = som.e_n;
        somDef.e_b0 = som.e_b;
        somDef.e_n0 = som.e_n;
        somDef.dsbeta = som.dsbeta;
        somDef.epsilon_ds = som.epsilon_ds;

        somDef.minwd = som.minwd;
        somDef.age_wins = som.age_wins;
        somDef.lp = som.lp;
        somDef.a_t = som.a_t;
        SOM<DSNode> *dssom = (SOM<DSNode>*)&somDef;
        
        switch (i){
            case 0:                
//                somDef.e_b = 0.5;
//                somDef.e_n = 0.5;
//                somDef.dsbeta = 0.9;
//                somDef.epsilon_ds = 0.5;
//                somDef.minwd = 0.9;
//                somDef.lp = 0.5;
//                somDef.a_t = 0.96;
                break;
            case 1:
//                somDef.e_b = 0.1;
//                somDef.e_n = 0.1;
//                somDef.dsbeta = 0.8;
//                somDef.epsilon_ds = 0.1;
//                somDef.minwd = 0.8;
//                somDef.lp = 0.1;
//                somDef.a_t = 0.965;
                break;
            case 2:
//                somDef.e_b = 0.05;
//                somDef.e_n = 0.05;
//                somDef.dsbeta = 0.6;
//                somDef.epsilon_ds = 0.05;
//                somDef.minwd = 0.6;
//                somDef.lp = 0.05;
//                somDef.a_t = 0.97;
                break;
            case 3:
//                somDef.e_b = 0.01;
//                somDef.e_n = 0.01;
//                somDef.dsbeta = 0.5;
//                somDef.epsilon_ds = 0.01;
//                somDef.minwd = 0.5;
//                somDef.lp = 0.01;
//                somDef.a_t = 0.975;
                break;
            case 4:
//                somDef.e_b = 0.005;
//                somDef.e_n = 0.005;
//                somDef.dsbeta = 0.4;
//                somDef.epsilon_ds = 0.005;
//                somDef.minwd = 0.4;
//                somDef.lp = 0.005;
//                somDef.a_t = 0.98;
                break;
            case 5:
//                somDef.e_b = 0.001;
//                somDef.e_n = 0.001;
//                somDef.dsbeta = 0.3;
//                somDef.epsilon_ds = 0.001;
//                somDef.minwd = 0.3;
//                somDef.lp = 0.001;
//                somDef.a_t = 0.985;
                break;
            case 6:
//                somDef.e_b = 0.0005;
//                somDef.e_n = 0.0005;
//                somDef.dsbeta = 0.2;
//                somDef.epsilon_ds = 0.0005;
//                somDef.minwd = 0.2;
//                somDef.lp = 0.0005;
//                somDef.a_t = 0.99;
                break;
            case 7:
//                somDef.e_b = 0.0001;
//                somDef.e_n = 0.0001;
//                somDef.dsbeta = 0.1;
//                somDef.epsilon_ds = 0.0001;
//                somDef.minwd = 0.1;
//                somDef.lp = 0.0001;
//                somDef.a_t = 0.995;
                break;
        
        }
        
        
        if(matFile)
            runMatFile(dssom, filename, &somDef, epocs, filterNoise, true, somDef.minwd, somDef.age_wins);
        else
            runArffFile(dssom, filename, &somDef, epocs, filterNoise, false, 1, somDef.minwd, somDef.age_wins);
   
    }
    
    return 0;
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