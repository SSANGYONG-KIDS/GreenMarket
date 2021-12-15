package com.ssangyong.GreenMarket.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(of = "mId")
@Table(name = "member")
public class MemberEntity {
	@Id
	private String mId;
	@JsonIgnore
	private String mPw;
	private String mName;
	private String mNickname;
	private String mEmail;
	private String mPhoto;
	private String mPhone;
	private String mInfo;
	
	private int mIsdropped;

	@Enumerated(EnumType.STRING)
	@Column(nullable = true)
	MemberRoleEnumType mRole;	
	
	@Embedded
	MemberAddress mAddress;
	
	// ManyToOne엔 Column 속성 사용 불가
	@ManyToOne
	@JoinColumn(name = "locId")
	@JsonManagedReference
	LocationEntity loc;

	public String getUserRoleEnumTypeKey() {
		return this.mRole.getKey();
	}
}
