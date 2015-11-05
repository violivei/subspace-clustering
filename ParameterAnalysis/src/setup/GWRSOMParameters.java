/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package setup;


/**
 *
 * @author hans
 */
public class GWRSOMParameters extends MethodParameters{

    GWRSOM gwrsom;


    public GWRSOMParameters(GWRSOM gwrsom) {
        this.gwrsom = gwrsom;
        setupParameterRanges();
    }
    
    @Override
    void setupParameterValues() {
        
    }
        
    @Override
    void setupParameterRanges() {
        gwrsom.a_t.setupRanges(0.55, 0.6, 0.025, ClusteringParameter.StepType.SUM);
        gwrsom.h_t.setupRanges(0.4, 0.5, 0.05, ClusteringParameter.StepType.SUM);
        gwrsom.e_b.setupRanges(0.25, 0.75, 0.25, ClusteringParameter.StepType.SUM);
        gwrsom.a_b.setupRanges(1.01, 1.11, 0.05, ClusteringParameter.StepType.SUM);
        gwrsom.h0.setupRanges(1.01, 1.21, 0.1, ClusteringParameter.StepType.SUM);
        gwrsom.tau_b.setupRanges(3.01, 3.67, 0.33, ClusteringParameter.StepType.SUM);

        parameterList.add(gwrsom.a_t);
        parameterList.add(gwrsom.h_t);
        parameterList.add(gwrsom.e_b);
        parameterList.add(gwrsom.a_b);
        parameterList.add(gwrsom.h0);
        parameterList.add(gwrsom.tau_b);
    }
    
    public static void main(String args[]) {

        /*
         * From	Offset	Op	Steps	To
         * avgerageDimensions	1	2	+	38	75
         * alpha	2	2	*	6	64
         * Total number of experiments: 228
         */
        GWRSOM som = new GWRSOM();
        GWRSOMParameters dssomParameters = new GWRSOMParameters(som);

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
