package com.github.photobug.bean;

import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.ColType;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;

@Table("t_photo")
public class Photo extends BasePojo {
	@Id
	private int id;
	@Column
	private String name;
	@Column("c_description")
	@ColDefine(type = ColType.VARCHAR, width = 500)
	private String description;
	@Column("c_group")
	private String group;
	@Column
	private String userName;
	@Column
	private String contestId;
	@Column
	private String url;
	@Column
	private String autor;
	@One(target = User.class, field = "userName")
	User user;
	@One(target = Contest.class, field = "contestId")
	User contest;

	/**
	 * 地址
	 */
	@Column
	private String location;

	/**
	 * 时间
	 */
	@Column("c_time")
	private String time;

	/**
	 * 相机
	 */
	@Column
	private String camera;
	/**
	 * 光圈
	 */
	@Column
	private String aperture;
	/**
	 * ISO
	 */
	@Column
	private String iso;
	/**
	 * 曝光
	 */
	@Column
	private String exposure;

	@Column
	private long views;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getContestId() {
		return contestId;
	}

	public void setContestId(String contestId) {
		this.contestId = contestId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public User getContest() {
		return contest;
	}

	public void setContest(User contest) {
		this.contest = contest;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getCamera() {
		return camera;
	}

	public void setCamera(String camera) {
		this.camera = camera;
	}

	public String getAperture() {
		return aperture;
	}

	public void setAperture(String aperture) {
		this.aperture = aperture;
	}

	public String getIso() {
		return iso;
	}

	public void setIso(String iso) {
		this.iso = iso;
	}

	public String getExposure() {
		return exposure;
	}

	public void setExposure(String exposure) {
		this.exposure = exposure;
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

}
