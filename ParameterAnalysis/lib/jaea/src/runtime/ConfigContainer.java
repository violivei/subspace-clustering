package runtime;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
/**
 * Class to process runtime & algorithm parameters passed in XML file, including of
 * Problem, Runtime and Method sections  
 * @author Le Minh Nghia, NTU-Singapore
 *
 */
public class ConfigContainer {
	@SuppressWarnings("unchecked")
	List myEmpls;
	Document dom;
	public Problem problem;
	public Method method;
	public Runtime runtime;
	String XmlFile = "";
	@SuppressWarnings("unchecked")
	public ConfigContainer(String filename)
	{
		myEmpls = new ArrayList();
		XmlFile = filename;
	}
	public void parseParams() {
		
		parseXmlFile();
		
		parseDocument();
		//Iterate through the list and print the data
		printData();
		
	}
	private void parseXmlFile(){
		//get the factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		
		try {
			
			//Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();
			
			//parse using builder to get DOM representation of the XML file
			dom = db.parse(XmlFile);
			

		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}

	
	@SuppressWarnings("unchecked")
	private void parseDocument(){
		//get the root elememt
		Element docEle = dom.getDocumentElement();
		
		//get a nodelist of <Problem> elements
		NodeList nl = docEle.getElementsByTagName("Problem");
		if(nl != null && nl.getLength() > 0) {
			for(int i = 0 ; i < nl.getLength();i++) {

				Element el = (Element)nl.item(i);
				
				//get the Employee object
				problem = new Problem(el);
				
				//add it to list
				myEmpls.add(problem);
			}
		}
		//get a nodelist of <Runtime> elements
		nl = docEle.getElementsByTagName("Runtime");
		if(nl != null && nl.getLength() > 0) {
			for(int i = 0 ; i < nl.getLength();i++) {
				
				Element el = (Element)nl.item(i);
				
				//get the Employee object
				runtime = new Runtime(el);
				
				//add it to list
				myEmpls.add(runtime);
			}
		}
		//get a nodelist of <Method> elements
		nl = docEle.getElementsByTagName("Method");
		if(nl != null && nl.getLength() > 0) {
			for(int i = 0 ; i < nl.getLength();i++) {
				
				//get the employee element
				Element el = (Element)nl.item(i);
				
				//get the Employee object
				method = new Method(el);
				
				//add it to list
				myEmpls.add(method);
			}
		}
	}

	/**
	 * I take a xml element and the tag name, look for the tag and get
	 * the text content 
	 * i.e for <employee><name>John</name></employee> xml snippet if
	 * the Element points to employee node and tagName is name I will return John  
	 * @param ele
	 * @param tagName
	 */
	public static String getTextValue(Element ele, String tagName) {
		String textVal = null;
		NodeList nl = ele.getElementsByTagName(tagName);
		if(nl != null && nl.getLength() > 0) {
			Element el = (Element)nl.item(0);
			textVal = el.getFirstChild().getNodeValue();
		}
		if (textVal != null)
			return textVal.trim();
		else
			return textVal;
	}

	
	/**
	 * Calls getTextValue and returns a int value
	 */
	public static int getIntValue(Element ele, String tagName) {
		//in production application you would catch the exception
		return Integer.parseInt(getTextValue(ele,tagName).trim());
		
	}
	/**
	 * Calls getTextValue and returns a boolean value
	 */
	public static boolean getBoolValue(Element ele, String tagName) {
		//in production application you would catch the exception
		return Boolean.parseBoolean(getTextValue(ele,tagName).trim());
	}
	/**
	 * Calls getTextValue and returns a double value
	 */
	public static double getDoubleValue(Element ele, String tagName) {
		//in production application you would catch the exception
		return Double.parseDouble(getTextValue(ele,tagName).trim());
	}
	/**
	 * Calls getTextValue and returns a long value
	 */
	public static long getLongValue(Element ele, String tagName) {
		//in production application you would catch the exception
		return Long.parseLong(getTextValue(ele,tagName).trim());
	}
	/**
	 * Iterate through the list and print the 
	 * content to console
	 */
	@SuppressWarnings("unchecked")
	private void printData(){
		
		System.out.println("No of Elements '" + myEmpls.size() + "'.");
		
		Iterator it = myEmpls.iterator();
		while(it.hasNext()) {
			System.out.println(it.next().toString());
		}
	}

	
	public static void main(String[] args){
		//create an instance
		ConfigContainer dpe = new ConfigContainer("conf.xml");
		dpe.parseParams();
	}

}
