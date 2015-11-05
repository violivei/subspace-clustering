/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package setup;

import java.util.ArrayList;
import java.util.Locale;
import optimization.tools.Permutation;
import optimization.sampling.SimpleLH;
import setup.ClusteringParameter.StepType;

/**
 *
 * @author hans
 */
public abstract class MethodParameters {
    int experimentNumber = 1;
    ArrayList<ClusteringParameter> parameterList = new ArrayList<ClusteringParameter>();
    boolean finished = false;
    boolean lhs = false;
    int nLHS;
    int iLHS;
    double[][] LHSMAT = null;

    abstract void setupParameterRanges();
    
    void addParameter(ClusteringParameter parameter, double start, double end, double step, ClusteringParameter.StepType stepType) {
        parameter.setupRanges(start, end, step, stepType);
        parameterList.add(parameter);
    }
    
    void addLHSParameter(ClusteringParameter parameter, double start, double end) {
        parameter.setupRanges(start, end, end-start, StepType.LHS);
        parameterList.add(parameter);
    }
    
    void initLHS(int nLHS, long seed) {
        lhs = true;
        this.nLHS = nLHS;
        iLHS = 1;
        
        LHSMAT = new double[nLHS][parameterList.size()];
        int c=0;        
        Permutation permGen = new Permutation(seed);
        for (ClusteringParameter parameter:parameterList) {
                double [] perm = permGen.getFloatPerm(parameter.getStart(), parameter.getEnd(), nLHS);
                for (int r = 0; r < nLHS; r++) {
                        LHSMAT[r][c] = perm[r];
                }
                parameter.setValue(LHSMAT[0][c]);
                c++;
        }
    }
    
    void initLHS(int nLHS) {
        initLHS(nLHS, System.currentTimeMillis());
    }
    
    abstract void setupParameterValues();

    void setupNextParameters() {

       if (finished)
           return;
       
       if (lhs) {

         if (iLHS>=nLHS) {
            finished=true;
            return;  
         }
         
         int c=0;
         for (ClusteringParameter parameter:parameterList) {
             parameter.setValue(LHSMAT[iLHS][c]);
             c++;
         }
         experimentNumber++;
         iLHS++;
         setupParameterValues();
         
       } else {
            if (parameterList.isEmpty()) {
                finished=true;
                return;
            }

            int i=0;
            while (parameterList.get(i).hasFinished()) {
                parameterList.get(i).restart();
                i++;
                if (i>=parameterList.size()) {
                     finished=true;
                     return;
                }
            }
            parameterList.get(i).next();
            experimentNumber++;
            setupParameterValues();
       }
    }

    boolean hasFinished() {
        return finished;
    }
     
    void restartAllValues() {
         for (ClusteringParameter cp: parameterList) {
             cp.restart();
         }
         experimentNumber = 1;
         iLHS = 0;
     }
     
    void printParameterNames() {
        for (ClusteringParameter p:parameterList) {
            System.out.print(p.getName() + "\t");
        }
    }
    
    void printParameterRanges() {
        for (ClusteringParameter p:parameterList) {
            double value = p.getStart();
            System.out.print(p.getName() + ":      \t[\t");
            while (value<=p.getEnd()+0.00000000001d) {
                System.out.print(value+"\t");
                if (p.getStepType()!=ClusteringParameter.StepType.MULT) {
                    value += p.getStep();
                } else {
                    value *= p.getStep();
                }
            }
            System.out.println("]");
        }
    }
        
    void printParameterValues() {
        for (ClusteringParameter p:parameterList) {
            System.out.print(formatF(p.getValue())+"\t");
        }
    }
    
    String formatF(double f) {
       return String.format(Locale.ENGLISH, "%.9f",f);
    }

    public int getExperimentNumber() {
        return experimentNumber;
    }
    
    public static void main(String[] args) {
        int N=200;
        int dim = 2;
        
        double [][] LH = new double [N][dim];
                Permutation permGen = new Permutation();
                for (int c = 0; c < dim; c++) {
                        double [] perm = permGen.getFloatPerm(0, 1, N);
                        for (int r = 0; r < N; r++) {
                                LH[r][c] = perm[r];
                        }
                }
        
        
        System.out.print("[");
        for (int i=0; i<N; i++) {
            for (int j=0; j<dim; j++) {
                System.out.print(LH[i][j] + " ");
            }
            System.out.print(";\n");
        }
        System.out.println("]");
    }
}
