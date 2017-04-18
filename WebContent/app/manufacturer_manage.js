$(function(){
	function initialMaPage(){
		$("#ma_tab tr").remove();
		project.post("app/base?action=queryAllMa",{},function(data){
			$.each(data,function(i,v){
				$("#ma_tab").append("<tr >" +
						"<td>"+v.manufacturer_name+"</td>" +
						"<td>"+v.manufacturer_address+"</td>" +
						"<td>"+v.manufacturer_phone+"</td>" +
						"</tr>");
			});
			
		});
	}
	
	function initialAddPage(){
		$("#maName").val("");
		$("#phone").val("");
		$("#address").val("");
		$("#save").click(function(){
			if($("#maName").val()==""){
				alert("Please enter Manufacturer name!");
				return;
			}
			if($("#phone").val()==""){
				alert("Please enter Manufacturer phone!");
				return;
			}		
			if($("#address").val()==""){
				alert("Please enter Manufacturer address!");
				return;
			}
			var param={};
			param.ma_name=$("#maName").val();
			param.phone=$("#phone").val();
			param.address=$("#address").val();
			project.post("app/base?action=addMan",param,function(){
				$("#addModal").modal("hide");
				initialMaPage();
			});
		});
	}
	initialMaPage();
	$("#addMa").click(function(){
		$("#addModal").modal("show");
		initialAddPage();
	});

});