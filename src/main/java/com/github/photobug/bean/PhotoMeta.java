package com.github.photobug.bean;

import java.util.Date;

import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.ColType;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Table("t_photometa")
public class PhotoMeta extends BasePojo {

	@Id
	private int id;

	@Column
	private String name;

	@Column("c_description")
	@ColDefine(type = ColType.VARCHAR, width = 500)
	private String description;

	/**
	 * 地址
	 */
	@Column
	private String location;

	/**
	 * 时间
	 */
	@Column("c_time")
	private Date time;

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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
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
}
