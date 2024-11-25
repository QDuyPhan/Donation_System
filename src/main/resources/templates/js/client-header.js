$(document).ready(function(){

	var pathS=window.location.pathname.split("/");
	var element=$("#client-header-nav a[href*='"+pathS[2]+"']");
	var liActiveElement=element.parent().addClass("active");
$("#btn-Search").on("click",function(){
	var keyword=$("#keyword").val();
	var pathS=window.location.pathname.split("/");
	window.location.replace("/"+pathS[1]+"/search?keyword="+keyword);
});
});