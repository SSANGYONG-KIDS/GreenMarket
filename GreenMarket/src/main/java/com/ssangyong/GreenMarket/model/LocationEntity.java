package com.ssangyong.GreenMarket.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
@ToString(exclude="members")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(of = "locId")
@Table(name = "location")
public class LocationEntity {
	@Id
	private String locId;
	private String locName;
	
	@OneToMany(mappedBy = "loc", //fk이름 "메여있다"
			cascade = CascadeType.ALL,
			fetch = FetchType.LAZY) //fetch = FetchType.EAGER
	List<MemberEntity> members;
}
