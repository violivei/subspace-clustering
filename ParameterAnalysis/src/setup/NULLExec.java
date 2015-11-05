package setup;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author hans
 */
public class NULLExec extends ExternalClusteringMethod {

    ClusteringParameter ny = new ClusteringParameter(10, "ny");
   
    @Override
    public String generateCommandString(String fileName) {
        exec = "";
        String commandString = "echo nullMethod";
        
        return commandString;
    }

    @Override
    public String getName() {
        return "nullMethod";
    }
}
