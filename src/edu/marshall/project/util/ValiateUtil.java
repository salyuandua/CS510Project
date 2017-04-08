package edu.marshall.project.util;

public class ValiateUtil {
/**
 * the name of an action must follow the rules as following:1,not null 2,not "" 3,xxxAction 
 * @param action
 * @return
 */
public static boolean valiateActionName(String actionName){
	if(actionName==null||actionName.trim().equals("")){
		return false;
	}	
	if(!actionName.endsWith("Action")||actionName.startsWith("Action")){
		return false;
	}
	return true;
}
public static void main(String[] args) {
	String a=" ttt ";
	System.out.println(a.trim());
}
}
