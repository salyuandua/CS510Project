$(function(){
	//-------------inital main table
	function initialPreTab(){
		$("#prescr_tab tr").remove();
		project.post("app/base?action=queryPrescr",{},function(data){
			$.each(data,function(i,v){
				$("#prescr_tab").append("<tr>" +
						"<td >"+v.prescription_num+"</td>" +
						"<td >"+v.real_date+"</td>" +
						"<td >"+v.patient_name+"</td>" +
						"<td >"+v.gender_name+"</td>" +
						"<td ><button type=button class='btn btn-info' data-options='"+JSON.stringify(v)+"'>Detail</button></td>" +
						"</tr>");
			});
			$(".btn.btn-info").click(function(){
				//alert("ss");
				$("#myModal").modal("show");
				initialDetail($(this).data("options"));
			});
		},function(){
			
		});
	}
	initialPreTab();
	//---------------------------------initial detail event
	function initialDetail(prescription){
		$("#prescr_num").html("Prescription Num:&nbsp&nbsp&nbsp"+prescription.prescription_num);
		$("#prescr_date").html("Prescription Date:&nbsp&nbsp&nbsp"+prescription.real_date);
		$("#patient_name").html("Patient Name:&nbsp&nbsp&nbsp"+prescription.patient_name);
		$("#patient_gender").html("Gender:&nbsp&nbsp&nbsp"+prescription.gender_name);
		$("#prescr_decr").html(prescription.prescription_decription);
		$("#med_tab tr").remove();
		project.post("app/base?action=queryMedByPre",prescription,function(data){
			$.each(data,function(i,v){
				$("#med_tab").append("<tr>" +
						"<td>"+v.medicine_name+"</td>" +
						"<td>"+v.manufacturer_name+"</td>" +
						"<td>"+v.prescription_medicine_req_mun+"</td>" +
						"<td>"+v.medicine_available_count+"</td>" +
						"</tr>");
			})
		},function(){
			
		});
	}
	
	
	//------------------------------
	function initialAddPre(){
		project.post("app/base?action=queryPatByUser",{},function(data){
		$("#patient_list option").remove();
		$.each(data,function(i,v){
			var jsonString=JSON.stringify(v);
			$("#patient_list").append("<option data-options='"+jsonString+"'>"+v.patient_name+" ("+v.gender_name+")</option>");
			
		});
		initalMed(data[0]);
		$("#patient_list").change(function(){
			var patient=$(this).find("option:selected").data("options");
			initalMed(patient);
		});
		
//		
		//detailEvent();
		//del();
	},function(e){
		
	});
	}
	//----------------initial medicines table-------------------
	function initialMedTab(optionId){
		$("#med_list #"+optionId).dblclick(function(){
			var reqMedicine=$(this).data("options");
			//remove this option
			$(this).remove();
			//add this node to table
			$("#req_med_table").append("<tr data-options='"+JSON.stringify(reqMedicine)+"'>" +
					"<td>"+reqMedicine.medicine_name+"</td>" +
					"<td>"+reqMedicine.manufacturer_name+"</td>" +
					"<td>"+reqMedicine.medicine_price+"</td>" +
					"<td>"+reqMedicine.medicine_available_count+"</td>" +
					"<td><input type=text class=form-control ></td>"+
					//"<td><button  type=button class='btn btn-danger'>Delete</button></td>"+
					"</tr>");
		});
	}
	//initail med list select--------------------------------
	function initalMed(patient){
		$("#med_list option").remove();
		project.post("app/base?action=queryAvMed",patient,function(data){
			$.each(data,function(i,v){
				$("#med_list").append("<option id="+v.medicine_id+" data-options='"+JSON.stringify(v)+"'>"+v.medicine_name+"("+v.manufacturer_name+")</option>");
				initialMedTab(v.medicine_id);
			});
		},function(e){
			
		});
	}
	
	
	
	//add event
	$("#addPre").click(function(){
		$("#addModal").modal("show");
		initialAddPre();
	});
	// save event
	$("#save").click(function(){
		if($("#patient_list option").length==0){
			alert("You can't save because you have no patient!");
				return;
			}
		var param={};
		param.patient_id=$("#patient_list option:selected").data("options").patient_id;
		if($("#req_med_table tr").lenth==0){
			alert("Please add at least one medicine!");
			return;
		}
		param.medicine_list=[];
		var pass=true;
		$.each($("#req_med_table tr"),function(i,v){
			if($(v).find(":input").val()==""||$(v).find(":input").val()==undefined){
				alert("Please enter requiring quitity!");
				pass=false;
				return;
			}
			var oneMed={};
			oneMed.medicine_id=$(v).data("options").medicine_id;
			oneMed.req_num=$(v).find(":input").val();
			param.medicine_list.push(oneMed);
		});
		if(!pass){
			return;
		}
		if($("#descr").val()==""||$("#descr").val()==undefined){
			alert("Please enter prescription description!");
			return;
		}
		param.description=$("#descr").val();
		//post
		console.log(param);
		project.post("app/base?action=addPrescr",param,function(data){
			$("#addModal").modal("hide");
			initialPreTab();
		},function(){
			
		});

		
		
	});

	
	
})