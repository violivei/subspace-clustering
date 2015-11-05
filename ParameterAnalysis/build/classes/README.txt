- use run.bat/run.sh for Weka Explorer

- command line (e.g. Proclus):
java -Xmx1024m -cp i9-weka.jar;weka.jar;i9-subspace.jar;Jama.jar;jsc.jar;commons-math-1.1.jar;vecmath.jar;j3dcore.jar;j3dutils.jar weka.subspaceClusterer.Proclus
hint: list all parameters with -h, set input file through -t 

- From within Java use 

SubspaceClusterer clusterer = new .... // e.g. new Proclus();
String[] options = ..... //set options, especially input file e.g. -t c:\data\pendigits.arff
String eval = SubspaceClusterEvaluation.evaluateClusterer(clusterer, options);

//Access list of clusters
List<Cluster> cluster = clusterer.getSubspaceClustering();

//Print evaluation/cluster results (different measures set through option -m)
System.out.println(eval);

