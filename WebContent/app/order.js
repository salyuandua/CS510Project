$(function(){
	//initail main order table
	initialMainPage();
	function initialMainPage(){
		$("#order_tab tr").remove();
		project.post("app/base?action=queryOrderByPat",{},function(data){
			$.each(data,function(i,v){
				var statueClass="<span class='label label-primary'>Submitted</span>";
				var disbale="";
				if(v.orders_statue==0||v.orders_statue=="0"){
					statueClass="<span class='label label-danger'>Returned</span>";
					disbale="disabled";
				}
				$("#order_tab").append("<tr>" +
						"<td>"+v.orders_num+"</td>" +
						"<td>"+v.order_date+"</td>" +
						"<td>"+v.orders_tax+"</td>" +
						"<td>"+v.orders_total_price+"</td>" +
						"<td>"+v.bank_account_num+"("+v.organization_name+")</td>" +
						
						"<td>"+statueClass+"</td>"+
						"<td><button type=button class='btn btn-info' data-options='"+JSON.stringify(v)+"'>Detail</button>" +
								"<button "+disbale+" type=button class='btn btn-danger' style='margin-left:10px'  data-options='"+JSON.stringify(v)+"'>Return</button></td>" +
						"</tr>");
			});
			//add detail event
			$(".btn.btn-info").click(function(){
				$("#myModal").modal("show");
				initialMedTab2($(this).data("options"));
			});
			//return
			$(".btn.btn-danger").click(function(){
				if(confirm("Are you sure return this order?")==true){
					//return
					var param={};
					param.order_id=$(this).data("options").orders_id;
					project.post("app/base?action=returnOrder",param,function(){
						initialMainPage();
					});
				}
			});
		},function(){
			
		});
		
	}
	//initial med table2
	function initialMedTab2(order){
		$("#order_num").html("Order Nnmber:&nbsp&nbsp&nbsp"+order.orders_num);
		$("#order_date").html("Order Date:&nbsp&nbsp&nbsp"+order.order_date);
		$("#payment").html("Payment:&nbsp&nbsp&nbsp"+order.bank_account_num+"("+order.organization_name+")");
		$("#tax").html("Tax:"+order.orders_tax);
		var sub_total=new Number(order.orders_total_price-order.orders_tax);
		sub_total=sub_total.toFixed(2);
		$("#sub_total").html("Sub Total:"+sub_total);
		$("#total").html("Total:"+order.orders_total_price);
		$("#med_tab2 tr").remove();
		project.post("app/base?action=queryOrderedMed",order,function(data){
			$.each(data,function(i,v){
				$("#med_tab2").append("<tr>" +
						"<td>"+v.medicine_name+"</td>" +
						"<td>"+v.manufacturer_name+"</td>" +
						"<td>"+v.medicine_price+"</td>" +
						"<td>"+v.order_medicine_req_num+"</td>" +
						"</tr>");
			});

		},function(){
			
		});
	}
	
	$("#addOrder").click(function(){
		$("#addModal").modal("show");
		initialAddPage();
	});
	function initialAddPage(){
		$("#med_list option").remove();
		$("#med_tab tr").remove();
		$("#pay_radio div").remove();
		project.post("app/base?action=queryAvMedByPre",{},function(data){
			$.each(data,function(i,v){
				$("#med_list").append("<option data-options='"+JSON.stringify(v)+"'>"+v.medicine_name+" ("+v.manufacturer_name+")</option>");
			});
			$("#med_list option").dblclick(function(){
				var temp=$(this).data("options")
				$(this).remove();
				initialMedTab(temp);
			});

		},function(){
			
		});
		//initial payment
		project.post("app/base?action=queryAvAccountByPat",{},function(data){
			$.each(data,function(i,v){
				var balanace=new Number(v.bank_account_blance);
				balanace=balanace.toFixed(2);
				v.bank_account_blance=balanace;
				$("#pay_radio").append("<div class=radio>" +
						"<label><input name=payment_radio type=radio data-options='"+JSON.stringify(v)+"'>"+v.bank_account_num+"&nbsp&nbsp&nbsp&nbsp&nbspBank:&nbsp "+v.organization_name+"&nbsp&nbsp&nbsp&nbsp&nbspBalance:&nbsp"+v.bank_account_blance+"</label>" +
						"</div>");
			});

		},function(){
			
		});
		
	}
	//inital medicine table
	function initialMedTab(medicine){
		$("#med_tab").append("<tr data-options='"+JSON.stringify(medicine)+"'>" +
				"<td>"+medicine.medicine_name+"</td>" +
				"<td>"+medicine.manufacturer_name+"</td>" +
				"<td>"+medicine.medicine_price+"</td>" +
				"<td>"+medicine.medicine_available_count+"</td>" +
				"<td><input data-price="+medicine.medicine_price+" type=text class=form-control ></td>"+
				
				"</tr>");
		//comput total price
		$("#med_tab").find(":input").focusout(function(){
			console.log($(this).val());
			var sub_total=new Number(0);
			$.each($("#med_tab").find(":input"),function(i,v){
				var num=new Number(0);
				if($(v).val()!=""){
					num=$(v).val();
				}
				sub_total+=$(v).data("price")*num;
			});
			sub_total=sub_total.toFixed(2);
			var tax=new Number(sub_total*0.07);
			tax=tax.toFixed(2);
			var total=new Number(Number(sub_total)+Number(tax));
			total=total.toFixed(2);
			console.log("*****"+total)
			//total=total.toFixed(2);
			$("#sub_total1").html("Sub Total:&nbsp&nbsp&nbsp"+sub_total);
			$("#sub_total1").data("price",sub_total);
			$("#total1").html("Total:&nbsp&nbsp&nbsp"+total);
			$("#total1").data("price",total);
			$("#tax1").html("Tax:&nbsp&nbsp&nbsp"+tax);
			$("#tax1").data("price",tax);
		});
	}
	//save
	$("#save").click(function(){
		
		if($("#med_tab tr").length==0){
			alert("Please enter at least one medicine!");
			return;
		}
		console.log($(":radio:checked"))
		if($(":radio:checked")==0){
			alert("Please select payment method!");
			return
		}
		//check 
		var param={};
		param.total=$("#total1").data("price");
		param.tax=$("#tax1").data("price");
		param.account_id=$(":radio:checked").data("options").bank_account_id;
		param.med_list=[];
		$.each($("#med_tab tr"),function(i,v){
			var oneMed={};
			oneMed.medicine_id=$(v).data("options").medicine_id;
			oneMed.num=$(v).find(":input").val();
			param.med_list.push(oneMed);
		});
		console.log(param);
		project.post("app/base?action=addOrder",param,function(data){
			if(data=="BALANCE_NOT_EN"){
				alert("Your balance is not enough!");
				return;
			}
			if(data==false){
				alert("Save failed!");
			}
			$("#addModal").modal("hide");
			initialMainPage();
			//success
		},function(){
			alert("Save failed!");
		});
		
	});
	
	
	
	
});