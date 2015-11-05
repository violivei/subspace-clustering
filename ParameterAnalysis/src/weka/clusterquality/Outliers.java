package weka.clusterquality;


import i9.subspace.base.Cluster;
import java.util.ArrayList;
import weka.core.Instances;

public class Outliers extends ClusterQualityMeasure{
	
	double outliers = -1.0;
	
	@Override
	public void calculateQuality(ArrayList<Cluster> clusters, Instances instances, ArrayList<Cluster> trueclusterList) {
            
            int N = 0;
            int nOutliers = 0;
            for (Cluster trueCluster: trueclusterList) {
                
                boolean isOutlier = true;
                for (Integer i: trueCluster.m_objects) {
                    for (Cluster cluster: clusters) {
                        if (cluster.m_objects.contains(i)) {
                            isOutlier = false;
                            break;
                        }
                    }
                    
                    if (isOutlier)
                        nOutliers++;
                    N++;
                }
            }
        
            outliers = nOutliers/(double)N;
	}

	
	@Override
	public Double getOverallValue(){
		return outliers;
	}
	
	@Override
	public String getName() {
		return "Outliers";
	}
}
