//JavaScript Document
$(function(){
	//编辑页删除and删除行时重组序号
	delRow($(".setTest .delRow"));
	
	//单选和多选之间切换

	checkToggle();

	
	//增加行
	$(".setTest .addRow").click(function(){
		addRow();		
	});


});
//增加行
function addRow(){
	var htmrow = "";
	var inptype = "radio";
	var rows = $(".setTest tbody").children("tr").length;
	if($('input[name=fdType]:checked').val() == 1 ){
		inptype = "checkbox";
	}
	htmrow += '<tr><th><span class="num">';
	htmrow += rows+1;
	htmrow += '</span><a href="##" class="delRow"><i class="icon-remove-sign"></i></a></th><td>';
	htmrow += '<input type="text" name="fdOption" value="" style="width:350px"/>';
	htmrow += '</td><th><input type="';
	htmrow += inptype;
	htmrow += '" class="ck" name="fdAnswer" value="';
	htmrow += rows+1;
	htmrow += '"/></th></tr>';
	var _row = $(".setTest tbody").append(htmrow);
	delRow(_row.find(".delRow"));//追加删除
	

}

//删除行
function delRow(DelBtn){
	DelBtn.click(function(){
		$(this).closest("tr").remove();
		$(".setTest tbody tr").each(function(i){
			$(this).find(".num").text(i+1);
			$(this).find(".ck").val(i+1);
		});
	});
}

//单选和多选之间切换
function checkToggle(){
	
	$('input[name=fdType]').click(function(){
		$('input.ck').each(function(){
			var val = $(this).val();
			if($('input[name=fdType]:checked').val() == 1 ){
				$(this).before('<input type="checkbox" class="ck" name="fdAnswer" value="'+val+'" />');
				$(this).remove();
			}
			else {

				$(this).before('<input type="radio" class="ck" name="fdAnswer" value="'+val+'" />');
				$(this).remove();
				
			}
			
		});
		
	});
			
}