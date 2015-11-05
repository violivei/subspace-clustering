package setup;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hans
 */
public class LARFDSSOM extends ExternalClusteringMethod {

   /* ClusteringParameter a_t = new ClusteringParameter(0.90,"a_t"); //Limiar de atividade .95
    ClusteringParameter d_max = new ClusteringParameter(1,"d_max");
    ClusteringParameter epsilon = new ClusteringParameter(0.9,"epsilon");
    ClusteringParameter ho_f = new ClusteringParameter(0.10,"ho_f");
    ClusteringParameter maxNodeNumber = new ClusteringParameter(64,"maxNodeNumber");
    //DS
    ClusteringParameter dsbeta = new ClusteringParameter(0.001,"dsbeta");
    ClusteringParameter epsilon_ds = new ClusteringParameter(0.0,"epsilon_ds");
    
    ClusteringParameter epochs = new ClusteringParameter(50,"epochs");*/
    
    ClusteringParameter maxNodeNumber = new ClusteringParameter(5, "maxNodeNumber");
    ClusteringParameter e_b = new ClusteringParameter(0.0005, "e_b");
    ClusteringParameter e_n = new ClusteringParameter(0.000001, "e_n");
    ClusteringParameter dsbeta = new ClusteringParameter(0.1, "dsbeta");
    ClusteringParameter epsilon_ds = new ClusteringParameter(0.001, "epsilon_ds");
    ClusteringParameter minwd = new ClusteringParameter(0.5, "minwd");
    ClusteringParameter age_wins = new ClusteringParameter(2, "age_wins");
    ClusteringParameter lp = new ClusteringParameter(0.1, "lp");
    ClusteringParameter a_t = new ClusteringParameter(0.700, "a_t");
    ClusteringParameter epocs = new ClusteringParameter(10, "epocs");

    @Override
    public String generateCommandString(String fileName) {
        String commandString = "./exec/larfdssom";
        commandString += " -m " + maxNodeNumber.getIntValue();
        commandString += " -e " + e_b.getValue();
        commandString += " -g " + e_n.getValue();
        commandString += " -s " + dsbeta.getValue();
        commandString += " -p " + epsilon_ds.getValue();
        commandString += " -w " + minwd.getValue();
        commandString += " -i " + age_wins.getValue();
        commandString += " -l " + lp.getValue();
        commandString += " -v " + a_t.getValue();
        commandString += " -n " + epocs.getIntValue();
        commandString += " -f " + fileName;
        
        /*        commandString += " -n " + epochs.getValue();
        commandString += " -a " + a_t.getValue();
        commandString += " -d " + d_max.getValue();
        commandString += " -e " + epsilon.getValue();
        commandString += " -h " + ho_f.getValue();
        commandString += " -m " + maxNodeNumber.getValue();
        commandString += " -s " + dsbeta.getValue();
        commandString += " -p " + epsilon_ds.getValue();
        commandString += " -f " + fileName;*/
        
        return commandString;
    }
    
    @Override
    public String getName() {
        return "LARFDSSOM";
    }
}
