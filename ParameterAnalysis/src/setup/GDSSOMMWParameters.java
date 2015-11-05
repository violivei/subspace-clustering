/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package setup;

/**
 *
 * @author hans
 */
public class GDSSOMMWParameters extends MethodParameters {

    GDSSOMMW som;

    public GDSSOMMWParameters(GDSSOMMW gdsommw) {
        this.som = gdsommw;
        setupParameterRanges();
    }

    @Override
    void setupParameterRanges() {

/*///    Robot
        som.isSubspaceClustering = true;
        som.lp.setupRanges(0.001, 0.05, 0.025, ClusteringParameter.StepType.SUM);
        som.a_t.setupRanges(0.699, 0.999, 0.005, ClusteringParameter.StepType.SUM);

        parameterList.add(som.lp);
        parameterList.add(som.a_t); 
*/

////  realdata
//        som.isSubspaceClustering = false;
//        som.lp.setupRanges(0.01, 0.1, 0.01, ClusteringParameter.StepType.SUM);
//        som.a_t.setupRanges(0.94, 0.98, 0.02, ClusteringParameter.StepType.SUM);
//
//        parameterList.add(som.lp);
//        parameterList.add(som.a_t);   

////  test pendigits
//        som.isSubspaceClustering = false;
//        som.lp.setupRanges(0.01, 0.06, 0.01, ClusteringParameter.StepType.SUM);
//        som.a_t.setupRanges(0.80, 0.98, 0.02, ClusteringParameter.StepType.SUM);
//
//        parameterList.add(som.lp);
//        parameterList.add(som.a_t); 
        
//////    Simulated
//        som.isSubspaceClustering = true;
//        som.lp.setupRanges(0.01, 0.06, 0.01, ClusteringParameter.StepType.SUM);
//        som.a_t.setupRanges(0.969, 0.999, 0.005, ClusteringParameter.StepType.SUM);
//
//        parameterList.add(som.lp);
//        parameterList.add(som.a_t);    

////    Simulated (short range)
//        som.isSubspaceClustering = true;
//        som.lp.setupRanges(0.01, 0.06, 0.01, ClusteringParameter.StepType.SUM);
//        som.a_t.setupRanges(0.999, 0.999, 0.005, ClusteringParameter.StepType.SUM);
//
//        parameterList.add(som.lp);
//        parameterList.add(som.a_t);    

//////    Test AT vs LP
//        som.isSubspaceClustering = true;
//        som.a_t.setupRanges(0.799, 0.999, 0.01, ClusteringParameter.StepType.SUM);
//        som.lp.setupRanges(0.001, 0.051, 0.01, ClusteringParameter.StepType.SUM);

//        parameterList.add(som.a_t);            
//        parameterList.add(som.lp);

//////    Test AT vs LP - Reversed
//        som.isSubspaceClustering = true;
//        som.a_t.setupRanges(0.789, 0.999, 0.03, ClusteringParameter.StepType.SUM);
//        som.lp.setupRanges(0.001, 0.061, 0.001, ClusteringParameter.StepType.SUM);
//
//        parameterList.add(som.a_t);            
//        parameterList.add(som.lp);

//////    Fixed
//        som.isSubspaceClustering = true;
//        som.lp.setupRanges(0.02, 0.02, 0.01, ClusteringParameter.StepType.SUM);
//        som.a_t.setupRanges(0.994, 0.994, 0.005, ClusteringParameter.StepType.SUM);

////      Simulated
//        som.isSubspaceClustering = true;
//        som.lp.setupRanges(0.01, 0.06, 0.01, ClusteringParameter.StepType.SUM);
//        som.a_t.setupRanges(0.904, 0.999, 0.005, ClusteringParameter.StepType.SUM);
//        som.e_b.setupRanges(0.05, 0.0005, 0.1, ClusteringParameter.StepType.MULT);
//        som.dsbeta.setupRanges(0.25, 0.0025, 0.1, ClusteringParameter.StepType.MULT);

//        parameterList.add(som.e_b);
//        parameterList.add(som.dsbeta);

//  Latin Hypercube Sampling (Real);
        som.isSubspaceClustering = false;
        addLHSParameter(som.seed, 0, 0);
        addLHSParameter(som.e_b, 0.0001, 0.1);//0.0005
        addLHSParameter(som.e_n, 0.001, 0.1);//0.002
        addLHSParameter(som.dsbeta, 0.001, 0.1);//0.1
        addLHSParameter(som.epsilon_ds, 0.01, 0.1);//*0
        addLHSParameter(som.minwd, 0, 0.5);//0.5
        addLHSParameter(som.age_wins, 0.1, 1);//10
        addLHSParameter(som.lp, 0.001, 0.1);//0.05        
        addLHSParameter(som.a_t, 0.7, 0.999);//0.999
        addLHSParameter(som.epochs, 10, 100);//0.999 

        initLHS(500);
        
//        ////  Latin Hypercube Sampling (Real);
//        som.isSubspaceClustering = false;
//        addLHSParameter(som.seed, 0, 0);//0
//        addLHSParameter(som.e_b, 0.0001, 0.1);//0.0005
//        addLHSParameter(som.e_n, 0.001, 0.1);//0.002
//        addLHSParameter(som.dsbeta, 0.001, 0.1);//0.1
//        addLHSParameter(som.epsilon_ds, 0.01, 0.1);
//        addLHSParameter(som.minwd, 0, 0.5);//0.5
//        addLHSParameter(som.age_wins, 0, 2);//10
//        addLHSParameter(som.lp, 0.01, 0.1);//0.05
//        addLHSParameter(som.a_t, 0.8, 0.975);//0.999
//        //addLHSParameter(som.epochs, 1, 100);//0.999

        //addLHSParameter(som.lp, 0.042, 0.042);//0.05
        //addLHSParameter(som.a_t, 0.957, 0.957);//0.999
        
        
        //addLHSParameter(som.lp, 0.0759, 0.0759);//0.01246939945760486	0.7032913930706488
        //addLHSParameter(som.a_t, 0.824, 0.824);//.016408439153659235	0.7972126717677905

        //initLHS(50);

////  Latin Hypercube Sampling (Simulated);
//        som.isSubspaceClustering = true;
//        addLHSParameter(som.seed, 0, 10);
//        addLHSParameter(som.e_b, 0.0001, 0.01);//0.0005
//        addLHSParameter(som.e_n, 0.001, 0.1);//0.002
//        addLHSParameter(som.dsbeta, 0.0001, 0.5);//0.1
//        addLHSParameter(som.epsilon_ds, 0.01, 0.5);//*0
//        addLHSParameter(som.minwd, 0, 0.5);//0.5
//        addLHSParameter(som.age_wins, 1, 100);//10
//        addLHSParameter(som.lp, 0.0001, 0.1);//0.05
//        addLHSParameter(som.a_t, 0.97, 0.999);//0.999
//        initLHS(200);

////  Latin Hypercube Sampling (Real);
//        som.isSubspaceClustering = true;
//        addLHSParameter(som.seed, 0, 0);//0
//        addLHSParameter(som.e_b, 0.0005, 0.0005);//0.0005
//        addLHSParameter(som.e_n, 0.002, 0.002);//0.002
//        addLHSParameter(som.dsbeta, 0.1, 0.1);//0.1
//        addLHSParameter(som.epsilon_ds, 0.01, 0.1);//*0
//        addLHSParameter(som.minwd, 0.5, 0.5);//0.5
//        addLHSParameter(som.age_wins, 10, 10);//10
//        addLHSParameter(som.lp, 0.05, 0.05);//0.05
//        addLHSParameter(som.a_t, 0.999, 0.999);//0.999
//
//        initLHS(100);       

////  Caltech 20Small
//        som.isSubspaceClustering = false;
//        som.filterNoise = false;
//        addLHSParameter(som.seed, 0, 0);//0
//        addLHSParameter(som.e_b, 0.0005, 0.0005);//0.0005
//        addLHSParameter(som.e_n, 0.002, 0.002);//0.002
//        addLHSParameter(som.dsbeta, 0.1, 0.1);//0.1
//        addLHSParameter(som.epsilon_ds, 0.01, 0.1);//*0
//        addLHSParameter(som.minwd, 0.5, 0.5);//0.5
//        addLHSParameter(som.age_wins, 10, 10);//10
//        addLHSParameter(som.lp, 0.0001, 0.01);//0.05
//        addLHSParameter(som.a_t, 0.99, 0.999);//0.999
//
//        initLHS(100);  
    }

    public static void main(String args[]) {

        /*
         * From	Offset	Op	Steps	To avgerageDimensions	1	2	+	38	75 alpha	2	2	*	6
         * 64 Total number of experiments: 228
         */
        GDSSOMMW som = new GDSSOMMW();
        GDSSOMMWParameters dssomParameters = new GDSSOMMWParameters(som);

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
