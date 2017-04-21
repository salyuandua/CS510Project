$(function(){
	function initialMaPage(){
		$("#org_tab tr").remove();
		project.post("app/base?action=queryOrgsWithType",{},function(data){
			$.each(data,function(i,v){
				$("#org_tab").append("<tr >" +
						"<td>"+v.organization_name+"</td>" +
						"<td>"+v.organization_type_name+"</td>" +
						"<td>"+v.organization_address+"</td>" +
						"<td>"+v.organization_phone+"</td>" +
						"</tr>");
			});
			
		});
	}
	initialMaPage();
	function initialAddPage(){
		$("#orgName").val("");
		$("#phone").val("");
		$("#address").val("");
		$("#orgtype_select option").remove();
		project.post("app/base?action=queryAllOrgType",{},function(data){
			$.each(data,function(i,v){
				$("#orgtype_select").append("<option id="+v.organization_type_id+">"+v.organization_type_name+"</option>");
			});
		});
		
		$("#save").click(function(){
			if($("#orgName").val()==""){
				alert("Please enter Organization name!");
				return;
			}
			if($("#phone").val()==""){
				alert("Please enter Organization phone!");
				return;
			}		
			if($("#address").val()==""){
				alert("Please enter Organization address!");
				return;
			}
			var param={};
			param.org_type=$("#orgtype_select option:selected").attr("id");
			param.org_name=$("#maName").val();
			param.phone=$("#phone").val();
			param.address=$("#address").val();
			console.log(param);
			project.post("app/base?action=addOrg",param,function(){
				$("#addModal").modal("hide");
				initialMaPage();
			});
		});
	}
	$("#addOrg").click(function(){
		$("#addModal").modal("show");
		initialAddPage();
	});
});