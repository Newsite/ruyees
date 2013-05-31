//JavaScript Document
$(function(){
	$(".setTest .delRow").click(function(){
		$(this).closest("tr").remove();
		$(".setTest tbody tr").each(function(i){
			$(this).find(".num").text(i+1);
		});
	});
	$(".setTest .addRow").click(function(){
		addRow();
		
	})
});
function addRow(){
	var htmrow = "";
	var rows = $(".setTest tbody").children("tr").length;
	htmrow += '<tr><th><span class="num">';
	htmrow += rows+1;
	htmrow += '</span><a href="##" class="delRow"><i class="icon-minus"></i></a></th><td>';
	htmrow += '<input type="text" name="indexs['+rows+'].fdOrder" value="" class="input-xmini required number"/></td><td>';
	htmrow += '<input type="text" name="indexs['+rows+'].fdIndexName" value="" class="span2 required"/>';
	htmrow += '</td><td><input type="text" name="indexs['+rows+'].fdValue" class="input-mini required number" value=""/></td>';
	htmrow += '<td><input type="text" id="fileName'+rows+'" name="indexs['+rows+'].fdFileName" class="span2"/><input type="hidden" id="fullName'+rows+'" name="indexs['+rows+'].attMain.filePath"  class="span1"/><a class="btn" onclick="javascript:openss(\'fileName'+rows+'\',\'fullName'+rows+'\');" id="demo_04">运行»</a></td></tr>';
	var _row = $(".setTest tbody").append(htmrow);
	_row.find(".delRow").click(function(){
		$(this).closest("tr").remove();
		$(".setTest tbody tr").each(function(i){
			$(this).find(".num").text(1+i);
		});
	});
	
}