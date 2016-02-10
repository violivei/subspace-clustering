/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package setup;


/**
 *
 * @author hans
 */
public class LARFDSSOMParameters extends MethodParameters{

    LARFDSSOM som;


    public LARFDSSOMParameters(LARFDSSOM larfdssom) {
        this.som = larfdssom;
        setupParameterRanges();
    }
    
        @Override
    void setupParameterValues() {
        
    }
        
    @Override
    void setupParameterRanges() {
        /*som.a_t.setupRanges(0.85, 0.9501, 0.025, ClusteringParameter.StepType.SUM);
        som.ho_f.setupRanges(0.1, 0.5, 0.2, ClusteringParameter.StepType.SUM);
        som.dsbeta.setupRanges(0.0001, 0.01, 10, ClusteringParameter.StepType.MULT);

        parameterList.add(som.a_t);
        parameterList.add(som.ho_f);
        parameterList.add(som.dsbeta);*/
        
        //som.maxNodeNumber.setupRanges(10, 100, 10, ClusteringParameter.StepType.SUM);
        //som.e_b.setupRanges(0.05, 1, 0.05, ClusteringParameter.StepType.SUM);
        //som.e_n.setupRanges(0.05, 1, 0.05, ClusteringParameter.StepType.SUM);
        //som.dsbeta.setupRanges(0.1, 1.00, 0.025, ClusteringParameter.StepType.SUM);
        //som.epsilon_ds.setupRanges(0.001, 0.1, 0.025, ClusteringParameter.StepType.SUM);
        //som.minwd.setupRanges(0.5, 1.00, 0.025, ClusteringParameter.StepType.SUM);
        //som.age_wins.setupRanges(2, 20, 5, ClusteringParameter.StepType.SUM);
        som.lp.setupRanges(0.001, 0.1, 0.005, ClusteringParameter.StepType.SUM);
        //som.a_t.setupRanges(0.975, 1.0, 0.001, ClusteringParameter.StepType.SUM);
        //som.epocs.setupRanges(10, 10, 0, ClusteringParameter.StepType.SUM);
        
        //parameterList.add(som.maxNodeNumber);
        //parameterList.add(som.e_b);
        //parameterList.add(som.e_n);
        //parameterList.add(som.dsbeta);
        //parameterList.add(som.epsilon_ds);
        //parameterList.add(som.minwd);
        //parameterList.add(som.age_wins);
        parameterList.add(som.lp);
        //parameterList.add(som.a_t);        
        //parameterList.add(som.epocs);
        
    }
    
    public static void main(String args[]) {

        /*
         * From	Offset	Op	Steps	To
         * avgerageDimensions	1	2	+	38	75
         * alpha	2	2	*	6	64
         * Total number of experiments: 228
         */
        LARFDSSOM som = new LARFDSSOM();
        LARFDSSOMParameters dssomParameters = new LARFDSSOMParameters(som);

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
