package com.ssangyong.GreenMarket.model;

import javax.persistence.Embeddable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@EqualsAndHashCode
@Embeddable
@ToString
public class MemberAddress {
		private String AddNum;
		private String memberAddress1;
		private String memberAddress2;
		private String memberAddress3;
}
