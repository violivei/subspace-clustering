/*
 * LARFDSSOM.h
 *
 *  Created on: 2014
 *      Author: hans
 */

#ifndef LARFDSSOM_H_
#define LARFDSSOM_H_

#include <set>
#include <map>
#include <vector>
#include "Mesh.h"
#include "MatUtils.h"
#include "MatVector.h"
#include "MatMatrix.h"
#include "SOM.h"
#include "DSNode.h"
#include <math.h>

#define qrt(x) ((x)*(x))

using namespace std;


struct by_second
{
    template <typename Pair>
    bool operator()(const Pair& a, const Pair& b)
    {
        return a.second < b.second;
    }
};


template <typename Fwd>
typename std::map<typename std::iterator_traits<Fwd>::value_type, int>::value_type
most_frequent_element(Fwd begin, Fwd end)
{
    std::map<typename std::iterator_traits<Fwd>::value_type, int> count;

    for (Fwd it = begin; it != end; ++it)
        ++count[*it];

    return *std::max_element(count.begin(), count.end(), by_second());
}

/**
*	compares two vector lengths
*/
template<class T>
void comp(vector<T> v1, vector<T> v2){
	if(v1.size() != v2.size()){
		cout << "vectors not the same size\n";
		exit(1);
	}
}

template<class T>
double HammingDist(vector<T> v1, vector<T> v2){
    double dist = 0;
	for (unsigned int i=0; i < v1.size(); i++){
		 if(v1[i] == v2[i])
                    dist++;
	}
    dist /= v1.size();
    return dist;
}

/**
*	Euclidean distance between two vectors of type T such that T has binary +,-,* 
*/
template<class T>
double euclidean(vector<T> v1, vector<T> v2){
	comp(v1, v2);
	T diff, sum;

	diff = v1[0] - v2[0];
	sum = diff * diff;

	for (unsigned int i=1; i < v1.size(); i++){
		diff = v1[i] - v2[i];
		sum += diff * diff;
	}
	return sqrt(double(sum));
}

/**
*	Jaccard Coefficient.	Use for asymetric binary values
*/
template<class T>
double jaccard(vector<T> v1, vector<T> v2){
	comp(v1, v2);
	int f11 = 0, f00 = 0;

	for (unsigned int i=0; i < v1.size(); i++){
		if(v1[i] == v2[i]){
			if(v1[i])
				f11++;
			else
				f00++;
		}
	}
	return double(f11) / double(v1.size() - (f11+f00));
}

/**
*	Cosine
*/
template<class T>
double cosine(vector<T> v1, vector<T> v2){
	comp(v1, v2);

	T lv1 = v1[0] * v1[0];
	T lv2 = v2[0] * v2[0];
	T dot12 = v1[0] * v2[0];

	for (unsigned int i=0; i < v1.size(); i++){
		lv1 += v1[i] * v1[i];
		lv2 += v2[i] * v2[i];
		dot12 += v1[i] * v2[i];
	}
	return double(dot12) / ( sqrt(double(lv1)) * sqrt(double(lv2)) );
}

double cosine_similarity(vector<float> A, vector<float> B, unsigned int Vector_Length)
{
    double dot = 0.0, denom_a = 0.0, denom_b = 0.0 ;
     for(unsigned int i = 0u; i < Vector_Length; ++i) {
        dot += A[i] * B[i] ;
        denom_a += A[i] * A[i] ;
        denom_b += B[i] * B[i] ;
    }
    return dot / (sqrt(denom_a) * sqrt(denom_b + 0.00001)) ;
}

/**
*	The mean of a vector
*/
template<class T>
double mean(vector<T> v1){
	T sum = v1[0];
	for (unsigned int i=1; i < v1.size(); i++)
		sum += v1[i];
	return double(sum) / double(v1.size());
}

/**
*	The Covariance
*/
template<class T>
double covariance(vector<T> v1, vector<T> v2){
	comp(v1, v2);
	double mean1 = mean(v1), mean2 = mean(v2);
	double sum = (double(v1[0]) - mean1) * (double(v2[0]) - mean2);
	for (unsigned int i=1; i < v1.size(); i++){
		sum += (double(v1[i]) - mean1) * (double(v2[i]) - mean2);
	}
	return double(sum) / double(v1.size()-1);
}

/**
*	standard deviation the covariance where both vectors are the same.
*/
template<class T>
double std_dev(vector<T> v1){
	return sqrt(covariance(v1, v1));
}

/**
*	Pearson Correlation
*/
template<class T>
double pearson(vector<T> v1, vector<T> v2){
	if (std_dev(v1) * std_dev(v2) == 0){
		cout << "( a standard deviaton was 0 )";
		return -2; // I dont know what to do here???
	}
	return covariance(v1,v2) / ( std_dev(v1) * std_dev(v2));
}  
    
class sort_map
{
  public:
        int key;
        int val;
};

bool Sort_by(const sort_map& a ,const sort_map& b)
{
        return a.val < b.val;
}

class GDSNodeMW;

class GDSConnectionMW : public Connection<GDSNodeMW> {
public:
    int age;

    GDSConnectionMW(TNode *node0, TNode *node1) : Connection<GDSNodeMW>(node0, node1), age(0) {
    }
};

class GDSNodeMW : public DSNode {
public:

    typedef GDSConnectionMW TConnection;
    typedef std::map<GDSNodeMW*, TConnection*> TPNodeConnectionMap; //Para mapeamento local dos n�s e conex�es ligadas a this

    int wins;
    int nodeUpdates;
    TPNodeConnectionMap nodeMap;
    TNumber act;

    inline int neighbors() {
        return nodeMap.size();
    }

    GDSNodeMW(int idIn, const TVector &v) : DSNode(idIn, v), wins(0), act(0) {
    };

    ~GDSNodeMW() {
    };
};

class LARFDSSOM : public SOM<GDSNodeMW> {
public:
    uint maxNodeNumber;
    float minwd;
    float e_b;
    float e_b0;
    float e_n;
    float e_n0;
    int nodesCounter;
    MatVector<float> globalDimensionRelevances;

    TNumber dsbeta; //Taxa de aprendizagem
    TNumber epsilon_ds; //Taxa de aprendizagem
    float age_wins;       //period to remove nodes
    float lp;           //remove percentage threshold
    float a_t;

    int nodesLeft;
    int nodeID;
    int groundTruthNodes;
    bool print = false;

    inline float activation(const TNode &node, const TVector &w) {  
        
        float distance = 0;
        
        for (uint i = 0; i < w.size(); i++) {
            distance += node.ds[i] * qrt((w[i] - (node.w[i])));
        }
        
        float sum = node.ds.sum();
        
        return (sum / (sum + distance + 0.0000001));
    }

    inline float getWinnerActivation(const TVector &w) {
        TNode* winner = getWinner(w);        
        float a = activation(*winner, w);
        dbgOut(1) << winner->getId() << "\t" << a << endl;
        return a;
    }
    
    inline float dist2(const TNode &node, const TVector &w) {
        /*float distance = 0;

        for (uint i = 0; i < w.size(); i++) {
            distance += node.ds[i] * qrt((w[i] - node.w[i]));
        }

        return distance / (node.ds.sum() + 0.0000001);*/
        return 1/activation(node, w);
    }

    inline float dist(const TNode &node, const TVector &w) {
        return sqrt(dist2(node, w));
    }
    
    inline float wdist(const TNode &node1, const TNode &node2) {
        float distance = 0;

        for (uint i = 0; i < node1.ds.size(); i++) {
            distance +=  qrt((node1.ds[i] - node2.ds[i]));
        }
        
        return sqrt(distance);
    }

    inline void updateNode(TNode &node, const TVector &w, TNumber e, bool neighbour, int cla) {
        
        TVector averageVector;
        averageVector.size(w.size());
        averageVector.fill(0);
        
        TVector Teste;
        Teste.size(w.size());
        Teste.fill(0);
        
        //update averages
        for (uint i = 0; i < node.a.size(); i++) {
            //update neuron weights
            float distance = fabs(w[i] - node.w[i]);
            //node.a[i] = e*dsbeta* distance + (1 - e*dsbeta) * node.a[i];
            
            node.a[i] = e*dsbeta* distance + (1 - e*dsbeta) * node.a[i];
            node.mean[i] += node.a[i];
            
            averageVector[i] =  node.mean[i]/(node.nodeUpdates + 1);
            node.max[i] = node.a[i] > node.max[i] ? node.a[i] : node.max[i];
            node.min[i] = node.a[i] < node.min[i] ? node.a[i] : node.min[i];
        }
        
        /*for (uint i = 0; i < averageVector.size(); i++) {
            dbgOut(1) << averageVector[i];
        }
        dbgOut(1) << endl << endl;;
        for (uint i = 0; i < averageVector.size(); i++) {
            dbgOut(1) << node.max[i];
        }
        dbgOut(1) << endl << endl;;
        for (uint i = 0; i < averageVector.size(); i++) {
            dbgOut(1) << node.min[i];
        }
        dbgOut(1) << endl << endl;;
        for (uint i = 0; i < averageVector.size(); i++) {
            dbgOut(1) << node.mean[i];
        }
        dbgOut(1) << endl << endl;*/
        
        std::ofstream file;
        file.open("MOVIES.depuracao", std::ios::app);
        
        float max = node.a.max();
        float min = node.a.min();
        float average = node.a.mean();
        
        //update neuron ds weights
        for (uint i = 0; i < node.a.size(); i++) {
            
            /*if ((max - min) != 0) { 
                Teste[i] = 1/(1+exp(pow((node.a[i]-average),2)/((max - min)*epsilon_ds)) - 1);
            }else{
                Teste[i] = 1;
            }
            
            if ((max - min) != 0) { 
                node.ds[i] = 1/(1+exp(pow((node.a[i]-average),2)/((max - min)*epsilon_ds)) - 1);
            }else{
                node.ds[i] = 1;
            }*/
            
            if ((node.max[i] - node.min[i]) != 0) { 
            
                float a = exp(pow((node.a[i]-averageVector[i]),2)/((node.max[i] - node.min[i])*epsilon_ds));
                float b = 1/(1+exp(pow((node.a[i]-averageVector[i]),2)/((node.max[i] - node.min[i])*epsilon_ds)) - 1);
                float c =averageVector[i];
                float d =node.max[i];
                float e =node.min[i];
                float f = node.a[i];
                node.ds[i] = 1/(1+exp(pow((node.a[i]-averageVector[i]),2)/((node.max[i] - node.min[i])*epsilon_ds)) - 1);
            }
            else {
                node.ds[i] = 1;
            }
        }
        
        TVector a(w.size());
        
        /*if(node.getId() == 7){
            
            for (uint i = 0; i < node.a.size(); i++) {
                file << node.a[i] << " ";
            }
            file << endl << endl;;
            for (uint i = 0; i < averageVector.size(); i++) {
                file << node.max[i] << " ";
            }
            file << endl << endl;;
            for (uint i = 0; i < averageVector.size(); i++) {
                file << node.mean[i] << " ";
            }
            file << endl << endl;;
            for (uint i = 0; i < averageVector.size(); i++) {
                file << averageVector[i] << " ";
            }
            file << endl << endl;
            for (uint i = 0; i < node.ds.size(); i++) {
                file << node.ds[i] << " ";
            }
            file << endl << endl;
            
            file << "MEDIA : " << average << " MAX : " << max << " MIN : " << min << " nodeUpdates: " << node.nodeUpdates << " CLASS: " << cla << " neighbour: " << neighbour << endl;
            for (uint i = 0; i < Teste.size(); i++) {
                file << Teste[i] << " ";
            }
            file << endl << endl;
            
        }*/

        /*if(!neighbour){
            int size = node.previousNodes->rows();
            node.w.fill(0, node.w.size());
            for (uint i = 0; i < node.previousNodes->cols(); i++) {

                for (uint j = 0; j < node.previousNodes->rows(); j++) {
                   node.w[i] += node.previousNodes->get(j,i);
                }
                node.w[i] = node.w[i]/node.previousNodes->rows();
            }
            
        }else{
            for (uint i = 0; i < node.w.size(); i++) {
                //node.w[i] = node.w[i] + e *(w[i] - node.w[i]);
            }
        }*/

        node.w = node.w + e * (w - node.w);
                
        if(node.getId() == 7 && print){
           file << "PESOS | ITERACAO: " << step << " MEDIA: " << average << " MAX: " << max << " MIN: " << min << " DSBETA: " << dsbeta << " EPSILONDS: " << epsilon_ds << " LEARNING_RATE: " << e << endl;
           file << "CLASS: " << cla << endl;
           
           for (uint i = 0; i < node.a.size(); i++) {
                file << node.a[i] << " ";
            }
           file << endl;           
           file << "RELEVANCIA | ITERACAO: " << step << endl;
            
           for (uint i = 0; i < node.ds.size(); i++) {
                file << node.ds[i] << " ";
            }
           file << endl;
           file << "CENTROIDE | ITERACAO: " << step << endl;
            
           for (uint i = 0; i < node.w.size(); i++) {
                file << node.w[i] << " ";
            }
           file << endl;
           file << "PADRAO: " << endl;
            
            for (uint i = 0; i < w.size(); i++) {
             file << w[i] << " ";
            }
           file << endl;
        }

        
    }

    LARFDSSOM& updateConnections(TNode *node) {
        
        TPNodeSet::iterator itMesh = meshNodeSet.begin();
            
        while (itMesh != meshNodeSet.end()) {
            if (*itMesh != node) {
                float dist = wdist(*node, *(*itMesh));
                if (dist<minwd) {
                    if (!isConnected(node, *itMesh))
                    connect(node, *itMesh);
                } else {
                    if (isConnected(node, *itMesh))
                        disconnect(node, *itMesh);
                }
            }
            itMesh++;
        }
        return *this;
    }
    
    LARFDSSOM& updateAllConnections() {

        //Conecta todos os nodos semelhantes
        TPNodeSet::iterator itMesh1 = meshNodeSet.begin();
        while (itMesh1 != meshNodeSet.end()) {
            TPNodeSet::iterator itMesh2 = meshNodeSet.begin();
            
            while (itMesh2 != meshNodeSet.end()) {
                if (*itMesh1!= *itMesh2) {
                    float dist = wdist(*(*itMesh1), *(*itMesh2));
                    if (dist<minwd) {
                        if (!isConnected(*itMesh1, *itMesh2))
                        connect(*itMesh1, *itMesh2);
                    } else {
                        if (isConnected(*itMesh1, *itMesh2))
                            disconnect(*itMesh1, *itMesh2);
                    }
                }
                itMesh2++;
            }
            
            itMesh1++;
        }

        return *this;
    }
    
    LARFDSSOM& labelClusters() {
         
        TPNodeSet::iterator it;
        for (it = Mesh<TNode>::meshNodeSet.begin(); it != Mesh<TNode>::meshNodeSet.end(); it++) {
                std::pair<int, int> x = most_frequent_element((*it)->classes->begin(), (*it)->classes->end());
                (*it)->setId(x.first);   
        }

        return *this;
    }

    LARFDSSOM& removeLoosers() {

        //outAccuracy();
        
        //dbgOut(1) << "CORTE: " << age_wins*lp << endl;
        
        TPNodeSet::iterator itMesh = meshNodeSet.begin();
        while (itMesh != meshNodeSet.end()) {
            if (meshNodeSet.size()<2)
                break;
            
            if ((*itMesh)->wins < age_wins*lp) {//maxcomp*lp
                eraseNode((*itMesh));
                itMesh = meshNodeSet.begin();
            } else {
                itMesh++;
            }
        }
        
        TPNodeSet::iterator it;
        for (it = Mesh<TNode>::meshNodeSet.begin(); it != Mesh<TNode>::meshNodeSet.end(); it++) {
                
                std::pair<int, int> x = most_frequent_element((*it)->classes->begin(), (*it)->classes->end());
                (*it)->setId(x.first);  
                (*it)->classes = new std::vector<int>();
                (*it)->previousNodes = new MatMatrix<float>();
        }
        //printWinners();       
        //outAccuracy();
        return *this;
    }
    
    LARFDSSOM& resetNodeVectors() {

        dbgOut(1) << "RESET" << endl;
        
        TPNodeSet::iterator itMesh = meshNodeSet.begin();
        while (itMesh != meshNodeSet.end()) {          
            //(*itMesh)->a.clear();
            (*itMesh)->a.fill(0);
            //(*itMesh)->ds.clear();
            (*itMesh)->ds.fill(1);
            itMesh++;            
        }

        return *this;
    }

    int get_second( pair<string, int> i ){ return i.second; }
    
    LARFDSSOM& removeLoosers2() {

        enumerateNodes();
        //printWinners();
        
        map<int,int> d;
	map<int,int>::iterator it;
	vector< sort_map > v;
	vector< sort_map >::iterator itv;
	sort_map sm;
        
        TPNodeSet::iterator itMesh = meshNodeSet.begin();
        while (itMesh != meshNodeSet.end()) {
            d[(*itMesh)->getId()] = (*itMesh)->wins;
            itMesh++;
        }
        
	for (it = d.begin(); it != d.end(); ++it)
	{
		sm.key = (*it).first; sm.val = (*it).second;
		v.push_back(sm);
	}
	for (itv = v.begin(); itv != v.end(); ++itv)
	{
		//cout << (*itv).key << " : " << (*itv).val << endl;
	}
	
	sort(v.begin(),v.end(),Sort_by);
	
	//cout << "sorted" << endl;
	for (itv = v.begin(); itv != v.end(); ++itv)
	{
		//cout << (*itv).key << " : " << (*itv).val << endl;
	}
        
        int cuts = groundTruthNodes;
        if(meshNodeSet.size() > groundTruthNodes)
        {
            for (itv = v.begin(); itv != v.end(); ++itv)
            {
                itMesh = meshNodeSet.begin();                    
                while (itMesh != meshNodeSet.end()) 
                {
                    if((*itMesh)->getId() == (*itv).key)
                    {
                        eraseNode((*itMesh));
                        break;
                    }
                    itMesh++;
                }
                if(meshNodeSet.size() == cuts){
                    break;
                }
            }
        }

        printWinners();        
        
        return *this;
    }

    //*
    LARFDSSOM& finishMap(int epocs) {
        
        dbgOut(1) << "Finishing map..." << endl;    
                
        age_wins = round(epocs);
                
        while (true){
            e_n = e_n0;
            e_b = e_b0;            
                                 
            int elm = 0;
            for (int i=0; i < epocs; i++) {
                trainningStep();
                elm++;
                if( i % data.rows() == 0){
                   elm = 0;
                }
            }

            int prefMeshSize = meshNodeSet.size();
            maxNodeNumber = meshNodeSet.size(); 
            removeLoosers();
            resetWins();
            updateAllConnections();
            dbgOut(1) << "Finishing: " << prefMeshSize << "\t->\t" << meshNodeSet.size() << endl;

            if (maxNodeNumber == meshNodeSet.size() || meshNodeSet.size()==1)
                break;
        }
        
        return *this;
    }
    /**/
    
    LARFDSSOM& finishMapFixed(std::string &filename) {
        e_n = e_n0;
        e_b = e_b0;
        dbgOut(1) << "Finishing map with: " << meshNodeSet.size() << endl;
        while (step!=1) { // finish the previous iteration
            trainningStep();
            if( step % data.rows() == 0){                 
                //dataDisplay.display(*som);
                //printMeshFile(filename + ".mesh", step);
             }
        }
        maxNodeNumber = meshNodeSet.size(); //fix mesh max size
        
        dbgOut(1) << "Finishing map with: " << meshNodeSet.size() << endl;
        
        e_n = e_n0;
        e_b = e_b0;
        trainningStep();//step equal to 2
        while (step!=1) {
            trainningStep();
             if( step % data.rows() == 0){                 
                //dataDisplay.display(*som);
                //printMeshFile(filename + ".mesh", step);
             }
        }
        
        dbgOut(1) << "Finishing map with: " << meshNodeSet.size() << endl;
        printWinners();
        return *this;
    }
    
    void printDistanceMetricBtwCentroids(const std::string filename) {
        std::ofstream file;
        file.open(filename.c_str(), std::ios::app);

        if (!file.is_open()) {
            dbgOut(0) << "Error openning output file" << endl;
        }
        
        TPNodeSet::iterator it;
        TPNodeSet::iterator it2;
        for (it = Mesh<TNode>::meshNodeSet.begin(); it != Mesh<TNode>::meshNodeSet.end(); it++) {
            
            for (it2 = Mesh<TNode>::meshNodeSet.begin(); it2 != Mesh<TNode>::meshNodeSet.end(); it2++) {
                std::vector<TNumber> a((*it)->w.size());
                std::vector<TNumber> b((*it2)->w.size());                
                for (uint j = 0; j < (*it)->w.size(); j++) {
                    a[j] = (*it)->w[j];
                    b[j] = (*it2)->w[j];
                }          
                
                file << euclidean(a, b) << " " ;
                //file << cosine_similarity(a, b, b.size()) << " " ;
                a.clear();
                b.clear();
                
            }
            file << std::endl;
        }
    }
    
    void printEucMeanNodes(const std::string filename) {
         std::ofstream file;
        file.open(filename.c_str(), std::ios::app);

        if (!file.is_open()) {
            dbgOut(0) << "Error openning output file" << endl;
        }
        
        int i=1;
        TPNodeSet::iterator it;
        for (it = Mesh<TNode>::meshNodeSet.begin(); it != Mesh<TNode>::meshNodeSet.end(); it++) {
            TVector dist((*it)->previousNodes->size());
            file << i << "SIZE:" << (*it)->previousNodes->size() << std::endl;
            
            for (uint i = 0; i < (*it)->previousNodes->size(); i++) {
                std::vector<TNumber> a((*it)->w.size());
                std::vector<TNumber> b((*it)->w.size());                
                for (uint j = 0; j < (*it)->w.size(); j++) {
                    a[j] = (*it)->w[j];
                    b[j] = (*it)->previousNodes->get(i,j);
                }                
                dist[i] = euclidean(a, b); 
                a.clear();
                b.clear();
            }

            (*it)->maxEucDist = dist.max();
            (*it)->minEucDist = dist.min();
            (*it)->meanEucDist = dist.mean();
            
            file << i << "MAX:" << (*it)->maxEucDist << std::endl;
            file << i << "MIN:" << (*it)->minEucDist << std::endl;
            file << i << "MEAN:" << (*it)->meanEucDist << std::endl;
            i++;
        }
     }
    
    //Function for variance
    double variance ( TVector& v , double mean )
    {
            double sum = 0.0;
            double temp =0.0;
            double var =0.0;

            for ( int j = 0; j < v.size(); j++)
            {
                temp = pow((v[j] - mean),2);
                sum += temp;
            }

            return var = sum/(v.size()-1);
    }
    
    double calcGiniCoefficient(TVector& values)
    {
            if (values.size() < 1) return 0;  //not computable
            if (values.size() == 1) return 0;
            double relVars = 0;

            double descMean = values.mean();
            if (descMean == 0.0) return 0; // only possible if all data is zero
            for (int i = 0; i < values.size(); i++) 
            {
                    for (int j = 0; j < values.size(); j++) 
                    {
                            if (i == j) continue;
                            relVars += (abs(values[i] - values[j]));
                    }
            }
            relVars = relVars / (2.0 * values.size() * values.size());
            return (relVars / descMean); // return gini value
    }

    void getDimensionRelevancesVector() {
        
        globalDimensionRelevances.size(data.cols());
        globalDimensionRelevances.fill(1);
        
        
                
        for (int i = 0; i < data.cols() ; i++) {
            TVector relevanceVect;
            relevanceVect.size(data.cols());
            relevanceVect.fill(0);
            for (int j = 0; j < data.rows(); j++) {
                //globalDimensionRelevances[i] = globalDimensionRelevances[i] + data[j][i];
                relevanceVect[j] = data[j][i];
            }
            //globalDimensionRelevances[i] = calcGiniCoefficient(relevanceVect);
            //globalDimensionRelevances[i] = 100*variance(relevanceVect, relevanceVect.mean());
            //globalDimensionRelevances[i] = 1;
            
        }
        
        dbgOut(1) << globalDimensionRelevances.mean() << endl << endl;
        dbgOut(1) << globalDimensionRelevances.max() << endl << endl;
        dbgOut(1) << globalDimensionRelevances.min() << endl << endl;
        
        float mean = globalDimensionRelevances.mean();
        float max = globalDimensionRelevances.max();
        float min = globalDimensionRelevances.min();
        
        for (int j = 0; j < data.cols(); j++) {
            //globalDimensionRelevances[j] = (globalDimensionRelevances[j] / data.rows());
            float x = globalDimensionRelevances[j];
            
            //globalDimensionRelevances[j] = 1/(1+exp((globalDimensionRelevances[j]-mean)/((max - min))));
            
            /*if( globalDimensionRelevances[j] < mean ) {
                //globalDimensionRelevances[j] = (1/(globalDimensionRelevances[j]));
                globalDimensionRelevances[j] = 0;
            } else {
                globalDimensionRelevances[j] = (1 - 1/globalDimensionRelevances[j]);
            }*/
            //globalDimensionRelevances[j] = abs(1 - 1/globalDimensionRelevances[j]);
            
            //globalDimensionRelevances[j] = globalDimensionRelevances[j] / data.rows(); 
            
            dbgOut(1) << x << " >> " << globalDimensionRelevances[j] << endl;
        }
        
        
    }
    
    LARFDSSOM& createStartNodesRandom(std::vector<int> groups, std::vector<int> groupLabels) {
        
        
        int vindex;
        
        for (int k = 0; k < 17 ; k++) {
            
            vindex = rand()%data.rows();
            
            TVector a(data.cols());
            a.fill(0);
            
            for (int i = 0; i < data.cols(); i++) {
                a[i] = data[vindex][i];
            }
            
            TNode *nodeNew = createNode(k, a);            
            updateConnections(nodeNew);
        }
        
        return *this;
    }
    
    LARFDSSOM& createStartNodesPCA(std::vector<int> groups, std::vector<int> groupLabels) {
                
        
        
        return *this;
    }
    
    LARFDSSOM& createStartNodes(std::vector<int> groups, std::map<int, int> groupLabels) {
         
        MatVector<TVector> allNodes;
        
        for (int l = 0; l < groupLabels.size(); l++) {
            
            TVector a(data.cols());
            a.fill(0);
            
            int count = 0;
            for (int k = 0; k < groups.size(); k++) {
               
                if(groupLabels[l]== groupLabels[groups[k]]){
                                        
                    for (int m = 0; m < data.cols(); m++) {
                        a[m] += data[k][m];
                    }
                    count++;
                }
                
            }
            
            for (int m = 0; m < data.cols(); m++) {
                //a[m] = a[m] > 1 ? 1 : 0;
                a[m] = a[m]/count;
            }
            allNodes.append(a);
        }
        
        for (int l = 0; l < groupLabels.size(); l++) {
            TVector b = allNodes[l];            
            for (int m = 0; m < b.size(); m++) { 
                //printf( " %f  ", b[m]);
                
            }
            
            TNode *nodeNew = createNode(l, b);            
            updateConnections(nodeNew);
            
            //printf( " \n\n\n\n  ");
        }
        
       return *this;         
    }
    
    void printWinners() {

        TPNodeSet::iterator itMesh = meshNodeSet.begin();
        while (itMesh != meshNodeSet.end()) {
            
            int count = 0;
            for (int i=0; i<data.rows(); i++) {
                TVector row;
                data.getRow(i, row);
                TNumber a = activation(*(*itMesh), row);
                
                if (a>=a_t) {
                        //dbgOut(1) << i << " ";
                        //dbgOut(1) << (*itMesh)->getId() << "\t" << a << endl;
                        count++;
                }
            }
            //dbgOut(1) << "\t" << (*itMesh)->getId() << "\t" << (*itMesh)->wins << "\t" << count << endl;
            dbgOut(1) << "\t" << (*itMesh)->getId() << "\t" << (*itMesh)->wins << "\t" << (*itMesh)->neighbors() << endl;
            itMesh++;
        }
    }

    /*
    LARFDSSOM& finishMap() {
        resetWins();

        TVector v;
        for (int i=0; i<data.rows(); i++) {
            data.getRow(i, v);
            TNode *winner = getWinner(v);
            if (activation(*winner, v)>= a_t) {
                winner->wins++;
                //step++;
            }
        }

        step = data.rows();
        //if (step==0) step = 1;

        removeLoosers();
        updateAllConnections();

        maxNodeNumber = meshNodeSet.size();
        trainning(age_wins);
    }/**/

    LARFDSSOM& resetWins() {

        //Remove os perdedores
        TPNodeSet::iterator itMesh = meshNodeSet.begin();
        while (itMesh != meshNodeSet.end()) {
             (*itMesh)->wins = 0;
             (*itMesh)->previousNodes = new MatMatrix<float>();
             itMesh++;
        }

        step = 0;

        return *this;
    }
    
    LARFDSSOM& updateMap2(const TVector &w) {
        using namespace std;
        TNode *winner1 = 0;
        winner1 = getWinner(w);
        TNumber a = activation(*winner1, w);
        
        updateNode(*winner1, w, e_b, false, 0);
        winner1->previousNodes->concatRows(w);
        winner1->wins++;
        
        TPNodeConnectionMap::iterator it;
        
        for (it = winner1->nodeMap.begin(); it != winner1->nodeMap.end(); it++) {            
            TNode* node = it->first;                
            updateNode(*node, w, e_n, true, 0);
        }
        
        return *this;
        
    }
    
    double outAccuracy() {
      
        float hits = 0;
        float total = 0;
        
        for (int k = 0; k < data.rows(); k++) {
            MatVector<float> sample;
            data.getRow(k, sample);
            TNode *winner1 = 0;
            winner1 = getWinner(sample);
            if(winner1->getId() == groupLavelsVector[classes[k]]){
                hits++;
                //nodeHits[winner]++;
            }            
            total++;            
        }
        
        //dbgOut(1) << "ACC: " << endl;
        //dbgOut(1) << hits << endl;
        //dbgOut(1) << total << endl;
        dbgOut(1) << "ACC: " << float(hits/total) << endl;
        //dbgOut(1) << "ACC: " << 1 - float(hits/total) << endl;
        return float(hits/total);
    }

    LARFDSSOM& updateMap(const TVector &w, int cla) {

        using namespace std;
        TNode *winner1 = 0;
        winner1 = getWinner(w);
        TNumber a = activation(*winner1, w);
        
        if ((a < a_t) && (meshNodeSet.size() < maxNodeNumber)) {
            TVector wNew(w);
            TNode *nodeNew = createNode(groupLavelsVector[classes[cla]], wNew);
            //TNode *nodeNew = createNode(nodeID++, wNew);
            //TNode *nodeNew = createNode(groupLavelsVector[classes[cla]], wNew);
            nodeNew->wins = 0;//step*lp;//step/meshNodeSet.size();/step*lp//age_wins*lp
            nodeNew->nodeUpdates = 0;
            nodeNew->previousNodes->concatRows(w);
            nodeNew->classes->push_back(groupLavelsVector[classes[cla]]);
            updateConnections(nodeNew);
            //printWinners();
            std::ofstream file;
            file.open("MOVIES.depuracao", std::ios::app);

            if(nodeNew->getId() == 7 && print){
               file << "INIT PESOS | ITERACAO: " << step << endl;
               file << "CLASS: " << groupLavelsVector[classes[cla]] << endl;
               
               for (uint i = 0; i < nodeNew->a.size(); i++) {
                    file << nodeNew->a[i] << " ";
                }
               file << endl;
               file << "RELEVANCIA | ITERACAO: " << step << endl;

               for (uint i = 0; i < nodeNew->ds.size(); i++) {
                    file << nodeNew->ds[i] << " ";
                }
               file << endl;
               file << "CENTROIDE | ITERACAO: " << step << endl;

               for (uint i = 0; i < nodeNew->w.size(); i++) {
                    file << nodeNew->w[i] << " ";
                }
               file << endl;
            }
            
           
        } else if (a >= a_t) { // caso contrário || ((a < a_t) && (meshNodeSet.size() >= maxNodeNumber))
            
            winner1->previousNodes->concatRows(w);
            winner1->classes->push_back(groupLavelsVector[classes[cla]]);
            winner1->wins++;
            winner1->nodeUpdates++;
            
            updateNode(*winner1, w, e_b, false, groupLavelsVector[classes[cla]]);
            TPNodeConnectionMap::iterator it;
            //updateConnections(winner1);
            for (it = winner1->nodeMap.begin(); it != winner1->nodeMap.end(); it++) {            
                TNode* node = it->first;   
                node->nodeUpdates++;
                updateNode(*node, w, e_n, true, groupLavelsVector[classes[cla]]); 
                //updateConnections(node);
            }
            //updateAllConnections();
        }
       
        //dbgOut(1) << "CLASS: " << groupLavelsVector[classes[cla]] << " ACTIVATION: " << a << endl;
        //labelClusters();
        //printWinners();
        //Passo 9:Se atingiu age_wins (maxcomp)
        if (step >= age_wins) {

            int size = meshNodeSet.size();
            //remove os perdedores
            removeLoosers();
            dbgOut(1) << size << "\t->\t" << meshNodeSet.size() << endl;
            //reseta o número de vitórias
            resetWins();
            //Passo 8.2:Adiciona conexões entre nodos semelhantes
            updateAllConnections();
        }
       
        step++;
        return *this;
    }

    virtual TNode *getFirstWinner(const TVector &w){
        TNode *winner = 0;
        TNumber temp = 0;
        TNumber d = dist(*(*Mesh<TNode>::meshNodeSet.begin()), w);
        winner = (*Mesh<TNode>::meshNodeSet.begin());
        winner->act = activation(*winner, w);

        TPNodeSet::iterator it;
        it = Mesh<TNode>::meshNodeSet.begin();
        it++;
        for (; it != Mesh<TNode>::meshNodeSet.end(); it++) {
            (*it)->act = activation(*(*it), w);
            temp = dist(*(*it), w);
            if (d > temp) {
                d = temp;
                winner = (*it);
            }
        }

        return winner;
    }

    virtual TNode *getNextWinner(TNode *previowsWinner) {
        previowsWinner->act = 0;
        
        TNode *winner = 0;
        winner = (*Mesh<TNode>::meshNodeSet.begin());
        TNumber winnerAct = winner->act;

        TPNodeSet::iterator it;
        it = Mesh<TNode>::meshNodeSet.begin();
        it++;
        for (; it != Mesh<TNode>::meshNodeSet.end(); it++) {

            if ((*it)->act > winnerAct) {
                winnerAct = (*it)->act;
                winner = (*it);
            }
        }
        
        if (winnerAct < a_t)
            return NULL;

        return winner;
    }

    inline TNode* getWinner(const TVector &w) {
        TNode *winner = 0;
        TNumber temp = 0;
        TNumber d = dist(*(*Mesh<TNode>::meshNodeSet.begin()), w);
        winner = (*Mesh<TNode>::meshNodeSet.begin());
       
        TPNodeSet::iterator it;
        it = Mesh<TNode>::meshNodeSet.begin();
        it++;
        for (; it != Mesh<TNode>::meshNodeSet.end(); it++) {
            temp = dist(*(*it), w);
            if (d > temp) {
                d = temp;
                winner = (*it);
            }
        }

        return winner;
    }
    
    void appendSamplesToWinners() {

        TNode *winner1 = 0;
        for (int i = 0; i < data.rows(); i++) {
            MatVector<float> sample;  
            data.getRow(i, sample);
            winner1 = getWinner(sample);
            winner1->previousNodes->concatRows(sample);
        }
    }

    inline LARFDSSOM& getWinners(const TVector &w, TNode* &winner1, TNode* &winner2) {
        TPNodeSet::iterator it = Mesh<TNode>::meshNodeSet.begin();
        TNumber minDist = dist2(*(*it), w);
        
        //find first winner
        winner1 = (*it);
        for (; it != Mesh<TNode>::meshNodeSet.end(); it++) {
            TNumber dist = dist2(*(*it), w);
            if (dist<minDist) {
                minDist = dist;
                winner1 = (*it);
            }
        }
        
        //find second winner
        it = Mesh<TNode>::meshNodeSet.begin();
        winner2 = (*it);
        minDist = dist2(*(*it), w);
        TNode* distWinner = NULL;
        for (; it != Mesh<TNode>::meshNodeSet.end(); it++) {
            if (*it!=winner1) {
                TNumber dist = dist2(*(*it), w);
                if (dist<minDist) {
                    minDist = dist;
                    winner2 = (*it);
                    if (wdist(*winner1, *(*it)) <= minwd)
                        distWinner = winner2;
                }
            }
        }
        
        if (distWinner!=NULL)
            winner2 = distWinner;

        return *this;
    }
    
    void getActivationVector(const TVector &sample, TVector &actVector) {
        actVector.size(Mesh<TNode>::meshNodeSet.size());
        
        int i=0;
        for (it = Mesh<TNode>::meshNodeSet.begin(); it != Mesh<TNode>::meshNodeSet.end(); it++) {
            actVector[i] = activation(*(*it), sample);
            i++;
        }
    }
    
    float getDimRel(int index){
        return globalDimensionRelevances[index];
    }
    
    bool isNoise(const TVector &w) {
        TNode *winner = getWinner(w);
        double a = activation(*winner, w);
        return (a<a_t);
    }

    void resetToDefault(int dimw = 2) {
        LARFDSSOM::dimw = dimw;
        step = 0;
        nodesLeft = 1;

        maxNodeNumber = 100;
        e_b = 0.05;
        e_n = 0.0006;
        counter_i = 0;
        aloc_node = 0;
        aloc_con = 0;
        nodesCounter = 1;
        nodeID = 0;

        destroyMesh();
        TVector v(dimw);
        v.random();
        TVector wNew(v);
        createNode(0, wNew);
    }

    void reset(int dimw) {
        LARFDSSOM::dimw = dimw;
        step = 0;
        nodesLeft = 1;

        counter_i = 0;
        aloc_node = 0;
        aloc_con = 0;
        nodesCounter = 1;
        nodeID = 0;

        destroyMesh();
        TVector v(dimw);
        v.random();
        TVector wNew(v);
        createNode(0, wNew);
        binarizeRelevances();
    }
    
    void binarizeRelevances() {
        
        TPNodeSet::iterator it;
        for (it = Mesh<TNode>::meshNodeSet.begin(); it != Mesh<TNode>::meshNodeSet.end(); it++) {
            TNode *node = *it;
            float average = node->ds.mean();
            for (int i=0; i<node->ds.size(); i++) {
                if (node->ds[i]>average)
                    node->ds[i] = 1;
                else
                    node->ds[i] = 0;
            }
        }
    }
    
    void binarizeWeights() {
        
        TPNodeSet::iterator it;
        for (it = Mesh<TNode>::meshNodeSet.begin(); it != Mesh<TNode>::meshNodeSet.end(); it++) {
            TNode *node = *it;
            float average = node->w.mean();
            for (int i=0; i<node->w.size(); i++) {
                if (node->w[i]>average)
                    node->w[i] = 1;
                else
                    node->w[i] = 0;
            }
        }
    }
    

    void reset(void) {
        reset(dimw);
    }

    LARFDSSOM(int dimw) {
        resetToDefault(dimw);
    };

    ~LARFDSSOM() {
    }

    template<class Number> LARFDSSOM& outputCentersDs(MatMatrix<Number> &m) {
        using namespace std;

        uint wSize = (*meshNodeSet.begin())->ds.size();
        uint meshNodeSetSize = meshNodeSet.size();
        m.size(meshNodeSetSize, wSize);

        int i = 0;
        typename TPNodeSet::iterator it;
        for (it = meshNodeSet.begin(); it != meshNodeSet.end(); it++) {
            for (uint j = 0; j < wSize; j++)
                m[i][j] = (*it)->ds[j];
            i++;
        }

        return *this;
    }
    
    virtual bool saveParameters(std::ofstream &file) {
        
        file << maxNodeNumber << "\t";
        file << minwd << "\t";
        file << e_b << "\t";
        file << e_n << "\t";
        file << dsbeta << "\t"; //Taxa de aprendizagem
        file << epsilon_ds << "\t"; //Taxa de aprendizagem
        file << age_wins << "\t";       //period to remove nodes
        file << lp << "\t";          //remove percentage threshold
        file << a_t << "\n";
        return true;
    }
    
    virtual bool readParameters(std::ifstream &file) {
        
        file >> maxNodeNumber;
        file >> minwd;
        file >> e_b;
        file >> e_n;
        file >> dsbeta; //Taxa de aprendizagem
        file >> epsilon_ds; //Taxa de aprendizagem
        file >> age_wins;       //period to remove nodes
        file >> lp;           //remove percentage threshold
        file >> a_t;
        file.get();//skip line end
        
        return true;
    }
};

#endif /* LARFDSSOM_H_ */
