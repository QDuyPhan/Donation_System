$(document).ready(function() {
	//set up web display
	if ($("#user-username").val() != "") $("#user-username").prop('readonly', true);
	if($("#user-id").val()==""){
		$("#user-status").prop('disabled',true);
	}
	else{
	//	if($("#user-role option:selected").text()=='Admin')$("#user-role").prop('disabled',true);
	}
	$("#btn-Save").on("click", function(event) {
		if(!validateUser()){
			alert("ok");
			event.preventDefault();
		}
		var pathS = window.location.pathname.split("/");
		if ($("#user-id").val() == "") {
			$("form").attr("action", "/" + pathS[1] + "/admin/user/?action=add");
		}
		else {
			$("form").attr("action", "/" + pathS[1] + "/admin/user/?action=edit");
		}


	})
	$("#btn-ResetPassword").on("click",function(){
		var pathS=window.location.pathname.split("/");
		$("form").attr("action", "/" + pathS[1] + "/admin/user/?action=resetpassword");
	})
	$("#btn-Cancel").on("click",function(){
		var pathS=window.location.pathname.split("/");
		window.location.replace("/"+pathS[1]+"/admin/user/");
	})
})
function validateUser() {
  var isValidate=true;
  var regexMail=/^[a-z][a-z0-9_\.]{5,32}@[a-z0-9]{2,}(\.[a-z0-9]{2,4}){1,2}$/g;
  if($("#user-username").val()==""){
	isValidate=false;
	$("#user-username").siblings(".form-message").text("This field is required !");
}
if($("#user-sdt").val()==""){
	isValidate=false;
	$("#user-sdt").siblings(".form-message").text("This field is required !");
}
if($("#user-email").val()==""){
	isValidate=false;
	$("#user-email").siblings(".form-message").text("This field is required !");
}
else if(!regexMail.test($("#user-email").val())){
	isValidate=false;
	$("#user-email").siblings(".form-message").text("Email is not valid!");
}
return isValidate;
}