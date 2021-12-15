package com.ssangyong.GreenMarket.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
@SequenceGenerator(
	name = "MESSAGE_SEQ_GENERATOR",
	sequenceName = "MESSAGE_SEQ",
	initialValue = 1,
	allocationSize = 1)
public class MessageEntity {
	@Id
	@GeneratedValue(strategy= GenerationType.SEQUENCE,
					generator = "MESSAGE_SEQ_GENERATOR")
	private int msgId;
	
	private String msgContent;
	
	@CreationTimestamp
	private Timestamp msgRegdate;
	private String msgFilename;
	private int msgIsread;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = true)
	private MsgEnumType msgType;
	
	@ManyToOne
	@JoinColumn(name = "mId")
	@JsonManagedReference
	private MemberEntity member; 
	
	@ManyToOne
	@JoinColumn(name = "tId")
	@JsonBackReference
	private TradeEntity trade; 
}
