package weka.clusterquality;

import i9.subspace.base.Cluster;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

public class ClassificationError extends ClusterQualityMeasure{
	
	public static String fileName = "";
        private double clserror = -2.0;
	
	public void calculateQuality(ArrayList<Cluster> clusterList, Instances instances, ArrayList<Cluster> trueclusterList) {
            
            /*
            Permutations<Integer> p = new Permutations<Integer>();
            Collection<Integer> input = new ArrayList<Integer>();
            
            int k = trueclusterList.size();
            for (int i=0; i<k; i++) {
                input.add(i);
            }

            Collection<List<Integer>> output = p.permute(input);
            Set<List<Integer>> pnr = null;
            pnr = new HashSet<List<Integer>>();
            for(List<Integer> integers : output){
                    pnr.add(integers.subList(0, integers.size()));
            }
            
            for (List<Integer> perm: pnr) {
                clusterList.get(k).
            }
            */
            
            try {
                String command = "sh exec/classErr.sh " + fileName + " " + fileName + ".results";
                String line, lastLine = "-1";
                Process p = Runtime.getRuntime().exec(command);

                BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
                BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
                while ((line = in.readLine()) != null) {
                    //System.out.println(line);
                    if (line.length()>0)
                        lastLine = line;
                }
                
                // read any errors from the attempted command
                boolean error = false;
                while ((line = stdError.readLine()) != null) {
                    //System.out.println(line);
                    error = true;
                }
        
                clserror = Double.parseDouble(lastLine);
                
                in.close();
            } catch (Exception e) {
                // ...
            }
	}
        
	public static void main(String[] args) throws Exception {
		FastVector atts;
		FastVector attVals;
		Instances data;
		double[] vals;
		int i;

		atts = new FastVector();
		atts.addElement(new Attribute("att1"));
		attVals = new FastVector();
		for (i = 0; i < 5; i++)
			attVals.addElement("val" + (i + 1));
		atts.addElement(new Attribute("att2", attVals));

		data = new Instances("MyRelation", atts, 0);

		vals = new double[data.numAttributes()];
		vals[0] = Math.PI;
		vals[1] = attVals.indexOf("val3");
                
                boolean[] subspace = {true, true};
                ArrayList<Integer> cluster1 = new ArrayList<Integer>();
                cluster1.add(1);
                cluster1.add(2);
                cluster1.add(3);
                cluster1.add(4);
                
                ArrayList<Integer> cluster2 = new ArrayList<Integer>();
                cluster2.add(5);
                cluster2.add(6);
                cluster2.add(7);
                cluster2.add(8);
                
                Cluster cl1 = new Cluster(subspace, cluster1);
                Cluster cl2 = new Cluster(subspace, cluster2);
                ArrayList<Cluster> clusterList = new ArrayList<Cluster>();
                clusterList.add(cl1);
                clusterList.add(cl2);
                
                ArrayList<Cluster> trueClust = new ArrayList<Cluster>();
                clusterList.add(cl2);
                clusterList.add(cl1);
                
                ClassificationError ce = new ClassificationError();
                ce.calculateQuality(clusterList, data, trueClust);
	}

	
	@Override
	public Double getOverallValue(){
		return clserror;
	}

	public String getName() {
		return "ClsErr";
	}



}
