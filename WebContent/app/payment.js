$(function(){
	function initialPayTab(){
		$("#payment_tab tr").remove();
		project.post("app/base?action=queryAvAccountByPat",{},function(data){
			$.each(data,function(i,v){
				var balance=new Number(v.bank_account_blance);
				balance=balance.toFixed(2);
				v.bank_account_blance=balance
				$("#payment_tab").append("<tr>" +
						"<td>"+v.bank_account_num+"</td>" +
						"<td>"+v.organization_name+"</td>" +
						"<td>"+v.bank_account_blance+"</td>" +
						"<td><button type=button class='btn btn-info' data-options='"+JSON.stringify(v)+"'>Deposit</button></td>" +
						"</tr>");
			});
			$(".btn.btn-info").click(function(){
				var deposite = prompt("Please enter amount you want to deposite", "0");
				console.log(deposite);
				if(deposite!=0||deposite!="0"){
					var param=$(this).data("options");
					param.deposite=deposite;
					project.post("app/base?action=updateBalance",param,function(){
						initialPayTab();
					});
					
				}
			});
		});
	}
	initialPayTab();
	
});