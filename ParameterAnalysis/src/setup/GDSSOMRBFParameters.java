/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package setup;

/**
 *
 * @author hans
 */
public class GDSSOMRBFParameters extends MethodParameters {

    GDSSOMRBF gdsomrbf;

    public GDSSOMRBFParameters(GDSSOMRBF gdsomrbf) {
        this.gdsomrbf = gdsomrbf;
        setupParameterRanges();
    }

    @Override
    void setupParameterRanges() {

////////  realdata
//        gdsomrbf.isSubspaceClustering = false;
//        gdsomrbf.lp.setupRanges(0.01, 0.1, 0.01, ClusteringParameter.StepType.SUM);
//        gdsomrbf.a_t.setupRanges(0.80, 0.98, 0.02, ClusteringParameter.StepType.SUM);

//    Simulated
        gdsomrbf.isSubspaceClustering = true;
        gdsomrbf.lp.setupRanges(0.01, 0.06, 0.01, ClusteringParameter.StepType.SUM);
        gdsomrbf.a_t.setupRanges(0.904, 0.999, 0.005, ClusteringParameter.StepType.SUM);

        parameterList.add(gdsomrbf.lp);
        parameterList.add(gdsomrbf.a_t);     

//////    Fixed
//        gdsomrbf.isSubspaceClustering = true;
//        gdsomrbf.lp.setupRanges(0.02, 0.02, 0.01, ClusteringParameter.StepType.SUM);
//        gdsomrbf.a_t.setupRanges(0.994, 0.994, 0.005, ClusteringParameter.StepType.SUM);

////      Simulated
//        gdsomrbf.isSubspaceClustering = true;
//        gdsomrbf.lp.setupRanges(0.01, 0.06, 0.01, ClusteringParameter.StepType.SUM);
//        gdsomrbf.a_t.setupRanges(0.904, 0.999, 0.005, ClusteringParameter.StepType.SUM);
//        gdsomrbf.e_b.setupRanges(0.05, 0.0005, 0.1, ClusteringParameter.StepType.MULT);
//        gdsomrbf.dsbeta.setupRanges(0.25, 0.0025, 0.1, ClusteringParameter.StepType.MULT);

//        parameterList.add(gdsomrbf.e_b);
//        parameterList.add(gdsomrbf.dsbeta);
    }

    public static void main(String args[]) {

        /*
         * From	Offset	Op	Steps	To avgerageDimensions	1	2	+	38	75 alpha	2	2	*	6
         * 64 Total number of experiments: 228
         */
        GDSSOMRBF som = new GDSSOMRBF();
        GDSSOMRBFParameters dssomParameters = new GDSSOMRBFParameters(som);

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

    @Override
    void setupParameterValues() {
    }
}
