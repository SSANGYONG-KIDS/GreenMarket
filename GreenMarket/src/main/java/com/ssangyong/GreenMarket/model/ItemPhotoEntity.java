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
@ToString
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "ipId")
@Table(name = "item_photo")
public class ItemPhotoEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int ipId;
	
	@ManyToOne
	@JoinColumn(name = "iId")
	private ItemEntity item; 
	
	private String ipFilename;
	private String ipOfilename;
	
	public ItemPhotoEntity(int ipId) {
		super();
		this.ipId = ipId;
	}
}
