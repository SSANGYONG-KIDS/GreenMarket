package com.ssangyong.GreenMarket.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IStateEnumType {
	GOOD("STATE_GOOD", "좋음"),
    NORMAL("STATE_NORMAL", "보통"),
    BAD("STATE_BAD", "나쁨");

	private final String key;
	private final String title;
}