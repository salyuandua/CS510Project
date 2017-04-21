$(function(){
	function initialMaPage(){
		$("#admin_tab tr").remove();
		project.post("app/base?action=queryAdmin",{},function(data){
			$.each(data,function(i,v){
				$("#admin_tab").append("<tr >" +
						"<td>"+v.admin_name+"</td>" +
						"<td>"+v.admin_gender+"</td>" +
						"<td>"+v.organization_name+"</td>" +
						"<td>"+v.admin_username+"</td>" +
						"</tr>");
			});
			
		});
	}
	initialMaPage();
	function initialAddPage(){
		$("#firstName").val("");
		$("#midName").val("");
		$("#lastName").val("");
		$("#userName").val("");
		$("#pwd").val("");
		$("#gender option").remove();
		$("#org_select option").remove();
		//request gender
		project.post("app/base?action=queryGender",{},function(data){
			$("#gender option").remove();
			$.each(data,function(i,v){
				$("#gender").append("<option data-options='"+JSON.stringify(v)+"'>"+v.gender_name+"</option>");
			});
			
		},function(e){
			
		});
		
		project.post("app/base?action=queryOrgsWithType",{},function(data){
			$.each(data,function(i,v){
				$("#org_select").append("<option id="+v.organization_id+"$"+v.organization_type_id+">"+v.organization_name+"</option>");
			});
			
		});
		
		$("#save").click(function(){
			var firstName=$("#firstName").val();
			var lastName=$("#lastName").val();
			var userName=$("#userName").val();
			var password=$("#pwd").val();
			if(firstName==""||firstName=="undefined"){
				alert("Please enter first name!");
				return;
			}
			if(lastName==""||lastName=="undefined"){
				alert("Please enter last name!");
				return;
			}
			if(userName==""||userName=="undefined"){
				alert("Please enter user name!");
				return;
			}
			if(password==""||password=="undefined"){
				alert("Please enter password!");
				return;
			}
			//prepare param which will be posted
			var param={};
			param.first_name=firstName;
			param.last_name=lastName;
			param.mid_name=$("#midName").val();
			param.gender_id=$("#gender option:selected").data("options").gender_id;
			param.org=$("#org_select option:selected").attr("id");
			param.user_name=userName;
			param.password=password;
			console.log(param);
			project.post("app/base?action=addAdmin",param,function(){
				$("#addModal").modal("hide");
				initialMaPage();
			});
		});
	}

	$("#addAdmin").click(function(){
		$("#addModal").modal("show");
		initialAddPage();
	});
});