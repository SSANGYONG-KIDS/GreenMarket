package com.ssangyong.GreenMarket.model;

import java.sql.Timestamp;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

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
@Table(name="itemcart")
public class ItemCartEntity {

	@EmbeddedId
	private ItemEntityId ieId;

}
