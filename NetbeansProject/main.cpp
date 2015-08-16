/* 
 * File:   main.cpp
 * Author: hans and victor
 *
 * Created on 11 de Outubro de 2010, 07:25
 */

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

using namespace std;

int main(int argc, char** argv) {

    srand(stime(0));
    dbgThreshold(1);

    LARFDSSOM som(1);
    //Default parameters
    som.a_t = 0.90; //Limiar de atividade .95
    som.d_max = 100;
    som.epsilon = 0.9;
    som.ho_f = 0.10;
    som.maxNodeNumber = 30;
    //DS
    som.dsbeta = 0.001;
    som.epsilon_ds = 0.0;
    
    int epocs = 10000;
    
    string filename = "";

    int c;
    while ((c = getopt(argc, argv, "f:n:t:a:d:e:h:m:s:p:")) != -1)
        switch (c) {            
            case 'f':
                filename.assign(optarg);
                break;
            case 'n':
            case 't':
                epocs = atoi(optarg);
                break;
            case 'a':
                som.a_t = atof(optarg);
                break;
            case 'd':
                som.d_max = atoi(optarg);
                break;
            case 'e':                
                som.epsilon = atof(optarg);
                break;
            case 'h':
                som.ho_f = atof(optarg);
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
    srand(time(0));
    
    SOM<DSNode> *dssom = (SOM<DSNode>*)&som;
    ClusteringMeshSOM clusteringSOM(dssom);
    
    if (clusteringSOM.readFile(filename)){
        som.reset(clusteringSOM.getInputSize());
        clusteringSOM.trainSOM(epocs);
        clusteringSOM.writeClusterResults(filename + ".results");
        clusteringSOM.outConfusionMatrix(clusteringSOM.groups, clusteringSOM.groupLabels);
        dbgOut(1) << clusteringSOM.outClassInfo() << endl;
        dbgOut(1) << "Done." << endl;
    }
    //DataDisplay dataDisplay(clusteringSOM.trainingData);
    //dataDisplay.display(clusteringSOM.som[0]);
    //getchar();
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