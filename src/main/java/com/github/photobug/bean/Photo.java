package com.github.photobug.bean;

import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.ColType;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Default;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;
import org.nutz.mvc.Mvcs;

@Table("t_photo")
public class Photo extends BasePojo {
	@Id
	private int id;
	
	@Column("c_group")
	private String group;
	
	@Column
	private int contestId;

	@Column
	@ColDefine(type = ColType.VARCHAR, width = 500)
	private String location;
	
	@Column
	private String author;

	@Column
	private String userName;

	@One(target = User.class, field = "userName")
	User user;

	@One(target = Contest.class, field = "contestId")
	Contest contest;

	@Column
	private int metaId;

	@One(target = PhotoMeta.class, field = "metaId")
	PhotoMeta meta;

	@Column
	@Default("0")
	private long views;
	

	@SuppressWarnings("unused")
	private String url;

	private int width;

	private int height;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public long getViews() {
		return views;
	}

	public void setViews(long views) {
		this.views = views;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getContestId() {
		return contestId;
	}

	public void setContestId(int contestId) {
		this.contestId = contestId;
	}

	public PhotoMeta getMeta() {
		return meta;
	}

	public void setMeta(PhotoMeta meta) {
		this.meta = meta;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getMetaId() {
		return metaId;
	}

	public void setMetaId(int metaId) {
		this.metaId = metaId;
	}

	public Contest getContest() {
		return contest;
	}

	public void setContest(Contest contest) {
		this.contest = contest;
	}

	public String getUrl() {
		return Mvcs.getServletContext().getContextPath() + "/photo/file/" + this.location;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
