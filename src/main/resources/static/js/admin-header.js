$(document).ready(function(){
	var pathS=window.location.pathname.split("/");
	var element=$("#admin-header-nav a[href*='"+pathS[3]+"']");
	var liActiveElement=element.parent().addClass("active");
  
});