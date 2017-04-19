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
		data.full_name=full_name
		//console.log(full_name);
		$("#user_name").html("Hi, "+full_name);
		//add application list
		$.each(data.apps,function(i,v){
			$("#list").append("<li data-app_url="+v.app_url+"><a class=ajax-link ><span>"+v.app_name+"</span></a></li>");
		});
		//add click event for all apps
		$("#list").children().click(function(){
			$("#content_frame").attr("src",$(this).data("app_url"));
			
		});
		$("#check_pro").click(function(){
			$("#myModal").modal("show");
			initialPro(data);
		});
	},function(e){
		
	});
	function initialPro(user){
		$("#user_name2").html("Name:&nbsp&nbsp&nbsp"+user.full_name);
		$("#org_name").html("Organization:&nbsp&nbsp&nbsp"+user.organization_name);
		$("#user_role").html("Role:&nbsp&nbsp&nbsp"+user.user_type);
		$("#log_name").html("Log in Name:&nbsp&nbsp&nbsp"+user.user_name);
//		if(user.user_type_id=="1"){//is patient
//			$("#track").append("<button id=set_pwd type=button class='btn btn-success' >Track Health Status</button>");
//			
//		}
	}
	
});