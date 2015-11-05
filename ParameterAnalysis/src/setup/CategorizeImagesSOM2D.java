package setup;

import java.util.Locale;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hans
 */
public class CategorizeImagesSOM2D extends ExternalClusteringMethod {

    //Categorize image parameters
    ClusteringParameter nx = new ClusteringParameter(4, "nx");
    ClusteringParameter ny = new ClusteringParameter(5, "ny");
    ClusteringParameter numNeighbors = new ClusteringParameter(2, "numNeighbors");
    ClusteringParameter sigma = new ClusteringParameter(0.4, "sigma");
    ClusteringParameter lambda1 = new ClusteringParameter(0.1, "lambda1");
    ClusteringParameter alpha = new ClusteringParameter(0.4, "alpha");
    ClusteringParameter lambda2 = new ClusteringParameter(0.15, "lambda2");
    ClusteringParameter tmax = new ClusteringParameter(1000, "tmax");
    ClusteringParameter seed = new ClusteringParameter(0, "seed");
    
    boolean isSubspaceClustering = true;
    boolean filterNoise = true;
    
    //Codebook parameters
    ClusteringParameter nxCB = new ClusteringParameter(1, "nxCB");
    ClusteringParameter nyCB = new ClusteringParameter(10, "nyCB");
    ClusteringParameter numNeighborsCB = new ClusteringParameter(2, "numNeighborsCB");
    ClusteringParameter sigmaCB = new ClusteringParameter(0.4, "sigmaCB");
    ClusteringParameter lambda1CB = new ClusteringParameter(0.1, "lambda1CB");
    ClusteringParameter alphaCB = new ClusteringParameter(0.4, "alphaCB");
    ClusteringParameter lambda2CB = new ClusteringParameter(0.15, "lambda2CB");
    ClusteringParameter tmaxCB = new ClusteringParameter(2, "tmaxCB");
    
    @Override
    public String generateCommandString(String fileName) {
        String commandString = "./exec/" + platform + "/categorizeimagessom2d";
        commandString += " -f " + fileName;
        commandString += " -x " + nx.getIntValue();
        commandString += " -y " + ny.getIntValue();
        commandString += " -g " + numNeighbors.getIntValue();
        commandString += " -s " + formatF(sigma.getValue());
        commandString += " -l " + formatF(lambda1.getValue());
        commandString += " -a " + formatF(alpha.getValue());
        commandString += " -d " + formatF(lambda2.getValue());
        commandString += " -n " + formatF(tmax.getValue());
        
        commandString += " -c ";
                
        commandString += " -x " + nxCB.getIntValue();
        commandString += " -y " + nyCB.getIntValue();
        commandString += " -g " + numNeighborsCB.getIntValue();
        commandString += " -s " + formatF(sigmaCB.getValue());
        commandString += " -l " + formatF(lambda1CB.getValue());
        commandString += " -a " + formatF(alphaCB.getValue());
        commandString += " -d " + formatF(lambda2CB.getValue());
        commandString += " -n " + formatF(tmaxCB.getValue());


        if (!isSubspaceClustering)
            commandString += " -P ";

        if (!filterNoise)
            commandString += " -o ";

        return commandString;
    }

    String formatF(double f) {
       return String.format(Locale.ENGLISH, "%.9f",f);
    }
        
    @Override
    public String getName() {
        return "categorizeImages";
    }
}
