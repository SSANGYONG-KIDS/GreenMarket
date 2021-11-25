package com.ssangyong.GreenMarket.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public enum MemberRoleEnumType {
	BUSINESS("ROLE_BUSINESS", "사업자"),
    USER("ROLE_USER", "일반 사용자"),
    ADMIN("ROLE_ADMIN", "관리자");

	private final String key;
	private final String title;
}