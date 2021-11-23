package com.ssangyong.GreenMarket.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

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
@Table(name= "message")
public class MessageEntity {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private int msgId;
	
	private String msgContent;
	
	@CreationTimestamp
	private Timestamp msgRegdate;
	private String msgFilename;
	private int msgIsread;
	
	@ManyToOne
	@JoinColumn(name = "mId")
	private MemberEntity member; 
	
	@ManyToOne
	@JoinColumn(name = "tId")
	private TradeEntity trade; 
}
