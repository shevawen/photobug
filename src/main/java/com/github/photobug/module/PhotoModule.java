package com.github.photobug.module;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.dao.Cnd;
import org.nutz.dao.QueryResult;
import org.nutz.dao.Sqls;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.sql.SqlCallback;
import org.nutz.img.Images;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.lang.Files;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.lang.util.NutMap;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.adaptor.JsonAdaptor;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Attr;
import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.filter.CheckSession;
import org.nutz.mvc.upload.FieldMeta;
import org.nutz.mvc.upload.TempFile;
import org.nutz.mvc.upload.UploadAdaptor;

import com.github.photobug.bean.Photo;
import com.github.photobug.bean.User;
import com.github.photobug.storage.LocalPhotoOperator;

@IocBean
@At("/photo")
@Ok("json")
@Fail("http:500")
public class PhotoModule extends BaseModule {

	Log log = Logs.get();

	LocalPhotoOperator operator = new LocalPhotoOperator();

	@At("/file/*")
	@Ok("raw")
	public Object photo(HttpServletRequest req) {
		/**
		 * req.getRequestURL() "http://localhost:8080/testweb/abc/getlist.nut"
		 * req.getRequestURI() "/testweb/abc/getlist.nut" req.getPathInfo()
		 * "/abc/getlist.nut" req.getServletPath() ""
		 */

		String location = req.getServletPath() + Strings.sBlank(req.getPathInfo());
		location = req.getRequestURI().replace(Mvcs.getServletContext().getContextPath(), "").substring(12);

		Photo photo = dao.fetch(Photo.class, Cnd.where("location", "=", location));
		File image = new File(operator.getPath() + File.separator + photo.getLocation());
		if (req.getQueryString() != null) {
			String query = req.getQueryString().toString();
			String[] params = query.split("[/]");
			if (params[0].equals("imageView2")) {
				int width = -1;
				int height = -1;
				int indexOfWidthP = Arrays.asList(params).indexOf("w");
				int indexOfHeightP = Arrays.asList(params).indexOf("h");
				if (indexOfWidthP > -1) {
					width = Integer.parseInt(params[indexOfWidthP + 1]);
				}
				if (indexOfHeightP > -1) {
					height = Integer.parseInt(params[indexOfHeightP + 1]);
				}
				if (params[1].equals("0")) {
					return Images.zoomScale(Images.read(image), width, height);
				} else if (params[1].equals("1")) {
					return Images.clipScale(Images.read(image), width, height);
				}
			}
		}
		return Images.read(image);
	}

	@At
	@Ok("jsp:jsp.photo.view")
	public void view(@Param("id") int id, HttpServletRequest req) {
		Photo photo = dao.fetch(Photo.class, id);

		photo.setViews(photo.getViews() + 1);
		dao.update(photo, "^views$");

		dao.fetchLinks(photo, null);
		req.setAttribute("photo", photo);
		req.setAttribute("photoJSON", Json.toJson(photo));

		User user = photo.getUser();
		List<Photo> userPhotos = dao.query(Photo.class, Cnd.where("userName", "=", user.getName()).and("id", "<>", id));
		req.setAttribute("userPhotos", userPhotos);
	}

	@At
	@Ok("jsp:jsp.photo.upload")
	@Filters(@By(type = CheckSession.class, args = { "me", "/user/login" }))
	public void upload() {
	}

	@At
	@Ok(">>:/photo/gallery")
	public void removeall() {
		List<Photo> photos = dao.query(Photo.class, null);
		for (Photo photo : photos) {
			dao.delete(photo);
		}
	}

	@At
	@Ok(">>:/photo/gallery")
	@Filters(@By(type = CheckSession.class, args = { "me", "/user/login" }))
	public NutMap remove(int id, @Attr("me") User me) {
		Photo photo = dao.fetch(Photo.class, id);
		dao.fetchLinks(photo, "^user$");
		if (photo.getUser().getName().equals(me.getName())) {
			dao.delete(Photo.class, id);
			return ajaxOk("");
		} else {
			return ajaxFail("无权删除他人的照片。");
		}
	}

	@At
	@Ok("jsp:jsp.photo.gallery")
	public void gallery() {
	}

	@At
	public Object popular(@Param("..") Pager pager) {
		QueryResult result = query(Photo.class, Cnd.orderBy().desc("views"), pager);
		for(Photo photo :result.getList(Photo.class)){
			dao.fetchLinks(photo, "^meta$");
		}
		return result;
	}

	@At("new")
	public Object _new(@Param("..") Pager pager) {
		QueryResult result = query(Photo.class, Cnd.orderBy().desc("time"), pager);
		return result;
	}

	@At
	public List<String> photo_group(@Attr("me") User me) {
		Sql sql = Sqls.create("SELECT DISTINCT c_group FROM t_photo WHERE user = @user");
		sql.params().set("user", me.getName());
		sql.setCallback(new SqlCallback() {
			public Object invoke(Connection conn, ResultSet rs, Sql sql) throws SQLException {
				List<String> list = new LinkedList<String>();
				while (rs.next())
					list.add(rs.getString("c_group"));
				return list;
			}
		});
		dao.execute(sql);
		return sql.getList(String.class);
	}

	// @At
	// public Object trending(@Param("..") Pager pager) {
	// QueryResult result = query(Photo.class, Cnd.orderBy().desc("time"),
	// pager);
	// for (Photo photo : result.getList(Photo.class)) {
	// String url = Files.getParent(photo.getUrl()) + "/" + photo.getId() +
	// "_preview400w" + Files.getSuffix(photo.getUrl());
	// photo.setUrl(url);
	// }
	// return result;
	// }

	@At
	@AdaptBy(type = JsonAdaptor.class)
	@Filters(@By(type = CheckSession.class, args = { "me", "/photo/gallery" }))
	public NutMap photo_edit_save_action(Photo[] photos, @Attr("me") User me) {
		for (Photo photo : photos) {
			// 防止两段式提交时，恶意提交其他用户图片id
			Photo photoPersisted = dao.fetch(Photo.class, photo.getId());
			dao.fetchLinks(photoPersisted, "^user$");
			if (photoPersisted.getUser().getName().equals(me.getName())
					&& photoPersisted.getMetaId() == photo.getMeta().getId()) {
				dao.updateIgnoreNull(photo.getMeta());
				//dao.updateIgnoreNull(photo);
			}
		}
		return ajaxOk(true);
	}

	@At
	@AdaptBy(type = UploadAdaptor.class, args = { "${app.root}/WEB-INF/tmp" })
	@Filters(@By(type = CheckSession.class, args = { "me", "/photo/gallery" }))
	public List<Photo> photo_upload_action(@Param("files[]") TempFile tf, @Attr("me") User me, HttpServletResponse response) {
		File f = tf.getFile(); // 这个是保存的临时文件
		FieldMeta meta = tf.getMeta(); // 这个原本的文件信息
		String name = meta.getFileLocalName(); // 这个时原本的文件名称;

		Photo photo = new Photo();
		photo.setUserName(me == null ? "" : me.getName());
		photo.setAuthor(me == null ? "" : me.getRealName());

		photo.setMeta(operator.getPhotoMeta(f));
		photo.getMeta().setName(Files.getMajorName(name));

		String location = operator.save(f, Files.getSuffix(name));

		photo.setLocation(location);

		dao.insertWith(photo, "^meta$");

		// for IE8 @see: http://www.oschina.net/question/223750_123703
		response.setHeader("Content-Type", "text/html");
		return Lang.list(photo);
	}
}
