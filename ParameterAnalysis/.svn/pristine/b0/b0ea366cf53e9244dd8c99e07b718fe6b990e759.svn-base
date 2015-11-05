/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package setup;

import weka.subspaceClusterer.Doc;


/**
 *
 * @author hans
 */
public class DOCParameters extends MethodParameters {

    ClusteringParameter alpha;
    ClusteringParameter beta;
    ClusteringParameter maxiter;
    ClusteringParameter k;
    ClusteringParameter w;
    Doc doc;

    public DOCParameters(Doc doc) {
        this.doc = doc;
        setupParameterRanges();
    }

    @Override
    void setupParameterRanges() {
        alpha = new ClusteringParameter(0.001, 0.1, 10.0, ClusteringParameter.StepType.MULT, "alpha");
        beta = new ClusteringParameter(0.1, 0.4, 0.1, ClusteringParameter.StepType.SUM, "beta");
        maxiter = new ClusteringParameter(1024, 1024, 0, ClusteringParameter.StepType.SUM, "maxiter");
        k = new ClusteringParameter(2, 64, 2, ClusteringParameter.StepType.MULT, "k");
        w = new ClusteringParameter(50, 200, 2, ClusteringParameter.StepType.MULT, "w");

        /*
        parameterList.add(alpha);
        parameterList.add(beta);
        //parameterList.add(maxout);
        parameterList.add(k);
        //parameterList.add(numbins);
        parameterList.add(w);
        */
        
        addLHSParameter(alpha, 0.001, 0.1);
        addLHSParameter(beta, 0.1, 0.4);
        //addLHSParameter(maxout, -1, 0);
        addLHSParameter(k, 2, 18);
        addLHSParameter(w, 50, 200);
        
        initLHS(500);
    }

    void setupParameterValues() {
        doc.setALPHA(alpha.getValue());
        doc.setBETA(beta.getValue());
        doc.setMAXITER(maxiter.getIntValue());
        doc.setk(k.getIntValue());
        doc.setw(w.getValue());
    }

    public static void main(String args[]) {

        Doc mineclus = new Doc();
        DOCParameters docParameters = new DOCParameters(mineclus);

        System.out.print("Exp\t");
        docParameters.printParameterNames();
        System.out.println();
        while (!docParameters.hasFinished()) {
            System.out.print(docParameters.getExperimentNumber()+"\t");
            docParameters.printParameterValues();
            System.out.println();
            docParameters.setupNextParameters();
        }
    }
}
