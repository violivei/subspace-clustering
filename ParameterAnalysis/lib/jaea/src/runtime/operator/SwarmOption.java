package runtime.operator;

import org.w3c.dom.Element;

import runtime.ConfigContainer;
/**
 * Parser for swarm move operator
 * @author Le Minh Nghia, NTU-Singapore
 *
 */
public class SwarmOption extends Operator{
	/** inertia to incorporate the previous velocity */
	public double inertia = -1;
	/** learning rate on best experience */
	public double localLearnRate = -1;
	/** learning rate on best global experience */
	public double neighborLearnRate = -1;
	public SwarmOption(Element elem)
	{
		Name = "SwarmMove";
		// Optional parameters
		String temp = ConfigContainer.getTextValue(elem, "inertia");
		if (temp != null)
		{
			inertia = Double.parseDouble(temp);
		}
		temp = ConfigContainer.getTextValue(elem, "localLearnRate");
		if (temp != null)
		{
			localLearnRate = Double.parseDouble(temp);
		}
		temp = ConfigContainer.getTextValue(elem, "neighborLearnRate");
		if (temp != null)
		{
			neighborLearnRate = Double.parseDouble(temp);
		}
	}
	public String toString()
	{
		String res = "";
		res += "Operator = " + this.Name + "\n";
		if (inertia > 0)
			res += "inertia (optional) = " + inertia + "\n";
		if (this.localLearnRate > 0)
			res += "localLearnRate (optional) = " + localLearnRate + "\n";
		if (this.neighborLearnRate > 0)
			res += "neighborLearnRate (optional) = " + neighborLearnRate + "\n";
		return res;
	}
}
