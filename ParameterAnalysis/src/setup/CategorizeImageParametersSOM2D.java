/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package setup;


/**
 *
 * @author hans
 */
public class CategorizeImageParametersSOM2D extends MethodParameters{

    CategorizeImagesSOM2D som;

    public CategorizeImageParametersSOM2D(CategorizeImagesSOM2D som) {
        this.som = som;
        setupParameterRanges();
    }

    @Override
    void setupParameterRanges() {

        som.isSubspaceClustering = false;
        som.filterNoise = false;

        addLHSParameter(som.seed, 0, 10);
        addLHSParameter(som.alpha, 0.001, 0.99);
        addLHSParameter(som.lambda2, 0.01, 2);
        addLHSParameter(som.sigma, 0.3, 2);
        addLHSParameter(som.lambda1, 0.01, 2);
        addLHSParameter(som.tmax, 100, 1000);
        addLHSParameter(som.nx, 6, 6);
        addLHSParameter(som.ny, 5, 5);

        //Codebook
        addLHSParameter(som.nyCB, 5, 200);
        addLHSParameter(som.alphaCB, 0.001, 0.99);
        addLHSParameter(som.lambda2CB, 0.01, 2);
        addLHSParameter(som.sigmaCB, 0.001, 2);
        addLHSParameter(som.lambda1CB, 0.01, 2);
        addLHSParameter(som.tmaxCB, 1, 200);
        initLHS(200);
    }

//    @Override
//    void setupParameterRanges() {
//
//        som.isSubspaceClustering = false;
//        som.filterNoise = false;
//        
//        addLHSParameter(som.seed,	0.556444858	,	0.5564448581	);//-r
//        addLHSParameter(som.e_b,	0.009064497	,	0.0090644971	); //-e
//        addLHSParameter(som.e_n,	0.030965976	,	0.0309659761	); //-g
//        addLHSParameter(som.dsbeta,	0.267120601	,	0.2671206011	); //-s
//        addLHSParameter(som.epsilon_ds,	0.741952078	,	0.7419520781	); //-p
//        addLHSParameter(som.minwd,	0.374173326	,	0.3741733261	); //-w
//        addLHSParameter(som.age_wins,	0.012564680	,	0.0125646801	); //-i
//        addLHSParameter(som.lp ,	0.028015073	,	0.0280150731	); //-l
//        addLHSParameter(som.a_t,	0.996616098	,	0.9966160981	); //-v
//        addLHSParameter(som.epochs,	0.874468522	,	0.8744685221	); //-n
//				
//        //Codebook				
//        addLHSParameter(som.lpCB,	0.090253553	,	0.0902535531	); //-l
//        addLHSParameter(som.a_tCB,	0.968463248	,	0.9684632481	); //-v
//        addLHSParameter(som.age_winsCB,	0.011630029	,	0.0116300291	); //-i
//        addLHSParameter(som.epochsCB,	0.014611928	,	0.0146119281	); //-i
//        addLHSParameter(som.epsilon_dsCB,	0.198764976	,	0.1987649761	); //-p
//
//        initLHS(1);
//    }

    void printComand() {

        som.isSubspaceClustering = false;
        som.filterNoise = false;

//        som.seed.setValue(	7.642504484	);//-r
//        som.e_b.setValue(	0.006339284	); //-e
//        som.e_n.setValue(	0.085256652	); //-g
//        som.dsbeta.setValue(	0.046209524	); //-s
//        som.epsilon_ds.setValue(	0.019371636	); //-p
//        som.minwd.setValue(	0.179886066	); //-w
//        som.age_wins.setValue(	0.094730621	); //-i
//        som.lp.setValue(	0.008323646	); //-l
//        som.a_t.setValue(	0.950148016	); //-v
//        som.epochs.setValue(	23.034050459	); //-n
//		
//        //Codebook		
//        som.lpCB.setValue(	0.000145666	); //-l
//        som.a_tCB.setValue(	0.984303171	); //-v
//        som.age_winsCB.setValue(	0.283316705	); //-i
//        som.epochsCB.setValue(	0.073657791	); //-i
//        som.epsilon_dsCB.setValue(	0.007581760	); //-p



        System.out.println(som.generateCommandString("/home/hans/databases/caltech/20_Caltech256.arff"));

    }
 
    public static void main(String args[]) {

        CategorizeImagesSOM2D som = new CategorizeImagesSOM2D();
        CategorizeImageParametersSOM2D somParameters = new CategorizeImageParametersSOM2D(som);

        somParameters.printParameterRanges();
        System.out.print("Exp\t");
        somParameters.printParameterNames();
        System.out.println();
        while (!somParameters.hasFinished()) {
            System.out.print(somParameters.getExperimentNumber() + "\t");
            somParameters.printParameterValues();
            System.out.println();
            somParameters.setupNextParameters();
        }
        
        somParameters.printComand();
    }

    @Override
    void setupParameterValues() {

    }
}
