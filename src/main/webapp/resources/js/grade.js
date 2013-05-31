//JavaScript Document
$(function(){
	$(".setNew .delNew").click(function(e){
		e.preventDefault();
		$(this).closest("tr").remove();
		$(".setNew tbody tr").each(function(i){
			$(this).find(".num").text(i+1);
		});
	});
	$(".setNew .addNew").click(function(e){
		e.preventDefault();
		addNew();
	});
});
function addNew(){
	var htmrow = "";
	var rows = $(".setNew tbody").children("tr").length;
	htmrow += '<tr><th><span class="num">';
	htmrow += rows+1;
	htmrow += '</span><a href="##" class="delNew"><i class="icon-minus"></i></a></th><td>';
	htmrow += '<input type="text" name="templateItem[4].templateContents[0].newItemConfs['+rows+'].fdName" value="" class="input-medium" />';
	htmrow += '</td><td><input type="text" name="templateItem[4].templateContents[0].newItemConfs['+rows+'].fdIndex" value="" class="input-xmini" /></td>';
	htmrow += '<td><input type="text" name="templateItem[4].templateContents[0].newItemConfs['+rows+'].fdRemarker" class="input-block"/></td></tr>';
	var _row = $(".setNew tbody").append(htmrow);
	_row.find(".delNew").click(function(e){
		e.preventDefault();
		$(this).closest("tr").remove();
		$(".setNew tbody tr").each(function(i){
			$(this).find(".num").text(1+i);
		});
	});
}

$(function(){
	$(".setCheck .delCheck").click(function(e){
		e.preventDefault();
		$(this).closest("tr").remove();
		$(".setCheck tbody tr").each(function(i){
			$(this).find(".num").text(i+1);
		});
	});
	$(".setCheck .addCheck").click(function(e){
		e.preventDefault();
		addCheck();
	});
});
function addCheck(){
	var htmrow = "";
	var rows = $(".setCheck tbody").children("tr").length;
	htmrow += '<tr><th><span class="num">';
	htmrow += rows+1;
	htmrow += '</span><a href="##" class="delCheck"><i class="icon-minus"></i></a></th><td>';
	htmrow += '<input type="text" name="templateItem[4].templateContents[0].evalItems['+rows+'].fdName" value="" class="input-medium" />';
	htmrow += '</td><td><input type="text" name="templateItem[4].templateContents[0].evalItems['+rows+'].fdIndex" value="" class="input-xmini" /></td>';
	htmrow += '<td><input type="text" name="templateItem[4].templateContents[0].evalItems['+rows+'].fdExplain" class="input-block"/></td></tr>';
	var _row = $(".setCheck tbody").append(htmrow);
	_row.find(".delCheck").click(function(e){
		e.preventDefault();
		$(this).closest("tr").remove();
		$(".setCheck tbody tr").each(function(i){
			$(this).find(".num").text(1+i);
		});
	});
}