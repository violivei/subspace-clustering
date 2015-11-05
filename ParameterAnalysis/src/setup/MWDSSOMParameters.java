/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package setup;

/**
 *
 * @author hans
 */
public class MWDSSOMParameters extends MethodParameters {

    MWDSSOM som;
    
    MWDSSOMParameters(MWDSSOM som) {
        this.som = som;
        setupParameterRanges();
    }

    @Override
    void setupParameterValues() {
        
    }
        
    @Override
    void setupParameterRanges() {

        /*/
        addParameter(som.alpha, 0.1, 0.9, 0.4, ClusteringParameter.StepType.SUM);
        addParameter(som.lambda1, 0.05, 0.2, 0.075, ClusteringParameter.StepType.SUM);
        addParameter(som.sigma, 0.1, 0.9, 0.4, ClusteringParameter.StepType.SUM);
        addParameter(som.lambda2, 0.05, 0.2, 0.075, ClusteringParameter.StepType.SUM);
        addParameter(som.ny, 8, 64, 2, ClusteringParameter.StepType.MULT);
        /**/
        
        /*Fixed
        som.ny.setupRanges(32, 32, 1, ClusteringParameter.StepType.MULT);
        som.numWinners.setupRanges(1, 1, 1, ClusteringParameter.StepType.SUM);
        som.outliers_threshold.setupRanges(0.95, 0.95, 1, ClusteringParameter.StepType.SUM);
        som.lambda1.setupRanges(0.1, 0.1, 0.1, ClusteringParameter.StepType.SUM);
        /**/
        
        addLHSParameter(som.seed, 0, 10);
        addLHSParameter(som.alpha, 0.001, 0.99);
        addLHSParameter(som.lambda2, 0.1, 2);
        addLHSParameter(som.sigma, 0.001, 0.99);
        addLHSParameter(som.lambda1, 0.1, 2);
        
        addLHSParameter(som.beta, 0.001, 0.99); 
        addLHSParameter(som.minDW, 0, 1);
        addLHSParameter(som.epsilonRho, 0, 1);
        addLHSParameter(som.numWinners, 1, 3);
        addLHSParameter(som.outliers_threshold, 0, 0.999);
        addLHSParameter(som.ny, 2, 18);
        
        initLHS(500);
    }

    public static void main(String args[]) {

        /*
         * From	Offset	Op	Steps	To avgerageDimensions	1	2	+	38	75 alpha	2	2	*	6
         * 64 Total number of experiments: 228
         */
        MWDSSOM som = new MWDSSOM();
        MWDSSOMParameters dssomParameters = new MWDSSOMParameters(som);
        dssomParameters.printParameterRanges();
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
}
