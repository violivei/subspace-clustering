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
public class CategorizeImagesSOM2DJoint extends CategorizeImagesSOM2D {

        //Codebook parameters
    ClusteringParameter nxCBA = new ClusteringParameter(1, "nxCBA");
    ClusteringParameter nyCBA = new ClusteringParameter(10, "nyCBA");
    ClusteringParameter numNeighborsCBA = new ClusteringParameter(2, "numNeighborsCBA");
    ClusteringParameter sigmaCBA = new ClusteringParameter(0.4, "sigmaCBA");
    ClusteringParameter lambda1CBA = new ClusteringParameter(0.1, "lambda1CBA");
    ClusteringParameter alphaCBA = new ClusteringParameter(0.4, "alphaCBA");
    ClusteringParameter lambda2CBA = new ClusteringParameter(0.15, "lambda2CBA");
    ClusteringParameter tmaxCBA = new ClusteringParameter(2, "tmaxCBA");
    
    @Override
    public String generateCommandString(String fileName) {
        String commandString = "./exec/" + platform + "/joinstimulisom2d";
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

        commandString += " -A -c ";
                
        commandString += " -x " + nxCBA.getIntValue();
        commandString += " -y " + nyCBA.getIntValue();
        commandString += " -g " + numNeighborsCBA.getIntValue();
        commandString += " -s " + formatF(sigmaCBA.getValue());
        commandString += " -l " + formatF(lambda1CBA.getValue());
        commandString += " -a " + formatF(alphaCBA.getValue());
        commandString += " -d " + formatF(lambda2CBA.getValue());
        commandString += " -n " + formatF(tmaxCBA.getValue());
        
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
        return "joinstimulisom2d";
    }
}
