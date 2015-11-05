/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package setup;

/**
 *
 * @author hans
 */
public class SOMAWParameters extends MethodParameters {
    
    SOMAW som;
    
    SOMAWParameters(SOMAW somaw) {
        this.som = somaw;
        setupParameterRanges();
    }
    
    @Override
    void setupParameterValues() {
    }
    
    @Override
    void setupParameterRanges() {
        
        /*
        addParameter(somaw.alpha, 0.1, 0.9, 0.8, ClusteringParameter.StepType.SUM);
        addParameter(somaw.lambda1, 0.05, 0.25, 0.2, ClusteringParameter.StepType.SUM);
        addParameter(somaw.sigma, 0.1, 0.9, 0.8, ClusteringParameter.StepType.SUM);
        addParameter(somaw.lambda2, 0.05, 0.25, 0.2, ClusteringParameter.StepType.SUM);
        addParameter(somaw.ny, 8, 64, 2, ClusteringParameter.StepType.MULT);
        //addParameter(somaw.k1, 0.0001, 0.0009, 0.0008, ClusteringParameter.StepType.SUM);
        addParameter(somaw.k2, 0.9, 0.99, 0.09, ClusteringParameter.StepType.SUM);
        addParameter(somaw.k3, 1.02, 1.09, 0.07, ClusteringParameter.StepType.SUM);
        */
        
        addLHSParameter(som.seed, 0, 10);
        addLHSParameter(som.alpha, 0.001, 0.99);
        addLHSParameter(som.lambda2, 0.1, 2);
        addLHSParameter(som.sigma, 0.001, 0.99);
        addLHSParameter(som.lambda1, 0.1, 2);
        
        addLHSParameter(som.ny, 2, 18);
        
        addLHSParameter(som.k1, 0.0001, 0.1);
        addLHSParameter(som.k2, 0.9, 0.99);
        addLHSParameter(som.k3, 1.01, 1.09);
        
        initLHS(1);
    }

    public static void main(String args[]) {

        /*
         * From	Offset	Op	Steps	To
         * avgerageDimensions	1	2	+	38	75
         * alpha	2	2	*	6	64
         * Total number of experiments: 228
         */
        SOMAW somaw = new SOMAW();
        SOMAWParameters somawParameters = new SOMAWParameters(somaw);

        somawParameters.printParameterRanges();
        System.out.print("Exp\t");
        somawParameters.printParameterNames();
        System.out.println();
        while (!somawParameters.hasFinished()) {
            System.out.print(somawParameters.getExperimentNumber()+"\t");
            somawParameters.printParameterValues();
            System.out.println();
            somawParameters.setupNextParameters();
        }
    }
}
