/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package setup;


/**
 *
 * @author hans
 */
public class LARFSOMParameters extends MethodParameters{

    LARFSOM som;


    public LARFSOMParameters(LARFSOM larfsom) {
        this.som = larfsom;
        setupParameterRanges();
    }
    
    @Override
    void setupParameterValues() {
        
    }

    @Override
    void setupParameterRanges() {
        /*
        larfsom.a_t.setupRanges(0.85, 0.9501, 0.025, ClusteringParameter.StepType.SUM);
        larfsom.ho_f.setupRanges(0.1, 0.5, 0.2, ClusteringParameter.StepType.SUM);
        larfsom.epsilon.setupRanges(0.05, 0.2, 0.05, ClusteringParameter.StepType.SUM);

        parameterList.add(larfsom.a_t);
        parameterList.add(larfsom.ho_f);
        parameterList.add(larfsom.epsilon);
        */
        
        addLHSParameter(som.seed, 0, 10);
        addLHSParameter(som.a_t, 0.001, 0.2);//addLHSParameter(som.a_t, 0.0001, 0.1);
        addLHSParameter(som.ho_f, 0.001, 0.5);
        addLHSParameter(som.epsilon, 0.001, 0.99);
        addLHSParameter(som.d_max, 50, 500);
        
        initLHS(500);
    }
    
    public static void main(String args[]) {

        /*
         * From	Offset	Op	Steps	To
         * avgerageDimensions	1	2	+	38	75
         * alpha	2	2	*	6	64
         * Total number of experiments: 228
         */
        LARFSOM som = new LARFSOM();
        LARFSOMParameters dssomParameters = new LARFSOMParameters(som);

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
