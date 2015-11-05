package runtime.operator;

import org.w3c.dom.Element;
import optimization.operator.population.*;
import runtime.ConfigContainer;

/**
 * Parser for mutation operator
 * @author Le Minh Nghia, NTU-Singapore
 *
 */
public class MutationOption extends Operator{
	public double mutationProb = 0.03;
	public int mutationType;
	public double mutationRadius = 1.0;
	public MutationOption(Element elem) 
	{
		Name = "Mutation";
		mutationProb = ConfigContainer.getDoubleValue(elem, "mutationProb");
		String temp = ConfigContainer.getTextValue(elem, "mutationType");
		if (temp.compareTo("Uniform")==0)
			this.mutationType = Mutation.uniform;
		else if (temp.compareTo("Gaussian")==0)
			this.mutationType = Mutation.gaussian;
		else
			this.mutationType = -1;
		
		this.mutationRadius = ConfigContainer.getDoubleValue(elem, "mutationRadius");
	}
	public String toString()
	{
		String res = "";
		res += "Operator = " + this.Name + "\n";
		res += "mutationProb = " + mutationProb + "\n";
		res += "mutationType = " ;
		switch (mutationType)
		{
		case Mutation.uniform:
			res+= "Uniform mutation \n"; break;
		case Mutation.gaussian:
			res+= "Gaussian mutation \n"; break;
		}
		res += "Gaussian radius = " + this.mutationRadius + "\n"; 
		return res;
	}
}
