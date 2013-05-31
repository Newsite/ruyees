//JavaScript Document
$(function(){
	$(".setItem .delItem").click(function(e){
		e.preventDefault();
		$(this).closest("tr").remove();
		$(".setItem tbody tr").each(function(i){
			$(this).find(".num").text(i+1);
		});
	});
	$(".setItem .addItem").click(function(e){
		e.preventDefault();
		addItem();
	});
});

function addItem(){
	var htmrow = "";
	var rows = $(".setItem tbody").children("tr").length;
	htmrow += '<tr><th><span class="num">';
	htmrow += rows+1;
	htmrow += '</span><a href="##" class="delItem"><i class="icon-minus"></i></a></th><td>';
	htmrow += '<input type="text" name="templateItem[3].templateContents[0].courseware.items['+rows+'].name" value="" class="input-medium span5" />';
	htmrow += '</td></tr>';
	var _row = $(".setItem tbody").append(htmrow);
	_row.find(".delItem").click(function(e){
		e.preventDefault();
		$(this).closest("tr").remove();
		$(".setItem tbody tr").each(function(i){
			$(this).find(".num").text(1+i);
		});
	});
}
