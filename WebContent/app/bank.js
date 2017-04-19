$(function(){
//	project.post("app/base?action=xxxaction",{},function(data){
//		$.each(data,function(i,k){
//			
//		});
//	});
	function initialAccTab(){
		$("#acc_tab tr").remove();
		project.post("app/base?action=queryAllAcc",{},function(data){
			$.each(data,function(i,v){
				$("#acc_tab").append("<tr>" +
						"<td>"+v.bank_account_num+"</td>" +
						"<td>"+v.patient_name+"</td>" +
						"<td>"+v.patient_phone+"</td>" +
						"<td>"+v.patient_email+"</td>" +
						"<td>"+v.bank_account_blance+"</td>" +
						"</tr>");
			});

		});
	}
	function initialAdd(){
		$("#pat_select option").remove();
		$("#deposit").val("");
		project.post("app/base?action=queryAllPat",{},function(data){
			$.each(data,function(i,v){
				$("#pat_select").append("<option id="+v.patient_id+">"+v.patient_name+"</option>");
			});
			
		});
	}
	initialAccTab();
	
	$("#addAcc").click(function(){
		$("#addModal").modal("show");
		initialAdd();
		
	});
	//save
	$("#save").click(function(){
		var param={};
		param.patient_id=$("#pat_select option:selected").attr("id");
		param.deposit=$("#deposit").val();
		project.post("app/base?action=addAccount",param,function(){
			$("#addModal").modal("hide");
			initialAccTab();
		});
	});
	
});