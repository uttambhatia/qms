package com.cs.demo.pojo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name ="datasource")
@XmlAccessorType(XmlAccessType.FIELD)
public class DataSource implements Serializable, Cloneable{

	private String url = "www.google.com";
	private String username;
	private String password;
	private String driverClass;
	
	@Override
	public Object clone() throws CloneNotSupportedException {
	    throw new CloneNotSupportedException("Can't clone a DataSource");
	  }
	
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDriverClass() {
		return driverClass;
	}
	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}
	
	
}
