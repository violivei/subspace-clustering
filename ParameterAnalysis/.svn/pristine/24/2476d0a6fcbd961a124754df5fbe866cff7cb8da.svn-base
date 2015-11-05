/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package setup;

import weka.subspaceClusterer.INSCY;

/**
 *
 * @author hans
 */
class INSCYParameters extends MethodParameters {

    ClusteringParameter density;
    ClusteringParameter epsilon;
    ClusteringParameter gridSize;
    ClusteringParameter maximalClusterRate;
    ClusteringParameter minPoints;
    ClusteringParameter minSize;
    ClusteringParameter usingKernel;
    INSCY inscy;

    public INSCYParameters(INSCY inscy) {
        this.inscy = inscy;
        setupParameterRanges();
    }

    @Override
    void setupParameterRanges() {
        density = new ClusteringParameter(10, 10, 0, ClusteringParameter.StepType.SUM, "density");
        epsilon = new ClusteringParameter(10, 160, 2, ClusteringParameter.StepType.MULT, "epsilon");
        gridSize = new ClusteringParameter(50, 50, 0, ClusteringParameter.StepType.SUM, "gridSize");
        maximalClusterRate = new ClusteringParameter(0, 0, 0, ClusteringParameter.StepType.SUM, "maximalClusterRate");
        minPoints = new ClusteringParameter(2, 32, 2, ClusteringParameter.StepType.MULT, "minPoints");
        minSize = new ClusteringParameter(20, 320, 2, ClusteringParameter.StepType.MULT, "minSize");
        usingKernel = new ClusteringParameter(1, 1, 0, ClusteringParameter.StepType.SUM, "usingKernel");

        //parameterList.add(density);
        parameterList.add(epsilon);
        //parameterList.add(gridSize);
        //parameterList.add(maximalClusterRate);
        parameterList.add(minPoints);
        parameterList.add(minSize);
        //parameterList.add(usingKernel);
        restartAllValues();
    }

    @Override
    void setupParameterValues() {
        inscy.setDensity(density.getValue());
        inscy.setEpsilon(epsilon.getValue());
        inscy.setGridSize((int) gridSize.getValue());
        inscy.setMaximalClusterRate(maximalClusterRate.getValue());
        inscy.setminPoints(minPoints.getValue());
        inscy.setminSize((int) minSize.getValue());
        inscy.setUsingKernel((int) usingKernel.getValue());
    }

    public static void main(String args[]) {
        INSCY inscy = new INSCY();
        INSCYParameters inscyParameters = new INSCYParameters(inscy);

        System.out.print("Exp\t");
        inscyParameters.printParameterNames();
        System.out.println();
        while (!inscyParameters.hasFinished()) {
            System.out.print(inscyParameters.getExperimentNumber() + "\t");
            inscyParameters.printParameterValues();
            System.out.println();
            inscyParameters.setupNextParameters();
        }
    }
}
