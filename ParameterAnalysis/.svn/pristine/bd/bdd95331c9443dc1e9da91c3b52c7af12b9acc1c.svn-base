package setup;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hans
 */
public class SOMAW extends ExternalClusteringMethod {

    ClusteringParameter numNeighbors = new ClusteringParameter(2, "numNeighbors");
    ClusteringParameter nx = new ClusteringParameter(1, "nx");
    ClusteringParameter ny = new ClusteringParameter(10, "ny");
    ClusteringParameter sigma = new ClusteringParameter(2, "sigma");
    ClusteringParameter lambda1 = new ClusteringParameter(0.1, "lambda1");
    ClusteringParameter alpha = new ClusteringParameter(0.05, "alpha");
    ClusteringParameter lambda2 = new ClusteringParameter(0.1, "lambda2");
    ClusteringParameter tmax = new ClusteringParameter(1000, "tmax");
    ClusteringParameter k1 = new ClusteringParameter(0.0001, "k1");
    ClusteringParameter k2 = new ClusteringParameter(0.99, "k2");
    ClusteringParameter k3 = new ClusteringParameter(1.02, "k3");
    ClusteringParameter seed = new ClusteringParameter(0, "seed");
    
    @Override
    public String generateCommandString(String fileName) {
        String commandString = "./exec/somaw";
        commandString += " -x " + nx.getIntValue();
        commandString += " -y " + ny.getIntValue();
        commandString += " -g " + numNeighbors.getIntValue();
        commandString += " -s " + sigma.getValue();
        commandString += " -l " + lambda1.getValue();
        commandString += " -a " + alpha.getValue();
        commandString += " -d " + lambda2.getValue();
        commandString += " -n " + tmax.getIntValue();
        commandString += " -i " + k1.getValue();
        commandString += " -j " + k2.getValue();
        commandString += " -k " + k3.getValue();
        commandString += " -r " + seed.getIntValue();
        commandString += " -f " + fileName;
        
        return commandString;
    }
    
    @Override
    public String getName() {
        return "SOMAW";
    }
}
