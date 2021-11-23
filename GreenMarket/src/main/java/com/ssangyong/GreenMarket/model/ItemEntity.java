package com.ssangyong.GreenMarket.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="item")
public class ItemEntity {
	
	@EmbeddedId
	private ItemEntityId ieId;
	
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
	
	@ManyToOne
	@JoinColumn(name = "icId")
	private ItemCategoryEntity itemCategory; //icId 가져오는곳
	
}
