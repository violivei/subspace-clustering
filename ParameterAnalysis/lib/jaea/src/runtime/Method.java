package runtime;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import runtime.operator.*;
import java.util.*;

/**
 * Class to process parameters passed in Method section, including of
 * <p>
 * - Name, maxPopRange, chromosomeDim, populationDim
 * </p>
 * <p>
 * - Operator
 * </p>
 * @author Le Minh Nghia, NTU-Singapore
 *
 */
public class Method {
	// Search methods
	public String searchMethod;
	/** Population parameters */
	public double maxPopRange = -1;
	public int chromosomeDim = 32;
    public int populationDim = 50;
    @SuppressWarnings("unchecked")
	public List operators;
    /**
     * Constructor - parsing information from Element object
     * @param elem
     */
    @SuppressWarnings("unchecked")
	public Method(Element elem) {
		searchMethod = ConfigContainer.getTextValue(elem,"Name");
		maxPopRange = ConfigContainer.getDoubleValue(elem, "maxPopRange");
		chromosomeDim = ConfigContainer.getIntValue(elem, "chromosomeDim");
		populationDim = ConfigContainer.getIntValue(elem, "populationDim");
		
		operators = new ArrayList();
		NodeList nl = elem.getElementsByTagName("Operator");
		if(nl != null && nl.getLength() > 0) {
			for(int i = 0 ; i < nl.getLength();i++) {
				
				//get the employee element
				Element el = (Element)nl.item(i);
				String name = ConfigContainer.getTextValue(el, "Name");
				//get the Employee object
				Operator e = null;
				if (name.compareTo("Crossover")== 0)
					e = new CrossoverOption(el);
				else if (name.compareTo("Mutation")== 0)
					e = new MutationOption(el);
				else if (name.compareTo("Local Learning")== 0)
					e = new LocalLearnOption(el);
				else if (name.compareTo("Selection")== 0)
					e = new SelectionOption(el);
				else if (name.compareTo("Evaluation")==0)
					e = new EvaluationOption(el);
				else if (name.compareTo("ES Mutation")== 0)
					e = new ESMutationOption(el);
				else if (name.compareTo("SwarmMove")== 0)
					e = new SwarmOption(el);
				else if (name.compareTo("Merging")==0)
					e = new MergeOption(el);
				else 
					continue;
				//add it to list
				operators.add(e);
			}
		}
	}
	@SuppressWarnings("unchecked")
	public String toString()
	{
		String res = "";
		res += "Search Method: " + searchMethod + "\n";
		res += "maxPopRange = " + maxPopRange + "\n";
		res += "chromosomeDim = " + chromosomeDim + "\n";
		res += "populationDim = " + populationDim + "\n";
		Iterator it = operators.iterator();
		while(it.hasNext()) {
			res += "- ";
			res += it.next().toString();
		}
		return res;
	}
}
