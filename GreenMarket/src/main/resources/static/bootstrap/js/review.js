/**
 * 
 */

var reviewManager= ( function(){
	var getAll= function(obj, callback){
		console.log("getAll......."+obj);
		$.getJSON("/review/myreviews/"+obj,callback);
	};
	var add= function(obj, callback){
		console.log("add review.......");
		console.log(obj["rContent"]);
		console.log(obj["rStar"]);
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