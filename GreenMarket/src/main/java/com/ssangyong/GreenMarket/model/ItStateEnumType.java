package com.ssangyong.GreenMarket.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ItStateEnumType {
	POSSIBLE("STATE_POSSIBLE", "대여가능"),
    ING("STATE_ING", "대여중"),
    IMPOSSIBLE("STATE_IMPOSSIBLE", "대여종료");

	private final String key;
	private final String title;
}