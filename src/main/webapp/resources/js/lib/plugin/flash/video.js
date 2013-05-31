function playVideo(videoname){
	var flashvars =
	   {
	      //flash接收的参数
		  autoPlay:'true',
		  skin:"/otp/resources/js/lib/plugin/flash/videoPlayerSkin.swf",
		  video:"mp4:"+videoname,
		  fms:"rtmp://video.xdf.cn/V3"
		};
		var params = 
		{
		  allowScriptAccess: "always",
		  allowFullScreen:"true"
		};
		var attributes = 
		{
		  id: "video"
		};
		swfobject.embedSWF("/otp/resources/js/lib/plugin/flash/videoplayer.swf", "video", "620", "436", "6.0.0", "expressInstall.swf", flashvars, params, attributes);
}

$(document).ready(function(){

	$('#videoList').jcarousel({
		vertical: true,
        scroll: 1,
		buttonNextHTML: '<button id="goD" class="btn btn-block" ><b class="caret"></b></button>',
        buttonPrevHTML: '<button id="goU" class="btn btn-block"><b class="caret"></b></button>'
		
	});
	var firstVideo = $('#videoList li a').eq(0).attr("href");
	currentMovieId = $('#videoList li a').eq(0).attr("id");
	playVideo(firstVideo);
	/*$('#video').flash({ 
			//src: 'baserk_vod/a_285x181.swf',
			//src: firstVideo,
			pluginspage:'/otp/resources/js/lib/plugin/flash/videoplayer.swf',
			flashvars: {
				playerSkinSrc: "/otp/resources/js/lib/plugin/flash/videoPlayerSkin.swf",
				autoPlay: true,
				video:"mp4:1",
				fms:"rtmp://192.168.25.66/kmss"
			},
			quality:'high',
			allowFullScreen:true,
			allowScriptAccess:'always',
			width: 620,
			height: 436,
			wmode: 'opaque'
		},
		{
			version: 6
		});
		*/
	$('#videoList li a').click(function(e){
		e.preventDefault();
		var url = $(this).attr("href");
		playVideo(url);
		/*$('#video').empty().flash({ 
			src: url,
			pluginspage:'http://www.macromedia.com/go/getflashplayer',
			quality:'high',
			flashvars: {playerSkinSrc: "videoPlayerSkin1.swf",autoPlay: true},
			allowFullScreen:true,
			allowScriptAccess:'always',
			width: 620,
			height: 436,
			wmode: 'opaque'
		},
		{
			version: 8
		});*/
		
	});
})


