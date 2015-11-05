/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package setup;


/**
 *
 * @author hans
 */
public class GDSSOMParameters extends MethodParameters{

    GDSSOM gdsom;


    public GDSSOMParameters(GDSSOM gdsom) {
        this.gdsom = gdsom;
        setupParameterRanges();
    }
    
    @Override
    void setupParameterRanges() {
        gdsom.lp.setupRanges(0.01, 0.06, 0.01, ClusteringParameter.StepType.SUM);
        gdsom.a_t.setupRanges(0.95, 0.995, 0.005, ClusteringParameter.StepType.SUM);

        parameterList.add(gdsom.lp);
        parameterList.add(gdsom.a_t);
    }
    
    public static void main(String args[]) {

        /*
         * From	Offset	Op	Steps	To
         * avgerageDimensions	1	2	+	38	75
         * alpha	2	2	*	6	64
         * Total number of experiments: 228
         */
        GDSSOM som = new GDSSOM();
        GDSSOMParameters dssomParameters = new GDSSOMParameters(som);

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

    @Override
    void setupParameterValues() {
       
    }
}
