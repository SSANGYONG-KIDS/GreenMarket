package com.ssangyong.GreenMarket.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name= "trade")
public class TradeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int tId; // PK

	private Timestamp tStartdate; // 대여 시작일
	private Timestamp tEnddate;	// 대여 종료일
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = true)
	private TStateEnumType tState; // 거래 상태

	@ManyToOne
	@JoinColumn(name = "iId")
	private ItemEntity item; // 대여 물품
	
	@ManyToOne
	@JoinColumn(name = "tBuyid")
	private MemberEntity buyMember;	// 구매자 
	
}
