/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package setup;

import weka.subspaceClusterer.Statpc;


/**
 *
 * @author hans
 */
public class STATPCParameters extends MethodParameters {

    ClusteringParameter alphah;
    ClusteringParameter alpha0;
    ClusteringParameter alphak;
    Statpc statpc;

    public STATPCParameters(Statpc statpc) {
        this.statpc = statpc;
        setupParameterRanges();
    }

    @Override
    void setupParameterRanges() {
        alphah = new ClusteringParameter(0.001, 0.1, 10.0, ClusteringParameter.StepType.MULT, "alphah");
        alpha0 = new ClusteringParameter(0.1, 0.4, 0.1, ClusteringParameter.StepType.SUM, "alpha0");
        alphak = new ClusteringParameter(1, 1, 0, ClusteringParameter.StepType.SUM, "alphak");

        /*
        parameterList.add(alphah);
        parameterList.add(alpha0);
        parameterList.add(alphak);
        */
        
        addLHSParameter(alphah, 0, 20);
        addLHSParameter(alpha0, 0, 20);
        addLHSParameter(alphak, 0, 0.5);
        
        initLHS(500);
    }

    void setupParameterValues() {
        statpc.setALPHA_h(Math.pow(10, -alphah.getValue()));
        statpc.setALPHA_0(Math.pow(10, -alpha0.getValue()));
        statpc.setALPHA_k(Math.pow(10, -alphak.getValue()));
    }

    public static void main(String args[]) {
        Statpc statpc = new Statpc();
        STATPCParameters statpcParameters = new STATPCParameters(statpc);

        System.out.print("Exp\t");
        statpcParameters.printParameterNames();
        System.out.println();
        while (!statpcParameters.hasFinished()) {
            System.out.print(statpcParameters.getExperimentNumber()+"\t");
            statpcParameters.printParameterValues();
            System.out.println();
            statpcParameters.setupNextParameters();
        }
    }
}
