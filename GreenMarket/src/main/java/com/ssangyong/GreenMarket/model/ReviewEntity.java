package com.ssangyong.GreenMarket.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

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
@Table(name= "review")
public class ReviewEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int rId;

	private String rContent;
	private double rStar;
	
	@CreationTimestamp
	private Timestamp rRegdate;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "mId")
	private MemberEntity member;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "iId")
	private ItemEntity item; 
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "tId")
	private TradeEntity trade; 
	
}
