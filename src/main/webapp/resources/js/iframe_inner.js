
$(document).ready(function(){
	//tooltip
	$("a[rel=tooltip]").tooltip();
	//popover
	$("[rel=popover]").popover({html:true});
	//show More list
	$(".showMoreWrap .media").showMore();
	$(".showMoreWrap .askList > li").showMore();
	$(".showMoreWrap .zl-list > li").showMore();
	
	//next step
//	$(".icon-nextStep").click(function(e){
//		if(!$(this).parent().hasClass("btn-next")){
//			e.preventDefault();
//			window.parent.nextIfra();
//		}
//	});
	//视频看过打勾
	$("#videoList > li > a > .text-success, #carousel > li > a > .text-success").before('<i class="ico-ok"></i>');
	
	var hasac = $(".jcarousel-list").children().hasClass("active");
	$(".jcarousel-list .jcarousel-item-vertical").each(function(i){
		if(!hasac && i==0){
			$(this).addClass("active");
		}
		$(this).bind("click",function(){
			$(this).addClass("active").siblings().removeClass("active");
		});
	});
	
	//点当前无链接
	$(".location .active").each(function(i){
		$(this).attr("href","#").css("cursor","default");
		$(this).click(function(e){
			e.preventDefault();	
		});	
	});
	$("a[disabled]").click(function(e){
		e.preventDefault();
	});
	
//	//在线测试
//	$("#butsubmit").click(function(e){
//		var empty = false;
//		//var answers = [1,1,1,1];//答案
//		//var rights = 0;
//		//var nums = answers.length;
//		
//		$(".examList dd ol").each(function(i){
//				
//				if($(this).find(":radio:checked,:checkbox:checked").val()== undefined ){
//					empty=true;
//				}/*else if($(this).find(":radio:checked").val()==answers[i])
//				{
//					rights++;
//				}*/
//			});
//			if(empty){
//				e.preventDefault();
//				window.parent.ifraAlert2("您好！请答完题再提交！");
//				return false;
//			}
//			else{
//				var data = $("#ajaxform").serializeArray();
//				$.post("ajax/exam/pushExam", data,
//						function (data){
//						alert(data.result);
//					}, "json");
//			}
//		});
	
	//名师教学DEMO
	$("#btn-success").click(function(e){
		e.preventDefault();
		$(this).remove();
		goTop(function(){
			$(".success_box").show("normal",function(){
				var h = $(document).height();
				window.parent.setIframeH(h);
			});
		});
	});
	
	//给老师评分
	$(".rating-star-bar a").each(function(i){
		$(this).bind({
		click:function(e){
			e.preventDefault();
			$(this).prevAll("a").addClass("rated");
			$(this).nextAll("a").removeClass("rated");
			$(this).addClass("rated");
			$(this).prevAll("input").val($(this).prevAll("a").length+1);
			var rated = $(this).closest("ol").find(".rated").length;
			var rows = $(this).closest("ol").find(".rating-star-bar").length;
			$(this).closest(".tab-pane").find(".point").text(rated/rows).prev().val(rated/rows);
		},
		mouseover:function(){$(this).prevAll("a").addClass("active")},
		mouseout:function(){$(this).prevAll("a").removeClass("active")}
		});
	});
	
	//进度条test
	$(".uploadList>li .btn-upload").each(function(){
		$(this).click(function(){
			var $this=$(this);
			var bar = $(this).nextAll(".progress");
			var $linkbtn=$(this).nextAll(".btn-link");
			$this.hide();
			bar.removeClass("hide");
			$linkbtn.addClass("hide");
			var temp = setInterval(sum,100);
			var n=0;
			function sum(){
				if(n<101){
					bar.children(".bar").width(n+"%");
					//alert(bar.width());
					n++;
				}else{
					clearInterval(temp);
					bar.addClass("hide");
					bar.prevAll(".btn-submit").removeAttr("disabled");
					$linkbtn.removeClass("hide");
					$this.show();
					bar.parent().next().find(".text-warning").text("未提交");
					bar.parent().next().find(".text-warning").removeClass("text-warning").addClass("text-info");
				}
			}
		});
	});
	
});

function RateArticleSuccess(data){
	alert(data);
	if(data.throng){
		window.parent.ifraAlert3("恭喜你全答对了!",function(){				
			goTop(succBox('恭喜你完成  <span class="text-info">业务学习</span>  关卡！请进入下一关  <span class="text-info">学术准备</span>','##',"点击进入下一关"));
			
		});
	}else{
		window.parent.ifraAlert3("您好！您做对<b class='point'>"+3+"</b>题，做错了<b class='point'>"+1+"</b>题!",function(){				
			goTop(succBox('恭喜你完成  <span class="text-info">业务学习</span>  关卡！请进入下一关  <span class="text-info">学术准备</span>','##',"点击进入下一关"));
			
		});
	}
	
}

//go parent top
function goTop(callback){
		$("html,body",window.parent.document).animate({scrollTop:0}, 'normal',callback);
}
//reset parent iframe height
function setParentHeight(){
	var h = $(document).height();
	window.parent.setIframeH(h);
}
$(window).load(setParentHeight);
//jObj
(function($){
	//showMore
	$.fn.showMore = function(op){
		var ps = $.extend({
			num:3,
			allNum:	$(this).length,
			sendH:	function (){
						var h = $(document).height();
						window.parent.setIframeH(h);
					}
		},op||{});
		$(this).each(function(i){
			if(i>ps.num-1){
				$(this).hide();
			}
		})
		
		$(this).parent().next(".showMore").click(function(e){
			e.preventDefault();
			if(ps.allNum > ps.num){
				if($(this).children("i").hasClass("icon-chevron-down")){
					$(this).children("i").addClass("icon-chevron-up").removeClass("icon-chevron-down");
					$(this).children("span").text("收起");
				}else{
					$(this).children("i").addClass("icon-chevron-down").removeClass("icon-chevron-up");
					$(this).children("span").text("查看全部");
				}
				
				$(this).prev().children().each(function(i){
					if(i>ps.num-1){
						$(this).slideToggle("normal",ps.sendH);
					}
				});
			}
		});
	}
	//
})(jQuery);
//create success box
function succBox(msg,url,tip){
	$(".success_box").html(msg+'<a href="'+url+'" rel="tooltip" title="'+tip+'" class="icon-nextStep"></a>').show("normal",function(){
		var h = $(document).height();
		window.parent.setIframeH(h);
	}).find("a[rel=tooltip]").tooltip();
}

function disPop(obj){
	$(obj).closest(".popover").prev().popover("hide");
}