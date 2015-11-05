package setup;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author hans
 */
public class DSSOM extends ExternalClusteringMethod {

    ClusteringParameter nx = new ClusteringParameter(1, "nx");
    ClusteringParameter ny = new ClusteringParameter(10, "ny");
    ClusteringParameter numNeighbors = new ClusteringParameter(2, "numNeighbors");
    ClusteringParameter sigma = new ClusteringParameter(0.4, "sigma");
    ClusteringParameter lambda1 = new ClusteringParameter(0.1, "lambda1");
    ClusteringParameter alpha = new ClusteringParameter(0.4, "alpha");
    ClusteringParameter lambda2 = new ClusteringParameter(0.15, "lambda2");
    ClusteringParameter beta = new ClusteringParameter(0.01, "beta");
    ClusteringParameter tmax = new ClusteringParameter(1000, "tmax");
    ClusteringParameter minDW = new ClusteringParameter(0.01, "minDW");
    ClusteringParameter seed = new ClusteringParameter(0, "seed");

/*
    int numNeighbors = 2;    //Num numNeighbors: City block distance to the farthest neigbors
    double sigma = 2;      //Sigma0: Neighborhood function standard deviation
    double lambda1 = 0.2;    //Neighborhood function decay with time
    double alpha = 0.15;      //Alpha0: Learning rate
    double lambda2 = 0.2;   //Learning rate decay with time
    double beta = 0.1;      //Beta0: Distance average rate
    double tmax = 2000;      //Maximum number of iterations
    double minDW = 0.001;     //Minimum value for dsWeights
    int numWinners = 1;      //Maximun number of Winners per input stimulus
    double epsilonRho = 0.9; //Minimum relevance required to search for another winner
    double outliers_threshold = 0;    //use outliers
/**/

    @Override
    public String generateCommandString(String fileName) {
        String commandString = "./exec/dssom";
        commandString += " -x " + nx.getIntValue();
        commandString += " -y " + ny.getIntValue();
        commandString += " -g " + numNeighbors.getIntValue();
        commandString += " -s " + sigma.getValue();
        commandString += " -l " + lambda1.getValue();
        commandString += " -a " + alpha.getValue();
        commandString += " -d " + lambda2.getValue();
        commandString += " -b " + beta.getValue();
        commandString += " -n " + tmax.getIntValue();
        commandString += " -m " + minDW.getValue();
        commandString += " -r " + seed.getIntValue();
        commandString += " -f " + fileName;

        return commandString;
    }

    @Override
    public String getName() {
        return "DSSOM";
    }
}
