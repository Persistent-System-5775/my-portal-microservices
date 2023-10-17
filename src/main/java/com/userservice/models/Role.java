package com.userservice.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//@Data
@Entity
@Table(name = "AUTH_ROLES")
//@AllArgsConstructor
//@NoArgsConstructor
@Getter
@Setter
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ROLE_ID")
	private Integer id;

	@Enumerated(EnumType.STRING)
	@Column(name = "ROLE_NAME", length = 20)
	private ERole roleName;

	@Column(name = "ROLE_DESCRIPTION")
	private ERole roleDesc;

	public Role() {

	}

	public Role(ERole roleName) {
		this.roleName = roleName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ERole getRoleName() {
		return roleName;
	}

	public void setRoleName(ERole roleName) {
		this.roleName = roleName;
	}

	public ERole getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(ERole roleDesc) {
		this.roleDesc = roleDesc;
	}

	@Override
	public String toString() {
		return "Role{" +
				"id=" + id +
				", roleName=" + roleName +
				", roleDesc=" + roleDesc +
				'}';
	}
}