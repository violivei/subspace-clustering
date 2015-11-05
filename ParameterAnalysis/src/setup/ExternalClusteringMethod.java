/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package setup;

import i9.subspace.base.Cluster;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hans
 */
public abstract class ExternalClusteringMethod {

        private int numClusters = 0;
        private int numDims = 0;
        //protected String exec = "/lib/ld-linux.so.2 ";//32bit binary
        protected String exec = "/lib64/ld-linux-x86-64.so.2 ";//64bit binary
        //protected String exec = "";
        public String platform = "osx";

        ExternalClusteringMethod() {

            if (OSInfo.isUnix())
                platform = "";
            else
                platform = OSInfo.getOsStr();
        }

        public abstract String generateCommandString(String filename);

        abstract public String getName();

        public int getNumClusters() {
            return numClusters;
        }
        
        public int getNumDims() {
            return numDims;
        }
        
        public ArrayList<Cluster> runSOM(String fileName, boolean allTrue) throws IOException, InterruptedException {

        String commandString = generateCommandString(fileName);

        //System.out.println(commandString);

        String s;
        Process p = Runtime.getRuntime().exec(exec + commandString);
        BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

//            System.out.println("Running:" + commandString);


        ReadStream out = new ReadStream(p.getInputStream(), false);
        ReadStream err = new ReadStream(p.getErrorStream(), true);
        out.start ();
        err.start ();
        p.waitFor();        

        ArrayList<Cluster> results = readResults(new File(fileName+".results"), allTrue);
        p.waitFor();
        p.getInputStream().close(); 
        p.getOutputStream().close(); 
        p.getErrorStream().close();             
        return results;
    }

    public ArrayList<Cluster> readResults(File file, boolean allTrue) {
        Scanner sc;
        try {
            sc = new Scanner(file);
            sc.useLocale(Locale.ENGLISH);
        } catch (IOException ex) {
            Logger.getLogger(LARFDSSOM.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

        //Read clusters info
        numClusters = sc.nextInt();
        int numAttributes = sc.nextInt();
        numDims = numAttributes;

        //Create clusters
        ArrayList<Cluster> clusterList = new ArrayList<Cluster>();
        for (int i=0; i<numClusters; i++) {
           sc.nextInt(); //skip cluster number
           double dsWs[] = new double[numAttributes];
           double average = 0;
           for (int j=0; j<numAttributes; j++) {
               dsWs[j] = sc.nextFloat();
               average+=dsWs[j];
           }
           average = average/numAttributes;

           
           /*
           double deviation = 0;
           for (int j=0; j<numAttributes; j++) {
               deviation += Math.abs(average-dsWs[j]);
           }
           deviation = deviation/numAttributes;
           average = average - deviation;
           /**/

           boolean[] subspace = new boolean[numAttributes];
           for (int j=0; j<numAttributes; j++) {
               if (allTrue) {
                   subspace[j] = true;
               } else {
               if (dsWs[j]>=average)
                   subspace[j] = true;
               else
                   subspace[j] = false;
               }
           }
           
           Cluster cluster = new Cluster(subspace, new ArrayList<Integer>());
           clusterList.add(cluster);
        }

        //Read clusters data
        while (sc.hasNext()) {
            int index = sc.nextInt();
            int cluster = sc.nextInt();
            if (cluster>=0)
                clusterList.get(cluster).m_objects.add(index);
        }
        /**/

       return clusterList;
    }
}
