<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">

<title>登录 &ndash; Photo Bug &ndash;</title>




<link rel="stylesheet" href="<c:url value="/css/pure.css" />">

<!--[if lte IE 8]>
	<link rel="stylesheet" href="<c:url value="/css/grids-responsive-old-ie-min.css" />">
<![endif]-->
<!--[if gt IE 8]><!-->
<link rel="stylesheet"
	href="<c:url value="/css/grids-responsive-min.css" />">
<!--<![endif]-->
<style type="text/css">
html, button, input, select, textarea, legend
	.pure-g [class*="pure-u"] {
	font-family: sans-serif;
	font-weight: 100;
	letter-spacing: 0.01em;
}

.custom-form {
	margin: 40px 20px;
}

.c {
	margin: auto;
	max-width: 600px;
	text-align: center;
}

.p {
	font-size: .9em;
	padding-top: 140px;
}
</style>

<!--[if lt IE 9]>
    <script src="<c:url value="/js/html5shiv.js" />" /></script>
<![endif]-->

</head>
<body>
	<form class="pure-form custom-form" onsubmit="return false;">
		<fieldset>
			<legend>登录 Photo Bug</legend>
			<label for="name">用户名</label>
			<input type="text" name="name" id="name" placeholder="综合信息系统用户名"> 
			<label for="password">密码</label>
			<input type="password" placeholder="密码" id="password" name="password">

			<button class="pure-button">登录</button>
		</fieldset>
	</form>
		<p id="login-result">
		</p>
	<script type="text/javascript">
		var contextPath = "<c:url value="/" />";
	</script>
	<script src="<c:url value="/js/jquery.min.js" />" /></script>
	<script src="<c:url value="/js/jquery.placeholder.js" />"></script>
	<script src="<c:url value="/js/jquery-serializeForm.min.js" />"></script>
	<script type="text/javascript">
		$(function(){

			$('input, textarea').placeholder();

			$(".custom-form").submit(function(){
				$.ajax({
					url : '<c:url value="/user/login_action" />',
					dataType : 'json',
					type: "post",
				    data: $(".custom-form").serializeForm()
				}).done(function(data) {
					if(data.ok === true){
						$('#login-result').hide();
						window.location.href='<c:url value="/photo/gallery" />';
					}else{
						$('#login-result').text('用户名不存在或密码错误.')
						$('#login-result').show();
					}
				});
			});
		});
			
	</script>
</body>
</html>