/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package setup;


/**
 *
 * @author hans
 */
public class GWRDSSOMParameters extends MethodParameters{

    GWRDSSOM gwrdssom;


    public GWRDSSOMParameters(GWRDSSOM gwrdssom) {
        this.gwrdssom = gwrdssom;
        setupParameterRanges();
    }
    
        @Override
    void setupParameterValues() {
        
    }
        
    @Override
    void setupParameterRanges() {
        gwrdssom.a_t.setupRanges(0.55, 0.6, 0.025, ClusteringParameter.StepType.SUM);
        gwrdssom.h_t.setupRanges(0.4, 0.5, 0.05, ClusteringParameter.StepType.SUM);
        gwrdssom.e_b.setupRanges(0.25, 0.75, 0.25, ClusteringParameter.StepType.SUM);
        gwrdssom.a_b.setupRanges(1.01, 1.11, 0.05, ClusteringParameter.StepType.SUM);
        gwrdssom.h0.setupRanges(1.01, 1.21, 0.1, ClusteringParameter.StepType.SUM);
        gwrdssom.dsbeta.setupRanges(0.0001, 0.01, 10, ClusteringParameter.StepType.MULT);

        parameterList.add(gwrdssom.a_t);
        parameterList.add(gwrdssom.h_t);
        parameterList.add(gwrdssom.e_b);
        parameterList.add(gwrdssom.a_b);
        parameterList.add(gwrdssom.h0);
        parameterList.add(gwrdssom.dsbeta);
    }
    
    public static void main(String args[]) {

        /*
         * From	Offset	Op	Steps	To
         * avgerageDimensions	1	2	+	38	75
         * alpha	2	2	*	6	64
         * Total number of experiments: 228
         */
        GWRDSSOM som = new GWRDSSOM();
        GWRDSSOMParameters dssomParameters = new GWRDSSOMParameters(som);

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
