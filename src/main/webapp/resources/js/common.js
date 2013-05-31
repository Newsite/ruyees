//列表批量选中
$(function(){
	// add multiple select / deselect functionality
    $("#selectAll").click(function () {
          $('.check').attr('checked', this.checked);
    });
 
    // if all checkbox are selected, check the selectall checkbox
    $(".check").click(function(){
        if($(".check").length == $(".check:checked").length) {
            $("#selectAll").attr("checked", "checked");
        } else {
            $("#selectAll").removeAttr("checked");
        }
 
    });
});
