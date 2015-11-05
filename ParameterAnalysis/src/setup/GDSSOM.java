package setup;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hans
 */
public class GDSSOM extends ExternalClusteringMethod {

    ClusteringParameter maxNodeNumber = new ClusteringParameter(20,"maxNodeNumber");
    ClusteringParameter e_b = new ClusteringParameter(0.0005,"e_b");
    ClusteringParameter e_n = new ClusteringParameter(0.000001,"e_n");
    //DS
    ClusteringParameter dsbeta = new ClusteringParameter(0.1,"dsbeta");
    ClusteringParameter epsilon_ds = new ClusteringParameter(0.001,"epsilon_ds");    
    ClusteringParameter minwd = new ClusteringParameter(0.5,"minwd");
    ClusteringParameter age_wins = new ClusteringParameter(10,"age_wins");
    ClusteringParameter lp = new ClusteringParameter(0.06,"lp");
    ClusteringParameter a_t = new ClusteringParameter(0.975,"a_t"); //Limiar de atividade .95
    
    ClusteringParameter epochs = new ClusteringParameter(100,"epochs");

    @Override
    public String generateCommandString(String fileName) {
        String commandString = "./exec/gdssom";
        commandString += " -n " + epochs.getValue();
        commandString += " -e " + e_b.getValue();
        commandString += " -g " + e_n.getValue();
        commandString += " -m " + maxNodeNumber.getValue();
        commandString += " -s " + dsbeta.getValue();
        commandString += " -p " + epsilon_ds.getValue();     
        commandString += " -w " + minwd.getValue(); 
        commandString += " -i " + age_wins.getValue(); 
        commandString += " -l " + lp.getValue(); 
        commandString += " -v " + a_t.getValue();
        commandString += " -f " + fileName;
        
        return commandString;
    }
    
    @Override
    public String getName() {
        return "GDSSOM";
    }
}
