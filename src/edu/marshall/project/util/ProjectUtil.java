package edu.marshall.project.util;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class ProjectUtil {

public static String prescriptionNumGenerter(){
	String date=getDate();
	date=date.replace("-", "");
	String uuid=UUID.randomUUID().toString().substring(0, 5);
	
	return "PRE"+date+uuid;
}
public static String orderNumGenerter(){
	String date=getDate();
	date=date.replace("-", "");
	String uuid=UUID.randomUUID().toString().substring(0, 5);
	
	return "ORD"+date+uuid;
}
public static String getDate(){
	SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
	Date date=new Date();
	return dateFormat.format(date);
}
//generte account num
public static String getAccountNum(){
	Random random=new Random();
	StringBuilder accountNum=new StringBuilder();
	for(int i=0;i<8;i++){
		accountNum.append(random.nextInt(10));
	}
	accountNum.append("-");
	for(int i=0;i<8;i++){
		accountNum.append(random.nextInt(10));
	}
	return "ACC"+accountNum.toString();
}


public static void main(String[] args) {
//	System.out.println(getDate());
//	System.out.println(prescriptionNumGenerter());
	System.out.println(getAccountNum());
}
}
