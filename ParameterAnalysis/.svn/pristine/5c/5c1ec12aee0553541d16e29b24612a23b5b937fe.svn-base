package runtime.operator;

import org.w3c.dom.Element;
import runtime.*;
import optimization.operator.population.*;
/**
 * Parser for crossover operator
 * @author Le Minh Nghia, NTU-Singapore
 *
 */
public class CrossoverOption extends Operator{
	public double crossoverProb = 0.8;
	public int crossoverType;
	
	public CrossoverOption(Element elem) 
	{
		Name = "Crossover";
		crossoverProb = ConfigContainer.getDoubleValue(elem, "crossoverProb");
		String temp = ConfigContainer.getTextValue(elem, "crossoverType");
		if (temp.compareTo("OnePoint") == 0)
			this.crossoverType = Crossover.ctOnePoint;
		else if (temp.compareTo("TwoPoint")== 0)
			this.crossoverType = Crossover.ctTwoPoint;
		else if (temp.compareTo("Uniform") == 0)
			this.crossoverType = Crossover.ctUniform;
		else if (temp.compareTo("Arithmetic") == 0)
			this.crossoverType = Crossover.ctArithmetic;
		else this.crossoverType = -1;
	}
	public String toString()
	{
		String res = "";
		res += "Operator = " + this.Name + "\n";
		res += "crossoverProb = " + crossoverProb + "\n";
		res += "crossoverType = ";
		switch (crossoverType) 
		{
			case Crossover.ctArithmetic:
				res+= "Arithmetic crossover \n"; break;
			case Crossover.ctOnePoint:
				res+= "One-point crossover \n"; break;
			case Crossover.ctTwoPoint:
				res+= "Two-point crossover \n"; break;
			case Crossover.ctUniform:
				res+= "Uniform crossover \n"; break;
		}
		return res;
	}
}
