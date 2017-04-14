$(function(){
	//initialize Doctors function
	function initializeDoctors(){
		project.post("app/base?action=queryDoctor",{},function(data){
			$("#doctor_list tr").remove();
			$.each(data,function(i,v){
				var full_name="";
				full_name=v.doctor_name_last+" ";
				if(v.doctor_name_mid!=""){
					full_name+=v.doctor_name_mid+" ";
				}
				full_name+=v.doctor_name_first;
				var disable="";
				if(v.patient_num!=0){
					disable="disabled";
				}
				var jsonString=JSON.stringify(v);
				$("#doctor_list").append("<tr>" +
						"<td>"+full_name+"</td>" +
								"<td>"+v.gender_name+"</td>" +
								"<td><button type=button class='btn btn-info' data-full_name='"+full_name+"' data-options='"+jsonString+"'>Detail</button>" +
								"<button type=button  style='margin-left:20px'  class='btn btn-danger "+disable+"' data-options='"+jsonString+"'>Delete</button></td>"+
								
								"</tr>");
				
			});
			
			detailEvent();
			del();
		},function(e){
			
		});
	}
	//add detail events
	function detailEvent(){
		$(".btn.btn-info").click(function(){
			console.log($(this).data("options"));
			var doctor=$(this).data("options");	
			$("#myModal").modal("show");
			//set doctor info in doctor detail modal
			$("#doctor_name").html("Doctor Name:&nbsp&nbsp&nbsp"+$(this).data("full_name"));
			$("#doctor_gender").html("Gender:&nbsp&nbsp&nbsp"+doctor.gender_name);
			//get all patients this doctor has
			initializePatients(doctor);
		});
	}
	//delete event
	function del(){
		$(".btn.btn-danger").click(function(){
			var doctor=$(this).data("options");	
			project.post("app/base?action=delDoctor",doctor,function(data){
				//reload doctor list
				initializeDoctors();
			},function(e){
				
			});
		});
	}
	
	//initialize Doctors
	initializeDoctors();
	

	//initialize patient list
	function initializePatients(param){
		project.post("app/base?action=queryPatByDoc",param,function(data){
			$("#patient_list tr").remove();
			$.each(data,function(i,v){
				$("#patient_list").append("<tr>" +
						"<td>"+v.patient_name+"</td>" +
								"<td>"+v.gender_name+"</td>" +
								"<td><button type=button class='btn btn-info' >Detail</button>" +
								"<button type=button  style='margin-left:20px' class='btn btn-danger'>Delete</button></td>"+
								
								"</tr>");
			});
			
		},function(e){
			
		});
	}
	//initialize add doctor page
	function initializeAddDoc(){
		$("#firstName").val("");
		$("#lastName").val("");
		$("#midName").val("");
		$("#userName").val("");
		$("#pwd").val("");
		//initialize gender select
		project.post("app/base?action=queryGender",{},function(data){
			$("#gender option").remove();
			$.each(data,function(i,v){
				$("#gender").append("<option data-options='"+JSON.stringify(v)+"'>"+v.gender_name+"</option>");
			});
			
		},function(e){
			
		});
		//addDoc event
		$("#saveDoc").click(function(){
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
			param.user_name=userName;
			param.password=password;
			console.log(param);
			project.post("app/base?action=addDoc",param,function(data){
				$("#addModal").modal("hide");
				initializeDoctors();
			},function(e){
				alert("Save failed!");
			});
		});
		
		
		
	}
	//add doctor event 
	$("#addDoc").click(function(){
		$("#addModal").modal("show");
		initializeAddDoc();
	});
	
	
	
});