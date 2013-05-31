
$().ready(function(){
	var data = [{text:"wang",url:"/page1"},{text:"zhang",url:"/page2"},{text:"zhang",url:"/page2"},{text:"zhang",url:"/page2"},{text:"zhang",url:"/page2"},{text:"zhang",url:"/page2"},{text:"zhang",url:"/page2"},{text:"zhang",url:"/page2"},{text:"zhang",url:"/page2"},{text:"zhang",url:"/page2"},{text:"zhang",url:"/page2"},{text:"zhang",url:"/page2"},{text:"zhang",url:"/page2"},{text:"zhang",url:"/page2"},{text:"zhang",url:"/page2"},{text:"zhang",url:"/page2"},{text:"zhang",url:"/page2"},{text:"zhang",url:"/page2"},{text:"zhang",url:"/page2"},{text:"zhang",url:"/page2"},{text:"zhang",url:"/page2"},{text:"zhang",url:"/page2"},{text:"zhang",url:"/page2"},{text:"zhang",url:"/page2"},{text:"zhang",url:"/page2"},{text:"zhang",url:"/page2"},{text:"zhang",url:"/page2"},{text:"zhang",url:"/page2"},{text:"zhang",url:"/page2"},{text:"zhang",url:"/page2"},{text:"zhang",url:"/page2"},{text:"zhang",url:"/page2"},{text:"zhang",url:"/page2"},{text:"zhang",url:"/page2"},{text:"zhang",url:"/page2"},{text:"zhang",url:"/page2"},{text:"zhang",url:"/page2"},{text:"zhang",url:"/page2"},{text:"zhang",url:"/page2"},{text:"zhang",url:"/page2"},{text:"zhang",url:"/page2"},{text:"zhang",url:"/page2"},{text:"zhang",url:"/page2"},{text:"liang",url:"/wpage3"},{text:"lina",url:"/page4"},{text:"wang",url:"/page1"},{text:"zhang",url:"/page2"},{text:"liang",url:"/wpage3"},{text:"lina",url:"/page4"},{text:"wang",url:"/page1"},{text:"zhang",url:"/page2"},{text:"liang",url:"/wpage3"},{text:"lina",url:"/page4"},{text:"wang",url:"/page1"},{text:"zhang",url:"/page2"},{text:"liang",url:"/wpage3"},{text:"lina",url:"/page4"}];
	//$.getJSON("URL",function(data){
		$("#inpUser").autocomplete(data,{
			formatItem: function(item) { 
				return item.text; 
			},
			matchContains:true ,
			max: 100,
			width:410

		})
		/*.result(function(event,item){
			location.href = item.url;
		});*/
	//});
	$(".u_list tbody a").click(function(){
				$(this).parents("tr").remove();
	});
	$("#addUser").click(function(){
		var val = $("#inpUser").val();
			$(".u_list tbody").append('<tr><td>'+val+'</td><td><a class="text-error">(remove)</a></td></tr>')
			.find("a").click(function(){
				$(this).parents("tr").remove();
			});	

		
	});
	
})