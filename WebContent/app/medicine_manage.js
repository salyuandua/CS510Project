$(function(){
	function initialMedTab(){
		$("#med_tab tr").remove();
		project.post("app/base?action=queryAllMed",{},function(data){
			$.each(data,function(i,v){
				$("#med_tab").append("<tr >" +
						"<td>"+v.medicine_name+"</td>" +
						"<td><a data-options='"+JSON.stringify(v)+"'>"+v.manufacturer_name+"</a></td>" +
						"<td>"+v.medicine_price+"</td>" +
						"<td>"+v.medicine_available_count+"</td>" +
						"</tr>");
			});
			$("#med_tab").find("a").mouseover(function(){
				var temp=$(this).data("options");
				$(this).popover({content:"Address: "+temp.manufacturer_address+"|\n"+
						"Phone: "+temp.manufacturer_phone,trigger:"manual",placement:"top"});
				$(this).popover("show");
			});
			$("#med_tab").find("a").mouseout(function(){
				$(this).popover("hide");
			});
			console.log($("#med_tab").find("a"));

		});
	}
	function initialAddPage(){
		$("#medName").val("");
		$("#medPrice").val("");
		$("#stock").val("");
		$("#ma_select option").remove();
		project.post("app/base?action=queryAllMa",{},function(data){
			$.each(data,function(i,v){
				$("#ma_select").append("<option id="+v.manufacturer_id+">"+v.manufacturer_name+"</option>");
			})
			
		});
	}
	initialMedTab();
	$("#addMed").click(function(){
		$("#addModal").modal("show");
		initialAddPage();
	});
	//save
	$("#save").click(function(){
		if($("#medName").val()==""){
			alert("Please enter Medicine Name!");
			return;
		}
		if($("#medPrice").val()==""){
			alert("Please enter Medicine Price!");
			return;
		}
		if($("#stock").val()==""){
			alert("Please enter Quantity in Stock!");
			return;
		}
		param={};
		param.med_name=$("#medName").val();
		param.med_price=$("#medPrice").val();
		param.med_stock=$("#stock").val();
		param.ma_id=$("#ma_select option:selected").attr("id");
		console.log(param);
		project.post("app/base?action=addMed",param,function(){
			$("#addModal").modal("hide");
			initialMedTab();
		});
	});
	
});