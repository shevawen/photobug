<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>


<t:page pageTitle="所有照片" pageDescription="Photos">
	<jsp:attribute name="pageStyles">
	<style type="text/css">
	.waterfall {
		margin-top:20px;
	}
	</style>
	</jsp:attribute>
	<jsp:attribute name="pageScript">
	<script src="<c:url value="/js/mustache.js" />"></script>
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
			       renderData: function (data) {
			    	   if(data.pager.pageNumber >= data.pager.pageCount){
			    		   $('.waterfall').waterfall('pause');
			    	   }
			    	   var template = $('#tmpl-photo').html();
					   return Mustache.render(template, data);
			       }
			    }
			});
		});
	</script>
	</jsp:attribute>
	<jsp:body>
	<div class="waterfall pure-g">
		<script type="x-tmpl-mustache" id="tmpl-photo">
			{{#list}}
			<div class="item" id="p{{id}}">
        		<a href="<c:url value="/photo/view" />?id={{id}}">
					<img src="{{url}}?imageView2/0/w/400" />
				</a>
    		</div>
			{{/list}}
		</script>
	</div>
	</jsp:body>
</t:page>