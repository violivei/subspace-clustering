package runtime.operator;

import optimization.operator.population.Selection;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import runtime.ConfigContainer;
/**
 * Parser for individual learning operator
 * @author Le Minh Nghia, NTU-Singapore
 *
 */
public class LocalLearnOption extends Operator{
	public int selectionType;
	public int poolsize = -1;
	public String learningScheme;
	public int intensity = -1;
	public double stepsize = 1.0;
	public double accuracy = 1E-5;
	public LocalLearnOption(Element elem) 
	{
		Name = "Local Learning";
		String temp = ConfigContainer.getTextValue(elem, "selectionType");
		if (temp.compareTo("RouletteWheel") == 0)
			this.selectionType = Selection.RouletteWheel;
		else if (temp.compareTo("Tournament") == 0)
			this.selectionType = Selection.Tournament;
		else if (temp.compareTo("SUS") == 0)
			this.selectionType = Selection.SUS;
		else if (temp.compareTo("KBest")==0)
			this.selectionType = Selection.KBest;
		else 
			this.selectionType = -1;
		// Optional parameters
		temp = ConfigContainer.getTextValue(elem, "poolsize");
		if (temp != null)
		{
			poolsize = Integer.parseInt(temp);
		}
		NodeList nl = elem.getElementsByTagName("learningScheme");
		if(nl != null && nl.getLength() > 0) {
			for(int i = 0 ; i < nl.getLength();i++) {
				Element el = (Element)nl.item(i);
				this.learningScheme = ConfigContainer.getTextValue(el, "Name");
				this.intensity = ConfigContainer.getIntValue(el, "Intensity");
				this.accuracy = ConfigContainer.getDoubleValue(el, "Accuracy");
				this.stepsize = ConfigContainer.getDoubleValue(el, "Stepsize");
			}
		}
		else {
			System.out.println("ERROR: No local search specified...");
			System.exit(0);
		}

	}
	public String toString()
	{
		String res = "";
		res += "Operator = " + this.Name + "\n";
		res += "learningScheme = " + learningScheme + "\n";
		res += "Poolsize = " + this.poolsize + "\n";
		res += "Max evaluation = " + this.intensity + "\n";
		res += "Accuracy = " + this.accuracy + "\n";
		res += "Stepsize = " + this.stepsize + "\n";
 		res += "Selection type = ";
		if (selectionType == Selection.KBest) res += "KBest\n";
		else if (selectionType == Selection.Tournament) res+= "Tournament\n";
		else if (selectionType == Selection.RouletteWheel) res+= "RouletteWheel\n";
		else if (selectionType == Selection.SUS) res+= "Universal Stochastic\n";
		else res += "NONE\n";
			
		return res;
	}
}
