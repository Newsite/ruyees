//JavaScript Document
$(function(){
	//编辑页删除and删除行时重组序号
	delRow($(".setTest .delRow"));
	
	//编辑页清空非Self的checkbox
	clearOther();
	
	//点选单选题时清空checkBox
	$('input[name=fdType]').each(function(){
		$(this).click(function(){
			if($('input[name=fdType]:checked').val() == 0 ){
				$('input[name="fdAnswer"]').attr("checked",false);
			}
		});
	});
	
	//增加行
	$(".setTest .addRow").click(function(){
		addRow();		
	});


});
//增加行
function addRow(){
	var htmrow = "";
	var rows = $(".setTest tbody").children("tr").length;
	htmrow += '<tr><th><span class="num">';
	htmrow += rows+1;
	htmrow += '</span><a href="##" class="delRow"><i class="icon-remove-sign"></i></a></th><td>';
	htmrow += '<input type="text" name="fdOption" value="" style="width:350px"/>';
	htmrow += '</td><th><input type="checkbox" class="ck" name="fdAnswer" value="';
	htmrow += rows+1;
	htmrow += '"/></th></tr>';
	var _row = $(".setTest tbody").append(htmrow);
	delRow(_row.find(".delRow"));//追加删除
	
	//单选状态下清空非Self的checkbox
	clearOther();
	
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

//单选状态下清空非Self的checkbox
function clearOther(){
	$('input[name="fdAnswer"]').each(function(){
		$(this).click(function(){
			if($('input[name=fdType]:checked').val() == 0 ){
				
				$('input[name="fdAnswer"]').not(this).attr("checked",false);
			}
			
		});
	})
}