/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package setup;

/**
 *
 * @author hans
 */
public class GPCAParameters extends MethodParameters {

    GPCA gpca;

    GPCAParameters(GPCA gpca) {
        this.gpca = gpca;
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
    }

    public static void main(String args[]) {

        /*
         * From	Offset	Op	Steps	To avgerageDimensions	1	2	+	38	75 alpha	2	2	*	6
         * 64 Total number of experiments: 228
         */
        GPCA gpca = new GPCA();
        GPCAParameters dssomParameters = new GPCAParameters(gpca);
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
