package com.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class SendMessageDto {
	private int userId;
	private int roomId;
	private String message;

}
