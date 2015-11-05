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
public class CategorizeImages extends ExternalClusteringMethod {

    //Categorize image parameters
    ClusteringParameter maxNodeNumber = new ClusteringParameter(10000,"maxNodeNumber");
    ClusteringParameter e_b = new ClusteringParameter(0.0005,"e_b");
    ClusteringParameter e_n = new ClusteringParameter(0.000001,"e_n");
    ClusteringParameter dsbeta = new ClusteringParameter(0.1,"dsbeta");
    ClusteringParameter epsilon_ds = new ClusteringParameter(0.1,"epsilon_ds");    
    ClusteringParameter minwd = new ClusteringParameter(0.5,"minwd");
    ClusteringParameter age_wins = new ClusteringParameter(10,"age_wins");
    ClusteringParameter lp = new ClusteringParameter(0.06,"lp");
    ClusteringParameter a_t = new ClusteringParameter(0.975,"a_t"); //Limiar de atividade .95
    ClusteringParameter epochs = new ClusteringParameter(100,"epochs");
    ClusteringParameter seed = new ClusteringParameter(0, "seed");
    boolean isSubspaceClustering = true;
    boolean filterNoise = true;
    
    //Codebook parameters
    ClusteringParameter maxNodeNumberCB = new ClusteringParameter(10000,"maxNodeNumberCB");
    ClusteringParameter e_bCB = new ClusteringParameter(0.0005,"e_bCB");
    ClusteringParameter e_nCB = new ClusteringParameter(0.000001,"e_nCB");
    ClusteringParameter dsbetaCB = new ClusteringParameter(0.1,"dsbetaCB");
    ClusteringParameter epsilon_dsCB = new ClusteringParameter(0.1,"epsilon_dsCB");    
    ClusteringParameter minwdCB = new ClusteringParameter(0.5,"minwdCB");
    ClusteringParameter age_winsCB = new ClusteringParameter(10,"age_winsCB");
    ClusteringParameter lpCB = new ClusteringParameter(0.06,"lpCB");
    ClusteringParameter a_tCB = new ClusteringParameter(0.975,"a_tCB"); //Limiar de atividade .95
    ClusteringParameter epochsCB = new ClusteringParameter(100,"epochsCB");  
    
    @Override
    public String generateCommandString(String fileName) {
        String commandString = "./exec/" + platform + "/categorizeimages";
        commandString += " -n " + formatF( epochs.getValue());
        commandString += " -e " + formatF(e_b.getValue());
        commandString += " -g " + formatF(e_n.getValue()*e_b.getValue());//makes e_n percentual to e_b
        commandString += " -m " + maxNodeNumber.getIntValue();
        commandString += " -s " + formatF(dsbeta.getValue());
        commandString += " -p " + formatF(epsilon_ds.getValue());
        commandString += " -w " + formatF(minwd.getValue());
        commandString += " -i " + formatF(age_wins.getValue()*epochs.getValue());
        commandString += " -l " + formatF(lp.getValue());
        commandString += " -v " + formatF(a_t.getValue());
        commandString += " -r " + seed.getIntValue();
        commandString += " -f " + fileName;
        
        commandString += " -c ";
                
        commandString += " -n " + formatF(epochsCB.getValue());
        commandString += " -e " + formatF(e_bCB.getValue());
        commandString += " -g " + formatF(e_nCB.getValue()*e_b.getValue());//makes e_n percentual to e_b
        commandString += " -m " + maxNodeNumberCB.getIntValue();
        commandString += " -s " + formatF(dsbetaCB.getValue());
        commandString += " -p " + formatF(epsilon_dsCB.getValue());
        commandString += " -w " + formatF(minwdCB.getValue());
        commandString += " -i " + formatF(age_winsCB.getValue()*epochsCB.getValue());
        commandString += " -l " + formatF(lpCB.getValue());
        commandString += " -v " + formatF(a_tCB.getValue());

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
        return "categorizeImages";
    }
}
