// JavaScript Document
if(!Modernizr.csstransitions){
$(function() {
    
    $.fn.rotate = function(op) {
		var timer1 = null,timer2 = null;
		var $this = $(this);
        var ps = $.extend({
			degree:0,
			minD: -120
		},op||{});
        //$elie.css({ WebkitTransform: 'rotate(' + degree + 'deg)'});  
                             
      	
			$(this.parent("li")).hover(
				function(){
					clearInterval(timer2);
 					 timer1 = setInterval(function() {
					
						if(ps.degree==ps.minD){
							clearInterval(timer1);
							
						}else{
							$this.children(".ch-thumb").css({ '-ms-transform': 'rotate(' + (ps.degree-=10) + 'deg)'});
						}
 					},1);
					
				},
				function(){
			
					clearInterval(timer1);
					timer2 = setInterval(function() {
					
						if(ps.degree==0){
							clearInterval(timer2);
							
						}else{
							$this.children(".ch-thumb").css({ '-ms-transform': 'rotate(' + (ps.degree+=10) + 'deg)'});
						}
 					},1);			
				}
			);
			

			
			
       
		
    }

		$(".ch-item").each(function(){
			$(this).rotate();
		});
}); 
}
