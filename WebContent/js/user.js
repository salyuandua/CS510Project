$(function(){
	project.post("app/base?action=userType",{},function(data){
		$.each(data,function(i,v){
		
		$("#userTypes").append("<option data-tablename="+v.user_type_tablename+"  "+"id="+v.user_type_id+">"+v.user_type_name+"</option>");
		//console.log($("#userTypes option:selected").attr("id"));
		});
	},function(e){
		
	});
	//inital popvoers
	$("#username").popover({content:"Please enter your user name!",trigger:"manual"}); 
	$("#password").popover({content:"Please enter your password!",trigger:"manual"}); 
	//$("#alert").alert("close");
	//if username is empty
	$("#username").focusout(function(e){
		console.log($(this).val()=="");
		if($(this).val()==""){
			$("#namediv").addClass("has-error");
		}
		if($("#namediv").hasClass("has-error")&&$(this).val()!=""){
			$("#namediv").removeClass("has-error");
		}
	});
	
	$("#password").focusout(function(e){
		console.log($(this).val()=="");
		if($(this).val()==""){
			$("#pwddiv").addClass("has-error");
		}
		if($("#pwddiv").hasClass("has-error")&&$(this).val()!=""){
			$("#pwddiv").removeClass("has-error");
		}
	});
	//document.write(location.host+"**"+location.hostname);
	$("#signIn").click(function(e){
		//console.log("ss");
		if($("#username").val()==""){//username is empty
			$("#username").popover("show");
			return;
		}
		if($("#password").val()==""){//username is empty
			$("#pwdname").popover("show");
			return;
		}
		//start login
		$(this).button('loading');
		//generate param 
		var param={};
		param.username=$("#username").val();
		param.password=$("#password").val();
		param.user_type_id=$("#userTypes option:selected").attr("id");
		if($("#userTypes option:selected").attr("data-tablename")=="undefined"||$("#userTypes option:selected").attr("data-tablename")==undefined){
			$("#userTypes option:selected").attr("data-tablename","");
		}
		param.tablename=$("#userTypes option:selected").attr("data-tablename");
		//post data
		project.post("/user/signin?action=signIn",param,function(data){//
			console.log(data.message+"&&&");
			if(data.message=="success"){//log success
				location.href="/CS510Project/app/home.html";
			}else{
				$("#alert").html(data.message);
				$("#alert").removeAttr("hidden");
				$("#signIn").button("reset");
			}

		},function(e){//error
			$("#signIn").button("reset");
		});
		
		
		
	});
	//$.support.transition = false
	//project.paramBuilder(["id=username"])

	
	
	
});