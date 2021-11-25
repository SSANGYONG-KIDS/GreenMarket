package com.ssangyong.GreenMarket.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Embeddable
public class ItemCartEntityId implements Serializable{
	@ManyToOne
	@JoinColumn(name = "mId")
	private MemberEntity member;
	
	@ManyToOne
	@JoinColumn(name = "iId")
	private ItemEntity item;
}
