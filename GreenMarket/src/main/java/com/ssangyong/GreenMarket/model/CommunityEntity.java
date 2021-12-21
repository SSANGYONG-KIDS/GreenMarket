package com.ssangyong.GreenMarket.model;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude= "creplies")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name= "community")
public class CommunityEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int cId;
	private String cTitle;
	@Lob  //어노테이션 추가
	private String cContent;
	private int cViews;
	
	@CreationTimestamp
	private Timestamp cRegdate;
	
	@UpdateTimestamp
	private Timestamp cUpdate;	

	@ManyToOne
	@JoinColumn(name = "mId")
	private MemberEntity member; //mId 가져오는곳
	
	@JsonIgnore
	@OneToMany(mappedBy = "community", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@OrderBy("crId desc")
	private List<CommunityReplyEntity> creplies; 

	@JsonIgnore
	@OneToMany(mappedBy = "community", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<CommunityTagEntity> tags; 
}
