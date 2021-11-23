package com.ssangyong.GreenMarket.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = "community")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "crId")
@Table(name = "community_reply")
public class CommunityReplyEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int crId;
	
	@ManyToOne
	@JoinColumn(name = "mId") //지정 안하면 member_m_id로 칼럼명이 생성된다.
	private MemberEntity member; // FK 참조키 
	
	@ManyToOne
	@JoinColumn(name = "cId")
	private CommunityEntity community; //FK 참조키
	
	private String crContent;
	private int crTopno;
	private int crOrder;
	private int crDepth;
}
