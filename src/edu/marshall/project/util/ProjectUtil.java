package edu.marshall.project.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class ProjectUtil {

public static String prescriptionNumGenerter(){
	String date=getDate();
	date=date.replace("-", "");
	String uuid=UUID.randomUUID().toString().substring(0, 5);
	
	return date+uuid;
}
public static String getDate(){
	SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
	Date date=new Date();
	return dateFormat.format(date);
}
public static void main(String[] args) {
	System.out.println(getDate());
	System.out.println(prescriptionNumGenerter());
}
}
