/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package setup;


/**
 *
 * @author hans
 */
public class GNGDSSOMParameters extends MethodParameters{

    GNGDSSOM gngdsom;


    public GNGDSSOMParameters(GNGDSSOM gngdsom) {
        this.gngdsom = gngdsom;
        setupParameterRanges();
    }
    
        @Override
    void setupParameterValues() {
        
    }
    
    @Override
    void setupParameterRanges() {
        gngdsom.age_max.setupRanges(4, 64, 4, ClusteringParameter.StepType.MULT);
        gngdsom.step_max.setupRanges(200, 400, 100, ClusteringParameter.StepType.SUM);
        gngdsom.alfa.setupRanges(0.4, 0.5, 0.05, ClusteringParameter.StepType.SUM);
        gngdsom.dsbeta.setupRanges(0.0001, 0.01, 10, ClusteringParameter.StepType.MULT);

        parameterList.add(gngdsom.age_max);
        parameterList.add(gngdsom.step_max);
        parameterList.add(gngdsom.alfa);
        parameterList.add(gngdsom.dsbeta);
    }
    
    public static void main(String args[]) {

        /*
         * From	Offset	Op	Steps	To
         * avgerageDimensions	1	2	+	38	75
         * alpha	2	2	*	6	64
         * Total number of experiments: 228
         */
        GNGDSSOM som = new GNGDSSOM();
        GNGDSSOMParameters dssomParameters = new GNGDSSOMParameters(som);

        dssomParameters.printParameterRanges();
        System.out.print("Exp\t");
        dssomParameters.printParameterNames();
        System.out.println();
        while (!dssomParameters.hasFinished()) {
            System.out.print(dssomParameters.getExperimentNumber()+"\t");
            dssomParameters.printParameterValues();
            System.out.println();
            dssomParameters.setupNextParameters();
        }
    }
}
