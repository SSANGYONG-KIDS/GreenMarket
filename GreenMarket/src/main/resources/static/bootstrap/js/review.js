/**
 * 
 */

var reviewManager= ( function(){
	var getAll= function(obj, callback){
		
		$.getJSON("/review/myreviews/"+obj,callback);
	};
	var add= function(obj, callback){
	
		$.ajax({
			url:"/review/"+obj["tnum"],
			data:JSON.stringify(obj),
			dataType:"json",
			type:"post",
			contentType:"application/json",
			success: callback
		});
	};
	var remove= function(obj, callback){
		console.log("remove review.......");
		$.ajax({
			url:"/review/"+obj["rId"],
			type:"delete",
			success: callback
		});
	};
	
	return { "getAll":getAll, 
			 "add": add,
			"remove":remove
	};
})();