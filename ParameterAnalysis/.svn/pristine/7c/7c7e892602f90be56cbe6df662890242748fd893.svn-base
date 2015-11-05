package setup;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hans
 */
public class GNGSOM extends ExternalClusteringMethod {

    ClusteringParameter age_max = new ClusteringParameter(16, "age_max");
    ClusteringParameter step_max = new ClusteringParameter(300, "step_max");
    ClusteringParameter alfa = new ClusteringParameter(0.5, "alfa");
    ClusteringParameter beta = new ClusteringParameter(0.0005, "beta");
    ClusteringParameter e_b = new ClusteringParameter(0.1,"e_b");
    ClusteringParameter e_n = new ClusteringParameter(0.0006,"e_n");
    ClusteringParameter maxNodeNumber = new ClusteringParameter(64,"maxNodeNumber");
    
    ClusteringParameter epochs = new ClusteringParameter(10000,"epochs");

    @Override
    public String generateCommandString(String fileName) {
        String commandString = "./exec/gngsom";
        commandString += " -n " + epochs.getValue();
        commandString += " -x " + age_max.getValue();
        commandString += " -y " + step_max.getValue();
        commandString += " -a " + alfa.getValue();
        commandString += " -b" + beta.getValue();
        commandString += " -e " + e_b.getValue();
        commandString += " -g " + e_n.getValue();
        commandString += " -m " + maxNodeNumber.getValue();        
        commandString += " -f " + fileName;
        
        return commandString;
    }
    
    @Override
    public String getName() {
        return "GNGSOM";
    }
}
