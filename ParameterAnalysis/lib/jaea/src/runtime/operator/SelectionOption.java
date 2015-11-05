package runtime.operator;

import org.w3c.dom.Element;

import runtime.ConfigContainer;
import optimization.operator.population.*;

/**
 * Parser for selection operator
 * @author Le Minh Nghia, NTU-Singapore
 *
 */
public class SelectionOption extends Operator {
	public int selectionType;
	public int poolsize = -1;
	public SelectionOption(Element elem) 
	{
		Name = "Selection";
		String temp = ConfigContainer.getTextValue(elem, "selectionType");
		if (temp.compareTo("RouletteWheel") == 0)
			this.selectionType = Selection.RouletteWheel;
		else if (temp.compareTo("Ranking") == 0)
			this.selectionType = Selection.Ranking;
		else if (temp.compareTo("Tournament") == 0)
			this.selectionType = Selection.Tournament;
		else if (temp.compareTo("Random") == 0)
			this.selectionType = Selection.Random;
		else if (temp.compareTo("SUS") == 0)
			this.selectionType = Selection.SUS;
		else 
			this.selectionType = -1;
		// Optional parameters
		temp = ConfigContainer.getTextValue(elem, "poolsize");
		if (temp != null)
		{
			poolsize = Integer.parseInt(temp);
		}
	}
	public String toString()
	{
		String res = "";
		res += "Operator = " + this.Name + "\n";
		res += "selectionType = ";
		switch (selectionType )
		{
			case Selection.RouletteWheel:
				res += "RW Selection \n"; break;
			case Selection.SUS:
				res += "SUS Selection \n"; break;
			case Selection.Ranking:
				res += "Ranking Selection \n"; break;
			case Selection.Random:
				res += "Randome \n"; break;
			case Selection.Tournament:
				res += "Tournament \n"; break;
		}
		res += "poolsize (optional) = " + poolsize + "\n";
		return res;
	}
}
