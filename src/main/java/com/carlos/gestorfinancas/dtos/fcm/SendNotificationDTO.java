package com.carlos.gestorfinancas.dtos.fcm;

import java.io.Serializable;

public class SendNotificationDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private NotificationDTO notification;
	private String to;
	
	public SendNotificationDTO() {
		
	}
	
	public SendNotificationDTO(String title, String body, String sound, String to) {
		this.notification = new NotificationDTO(title, body, sound);
		this.to = to;
	}
	
	public SendNotificationDTO(NotificationDTO notification, String to) {
		super();
		this.notification = notification;
		this.to = to;
	}

	public NotificationDTO getNotification() {
		return notification;
	}

	public void setNotification(NotificationDTO notification) {
		this.notification = notification;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}
}
