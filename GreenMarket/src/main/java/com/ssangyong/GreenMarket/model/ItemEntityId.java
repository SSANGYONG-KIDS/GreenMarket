package com.ssangyong.GreenMarket.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Embeddable
public class ItemEntityId implements Serializable{
	
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int iId;
	
	@ManyToOne
	@JoinColumn(name = "mId")
	private MemberEntity member; //mId 가져오는곳
}
