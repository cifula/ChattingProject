package com.client.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;



@AllArgsConstructor
@Data
public class roomdto { //만들어질 방
	private int roomnumber=0;
	private String roomname;
	private List<userDto> userDto = new ArrayList<>();
	
}
