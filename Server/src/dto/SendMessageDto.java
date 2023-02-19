package dto;


import lombok.Data;

@Data
public class SendMessageDto {
	private String fromUser;
	private String toUser;
	private String message;

}
