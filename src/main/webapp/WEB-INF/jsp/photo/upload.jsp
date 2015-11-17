<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>


<t:page pageTitle="上传照片" pageDescription="Submit a Photo">
	<jsp:attribute name="pageStyles">
	<style type="text/css">
.upload-view {
	max-width: 980px;
	margin: 0 auto;
	padding: 44px 0 20px;
}

#droptarget {
	background: none repeat scroll 0 0 #eee;
	border: 3px dashed #c2c2c2;
	height: 400px;
	text-align: center;
	margin-bottom: 20px;
}

.form-box {
	font-size: 120%;
	border-bottom: 1px solid #eee;
}

#droptarget p {
	color: #888;
	line-height: 25px;
	margin-top: 20px;
}

p.por {
	color: #969696 !important;
	font-size: 22px;
	font-weight: 400;
}

.fileupload-buttonbar .btn, .fileupload-buttonbar .toggle {
	margin-bottom: 5px;
}

.fileinput-button {
	position: relative;
	overflow: hidden;
	display: inline-block;
}

.fileinput-button input {
	position: absolute;
	top: 0;
	right: 0;
	margin: 0;
	opacity: 0;
	-ms-filter: 'alpha(opacity=0)';
	font-size: 200px;
	direction: ltr;
	cursor: pointer;
}

.progress {
	width: 100% !important;
	border-radius: 0px !important;
}

.progress {
	height: 20px;
	margin-bottom: 20px;
	overflow: hidden;
	background-color: #f5f5f5;
	border-radius: 4px;
	-webkit-box-shadow: inset 0 1px 2px rgba(0, 0, 0, .1);
	box-shadow: inset 0 1px 2px rgba(0, 0, 0, .1);
}

.pace .pace-progress {
	background-color: #29d;
	position: relative;
	z-index: 1;
	top: 0;
	left: 0;
	height: 20px;
	overflow: hidden;
	-webkit-transition: width 1s;
	-moz-transition: width 1s;
	-o-transition: width 1s;
	transition: width 1s;
}

.pace .pace-progress-inner {
	position: absolute;
	top: 0;
	left: 0;
	right: -32px;
	bottom: 0;
	background-image: -webkit-gradient(linear, 0 100%, 100% 0, color-stop(0.25, rgba(255, 255,
		255, 0.2)), color-stop(0.25, transparent),
		color-stop(0.5, transparent), color-stop(0.5, rgba(255, 255, 255, 0.2)),
		color-stop(0.75, rgba(255, 255, 255, 0.2)),
		color-stop(0.75, transparent), to(transparent));
	background-image: -webkit-linear-gradient(45deg, rgba(255, 255, 255, 0.2)
		25%, transparent 25%, transparent 50%, rgba(255, 255, 255, 0.2) 50%,
		rgba(255, 255, 255, 0.2) 75%, transparent 75%, transparent);
	background-image: -moz-linear-gradient(45deg, rgba(255, 255, 255, 0.2)
		25%, transparent 25%, transparent 50%, rgba(255, 255, 255, 0.2) 50%,
		rgba(255, 255, 255, 0.2) 75%, transparent 75%, transparent);
	background-image: -o-linear-gradient(45deg, rgba(255, 255, 255, 0.2) 25%,
		transparent 25%, transparent 50%, rgba(255, 255, 255, 0.2) 50%,
		rgba(255, 255, 255, 0.2) 75%, transparent 75%, transparent);
	background-image: linear-gradient(45deg, rgba(255, 255, 255, 0.2) 25%,
		transparent 25%, transparent 50%, rgba(255, 255, 255, 0.2) 50%,
		rgba(255, 255, 255, 0.2) 75%, transparent 75%, transparent);
	-webkit-background-size: 32px 32px;
	-moz-background-size: 32px 32px;
	-o-background-size: 32px 32px;
	background-size: 32px 32px;
	-webkit-animation: pace-stripe-animation 500ms linear infinite;
	-moz-animation: pace-stripe-animation 500ms linear infinite;
	-ms-animation: pace-stripe-animation 500ms linear infinite;
	-o-animation: pace-stripe-animation 500ms linear infinite;
	animation: pace-stripe-animation 500ms linear infinite;
}

items.photos item {
	margin-bottom: 20px;
	padding-bottom: 10px;
	border-color: #3A7AD2;
}

items item {
	overflow: hidden;
	padding: 0 0 5px;
	margin: 0 0 7px;
}

.upload-settings-wrapper {
	padding: 20px 10px;
	margin-bottom: 40px;
	background: #fff;
	border-left: 20px solid #548ac1;
}

.demo-image-container {
	width: 100%;
	overflow: hidden;
	height: auto;
	background: #dee4e9;
	text-align: center;
}
.photo-view {
	display: none;
}

</style>
	<link rel="stylesheet" href="<c:url value="/css/datepicker-base.css" />">
	<link rel="stylesheet" href="<c:url value="/css/datepicker-clean.css" />">
	</jsp:attribute>
	<jsp:attribute name="pageScript">
	<script src="<c:url value="/js/jquery.ui.widget.js" />"></script>
	<script src="<c:url value="/js/jquery.iframe-transport.js" />"></script>
	<script src="<c:url value="/js/jquery.fileupload.js" />"></script>
	<script src="<c:url value="/js/json2.js" />"></script>
	<script src="<c:url value="/js/jquery-serializeForm.min.js" />"></script>
	<script src="<c:url value="/js/mustache.js" />"></script>
	<script src="<c:url value="/js/datepicker.js" />"></script>
	
	<script type="text/javascript">
		$(function() {
			var ver = getInternetExplorerVersion();

		    if ( ver > -1 )
		    {
		        if ( ver < 10.0 ) {
		        	$('#droptarget h2,#droptarget .por').css('visibility','hidden');
		        }
		    }
			$(document).bind('drop dragover', function (e) {
			    e.preventDefault();
			});
			$('#fileupload').fileupload(
					{
						url : '<c:url value="/photo/photo_upload_action" />',
						dataType : 'json',
						dropZone : $('#droptarget'),
						done : function(e, data) {
							$('.photo-view').show('slow');
							var template = $('#tmpl-photo').html();
							$.each(data.result, function(index, photo) {
								var rendered = Mustache.render(template, photo);
								$('.photo-edit-list').append(rendered);
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
							});
						},
						progressall : function(e, data) {
							var progress = parseInt(data.loaded / data.total
									* 100, 10);
							$('.progress .bar').css('width', progress + '%');
						},
						start : function(e, data) {
							$('.progress .bar').css('width', 0);
						}
					}).prop('disabled', !$.support.fileInput).parent()
					.addClass($.support.fileInput ? undefined : 'disabled');

			$("#photo-edit-save").click(function() {
				var photos = [];
				$('.photo-info').each(function(index, photoItem) {
					photos.push( $(photoItem).serializeForm() );
				});
				
				$.ajax({
					url : '<c:url value="/photo/photo_edit_save_action" />',
					dataType : 'json',
					type: "POST",
				    data: JSON.stringify(photos),
				    processData: false
				}).done(function(data) {
					if(data.ok === true){
						window.location.href='<c:url value="/photo/gallery" />';
					}
				});
			});
		});
		/**
		 * Returns the version of Internet Explorer or a -1
		 * (indicating the use of another browser).
		 */
		function getInternetExplorerVersion()
		{
		    var rv = -1; // Return value assumes failure.

		    if (navigator.appName == 'Microsoft Internet Explorer')
		    {
		        var ua = navigator.userAgent;
		        var re  = new RegExp("MSIE ([0-9]{1,}[\.0-9]{0,})");
		        if (re.exec(ua) != null)
		            rv = parseFloat( RegExp.$1 );
		    }

		    return rv;
		}

	</script>
	</jsp:attribute>
	<jsp:body>
	<div class="upload-view pure-g">
		<div class="pure-u-1 form-box">
			<form id="uploadform">
		
		        <div id="droptarget">
		            <h2 style="margin-top:80px">拖动图片到此框中</h2>
		            <p class="por">或者</p>
		            <span
							class="pure-button pure-button-primary fileinput-button">
	                    <i class="glyphicon glyphicon-plus"></i>
	                    <span>从设备中选择图片</span>
	                    <input id="fileupload" type="file" name="files[]"
							multiple>
	                </span>
		            <p>
		                建议: 最多一次上传 10 张照片 且 图片大于 1200px X 600px.
		            </p>
		        </div>
		        
		    </form>
		        <div aria-valuemax="0" aria-valuemin="0" role="progressbar"
					class="progress progress-success progress-striped active pace"
					aria-valuenow="0">
			        <div style="width: 0;" class="bar pace-progress">
						<div class="pace-progress-inner"></div>
					</div>
			    </div>
			    </div>
			    <div class="pure-u-1 photo-view">
			    <script type="x-tmpl-mustache" id="tmpl-photo">
	<div class="item photo-info" media_id="{{id}}">
		<div class="pure-g demo-image-container">
			<div class="pure-u-1" style="text-align:center">
				<img src="{{{url}}}?imageView2/0/h/400" alt="{{name}}">
			</div>
		</div>
		<div class="upload-settings-wrapper">

        <div class="upload-settings pure-form pure-form-stacked">
			<input id="id" name="id" type="hidden" value="{{id}}">
			<input id="metaId" name="metaId" type="hidden" value="{{metaId}}">
        	<legend>详情</legend>
			<div class="pure-g">
            <div class="pure-u-1 pure-u-md-1-3">
                <label for="meta[name]">名称</label>
                <input id="meta[name]" name="meta[name]" class="pure-u-23-24" type="text" value="{{meta.name}}">
            </div>

            <div class="pure-u-1 pure-u-md-1-3">
                <label for="author">作者</label>
                <input id="author" name="author" class="pure-u-23-24" type="text">
            </div>

            <div class="pure-u-1 pure-u-md-1-3">
                <label for="contestId">参赛</label>
                <select id="contestId" name="contestId" class="pure-u-23-24">
                    <option value="0">不参赛</option>
                    <option value="1" selected>[我爱我家--多彩气象]数码相机专区</option>
					<option value="2">[我爱我家--多彩气象]手机专区</option>
                </select>
            </div>

            <div class="pure-u-1 pure-u-md-1-3">
                <label for="group">如果图片是组图中的一副，输入组图名称</label>
                <input id="group" name="group" class="pure-u-23-24" placeholder="" type="text">
            </div>

			<input id="meta[id]" name="meta[id]" type="hidden" value="{{meta.id}}">

			<div class="pure-u-1 pure-u-md-1-3">
                <label for="meta[location]">拍摄地点</label>
                <input id="meta[location]" name="meta[location]" class="pure-u-23-24" type="text" value="{{meta.location}}">
            </div>

			<div class="pure-u-1 pure-u-md-1-3">
                <label for="meta[time]">拍摄时间</label>
                <input id="meta[time]" name="meta[time]" class="input-date pure-u-23-24" type="text" value="{{meta.time}}">
            </div>

            <div class="pure-u-1">
                <label for="meta[description]">描述</label>
				<textarea class="pure-u-7-8" id="meta[description]" name="meta[description]" placeholder=""></textarea>
            </div>
			</div>
			<legend>参数</legend>
			<div class="pure-g">
			<div class="pure-u-1 pure-u-md-1-3">
                <label for="meta[camera]">相机</label>
                <input id="meta[camera]" name="meta[camera]" class="pure-u-23-24" type="text" value="{{meta.camera}}">
            </div>

			<div class="pure-u-1 pure-u-md-1-3">
                <label for="meta[aperture]">光圈</label>
                <input id="meta[aperture]" name="meta[aperture]" class="pure-u-23-24" type="text" value="{{meta.aperture}}">
            </div>

			<div class="pure-u-1 pure-u-md-1-3">
                <label for="meta[iso]">ISO</label>
                <input id="meta[iso]" name="meta[iso]" class="pure-u-23-24" type="text" value="{{meta.iso}}">
            </div>

			<div class="pure-u-1 pure-u-md-1-3">
                <label for="meta[exposure]">曝光</label>
                <input id="meta[exposure]" name="meta[exposure]" class="pure-u-23-24" type="text" value="{{meta.exposure}}">
            </div>
			</div>
            
        </div>
		</div>
</div>
				</script>
			    <div class="items photo-edit-list">
					
				</div>
				<p style="text-align:center;">
		                照片已上传，请保存以上配置.
		        </p>
		        <div style="text-align:center;">
					<button type="submit" id="photo-edit-save"
					class="pure-button pure-input-1-2 pure-button-primary">保存</button>
				</div>
		</div>
	</div>
	</jsp:body>
</t:page>