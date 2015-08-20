<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
%>
<%
	String root_path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">

	<link href="../../css/bootstrap.min.css" rel="stylesheet">
	<link href="../../css/navbar-fixed-top.css" rel="stylesheet">
	<link href="../../css/bootstrap-table.css" rel="stylesheet">
	<link href='../../css/bootstrap-theme.min.css' rel='stylesheet' />

	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script src="../../js/bootstrap.min.js"></script>
	<script src="../../js/bootstrap-table.js"></script>

	<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
	<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
	<!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
	<title>::MICE::</title>
	<!-- Bootstrap -->
	<link href="../../css/bootstrap.min.css" rel="stylesheet">
	<link href="../../css/kfonts2.css" rel="stylesheet">

	<script language="JavaScript">
	<!--
		function MM_preloadImages() { //v3.0
			var d = document;
			if (d.images) {
				if (!d.MM_p)
					d.MM_p = new Array();
				var i, j = d.MM_p.length, a = MM_preloadImages.arguments;
				for (i = 0; i < a.length; i++)
					if (a[i].indexOf("#") != 0) {
						d.MM_p[j] = new Image;
						d.MM_p[j++].src = a[i];
					}
			}
		}
	//-->
	</script>
	
	<script language="javascript">
	<!--
		function goLogin() {
	
			if (document.login.adminId.value == "") {
				alert("아이디를 입력하세요.");
				document.login.adminId.focus();
				return;
			}
	
			if (document.login.adminPass.value == "") {
				alert("비밀번호를 입력하세요.");
				document.login.adminPass.focus();
				return;
			}
	
			document.login.action = "login_proc.jsp";
			document.login.submit();
		}
	//-->
	</script>
</head>

<body>
	<form name="login" method="post">
		<!--전체 -->
		<div class="container" style="margin-top: 100px; max-width: 600px;">
			<!--login -->
			<span style="text-align: center;"><h1>MICE Admin Page</h1></span>
			<br></br>
			<div class="input-group">
				<span class="input-group-addon"> <span
					class="glyphicon glyphicon-user"> </span>
				</span> <input name="adminId" type="text" class="form-control" id="adminId"
					tabindex=1 placeholder="아이디">
			</div>
			<br>
			<div class="input-group">
				<span class="input-group-addon"> <span
					class="glyphicon glyphicon-lock"></span>
				</span> <input name="adminPass" type="password" class="form-control"
					id="adminPass" onKeypress="if (event.keyCode==13) goLogin();"
					placeholder="암 호">
			</div>
			<br>
			<button type="button" style="float: right;" class="btn btn-default" onClick="goLogin();">로그인 </button>

			<!--//login -->
		</div>
		<!--//전체 -->

	</form>
</body>
</html>