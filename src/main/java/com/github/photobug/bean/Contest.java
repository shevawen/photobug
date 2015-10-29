package com.github.photobug.bean;

import java.util.Date;

import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.ColType;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

@Table("t_contest")
public class Contest extends BasePojo {
	@Id
	private int id;
	@Name
	@Column
	private String name;
	@Column
	private String subtitle;
	@Column("c_description")
	@ColDefine(type = ColType.VARCHAR, width = 500)
	private String description;
	/**
	 * 上传截止时间
	 */
	@Column
	private Date submitDeadline;
	/**
	 * 投票截止时间
	 */
	@Column
	private Date voteDeadline;
	@Column
	private String cover;

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

	public Date getSubmitDeadline() {
		return submitDeadline;
	}

	public void setSubmitDeadline(Date submitDeadline) {
		this.submitDeadline = submitDeadline;
	}

	public Date getVoteDeadline() {
		return voteDeadline;
	}

	public void setVoteDeadline(Date voteDeadline) {
		this.voteDeadline = voteDeadline;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

}
