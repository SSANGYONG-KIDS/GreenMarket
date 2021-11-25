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
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
	private int tId;

	private Timestamp tStartdate;
	private Timestamp tEnddate;	
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = true)
	private TStateEnumType tState;

	@ManyToOne
	@JoinColumn(name = "iId")
	private ItemEntity item; 
	
	@ManyToOne
	@JoinColumn(name = "tBuyid")
	private MemberEntity buyMember; 
	
}
