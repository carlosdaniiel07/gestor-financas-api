package com.carlos.gestorfinancas.dtos.fcm;

import java.io.Serializable;

public class NotificationDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String title;
	private String body;
	private String sound;
	
	public NotificationDTO() {
		
	}
	
	public NotificationDTO(String title, String body, String sound) {
		super();
		this.title = title;
		this.body = body;
		this.sound = sound;
	}

	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getBody() {
		return body;
	}
	
	public void setBody(String body) {
		this.body = body;
	}
	
	public String getSound() {
		return sound;
	}
	
	public void setSound(String sound) {
		this.sound = sound;
	}
}
