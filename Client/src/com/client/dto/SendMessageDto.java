package com.client.dto;

import com.client.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class SendMessageDto {
	private User user;
	private int roomId;
	private String message;

}
