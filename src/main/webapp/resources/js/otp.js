// iframe autoheight
$("iframe").load(function(){
		$(this).height($(this).contents().height());
});
function setIframeH(h){
	$("iframe").each(function(){
		$(this).height(h);
	});
}

//tooltip
$("a[rel=tooltip]").tooltip();

//第三关popover
$("#threeStep").popover({
	html: true
});
function disPop3(){
	$("#threeStep").popover("destroy");
}

//iframe control
$(".steps-tabs>.nav-tabs>li>a").each(function(i){
	$(this).click(function(e){
		if($(this).closest("li").hasClass("disabled")){
			e.preventDefault()
		}
		else{
			$(this).closest("li").addClass("active").siblings().removeClass("active");
		}
	});
});

function nextIfra(){
	var next = 0;
	$(".steps-tabs>.nav-tabs>li").each(function(i){
		
		if($(this).hasClass("active") && i<5)
		{
			$(this).next().removeClass("disabled");
			$(this).removeClass("active");
			next = i+1;
		}
		
	});
	if(next<6)	 {
		var url = $(".steps-tabs>.nav-tabs>li").eq(next).addClass("active").children().attr("href");
		$("iframe").attr("src",url);
	}
//	if(next==2){
//		
//		$(".steps-tabs>.nav-tabs>li").eq(next).children("a").popover({html:true});
//		$(".steps-tabs>.nav-tabs>li").eq(next).children("a").popover("show");
//	}
}

//active Step
function activeStep(index){
	$(".steps-tabs>.nav-tabs>li").each(function(i){
		if(i<index){
			$(this).removeClass("disabled");
			if(i==index-1){
				$(this).addClass("active").siblings().removeClass("active");
				$("iframe").attr("src",$(this).children().attr("href"));
			}			
		}
	});
}

//iframe jalert2
function ifraAlert2(msg){
	$.fn.jalert2(msg);
}
//iframe jalert3
function ifraAlert3(msg,goOn){
	$.fn.jalert3(msg,goOn);
}

//activeStep(2)