<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>


<t:page pageTitle="所有照片" pageDescription="Photos">
	<jsp:attribute name="pageStyles">
	<style type="text/css">
	.photos-view
	</style>
	</jsp:attribute>
	<jsp:attribute name="pageScript">
	<script src="<c:url value="/js/tmpl.min.js" />"></script>
	<script src="<c:url value="/js/waterfall.min.js" />"></script>
	<script type="text/javascript">
		$(function() {
			$('.waterfall').waterfall({
			    itemCls: 'item',
			    colWidth: 400,  
			    gutterWidth: 15,
			    gutterHeight: 15,
			    checkImagesLoaded: true,
			    path: function(page) {
			        return '<c:url value="/photo/popular" />?pageSize=20&pageNumber=' + page;
			    },
			    callbacks: {
			        /*
			        * 处理ajax返回数方法
			        * @param {String} data
			        */
			       renderData: function (data) {
			    	   if(data.pager.pageNumber === data.pager.pageCount){
			    		   $('.waterfall').waterfall('pause');
			    	   }
			           return tmpl("tmpl-photo", data)
			       }
			    }
			});
		});
	</script>
	</jsp:attribute>
	<jsp:body>
	<div class="waterfall pure-g">
		<script type="text/x-tmpl" id="tmpl-photo">
			{% for (var i=0; i<o.list.length; i++) { %}
			<div class="item">
        		<img src="{%=contextPath + o.list[i].url%}" />
    		</div>
			{% } %}
		</script>
	</div>
	</jsp:body>
</t:page>