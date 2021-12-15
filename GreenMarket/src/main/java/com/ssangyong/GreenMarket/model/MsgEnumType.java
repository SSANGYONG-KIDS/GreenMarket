package com.ssangyong.GreenMarket.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MsgEnumType {
	TEXT("MSG_TEXT", "텍스트"),
    IMAGE("MSG_IMAGE", "이미지");

	private final String key;
	private final String title;
}