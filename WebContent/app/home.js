$(function(){
	
	
	
	project.post("app/initialize?action=iniliatize",{},function(data){
		//ADD USER INFO
		//add user name
		var full_name="";
		full_name=data.user_last_name+" ";
		if(data.user_mid_name!=""){
			full_name+=data.user_mid_name+" ";
		}
		full_name+=data.user_first_name;
		console.log(full_name);
		$("#user_name").html("Hi, "+full_name);
		//add application list
		$.each(data.apps,function(i,v){
			//console.log("<li data-app_url="+v.app_url+"><a class=ajax-link ><span>"+v.app_name+"</span></a></li>");
			$("#list").append("<li data-app_url="+v.app_url+"><a class=ajax-link ><span>"+v.app_name+"</span></a></li>");
		});
		//add click event for all apps
		$("#list").children().click(function(){
			//console.log($(this).data("app_url"));
//			var app=$(this).data("options");
			$("#content_frame").attr("src",$(this).data("app_url"));
			
		});
		
	},function(e){
		
	});
//	$("#list li").click(function(){
//		alert("ss");
//		alert($(this).data("options"));
//	});
	
	
});