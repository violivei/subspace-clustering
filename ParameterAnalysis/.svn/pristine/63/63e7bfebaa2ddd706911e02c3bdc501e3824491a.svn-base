package weka.clusterquality;


import i9.subspace.base.Cluster;
import java.util.ArrayList;
import weka.core.Instances;

public class Purity extends ClusterQualityMeasure{
	
	double purity = 0.0;
	
	@Override
	public void calculateQuality(ArrayList<Cluster> clusters, Instances instances, ArrayList<Cluster> trueclusterList) {
            
            int N = 0;
            int nCorrect = 0;
            int[] clustCount = new int[trueclusterList.size()];
            for (Cluster cluster: clusters) {
                //compute most common value
                for (int t=0; t<trueclusterList.size(); t++) {
                        clustCount[t] = 0;
                }
                
                for (Integer i: cluster.m_objects) {
                    for (int t=0; t<trueclusterList.size(); t++) {
                        if (trueclusterList.get(t).m_objects.contains(i))
                            clustCount[t]++;
                    }
                }
                
                int max=0, indexMax = 0;
                for (int t=0; t<trueclusterList.size(); t++) {
                    if (max<clustCount[t]) {
                        max = clustCount[t];
                        indexMax = t;
                    }
                }
                
                for (Integer i: cluster.m_objects) {
                    if (trueclusterList.get(indexMax).m_objects.contains(i)) {
                            nCorrect++;
                    }
                    N++;
                }
            }
        
            purity = nCorrect/(double)N;
            
	}

	
	@Override
	public Double getOverallValue(){
		return purity;
	}
	
	@Override
	public String getName() {
		return "Purity";
	}


}
