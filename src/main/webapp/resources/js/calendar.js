
$(document).ready(function(){
	
	//自定义对象数组，暂代后台数据
	var events = [{title:"广州学校/英联帮",pass:true,date:'2012-11-19',place:'新东方南楼第五会议室',teach:'郑茵 乐静',newteach:'张三',remark:'请携带雅思听力第四课课件打印稿3份。'},{title:"广州学校/英联帮",pass:false,date:'2012-11-26',place:'新东方南楼第五会议室',teach:'郑茵 乐静',newteach:'张三',remark:'请携带雅思听力第四课课件打印稿3份。'},{title:"广州学校/英联帮",pass:false,date:'2012-11-29',place:'新东方南楼第五会议室',teach:'郑茵 乐静',newteach:'张三',remark:'请携带雅思听力第四课课件打印稿3份。'}];
	
	//默认加载第一个备课
	var pkclass="point";
	if(events[0].pass){pkclass="point pass";}
	$("#eventCont").html('<b>备课：</b>'+events[0].title+'<br />\
						<b>批课：</b><b class="'+pkclass+'">'+1+'<i class="text-dack"> / '+events.length+'</i></b><br />\
						<b>时间：</b>'+events[0].date+'<br />\
						<b>地点：</b>'+events[0].place+'<br />\
						<b>批课教师：</b>'+events[0].teach+'<br />\
						<b>新教师：</b>'+events[0].newteach+'<br />\
						<b>备注：</b>'+events[0].remark+'<br />\
					');
	
					
	//测试当天是否有事件
    function hasEvents(date){
		var da = date.getFullYear()+"-"+fill0(date.getMonth()+1)+"-"+fill0(date.getDate());
		for(var i in events){
			if(events[i].date == da ){  
				return events[i];
			}
		}
		return false;
	}
	
	//不足10的补0
	function fill0(n){
		if(n<10){
			return "0"+n;
		}
		else{
			return n;
		}
	}
	
			
	$("#calendar").datepicker({
		dateFormat: "yy-mm-dd",
		beforeShowDay: function(date) { 
			var css = "";
			var alt = "";
			if(hasEvents(date)){
				if(hasEvents(date).pass){
					css = "day-isPass";				
					alt = "已批";
				}else{
					css = "day-notPass";				
					alt = "未批";
				}
			}
			return [true,css,alt];
		},
		onSelect: function(dateText,inst){
			var pkclass="point";
			for(var x in events){
				if(events[x].date == dateText)
				{
					if(events[x].pass){
						pkclass="point pass";
					}
					$("#eventCont").html('<b>备课：</b>'+events[x].title+'<br />\
						<b>批课：</b><b class="'+pkclass+'">'+(parseInt(x)+1)+'<i class="text-dack"> / '+events.length+'</i></b><br />\
						<b>时间：</b>'+events[x].date+'<br />\
						<b>地点：</b>'+events[x].place+'<br />\
						<b>批课教师：</b>'+events[x].teach+'<br />\
						<b>新教师：</b>'+events[x].newteach+'<br />\
						<b>备注：</b>'+events[x].remark+'<br />\
					');
				}
				
			}
		}

	});
/*	$(".day-hasEvent").tooltip({
		delay: {show: 300, hide: 500}
	});*/


})

