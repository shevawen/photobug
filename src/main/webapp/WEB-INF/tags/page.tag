<%@ tag description="Page template"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ attribute name="pageTitle" required="true"%>
<%@ attribute name="pageDescription" required="false"%>
<%@ attribute name="pageStyles" fragment="true"%>
<%@ attribute name="pageScript" fragment="true"%>

<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="A layout example that shows off a responsive photo gallery.">

    <title>${pageTitle } &ndash; Photo Bug &ndash; </title>

    


<link rel="stylesheet" href="<c:url value="/css/pure.css" />">

<!--[if lte IE 8]>
	<link rel="stylesheet" href="<c:url value="/css/grids-responsive-old-ie-min.css" />">
<![endif]-->
<!--[if gt IE 8]><!-->
	<link rel="stylesheet" href="<c:url value="/css/grids-responsive-min.css" />">
<!--<![endif]-->
<style type="text/css">

	html, button, input, select, textarea,legend
	.pure-g [class *= "pure-u"] {
	    font-family: sans-serif;
	    font-weight: 100;
	    letter-spacing: 0.01em;
	}
	#wrapper {
	    margin: 0 auto;
	    background: #F6F7F8;
	    min-width: 950px;
	}
	.header .pure-menu-list li {
	    padding: 0px 10px 0;
	}
	.header {
	    width: 100%;
	    height: 44px;
	    z-index: 20;
	    top: 0;
	    left: 0;
	    color: white;
	    background: #FFF;
	}
	.header .pure-menu {
	    height: 44px;
	    border-bottom: 1px solid rgba(0, 0, 0, 0.15);
	    box-shadow: 0 0 4px rgba(0, 0, 0, 0.15);
	}
</style>

<!--[if lt IE 9]>
    <script src="<c:url value="/js/html5shiv.js" />" /></script>
<![endif]-->

<jsp:invoke fragment="pageStyles" />

</head>
<body>







<div id="wrapper">
    <div class="header">
        <div class="pure-menu pure-menu-horizontal">
            <a class="pure-menu-heading" href="">Photo Bug</a>

            <ul class="pure-menu-list">
                <li class="pure-menu-item pure-menu-disabled"><a href="#" class="pure-menu-link">首页</a></li>
                <li class="pure-menu-item pure-menu-disabled"><a href="#" class="pure-menu-link">摄影比赛</a></li>
                <li class="pure-menu-item"><a href="<c:url value="/photo/gallery" />" class="pure-menu-link">所有照片</a></li>
                <li class="pure-menu-item pure-menu-disabled"><a href="#" class="pure-menu-link">参与投票</a></li>
                <li class="pure-menu-item pure-menu-selected"><a href="<c:url value="/photo/upload" />" class="pure-menu-link">上传照片</a></li>
            </ul>
        </div>
    </div>

    
    <jsp:doBody></jsp:doBody>

    <div class="footer">
        
    </div>
</div>
<script>
	var contextPath = "<c:url value="/" />";
</script>
<script src="<c:url value="/js/jquery.min.js" />" /></script>
<jsp:invoke fragment="pageScript" />
</body>
</html>