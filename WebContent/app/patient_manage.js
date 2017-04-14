$(function(){
	//del method
	function del(){
		$(".btn .btn-danger").click(function(){
			
		});
	}
	//initialize patients function
	function initializePatients(){
		project.post("app/base?action=queryPatByOrg",{},function(data){
			$("#patient_list tr").remove();
			$.each(data,function(i,v){
				var jsonString=JSON.stringify(v);
				$("#patient_list").append("<tr>" +
						"<td>"+v.patient_name+"</td>" +
								"<td>"+v.gender_name+"</td>" +
								"<td>"+v.organization_name+"</td>" +
								"<td>"+v.doctor_name+"</td>" +
								"<td><button type=button class='btn btn-info' data-options='"+jsonString+"'>Detail</button>" +
								"<button type=button  style='margin-left:20px'  class='btn btn-danger ' data-options='"+jsonString+"'>Delete</button></td>"+
								
								"</tr>");
				
			});
//			
			detailEvent();
			del();
		},function(e){
			
		});
	}
	//initialize restriction
	function initialRestr(patient){
		$("#restr_list tr").remove();
		project.post("app/base?action=queryRestrByPat",patient,function(data){
			$.each(data,function(i,v){
				var jsonString=JSON.stringify(v);
				$("#restr_list").append("<tr>" +
						"<td>"+v.medicine_name+"</td>" +
								"<td>"+v.manufacturer_name+"</td>" +
								"<td>"+v.manufacturer_address+"</td>" +
								"<td>"+v.manufacturer_phone+"</td></tr>");
				
			});
		},function(){
			
		});
	}
	//initialize req med
	function initialReqMed(patient){
		$("#req_list tr").remove();
		project.post("app/base?action=queryReqMedByPat",patient,function(data){
			$.each(data,function(i,v){
				var jsonString=JSON.stringify(v);
				$("#req_list").append("<tr>" +
						"<td>"+v.medicine_name+"</td>" +
								"<td>"+v.manufacturer_name+"</td>" +
								"<td>"+v.manufacturer_address+"</td>" +
								"<td>"+v.manufacturer_phone+"</td>"+
								"<td>"+v.patient_req_medicines_req_num+"</td>" +
								"<td>"+v.medicine_available_count+"</td>" +
										"</tr>");
				
			});
		},function(){
			
		});
	}

	//---------------------------------------------add detail events
	function detailEvent(){
		$(".btn.btn-info").click(function(){
			console.log($(this).data("options"));
			var patient=$(this).data("options");	
			$("#myModal").modal("show");
			//set doctor info in doctor detail modal
			$("#patient_name").html("Patient Name:&nbsp&nbsp&nbsp"+patient.patient_name);
			$("#patient_gender").html("Gender:&nbsp&nbsp&nbsp"+patient.gender_name);
			$("#org_name").html("Health Care Provider:&nbsp&nbsp&nbsp"+patient.organization_name);
			$("#doctor_name").html("Admin Doctor:&nbsp&nbsp&nbsp"+patient.doctor_name);
			$("#phone").html("Phone Num:&nbsp&nbsp&nbsp"+patient.patient_phone);
			$("#email").html("E-Mail:&nbsp&nbsp&nbsp"+patient.patient_email);
			$("#emergency_contact_name").html("Emergency Contact Name:&nbsp&nbsp&nbsp"+patient.patient_emg_contact_name);
			$("#emergency_contact_phone").html("Emergency Contact Phone Num:&nbsp&nbsp&nbsp"+patient.patient_emg_contact_phone);
			$("#health_pr").html(patient.patient_health_problem);
			//initial restrctions
			initialRestr(patient);
			//initial requiring medicines
			initialReqMed(patient);
		});
	}
	//---------------------------------------------add detail events-----------end
	initializePatients();
	//inital add req medicines table
	function initialMedTab(optionId){
		$("#req_select #"+optionId).dblclick(function(){
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
					"</tr>");
		});
	}
	
	//initialize add req medicines------------------------------------
	function initialAddReqMed(){
		$("#req_select option").remove();
		$("#req_med_table tr").remove();
		var spans=$("#labels span");

		var list=[];
		$.each(spans,function(i,v){
			list[i]=$(v).data("options").medicine_id;
		});
		var param={};
		param.idList=list;
		project.post("app/base?action=queryMedByRestr",param,function(data){
			$.each(data,function(i,v){
				$("#req_select").append("<option id="+v.medicine_id+" data-options='"+JSON.stringify(v)+"'>"+v.medicine_name+"("+v.manufacturer_name+")</option>");
				initialMedTab(v.medicine_id);
			});
			
		},function(e){
			
		});
		
	}
	//initialize add req medicines------------------------------------end
	
	//initialize restr--------------------------------------
	function initializeRestrSelect(optionId){
		$("#restr_select #"+optionId).dblclick(function(){
			//alert("ss");
			//var optionId=$(this).attr("id");
			var medicine=$(this).data("options");
			//remove this option
			$(this).remove();
			//add this node to label
			//console.log(medicine);
			$("#labels").append("<span id="+medicine.medicine_id+" class='label label-danger' style='margin-left:10%;' data-options='"+JSON.stringify(medicine)+"'>"+medicine.medicine_name+"</span>");
			//add double click on labels
			$("#labels #"+medicine.medicine_id).dblclick(function(){
				var medicine=$(this).data("options");
				//remove this option
				$(this).remove();
				$("#restr_select").append("<option id="+medicine.medicine_id+"  data-options='"+JSON.stringify(medicine)+"'>"+medicine.medicine_name+"("+medicine.manufacturer_name+")</option>");
				initializeRestrSelect(medicine.medicine_id);
				//initial req medicine
				initialAddReqMed();
			});
			//initial req medicine
			initialAddReqMed();
		});
	}
	
	
	
	//initialize add patient page------------------------------
	function initializeAddPat(){
//		$("#firstName").val("");
//		$("#lastName").val("");
//		$("#midName").val("");
//		$("#userName").val("");
//		$("#pwd").val("");
		//delete all input content
		$(":input").val("");
		$("#prob_dec").val("");
		//delete restriction select
		$("#restr_select option").remove();
		$("#labels span").remove();
		$("#req_select option").remove();
		$("#req_med_table tr").remove();
		//delete
		//initialize gender select
		project.post("app/base?action=queryGender",{},function(data){
			$("#gender option").remove();
			$.each(data,function(i,v){
				$("#gender").append("<option data-options='"+JSON.stringify(v)+"'>"+v.gender_name+"</option>");
			});
			
		},function(e){
			
		});
		//initialize doctor select
		project.post("app/base?action=queryDoctor",{},function(data){
			$("#doctor option").remove();
			$.each(data,function(i,v){
				$("#doctor").append("<option data-options='"+JSON.stringify(v)+"'>"+v.doctor_name+"</option>");
			});
			
		},function(e){
			
		});
		//initialize restriction select
		project.post("app/base?action=queryAllMed",{},function(data){
			$("#restr_select option").remove();
			$.each(data,function(i,v){
				$("#restr_select").append("<option id="+v.medicine_id+"  data-options='"+JSON.stringify(v)+"'>"+v.medicine_name+"("+v.manufacturer_name+")</option>");
				//add double click on option
				initializeRestrSelect(v.medicine_id);
			});
			
			

			
		},function(e){
			
		});
	}
	//initialize add patient page----------------------------------------------------end
	//add adding event
	$("#addPat").click(function(){
		$("#addModal").modal("show");
		initializeAddPat();
	});
	//save patient button----------------------
	$("#savePat").click(function(){
		var param={};
		//--------test
//		var req_med_list=$("#req_med_table tr");
//		if(req_med_list.length==0){
//			alert("Please enter at least one requiring medicine!");
//			return;
//		}
//		param.req_med_list=[];
//		$.each(req_med_list,function(i,v){
//			//param.req_med_list[i]=$(v).data("options").medicine_id;
//			console.log($(v).find(":input"));
//			var one_req_med={};
//			one_req_med.medicine_id=$(v).data("options").medicine_id;
//
//		});
		//-------
		if($("#firstName").val()==""||$("#firstName").val()==undefined){
			alert("Please enter first name!");
			return
		}
		param.first_name=$("#firstName").val();
		if($("#lastName").val()==""||$("#lastName").val()==undefined){
			alert("Please enter last name!");
			return
		}
		param.last_name=$("#lastName").val();
		if($("#midName").val()==""||$("#midName").val()==undefined){
			param.mid_name="";	
		}
		param.mid_name=$("#midName").val();
		param.gender_id=$("#gender option:selected").data("options").gender_id;
		if($("#userName").val()==""||$("#userName").val()==undefined){
			alert("Please enter user name!");
			return
		}
		param.user_name=$("#userName").val();
		if($("#pwd").val()==""||$("#pwd").val()==undefined){
			alert("Please enter password!");
			return
		}
		param.pwd=$("#pwd").val();
		param.doctor_id=$("#doctor option:selected").data("options").doctor_id;
		if($("#phone_input").val()==""||$("#phone_input").val()==undefined){
			//console.log($("#phone").val());
			alert("Please enter phone num!");
			return
		}
		param.phone=$("#phone_input").val();
		if($("#email_input").val()==""||$("#email_input").val()==undefined){
			alert("Please enter email!");
			return
		}
		param.email=$("#email_input").val();
		if($("#ecName").val()==""||$("#ecName").val()==undefined){
			alert("Please enter Emergency Contact Name!");
			return
		}
		param.ec_name=$("#ecName").val();
		if($("#ecphone").val()==""||$("#ecphone").val()==undefined){
			alert("Please enter Emergency Contact phone Number!");
			return
		}
		param.ec_phone=$("#ecphone").val();
		if($("#prob_dec").val()==""||$("#prob_dec").val()==undefined){
			alert("Please enter EHealth Problems Description!");
			return
		}
		param.prob_dec=$("#prob_dec").val();
		//restriction medicines------------------------------
		var restr_list=$("#labels span");
		if(restr_list.length==0){
			alert("Please enter at least one restriction medicine!");
			return;
		}
		param.restr_list=[];
		$.each(restr_list,function(i,v){
			param.restr_list[i]=$(v).data("options").medicine_id;
		});
		//req medicines-------------------------------------
		var req_med_list=$("#req_med_table tr");
		if(req_med_list.length==0){
			alert("Please enter at least one requiring medicine!");
			return;
		}
		param.req_med_list=[];
		var pass=true;
		$.each(req_med_list,function(i,v){
			//param.req_med_list[i]=$(v).data("options").medicine_id;
			if($(v).find(":input").val()==""||$(v).find(":input").val()==undefined){
				alert("Please enter the Requiring Quantity of medicine "+$(v).data("options").medicine_name);
				pass=false;
				return;
			}

			var one_req_med={};
			one_req_med.medicine_id=$(v).data("options").medicine_id;
			one_req_med.re_num=$(v).find(":input").val();
			param.req_med_list.push(one_req_med);
		});
		if(!pass){//lack req quantity
			return;
		}
		console.log(param);
		project.post("app/base?action=addPat",param,function(data){
			$("#addModal").modal("hide");
			
			initializePatients();
		},function(){
			alert("Add patient failed!");
		});
		
	});
	
	
	
});