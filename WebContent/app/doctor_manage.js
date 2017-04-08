$(function(){
	//get all doctors
	project.post("app/base?action=queryDoctor",{beginNum:0,endNum:10},function(data){
		$.each(data,function(i,v){
			var full_name="";
			full_name=v.doctor_name_last+" ";
			if(v.doctor_name_mid!=""){
				full_name+=v.doctor_name_mid+" ";
			}
			full_name+=v.doctor_name_first;
			var jsonString=JSON.stringify(v);
			$("#doctor_list").append("<tr>" +
					"<td>"+full_name+"</td>" +
							"<td>"+v.gender_name+"</td>" +
							"<td><button type=button class='btn btn-info' data-full_name='"+full_name+"' data-options='"+jsonString+"'>Detail</button>" +
							"<button type=button  style='margin-left:20px' class='btn btn-danger'>Delete</button></td>"+
							
							"</tr>");
			
		});
		//add detail events
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
	});
	//initialize patient list
	function initializePatients(param){
		project.post("app/base?action=queryPatByDoc",param,function(data){
			$.each(data,function(i,v){
				$("#patient_list").append("<tr>" +
						"<td>"+v.patient_name+"</td>" +
								"<td>"+v.gender_name+"</td>" +
								"<td><button type=button class='btn btn-info' >Detail</button>" +
								"<button type=button  style='margin-left:20px' class='btn btn-danger'>Delete</button></td>"+
								
								"</tr>");
			});
			
			$("#patient_list");
		},function(e){
			
		});
	}
	
	
});