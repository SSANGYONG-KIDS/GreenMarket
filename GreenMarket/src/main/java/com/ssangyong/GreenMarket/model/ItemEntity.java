package com.ssangyong.GreenMarket.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@ToString(exclude={"photos", "trades"})
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="item")
public class ItemEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int iId;

	@ManyToOne
	@JoinColumn(name = "mId")
	@JsonIgnore
	private MemberEntity member;

	private String iTitle;
	private String iContent;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = true)
	private IStateEnumType iState;

	@CreationTimestamp
	private Timestamp iRegdate;	
	
	private int iPrice;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = true)
	private ItStateEnumType iTstate;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = true)
	private ICategoryEnumType iCategory;
	
	@OneToMany(mappedBy = "item", //fk이름 "메여있다"
			cascade = CascadeType.ALL, fetch = FetchType.EAGER) //fetch = FetchType.EAGER
	@JsonIgnore
	List<ItemPhotoEntity> photos;
	
	@OneToMany(mappedBy = "item",
			cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	List<TradeEntity> trades;
}
