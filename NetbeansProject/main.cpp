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
#include "ClusteringMetrics.h"
#include "ArffData.h"
#include "ClusteringSOM.h"
#include "LARFDSSOM.h"

using namespace std;

int main(int argc, char** argv) {

    int seed = 0;
    dbgThreshold(1);

    LARFDSSOM som(1);
    
    som.maxNodeNumber = 20;
    som.e_b = 0.0005;
    som.e_n = 0.000001;
    //
    som.dsbeta = 0.1;
    som.epsilon_ds = 0.001;    
    som.minwd = 0.5;
    som.age_wins = 10;
    som.lp       = 0.06;//0.175;
    som.a_t = 0.975; //Limiar de atividade .95
    int epocs = 100;
    bool isSubspaceClustering = true;
    bool filterNoise = true;
    string filename = "";

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
                isSubspaceClustering = false;
                break;
            case 'r':
                seed = atoi(optarg);
                break;  
            case 'o':
                filterNoise = false;
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
    
    srand(seed);
    SOM<DSNode> *dssom = (SOM<DSNode>*)&som;
    ClusteringMeshSOM clusteringSOM(dssom);
    
    if (clusteringSOM.readFile(filename)){
        
        clusteringSOM.setIsSubspaceClustering(isSubspaceClustering);
        clusteringSOM.setFilterNoise(filterNoise);
        som.minwd = som.minwd*sqrt(clusteringSOM.getInputSize());
        som.age_wins = round(som.age_wins*clusteringSOM.getNumSamples());
        som.reset(clusteringSOM.getInputSize());
        clusteringSOM.trainSOM(epocs*clusteringSOM.getNumSamples());
        som.finishMapFixed();
        clusteringSOM.writeClusterResults(filename + ".results");
    }
}
