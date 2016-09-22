package com.denis.phoneguard.bean;

public class UpdateBean {
	private String versionName; //�汾����
	private int versionCode;    //�汾��
	private String description; //�汾����
	private String url;			//����·��

	public UpdateBean(String versionName, int versionCode, String description, String url) {
		super();
		this.versionName = versionName;
		this.versionCode = versionCode;
		this.description = description;
		this.url = url;
	}

	public UpdateBean() {
		
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public int getVersionCode() {
		return versionCode;
	}

	@Override
	public String toString() {
		return "UpdateBean [versionName=" + versionName + ", versionCode=" + versionCode + ", description="
				+ description + ", url=" + url + "]";
	}

	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
