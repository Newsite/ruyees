
$().ready(function(){
	$(".kj-li h6").each(function(){
		$(this).text($(this).next().val());
	});
	
	$(".kj-edit").click(function(){
			if($('.kj-li .thumbnail').hasClass("edit")){
				$(".kj-li h6").each(function(){
					$(this).text($(this).next().val());
				});
				$(this).button("reset");
				$(this).next().removeAttr('disabled');
			}
			else{			
			$(this).button("cancel");
			$(this).next().attr('disabled','disabled');
			$(this).toggleClass("active");
			$('.kj-li .del-kj').click(function(e){
				e.preventDefault();
					$(this).closest('.kj-li').remove();
			});

			}
			$('.kj-li .thumbnail').toggleClass("edit");

		


		
	})

})

