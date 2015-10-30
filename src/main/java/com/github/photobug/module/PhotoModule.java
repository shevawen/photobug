package com.github.photobug.module;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.dao.QueryResult;
import org.nutz.dao.pager.Pager;
import org.nutz.img.Images;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Files;
import org.nutz.lang.Lang;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.adaptor.JsonAdaptor;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.upload.FieldMeta;
import org.nutz.mvc.upload.TempFile;
import org.nutz.mvc.upload.UploadAdaptor;

import com.github.photobug.bean.Photo;

@IocBean
@At("/photo")
@Ok("json")
@Fail("http:500")
// @Filters(@By(type=CheckSession.class, args={"me", "/"}))
public class PhotoModule extends BaseModule {

	static String MEDIA_PATH = "/uploadfiles";

	@At
	@Ok("jsp:jsp.photo.view")
	public void view() {
	}
	@At
	@Ok("jsp:jsp.photo.upload")
	public void upload() {
	}

	@At
	@Ok("jsp:jsp.photo.gallery")
	public void gallery() {
	}

	@At
	public Object popular(@Param("..") Pager pager) {
		QueryResult result = query(Photo.class, Cnd.orderBy().desc("views"), pager);
		for (Photo photo : result.getList(Photo.class)) {
			String url = Files.getParent(photo.getUrl()) + "/" + photo.getId() + "_preview400w" + Files.getSuffix(photo.getUrl());
			photo.setUrl(url);
		}
		return result;
	}

	@At
	@AdaptBy(type = JsonAdaptor.class)
	public NutMap photo_edit_save_action(Photo[] photos) {
		for (Photo photo : photos) {
			dao.update(photo);
		}
		return ajaxOk(true);
	}

	@At
	@AdaptBy(type = UploadAdaptor.class, args = { "${app.root}/WEB-INF/tmp" })
	public List<Photo> photo_upload_action(@Param("files[]") TempFile tf, HttpSession session) {
		ServletContext ctx = Mvcs.getServletContext();
		File f = tf.getFile(); // 这个是保存的临时文件
		FieldMeta meta = tf.getMeta(); // 这个原本的文件信息
		String name = meta.getFileLocalName(); // 这个时原本的文件名称;

		Object me = session.getAttribute("me");

		Photo photo = new Photo();
		photo.setName(Files.getMajorName(name));
		photo.setAutor(me == null ? "" : me.toString());
		photo = dao.insert(photo);

		Calendar now = Calendar.getInstance();
		String datePath = now.get(Calendar.YEAR) + "/" + (now.get(Calendar.MONTH) + 1) + "/" + now.get(Calendar.DAY_OF_MONTH);
		String fileDir = ctx.getRealPath(MEDIA_PATH) + "/" + datePath;
		Files.createDirIfNoExists(fileDir);

		String uniqueName = photo.getId() + Files.getSuffix(name);
		String filePath = fileDir + "/" + uniqueName;

		File newFile = new File(filePath);
		f.renameTo(newFile);

		BufferedImage img = Images.read(newFile);

		BufferedImage thumbnail = Images.zoomScale(img, -1, 400, Color.WHITE);
		String thumbnailName = photo.getId() + "_preview400h" + Files.getSuffix(name);
		Images.write(thumbnail, new File(fileDir + "/" + thumbnailName));

		BufferedImage widepreview = Images.zoomScale(img, 400, -1, Color.WHITE);
		String widepreviewName = photo.getId() + "_preview400w" + Files.getSuffix(name);
		Images.write(widepreview, new File(fileDir + "/" + widepreviewName));

		photo.setUrl(MEDIA_PATH + "/" + datePath + "/" + uniqueName);
		dao.update(photo);

		photo.setUrl(
				photo.getUrl().substring(0, photo.getUrl().lastIndexOf(File.separator)) 
				+ "/" + photo.getId() + "_preview400w" + Files.getSuffix(photo.getUrl()));
//		photo.setWidth(thumbnail.getWidth());
//		photo.setHeight(thumbnail.getHeight());

		return Lang.list(photo);
	}
}
