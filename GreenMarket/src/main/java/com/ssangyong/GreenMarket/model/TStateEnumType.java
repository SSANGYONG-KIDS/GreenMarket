package com.ssangyong.GreenMarket.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TStateEnumType {
	WAIT("STATE_WAIT", "대기중"),
    ING("STATE_ING", "대여중"),
    END("STATE_END", "거래완료"),
	CANCEL("STATE_CANCEL", "거래취소");

	private final String key;
	private final String title;
}