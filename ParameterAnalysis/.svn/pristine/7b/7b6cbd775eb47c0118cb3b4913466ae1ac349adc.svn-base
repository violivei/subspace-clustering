/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package setup;


/**
 *
 * @author hans
 */
public class GNGSOMParameters extends MethodParameters{

    GNGSOM gngsom;


    public GNGSOMParameters(GNGSOM gngsom) {
        this.gngsom = gngsom;
        setupParameterRanges();
    }
    
        @Override
    void setupParameterValues() {
        
    }
        
    @Override
    void setupParameterRanges() {
        gngsom.age_max.setupRanges(4, 64, 4, ClusteringParameter.StepType.MULT);
        gngsom.step_max.setupRanges(200, 400, 100, ClusteringParameter.StepType.SUM);
        gngsom.alfa.setupRanges(0.4, 0.5, 0.05, ClusteringParameter.StepType.SUM);
        gngsom.e_b.setupRanges(0.05, 0.151, 0.05, ClusteringParameter.StepType.SUM);

        parameterList.add(gngsom.age_max);
        parameterList.add(gngsom.step_max);
        parameterList.add(gngsom.alfa);
        parameterList.add(gngsom.beta);
        parameterList.add(gngsom.e_b);
    }
    
    public static void main(String args[]) {

        /*
         * From	Offset	Op	Steps	To
         * avgerageDimensions	1	2	+	38	75
         * alpha	2	2	*	6	64
         * Total number of experiments: 228
         */
        GNGSOM som = new GNGSOM();
        GNGSOMParameters dssomParameters = new GNGSOMParameters(som);

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
