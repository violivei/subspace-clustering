/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package setup;

import weka.subspaceClusterer.Proclus;

/**
 *
 * @author hans
 */
public class ProclusParameters extends MethodParameters {

    ClusteringParameter averageDimensions;
    ClusteringParameter numberOfClusters;
    Proclus proclus;

    ProclusParameters(Proclus proclus) {
        this.proclus = proclus;
        setupParameterRanges();
    }
    
    @Override
    void setupParameterValues() {
        proclus.setAverageDimensions(averageDimensions.getIntValue());
        proclus.setNumberOfClusters(numberOfClusters.getIntValue());
    }
    
    @Override
    void setupParameterRanges() {
//        averageDimensions = new ClusteringParameter(1, 75, 2, ClusteringParameter.StepType.SUM, "avgDims");
//        numberOfClusters = new ClusteringParameter(2, 64, 2, ClusteringParameter.StepType.MULT, "numClust");

//        parameterList.add(averageDimensions);
//        parameterList.add(numberOfClusters);
        
        averageDimensions = new ClusteringParameter(15, 75, 2, ClusteringParameter.StepType.SUM, "avgDims");
        numberOfClusters = new ClusteringParameter(2, 2, 1, ClusteringParameter.StepType.MULT, "numClust");
        

        /*
        parameterList.add(numberOfClusters);        
        parameterList.add(averageDimensions);
        */
        
        addLHSParameter(averageDimensions, 2, 75);
        addLHSParameter(numberOfClusters, 2, 18);
        
        initLHS(500);
    }

    public static void main(String args[]) {

        /*
         * From	Offset	Op	Steps	To avgerageDimensions	1	2	+	38	75
         * numberOfClusters	2	2	*	6	64 Total number of experiments: 228
         */
        Proclus proclus = new Proclus();
        ProclusParameters proclusParameters = new ProclusParameters(proclus);

        System.out.print("Exp\t");
        proclusParameters.printParameterNames();
        System.out.println();
        while (!proclusParameters.hasFinished()) {
            System.out.print(proclusParameters.getExperimentNumber() + "\t");
            proclusParameters.printParameterValues();
            System.out.println();
            proclusParameters.setupNextParameters();
        }
    }
}
