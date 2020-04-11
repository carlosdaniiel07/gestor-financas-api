package com.carlos.gestorfinancas.dtos.fcm;

import java.io.Serializable;

public class SendNotificationDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private NotificationDTO notification;
	private DataDTO data;
	private String to;
	
	public SendNotificationDTO() {
		
	}
	
	public SendNotificationDTO(String title, String body, String sound, String id, String to) {
		this.notification = new NotificationDTO(title, body, sound);
		this.data = new DataDTO(id);
		this.to = to;
	}
	
	public SendNotificationDTO(NotificationDTO notification, DataDTO data, String to) {
		super();
		this.notification = notification;
		this.data = data;
		this.to = to;
	}

	public NotificationDTO getNotification() {
		return notification;
	}

	public void setNotification(NotificationDTO notification) {
		this.notification = notification;
	}

	public DataDTO getData() {
		return data;
	}

	public void setData(DataDTO data) {
		this.data = data;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}
}
