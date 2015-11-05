/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package setup;

import i9.subspace.base.Cluster;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.clusterquality.ClassificationError;
import weka.clusterquality.ClusterQualityMeasure;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;
import weka.subspaceClusterer.MineClus;
import weka.subspaceClusterer.Doc;
import weka.subspaceClusterer.Statpc;
import weka.subspaceClusterer.Proclus;
import weka.subspaceClusterer.SubspaceClusterEvaluation;
import weka.subspaceClusterer.SubspaceClusterer;

/**
 *
 * @author hans
 */
public class Main {

    static boolean allTrue = false;
    static int repeat = 1;
    static String extension = ".arff";

    public static String getFileExtension(String fileName) {
        int dotPos = fileName.lastIndexOf(".");
        if (dotPos < 0) {
            return "";
        }
        return fileName.substring(dotPos);
    }

    public static String removeExtension(String fileName) {
        int dotPos = fileName.lastIndexOf(".");
        return fileName.substring(0, dotPos);
    }

    public static int averageDimensions(ArrayList<Cluster> clusterList) {
        int average = 0;
        for (int i = 0; i < clusterList.size(); i++) {
            for (int j = 0; j < clusterList.get(i).m_subspace.length; j++) {
                if (clusterList.get(i).m_subspace[j]) {
                    average++;
                }
            }
        }
 
        return average / clusterList.size();
    }

    public static ArrayList<Cluster> readTrueClusters(File file) {
        Scanner sc;
        try {
            sc = new Scanner(file);
            sc.useLocale(Locale.ENGLISH);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        ArrayList<Cluster> clusterList = new ArrayList<Cluster>();
        sc.skip("DIM=");
        sc.useDelimiter(";");
        int dim = sc.nextInt();
        sc.nextLine();

        sc.useDelimiter("\\s");
        while (sc.hasNext()) {
            boolean[] subspace = new boolean[dim];
            for (int j = 0; j < dim; j++) {
                int useDim = sc.nextInt();
                if (useDim > 0.5) {
                    subspace[j] = true;
                } else {
                    subspace[j] = false;
                }
            }

            int numInstances = sc.nextInt();
            ArrayList<Integer> objects = new ArrayList<Integer>();

            for (int i = 0; i < numInstances; i++) {
                int value = sc.nextInt();
                System.out.println(value);
                objects.add(value);
            }

            Cluster cluster = new Cluster(subspace, objects);
            clusterList.add(cluster);
        }

        return clusterList;
    }

    public static ArrayList<Cluster> readResults(File file) {
        Scanner sc;
        try {
            sc = new Scanner(file);
            sc.useLocale(Locale.ENGLISH);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

        //Read clusters info
        int numClusters = sc.nextInt();
        int numAttributes = sc.nextInt();

        //Create clusters
        ArrayList<Cluster> clusterList = new ArrayList<Cluster>();
        for (int i = 0; i < numClusters; i++) {
            boolean[] subspace = new boolean[numAttributes];
            sc.nextInt(); //skip cluster number
            for (int j = 0; j < numAttributes; j++) {
                float dsWeight = sc.nextFloat();
                if (dsWeight > 0.1) {
                    subspace[j] = true;
                } else {
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
            clusterList.get(cluster).m_objects.add(index);
        }
        /**/

        return clusterList;
    }

    public static ArrayList<ClusterQualityMeasure> evaluateClusters(String measuresStr, ArrayList<Cluster> clusterResults, Instances instances, ArrayList<Cluster> trueClusters) {
        ArrayList<ClusterQualityMeasure> eMeasures = SubspaceClusterEvaluation.getMeasuresByOptions(measuresStr);

        for (ClusterQualityMeasure m : eMeasures) {
            m.calculateQuality(clusterResults, instances, trueClusters);
        }
        return eMeasures;
    }

    public static void printMeasures(ArrayList<ClusterQualityMeasure> measures) {
        for (ClusterQualityMeasure m : measures) {
            System.out.println(m.getName() + ":\t" + m.getOverallValue());
        }
    }

    public static void printHeaders(ArrayList<ClusterQualityMeasure> measures, ArrayList<String> methodNames) {
        System.out.print("File");
        for (int meassure = 0; meassure < measures.size(); meassure++) {
            for (int method = 0; method < methodNames.size(); method++) {
                System.out.print("\t" + methodNames.get(method) + "-" + measures.get(meassure).getName());
            }
        }
    }

    public static void printMeasures(ArrayList<ArrayList<ClusterQualityMeasure>> measures, ArrayList<String> methodNames) {

        for (int meassure = 0; meassure < measures.get(0).size(); meassure++) {
            for (int method = 0; method < methodNames.size(); method++) {
                System.out.print(measures.get(method).get(meassure).getOverallValue() + "\t");
            }
        }
    }

    public static void printDetailedResults(ArrayList<ClusterQualityMeasure> measures, ArrayList<Cluster> clusterResults) {
        //print values
        DecimalFormat format = new DecimalFormat();
        format.setMaximumFractionDigits(2);
        StringBuffer customOutputBlocks = new StringBuffer();
        StringBuffer sb = new StringBuffer();
        for (ClusterQualityMeasure m : measures) {
            //overall value
            String row = "";
            if (m.getOverallValue() != null) {
                if (m.getOverallValue().equals(Double.NaN)) {
                    row += "undef\t\t";
                } else {
                    row += format.format(m.getOverallValue()) + "\t\t";
                }
            }

            try {
                //test if method is implemented
                Class[] para = {int.class};
                m.getClass().getDeclaredMethod("getValuePerCluster", para);
                //print cluster values
                for (int i = 0; i < clusterResults.size(); i++) {
                    if (m.getValuePerCluster(i) != null) {
                        if (m.getValuePerCluster(i).equals(Double.NaN)) {
                            row += "NaN\t\t";
                        } else {
                            row += format.format(m.getValuePerCluster(i)) + "\t\t";
                        }
                    }
                }
            } catch (NoSuchMethodException e) {
            }

            if (row != "") {
                sb.append(m.getName() + " \t");
                sb.append(row);
                sb.append("\n");
            }

            if (m.getCustomOutput() != null) {
                customOutputBlocks.append(" \n\n" + m.getName() + ": \n");
                customOutputBlocks.append(m.getCustomOutput());
            }
        }
        System.out.println(sb);
    }

    public static void printClustersData(ArrayList<Cluster> clusterResults) {

        for (int i = 0; i < clusterResults.size(); i++) {
            for (int j = 0; j < clusterResults.get(i).m_objects.size(); j++) {
                System.out.println("" + clusterResults.get(i).m_objects.get(j) + "\t" + i);
            }
        }
    }

    public static void printClustersDimensions(ArrayList<Cluster> clusterResults) {
        //Print cluster dimensions
        for (int i = 0; i < clusterResults.size(); i++) {
            System.out.print("C" + i + ":\t");
            for (int j = 0; j < clusterResults.get(i).m_subspace.length; j++) {
                if (clusterResults.get(i).m_subspace[j])
                    System.out.print("1\t");
                else
                    System.out.print("0\t");
            }
            System.out.println();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: 'Datafiles directory' 'method name' [-t] [-e extension]");
            return;
        }

        for (int i=2; i<args.length; i++) {
            if (args[i].compareTo("-t") == 0) {
                allTrue = true;
            } else if (args[i].compareTo("-e") == 0) {
                if (args.length < i) {
                    System.out.println("Usage: 'Datafiles directory' 'method name' [-t] [-e extension]");
                    return;
                }
                
                i++;
                extension = args[i];
            }
        }
        
        String dir = args[0];
        if (!dir.endsWith("/"))
            dir = dir + "/";
        
        String method = args[1];
        
        if (method.compareTo("categorizeImages")==0 || method.compareTo("nullExec2")==0 || method.compareTo("categorizeImagesSOM2D")==0 || method.compareTo("joinstimulisom2d")==0)
            runImageTests(dir, method);
        else
            runTests(dir, method);
    }

    //static String measuresStr = "CE:F1Measure:RNIA:Accuracy:Entropy";
    //static String measuresStr = "CEN:CEntropy";
    //static String measuresStr = "CE:F1Measure";
    static String measuresStr = "CEN:Accuracy";
    static File directory;
    static String filename[];
    static ArrayList<String> methodNames = new ArrayList<String>();
    static ArrayList<ClusterQualityMeasure> measures = SubspaceClusterEvaluation.getMeasuresByOptions(measuresStr);
    static ArrayList<Cluster> trueClusters;
    static ArrayList<ArrayList<ClusterQualityMeasure>> methodsResults;

    public static void runTests(String path, String method) {
        directory = new File(path);
        if (!directory.exists()) {
            System.out.println("Directory not found: " + path);
            return;
        }

        filename = directory.list();

        Arrays.sort(filename, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((String) o1).compareTo((String) o2);
            }
        });

        String dataFile;
        String trueClustersFile;

        for (int i = 0; i < filename.length; i++) {
            if (getFileExtension(filename[i]).compareTo(extension) == 0) {
                dataFile = path + filename[i];
                trueClustersFile = path + removeExtension(filename[i]) + ".true";
                
                ClassificationError.fileName = dataFile;

                Instances dataInstances = null;
                Instances train = null;
                try {
                    if (extension.compareTo(".arff")==0) {
                        dataInstances = new Instances(new FileReader(dataFile));
                        // Make the last attribute be the class
                        dataInstances.setClassIndex(dataInstances.numAttributes() - 1);

                        DataSource source = new DataSource(dataFile);
                        Instances inst = source.getDataSet();

                        int theClass = dataInstances.numAttributes();
                        Remove removeClass = new Remove();
                        removeClass.setAttributeIndices("" + theClass);
                        removeClass.setInvertSelection(false);
                        removeClass.setInputFormat(dataInstances);
                        train = Filter.useFilter(inst, removeClass);
                    }

                } catch (Exception ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    return;
                }

                trueClusters = readTrueClusters(new File(trueClustersFile));
                methodsResults = new ArrayList<ArrayList<ClusterQualityMeasure>>();

                for (int r = 0; r < repeat; r++) {
                    
                    methodNames.clear();
                    methodsResults.clear();
                    //Run method

                    if (method.compareTo("nullExec") == 0) {

                        //*NullExec
                        NULLExec nullExec = new NULLExec();
                        runExternalMethod(path, filename[i], dataInstances, nullExec, new NULLParameters());
                        /**/
                    } else if (method.compareTo("categorizeImages") == 0) {

                        //*CategorizeImages
                        CategorizeImages ci = new CategorizeImages();
                        runExternalMethod(path, filename[i], dataInstances, ci, new CategorizeImageParameters(ci));
                        /**/
                    } else if (method.compareTo("categorizeImagesSOM2D") == 0) {

                        //*CategorizeImages
                        CategorizeImagesSOM2D ci = new CategorizeImagesSOM2D();
                        runExternalMethod(path, filename[i], dataInstances, ci, new CategorizeImageParametersSOM2D(ci));
                        /**/
                    } else if (method.compareTo("proclus") == 0) {

                        //*Proclus
                        Proclus proclus = new Proclus();
                        runJavaMethod(filename[i], dataInstances, train, proclus, new ProclusParameters(proclus));
                        /**/
                    } else if (method.compareTo("mineclus") == 0) {

                        //*MineClus
                        MineClus mineclus = new MineClus();
                        runJavaMethod(filename[i], dataInstances, train, mineclus, new MineClusParameters(mineclus));
                        /**/

                    } else if (method.compareTo("doc") == 0) {

                        //*Doc
                        Doc doc = new Doc();
                        runJavaMethod(filename[i], dataInstances, train, doc, new DOCParameters(doc));
                        /**/

                    } else if (method.compareTo("statpc") == 0) {

                        //*Doc
                        Statpc statpc = new Statpc();
                        runJavaMethod(filename[i], dataInstances, train, statpc, new STATPCParameters(statpc));
                        /**/

                    } else if (method.compareTo("som2d") == 0) {

                        //* SOM2D
                        SOM2D som2d = new SOM2D();
                        runExternalMethod(path, filename[i], dataInstances, som2d, new SOM2DParameters(som2d));
                        /**/

                    } else if (method.compareTo("somaw") == 0) {

                        //* SOMAW
                        SOMAW somaw = new SOMAW();
                        runExternalMethod(path, filename[i], dataInstances, somaw, new SOMAWParameters(somaw));
                        /**/

                    } else if (method.compareTo("dssom") == 0) {

                        //* DSSOM
                        DSSOM dssom = new DSSOM();
                        //dssom.ny.setValue(trueClusters.size());
                        runExternalMethod(path, filename[i], dataInstances, dssom, new DSSOMParameters(dssom));
                        /**/

                    } else if (method.compareTo("mwdssom") == 0) {

                        //* MWDSSOM
                        MWDSSOM mwdssom = new MWDSSOM();
                        runExternalMethod(path, filename[i], dataInstances, mwdssom, new MWDSSOMParameters(mwdssom));
                        /**/
                    } else if (method.compareTo("gngdssom") == 0) {

                        //*
                        GNGDSSOM gngdssom = new GNGDSSOM();
                        runExternalMethod(path, filename[i], dataInstances, gngdssom, new GNGDSSOMParameters(gngdssom));
                        /**/
                    } else if (method.compareTo("gngsom") == 0) {

                        //*
                        GNGSOM gngsom = new GNGSOM();
                        runExternalMethod(path, filename[i], dataInstances, gngsom, new GNGSOMParameters(gngsom));
                        /**/
                    } else if (method.compareTo("gwrsom") == 0) {

                        //*
                        GWRSOM gwrsom = new GWRSOM();
                        runExternalMethod(path, filename[i], dataInstances, gwrsom, new GWRSOMParameters(gwrsom));
                        /**/
                    } else if (method.compareTo("gwrdssom") == 0) {

                        //*
                        GWRDSSOM gwrdssom = new GWRDSSOM();
                        runExternalMethod(path, filename[i], dataInstances, gwrdssom, new GWRDSSOMParameters(gwrdssom));
                        /**/

                    } else if (method.compareTo("larfsom") == 0) {

                        //*
                        LARFSOM larfsom = new LARFSOM();
                        runExternalMethod(path, filename[i], dataInstances, larfsom, new LARFSOMParameters(larfsom));
                        /**/
                    } else if (method.compareTo("larfdssom") == 0) {

                        //*
                        LARFDSSOM larfdssom = new LARFDSSOM();
                        runExternalMethod(path, filename[i], dataInstances, larfdssom, new LARFDSSOMParameters(larfdssom));
                        /**/

                    } else if (method.compareTo("gdssom") == 0) {

                        //*
                        GDSSOM gdssom = new GDSSOM();
                        runExternalMethod(path, filename[i], dataInstances, gdssom, new GDSSOMParameters(gdssom));
                        /**/

                    } else if (method.compareTo("gdssomrbf") == 0) {

                        //*
                        GDSSOMRBF gdssomrbf = new GDSSOMRBF();
                        runExternalMethod(path, filename[i], dataInstances, gdssomrbf, new GDSSOMRBFParameters(gdssomrbf));
                        /**/

                    } else if (method.compareTo("gdssommw") == 0) {

                        //*
                        GDSSOMMW gdssommw = new GDSSOMMW();
                        runExternalMethod(path, filename[i], dataInstances, gdssommw, new GDSSOMMWParameters(gdssommw));
                        /**/

                    } else if (method.compareTo("ssc") == 0) {

                        //*
                        SSC ssc = new SSC();
                        ssc.ny.setValue(trueClusters.size());
                        runExternalMethod(path, filename[i], dataInstances, ssc, new SSCParameters(ssc));
                        /**/

                    } else if (method.compareTo("gpca") == 0) {

                        //*
                        GPCA gpca = new GPCA();
                        gpca.ny.setValue(trueClusters.size());
                        runExternalMethod(path, filename[i], dataInstances, gpca, new GPCAParameters(gpca));
                        /**/

                    } else {
                        System.out.println("Method not found: " + method);
                        return;
                    }
                }
            }
        }
    }
    
    public static void runImageTests(String path, String method) {
        directory = new File(path);
        if (!directory.exists()) {
            System.out.println("Directory not found: " + path);
            return;
        }

        filename = directory.list();

        Arrays.sort(filename, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((String) o1).compareTo((String) o2);
            }
        });

        String dataFile;
        String evalDataFile;
        String trueClustersFile;

        for (int i = 0; i < filename.length; i++)
        if (getFileExtension(filename[i]).compareTo(extension) == 0) {
                dataFile = path + filename[i];
                trueClustersFile = path + removeExtension(filename[i]) + ".true";
                methodsResults = new ArrayList<ArrayList<ClusterQualityMeasure>>();
                methodNames.clear();
                methodsResults.clear();
                    //Run method
                                
                if (method.compareTo("categorizeImages")==0 || method.compareTo("nullExec2")==0 || method.compareTo("categorizeImagesSOM2D")==0 || method.compareTo("joinstimulisom2d")==0) {

                        //*CategorizeImages
                        CategorizeImagesSOM2D ci = new CategorizeImagesSOM2D();
                        CategorizeImageParametersSOM2D parameters = new CategorizeImageParametersSOM2D(ci);
                        
                        methodNames.add(ci.getName());
                        System.out.print("Exp#\t");
                        printHeaders(measures, methodNames);
                        System.out.print("\ttime\tnClust\tnDims\t");
                        parameters.printParameterNames();
                        parameters.setupParameterValues();
                        while (!parameters.hasFinished()) {

                            //System.out.print("\n\t\t\t\t\t\t\t\t");parameters.printParameterValues();                
                            System.out.print("\n" + parameters.getExperimentNumber() + "\t");
                            System.out.print(removeExtension(filename[i]) + "\t");

                            PrintStream stdout = System.out;
                            long start=0, finish;
                            try {
                                //Run method
                                start = System.currentTimeMillis();
                                ArrayList<Cluster> somResults = ci.runSOM(dataFile, allTrue);
                                finish = System.currentTimeMillis();

                                trueClusters = readTrueClusters(new File(trueClustersFile));
                                //Instances dataInstances = generateInstances(trueClusters);
                                
                                //Evaluate results
                                ArrayList<ClusterQualityMeasure> somMeasures =
                                        evaluateClusters(measuresStr, somResults, null, trueClusters);
                                methodsResults.clear();
                                methodsResults.add(somMeasures);

                                //Print results
                                printMeasures(methodsResults, methodNames);
                                System.out.print((finish - start) + "\t" + ci.getNumClusters() + "\t" + ci.getNumDims() + "\t");
                                parameters.printParameterValues();

                            } catch (Exception ex) {
                                finish = System.currentTimeMillis();
                                System.setOut(stdout);
                                System.out.print("Error: " + ex.toString() + "\t - \t" + (finish - start) + "\t-\t");
                                parameters.printParameterValues();
                            }
                            parameters.setupNextParameters();
                        }
                        /**/
                } else  if (method.compareTo("nullExec2") == 0) {

                        //*CategorizeImages
                        NULLExec ne = new NULLExec();
                        NULLParameters parameters = new NULLParameters();
                        
                        methodNames.add(ne.getName());
                        System.out.print("Exp#\t");
                        printHeaders(measures, methodNames);
                        System.out.print("\ttime\tnClust\tnDims\t");
                        parameters.printParameterNames();
                        parameters.setupParameterValues();
                        while (!parameters.hasFinished()) {

                            //System.out.print("\n\t\t\t\t\t\t\t\t");parameters.printParameterValues();                
                            System.out.print("\n" + parameters.getExperimentNumber() + "\t");
                            System.out.print(removeExtension(filename[i]) + "\t");

                            PrintStream stdout = System.out;
                            long start=0, finish;
                            try {
                                //Run method
                                start = System.currentTimeMillis();
                                ArrayList<Cluster> somResults = ne.runSOM(dataFile, allTrue);
                                finish = System.currentTimeMillis();

                                trueClusters = readTrueClusters(new File(trueClustersFile));
                                //Instances dataInstances = generateInstances(trueClusters);
                                
                                //Evaluate results
                                ArrayList<ClusterQualityMeasure> somMeasures =
                                        evaluateClusters(measuresStr, somResults, null, trueClusters);
                                methodsResults.clear();
                                methodsResults.add(somMeasures);

                                //Print results
                                printMeasures(methodsResults, methodNames);
                                System.out.print((finish - start) + "\t" + ne.getNumClusters() + "\t" + ne.getNumDims() + "\t");
                                parameters.printParameterValues();

                            } catch (Exception ex) {
                                finish = System.currentTimeMillis();
                                System.setOut(stdout);
                                System.out.print("Error: " + ex.toString() + "\t - \t" + (finish - start) + "\t-\t");
                                parameters.printParameterValues();
                            }
                            parameters.setupNextParameters();
                        }
                        /**/
                }
         }
    }
    
    static Instances generateInstances(ArrayList<Cluster> trueClusters) {
        int nDims = trueClusters.get(0).m_subspace.length;
                
        FastVector attVals = new FastVector();                
        
        FastVector atts = new FastVector();
        for (int i=0; i<nDims;i++)
            atts.addElement(new Attribute("att" + (i+1)));                       
        
        for (int i = 0; i < trueClusters.size(); i++)
            attVals.addElement("" + i);        
        atts.addElement(new Attribute("class", attVals)); 
        
        Instances data = new Instances("MyRelation", atts, 0);

        for (int k=0; k<trueClusters.size(); k++) {
             for (int j=0; j<trueClusters.get(k).m_objects.size(); j++) {
                Instance instance = new Instance(nDims+1);
                for (int i = 0; i < nDims; i++) {
                   instance.setValue(i, i);
                }
                instance.setValue(nDims, k);

                data.add(instance);
             }
        }
        data.setClassIndex(nDims);
        
        return data;
    }

    static void runJavaMethod(String filename, Instances dataInstances, Instances train, SubspaceClusterer method, MethodParameters parameters) {
        methodNames.add(method.getName());
        System.out.print("Exp#\t");
        printHeaders(measures, methodNames);
        System.out.print("\ttime\tnClust\t");
        parameters.printParameterNames();
        parameters.setupParameterValues();
        while (!parameters.hasFinished()) {

            System.out.print("\n" + parameters.getExperimentNumber() + "\t");
            System.out.print(removeExtension(filename) + "\t");

            PrintStream stdout = System.out;
            try {
                //Run method
                PrintStream bla = new PrintStream(new File("log.out"));
                System.setOut(bla);

                long start = System.currentTimeMillis();
                method.buildSubspaceClusterer(train);
                ArrayList<Cluster> results = (ArrayList<Cluster>) method.getSubspaceClustering();
                long finish = System.currentTimeMillis();

                System.setOut(stdout);
                //Evaluate results

                //Print cluster data
                //System.out.println();
                //printClustersDimensions(results);
                //printClustersData(results);

                int i=0;
                String relevances = "\t;\t";
                for (Cluster c : results) {
                    relevances+=(i + "\t");
                    for (int s = 0; s < c.m_subspace.length; s++) {
                        if (c.m_subspace[s]) {
                            relevances+=(1 + "\t");
                        } else{
                             relevances+=(0 + "\t");
                        }
                    }
                    relevances+=("\t;");
                    i++;
                }

                if (allTrue) {
                    for (Cluster c : results) {
                        for (int s = 0; s < c.m_subspace.length; s++) {
                            c.m_subspace[s] = true;
                        }
                    }
                }

                //Evaluate results
                ArrayList<ClusterQualityMeasure> somMeasures =
                        evaluateClusters(measuresStr, results, dataInstances, trueClusters);
                methodsResults.clear();
                methodsResults.add(somMeasures);

                //Print results
                printMeasures(methodsResults, methodNames);
                //System.out.print((finish - start) + "\t" + results.size() + "\t");
                //parameters.printParameterValues();
                System.out.print(relevances);
            } catch (Exception ex) {
                System.setOut(stdout);
                System.out.print("\tError: " + ex.getMessage());
            }
            parameters.setupNextParameters();
        }
    }

    static void printCluster(ArrayList<Cluster> clusters) {
        for (Cluster c : clusters) {
            System.out.print(c);
        }
    }

    static void runExternalMethod(String path, String filename, Instances dataInstances, ExternalClusteringMethod method, MethodParameters parameters) {
        methodNames.add(method.getName());
        System.out.print("Exp#\t");
        printHeaders(measures, methodNames);
        System.out.print("\ttime\tnClust\t");
        parameters.printParameterNames();
        parameters.setupParameterValues();
        while (!parameters.hasFinished()) {

            //System.out.print("\n\t\t\t\t\t\t\t\t");parameters.printParameterValues();                
            System.out.print("\n" + parameters.getExperimentNumber() + "\t");
            System.out.print(removeExtension(filename) + "\t");

            PrintStream stdout = System.out;
            long start=0, finish;
            try {
                //Run method
                start = System.currentTimeMillis();
                ArrayList<Cluster> somResults = method.runSOM(path + filename, allTrue);
                finish = System.currentTimeMillis();
                /*
                 System.out.print("\t" + method.getNumClusters() + "\t");                
                 parameters.printParameterValues();
                 System.out.println();
                 printCluster(somResults);
                 /**/

                //Evaluate results
                ArrayList<ClusterQualityMeasure> somMeasures =
                        evaluateClusters(measuresStr, somResults, dataInstances, trueClusters);
                methodsResults.clear();
                methodsResults.add(somMeasures);

                //Print results
                printMeasures(methodsResults, methodNames);
                System.out.print((finish - start) + "\t" + method.getNumClusters() + "\t");
                parameters.printParameterValues();

            } catch (Exception ex) {
                finish = System.currentTimeMillis();
                System.setOut(stdout);
                System.out.print("Error: " + ex.toString() + "\t - \t" + (finish - start) + "\t-\t");
                parameters.printParameterValues();
            }
            parameters.setupNextParameters();
        }
    }
}
