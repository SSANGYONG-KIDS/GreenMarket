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
@EqualsAndHashCode(of = "ctId")
@Table(name = "community_tag")
public class CommunityTagEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int ctId;
	
	@ManyToOne
	@JoinColumn(name = "cId")
	private CommunityEntity community; //FK 참조키
	
	private String ctName;
}
