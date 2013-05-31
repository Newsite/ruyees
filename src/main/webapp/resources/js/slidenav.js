/*左侧子菜单显隐*/

$(".bs-docs-sidenav > li > a").click(function(){
	$(this).parent().addClass("active").siblings().removeClass("active");
	$(this).parent().siblings().find("ul").hide();
	if($(this).next("ul").length > 0)
	{
	$(this).next("ul").slideToggle();
	$(this).children("i").toggleClass("icon-chevron-down");
	}
	$(this).parent().siblings().find("i").removeClass("icon-chevron-down");
});
$(".bs-docs-sidenav ul li").click(function(){
	$(this).addClass("active").siblings().removeClass("active");
});