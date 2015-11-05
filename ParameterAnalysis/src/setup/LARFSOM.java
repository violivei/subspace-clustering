package setup;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hans
 */
public class LARFSOM extends ExternalClusteringMethod {

    ClusteringParameter a_t = new ClusteringParameter(0.90,"a_t"); //Limiar de atividade .95
    ClusteringParameter d_max = new ClusteringParameter(100,"d_max");
    ClusteringParameter epsilon = new ClusteringParameter(0.9,"epsilon");
    ClusteringParameter ho_f = new ClusteringParameter(0.10,"ho_f");
    ClusteringParameter maxNodeNumber = new ClusteringParameter(70,"maxNodeNumber");    
    ClusteringParameter epochs = new ClusteringParameter(100,"epochs");
    ClusteringParameter seed = new ClusteringParameter(0, "seed");

    @Override
    public String generateCommandString(String fileName) {
        String commandString = "./exec/larfsom";
        commandString += " -n " + epochs.getIntValue();
        commandString += " -a " + a_t.getValue();
        commandString += " -d " + d_max.getIntValue();
        commandString += " -e " + epsilon.getValue();
        commandString += " -h " + ho_f.getValue();
        commandString += " -m " + maxNodeNumber.getIntValue();
        commandString += " -r " + seed.getIntValue();
        commandString += " -f " + fileName;
        
        return commandString;
    }
    
    @Override
    public String getName() {
        return "LARFSOM";
    }
}
