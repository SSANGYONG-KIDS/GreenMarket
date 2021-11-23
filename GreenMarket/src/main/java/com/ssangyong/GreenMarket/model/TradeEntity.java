package com.ssangyong.GreenMarket.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
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

	@ManyToOne
	@JoinColumns({
		@JoinColumn(name = "mId", referencedColumnName="mId"),
		@JoinColumn(name = "iId", referencedColumnName="iId")
	})
	private ItemEntity item; 
	
	@ManyToOne
	@JoinColumn(name = "tBuyid")
	private MemberEntity member; 
	
}
