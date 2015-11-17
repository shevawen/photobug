<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>


<t:page pageTitle="查看照片" pageDescription="View Photo">
	<jsp:attribute name="pageStyles">
	<style type="text/css">
	.photo {
		text-align: center;
	}
	
	div.btnArrows {
		top: 300px;
		opacity: 0.4;
		position: absolute;
		font-size: 0;
		width: 50px;
		height: 50px;
		background-repeat: no-repeat;
		-webkit-transition: 0.2s ease-in-out;
		-moz-transition: 0.2s ease-in-out;
		-o-transition: 0.2s ease-in-out;
		transition: 0.2s ease-in-out;
		background-color: inherit;
		border-radius: 50%;
	}
	
	.btnNext {
		top: 50%;
		right: 0;
		background-image: url("<c:url value="/image/arrow_next.png " />");
		position: relative;
		background-position: 18px center;
	}
	
	.btnPrev {
		background-image: url("<c:url value="/image/arrow_previous.png " />");
		background-position: 15px center;
	}
	</style>
	<link rel="stylesheet" href="<c:url value="/css/datepicker-base.css" />">
	<link rel="stylesheet" href="<c:url value="/css/datepicker-clean.css" />">
	</jsp:attribute>
	<jsp:attribute name="pageScript">
	<script src="<c:url value="/js/json2.js" />"></script>
	<script src="<c:url value="/js/jquery-serializeForm.min.js" />"></script>
	<script src="<c:url value="/js/mustache.js" />"></script>
	<script src="<c:url value="/js/datepicker.js" />"></script>
	<script type="text/javascript">
		$(function() {
			var photo = ${photoJSON};
			$("#photo-edit-button").click(function(){
				$("#photo-edit-wrap").show();
				var template = $('#tmpl-photo').html();
				var rendered = Mustache.render(template, photo);
				$('#photo-edit').html(rendered);
			});
			
			
			$('input, textarea').placeholder();
			$('.input-date').DatePicker({
				mode: 'single',
				position: 'right',
			 	onBeforeShow: function(el){
			 		if($('.input-date').val())
						$('.input-date').DatePickerSetDate($('.input-date').val(), true);
			 		},
			 	onChange: function(date, el) {
			 		$(el).val(
			 			date.getFullYear() + '-' + (date.getMonth()+1)+ '-' + date.getDate()
			 		);
			  	}
			});
			$("#photo-edit-save").click(function() {
				var photos = [];
				photos.push( $('#photo-edit').serializeForm() );
				
				$.ajax({
					url : '<c:url value="/photo/photo_edit_save_action" />',
					dataType : 'json',
					type: "POST",
				    data: JSON.stringify(photos),
				    processData: false
				}).done(function(data) {
					if(data.ok === true){
						window.location.href='<c:url value="/photo/view?id=${photo.id}" />';
					}
				});
			});
		});
	</script>
	</jsp:attribute>
	<jsp:body>
	<div class="pure-g"
			style="background-color: #F3F3F3; padding: .5em 1em;">
		<div class="pure-u-1-6" style="padding-left: 12px;">
				<p>${photo.meta.name }</p>
		</div>
		<div class="pure-u-1-3"
				style="position: absolute; right: 0; text-align: right; padding-right: 12px;">
				<p>${photo.user.realName }</p>
		</div>
	</div>
	<div class="pure-g"
			style="background-color: #F6F7F8; padding: .5em 1em;">
<%-- 		<a href="<c:url value="/photo?id=${prevId } " />" class="pure-u-1-24 btnArrows"> --%>
<!-- 			<div class="btnArrows btnPrev"></div> -->
<!-- 		</a> -->
    	<div class="pure-u-1">
    		<div class="photo">
				<img src="${photo.url}"
						style="max-height: 1300px;" id="main_image"
						alt="${photo.meta.name}">
			</div>								
		</div>
<%--     	<a href="<c:url value="/photo?id=${nextId } " />" class="pure-u-1-24 btnArrows"> --%>
<!-- 			<div class="btnArrows btnNext"></div> -->
<!-- 		</a> -->
	</div>
	<script type="x-tmpl-mustache" id="tmpl-photo">
	<div class="item photo-info" media_id="{{id}}">
		<div class="upload-settings-wrapper">

	        <div class="upload-settings pure-form pure-form-stacked">
				<input id="id" name="id" type="hidden" value="{{id}}">
				<input id="metaId" name="metaId" type="hidden" value="{{metaId}}">
	        	<legend>详情</legend>
				<div class="pure-g">
		            <div class="pure-u-1 pure-u-md-1-2">
		                <label for="meta[name]">名称</label>
		                <input id="meta[name]" name="meta[name]"
										class="pure-u-23-24" type="text" value="{{meta.name}}">
		            </div>
		
		            <div class="pure-u-1 pure-u-md-1-2">
		                <label for="author">作者</label>
		                <input id="author" name="author" class="pure-u-23-24"
										type="text">
		            </div>
		
		            <div class="pure-u-1 pure-u-md-1-2">
		                <label for="contestId">参赛</label>
		                <select id="contestId" name="contestId"
										class="pure-u-23-24">
		                    <option value="0">不参赛</option>
		                    <option value="1" selected>[我爱我家--多彩气象]数码相机专区</option>
							<option value="2">[我爱我家--多彩气象]手机专区</option>
		                </select>
		            </div>
		
		            <div class="pure-u-1 pure-u-md-1-2">
		                <label for="group">如果图片是组图中的一副，输入组图名称</label>
		                <input id="group" name="group" class="pure-u-23-24"
										placeholder="" type="text" value="{{group}}">
		            </div>
		
					<input id="meta[id]" name="meta[id]" type="hidden"
									value="{{meta.id}}">
		
					<div class="pure-u-1 pure-u-md-1-2">
		                <label for="meta[location]">拍摄地点</label>
		                <input id="meta[location]" name="meta[location]"
										class="pure-u-23-24" type="text" value="{{meta.location}}">
		            </div>
		
					<div class="pure-u-1 pure-u-md-1-2">
		                <label for="meta[time]">拍摄时间</label>
		                <input id="meta[time]" name="meta[time]"
										class="input-date pure-u-23-24" type="text"
										value="{{meta.time}}">
		            </div>
		
		            <div class="pure-u-1">
		                <label for="meta[description]">描述</label>
						<textarea class="pure-u-7-8" id="meta[description]"
										name="meta[description]" placeholder=""></textarea>
		            </div>
				</div>
				<legend>参数</legend>
				<div class="pure-g">
					<div class="pure-u-1 pure-u-md-1-2">
		                <label for="meta[camera]">相机</label>
		                <input id="meta[camera]" name="meta[camera]"
										class="pure-u-23-24" type="text" value="{{meta.camera}}">
		            </div>
		
					<div class="pure-u-1 pure-u-md-1-2">
		                <label for="meta[aperture]">光圈</label>
		                <input id="meta[aperture]" name="meta[aperture]"
										class="pure-u-23-24" type="text" value="{{meta.aperture}}">
		            </div>
		
					<div class="pure-u-1 pure-u-md-1-2">
		                <label for="meta[iso]">ISO</label>
		                <input id="meta[iso]" name="meta[iso]"
										class="pure-u-23-24" type="text" value="{{meta.iso}}">
		            </div>
		
					<div class="pure-u-1 pure-u-md-1-2">
		                <label for="meta[exposure]">曝光</label>
		                <input id="meta[exposure]" name="meta[exposure]"
										class="pure-u-23-24" type="text" value="{{meta.exposure}}">
		            </div>
				</div>
	            
	        </div>
		</div>
	</div>
	</script>
	<div class="pure-g" style="padding: .5em 1em;">
		<c:if test="${not empty me and me.name == photo.user.name}">
		<div class="pure-u-2-3">
			<a class="pure-button pure-button-primary"
						href="<c:url value="/photo/remove?id=${photo.id }" />">删除</a>
			<button class="pure-button pure-button-primary" id="photo-edit-button">编辑</button>
			<div id="photo-edit-wrap" style="display:none;">
				<div id="photo-edit">
				
				</div>
				<div style="text-align:center;">
						<button type="submit" id="photo-edit-save"
						class="pure-button pure-input-1-2 pure-button-primary">保存</button>
				</div>
			</div>
		</div>
		
		</c:if>
		<div class="pure-u-1-3">
			<c:if test="${not empty userPhotos }">
			<h3>${ photo.user.realName }的其他照片</h3>
			<c:forEach items="${ userPhotos}" var="userPhoto">
			<div class="pure-u-1-4 pure-u-lg-1-8">
				<a href="<c:url value="/photo/view?id=${userPhoto.id } " />">
					<img class="pure-img"
								src="<c:url value="/photo/file/${userPhoto.location}" />?imageView2/1/w/75/h/75"
								alt="${userPhoto.meta.name}">
				</a>
		    </div>
		    </c:forEach>
		    </c:if>
		    <h3>浏览量</h3>
		    <p>${ photo.views }次</p>
		    <c:if test="${not empty photo.meta.time}">
		    <h3>拍摄时间</h3>
		    <p>
						<fmt:formatDate value="${photo.meta.time}" pattern="yyyy-MM-dd" />
					</p>
		    </c:if>
		    <c:if test="${not empty photo.meta.location}">
		    <h3>拍摄地点</h3>
		    <p>${ photo.meta.location }</p>
		    </c:if>
		    <c:if test="${not empty photo.meta.description}">
		    <h3>描述</h3>
		    <p>${ photo.meta.description }</p>
		    </c:if>
		    <c:if test="${not empty photo.meta.camera}">
		    <h3>相机</h3>
		    <p>${ photo.meta.camera }</p>
		    </c:if>
		    <c:if test="${not empty photo.meta.aperture}">
		    <h3>光圈</h3>
		    <p>${ photo.meta.aperture }</p>
		    </c:if>
		    <c:if test="${not empty photo.meta.iso}">
		    <h3>ISO</h3>
		    <p>${ photo.meta.iso }</p>
		    </c:if>
		    <c:if test="${not empty photo.meta.exposure}">
		    <h3>曝光</h3>
		    <p>${ photo.meta.exposure }</p>
		    </c:if>
		</div>
	</div>
	</jsp:body>
</t:page>