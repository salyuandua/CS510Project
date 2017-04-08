package edu.marshall.project.util;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XMLUtil {
	/**
	 * find complete class name(include package name) by action 
	 * @param action from actions.xml
	 * throw NullPointerException if class is not exists;
	 */
	
	public static String findActionClass(String action){
		if(action==null||action.equals("")){
			throw new NullPointerException("action is Null!");
		}
		String filePath = null;
		try {
			filePath = XMLUtil.class.getClassLoader().getResource("actions.xml").toURI().getPath();

		} catch (URISyntaxException e1) {
			
			e1.printStackTrace();
			return "";
		}
		File file=new File(filePath);
		SAXReader reader=new SAXReader();
		try {
			Document doc=reader.read(file);
			Element rootElement=doc.getRootElement();
			Iterator<Element> it=rootElement.elementIterator();
			while(it.hasNext()){
				Element child=it.next();
				
				if(child.attributeValue("name").equals(action)){
					return child.attributeValue("class");
				}
			}
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		throw new NullPointerException("the action name is "+action+" is not exist");
	}
	//test
public static void main(String[] args) {
	
	System.out.println(findActionClass("testAction"));
}
}
