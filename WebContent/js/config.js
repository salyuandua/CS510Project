
(function(window){
	var project={};
	project.rootPath="http://"+location.host+"/CS510Project/";
	project.post=function(url,param,success,fail){
		param=JSON.stringify(param);
		$.post(project.rootPath+url,{"param":param},function(data){
			data=JSON.parse(data);
			console.log(data);
			success(data);
		}).fail(function(e){
			console.log(e);
			fail(e);
		});
	};
//	project.paramBuider=function(attrArr){//["id=xxx","class=xxx"]
//		for(var i=0;i<attrArr.length;i++){
//			console.log(attrArr[i].attrType+"****"+attrArr[i].attrName);
//		}
//	}
	
	
	window.project=project;
})(window);