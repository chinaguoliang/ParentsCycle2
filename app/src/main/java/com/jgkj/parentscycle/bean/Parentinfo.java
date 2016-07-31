package com.jgkj.parentscycle.bean;

import java.util.Date;

//import com.rmbbox.base.BaseObject;


public class Parentinfo  {



	public String getHeadportrait() {
		return headportrait;
	}

	public void setHeadportrait(String headportrait) {
		this.headportrait = headportrait;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTmpinfoid() {
		return tmpinfoid;
	}

	public void setTmpinfoid(String tmpinfoid) {
		this.tmpinfoid = tmpinfoid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getFamilyrole() {
		return familyrole;
	}

	public void setFamilyrole(String familyrole) {
		this.familyrole = familyrole;
	}

	public String getBabyname() {
		return babyname;
	}

	public void setBabyname(String babyname) {
		this.babyname = babyname;
	}

	public String getBabysex() {
		return babysex;
	}

	public void setBabysex(String babysex) {
		this.babysex = babysex;
	}

	public String getBabyage() {
		return babyage;
	}

	public void setBabyage(String babyage) {
		this.babyage = babyage;
	}

	public String getFmbg() {
		return fmbg;
	}

	public void setFmbg(String fmbg) {
		this.fmbg = fmbg;
	}

	/** id */
	String id;

	/** 登录id */
	String tmpinfoid;

	/** 头像 */
     String headportrait;

    /** 昵称 */
     String nickname;

    /** 账号 */
     String account;

    /** 地区 */
     String region;

    /** 性别 */
     String sex;

    /** 家庭角色 */
     String familyrole;

    /** 宝宝姓名 */
     String babyname;

    /** 宝宝性别 */
     String babysex;

    /** 宝宝年龄 */
     String babyage;

    /** 父母圈背景图 */
     String fmbg;



}