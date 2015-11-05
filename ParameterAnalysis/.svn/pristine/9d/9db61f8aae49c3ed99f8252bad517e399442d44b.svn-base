package setup;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author hans
 */
public class GPCA extends ExternalClusteringMethod {

    ClusteringParameter ny = new ClusteringParameter(10, "ny");
    
    @Override
    public String generateCommandString(String fileName) {
        String commandString = "echo ";
        commandString += fileName + " " + (int)ny.getValue();
        
        return commandString;
    }

    @Override
    public String getName() {
        return "GPCA";
    }
}
