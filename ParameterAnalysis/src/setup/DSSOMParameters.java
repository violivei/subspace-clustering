/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package setup;

/**
 *
 * @author hans
 */
public class DSSOMParameters extends MethodParameters {

    DSSOM som;

    DSSOMParameters(DSSOM som) {
        this.som = som;
        setupParameterRanges();
    }

    @Override
    void setupParameterRanges() {

        /*
        addParameter(som.alpha, 0.1, 0.9, 0.4, ClusteringParameter.StepType.SUM);
        addParameter(som.lambda1, 0.05, 0.2, 0.075, ClusteringParameter.StepType.SUM);
        addParameter(som.sigma, 0.1, 0.9, 0.4, ClusteringParameter.StepType.SUM);
        addParameter(som.lambda2, 0.05, 0.2, 0.075, ClusteringParameter.StepType.SUM);
        addParameter(som.ny, 8, 16, 2, ClusteringParameter.StepType.MULT);
        */

        /*//Fixed
        som.ny.setupRanges(32, 32, 1, ClusteringParameter.StepType.MULT);
        som.numWinners.setupRanges(1, 1, 1, ClusteringParameter.StepType.SUM);
        som.outliers_threshold.setupRanges(0.95, 0.95, 1, ClusteringParameter.StepType.SUM);
        som.lambda1.setupRanges(0.1, 0.1, 0.1, ClusteringParameter.StepType.SUM);     
        */
        
        addLHSParameter(som.seed, 0, 10);
        addLHSParameter(som.alpha, 0.001, 0.99);
        addLHSParameter(som.lambda2, 0.01, 2);
        addLHSParameter(som.sigma, 0.001, 0.99);
        addLHSParameter(som.lambda1, 0.01, 2);
        
        addLHSParameter(som.beta, 0.001, 0.1); 
        addLHSParameter(som.minDW, 0, 1);
        addLHSParameter(som.ny, 2, 18);
        
        initLHS(200);
    }

    public static void main(String args[]) {

        DSSOM som = new DSSOM();
        DSSOMParameters dssomParameters = new DSSOMParameters(som);
        System.out.print("Exp\t");
        dssomParameters.printParameterNames();
        System.out.println();
        while (!dssomParameters.hasFinished()) {
            System.out.print(dssomParameters.getExperimentNumber() + "\t");
            dssomParameters.printParameterValues();
            System.out.println();
            dssomParameters.setupNextParameters();
        }
    }

    @Override
    void setupParameterValues() {

    }
}
