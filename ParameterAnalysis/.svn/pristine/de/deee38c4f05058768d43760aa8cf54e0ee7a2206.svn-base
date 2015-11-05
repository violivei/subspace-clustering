package setup;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author hans
 */
public class SOM2D extends ExternalClusteringMethod {

    ClusteringParameter nx = new ClusteringParameter(1, "nx");
    ClusteringParameter ny = new ClusteringParameter(10, "ny");
    ClusteringParameter numNeighbors = new ClusteringParameter(2, "numNeighbors");
    ClusteringParameter sigma = new ClusteringParameter(0.4, "sigma");
    ClusteringParameter lambda1 = new ClusteringParameter(0.1, "lambda1");
    ClusteringParameter alpha = new ClusteringParameter(0.4, "alpha");
    ClusteringParameter lambda2 = new ClusteringParameter(0.15, "lambda2");
    ClusteringParameter tmax = new ClusteringParameter(1000, "tmax");
    ClusteringParameter seed = new ClusteringParameter(0, "seed");

    @Override
    public String generateCommandString(String fileName) {
        String commandString = "./exec/som2d";
        commandString += " -x " + nx.getIntValue();
        commandString += " -y " + ny.getIntValue();
        commandString += " -g " + numNeighbors.getIntValue();
        commandString += " -s " + sigma.getValue();
        commandString += " -l " + lambda1.getValue();
        commandString += " -a " + alpha.getValue();
        commandString += " -d " + lambda2.getValue();
        commandString += " -n " + tmax.getIntValue();
        commandString += " -r " + seed.getIntValue();
        commandString += " -f " + fileName;
        
        return commandString;
    }

    @Override
    public String getName() {
        return "SOM2D";
    }
}
