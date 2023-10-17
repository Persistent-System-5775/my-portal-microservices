package com.userservice.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "AUTH_USER"
		, uniqueConstraints = { @UniqueConstraint(columnNames = "USERNAME"),
		@UniqueConstraint(columnNames = "EMAIL") }
)
public class Users implements UserDetails,Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="AUTH_USER_ID")
	private Long id;

//	@NotBlank
//	@Size(max = 20)
	@Column(name="USERNAME")
	private String username;

//	@NotBlank
//	@Size(max = 50)
//	@Email
	@Column(name="EMAIL")
	private String email;

//	@NotBlank
//	@Size(max = 120)
	@Column(name="PASSWORD")
	private String password;

	@ManyToMany(cascade = CascadeType.ALL /* fetch = FetchType.LAZY */)
	@JoinTable(name = "AUTH_USER_ROLES", joinColumns = @JoinColumn(name = "AUTH_USER_ID"),
	           inverseJoinColumns = @JoinColumn(name = "AUTH_ROLE_ID"))
	private Set<Role> roles = new HashSet<>();

//	@NotBlank
//	@Size(max = 10)
	@Column(name="GENDER")
	private String gender;

//	@NotBlank
//	@Size(max = 120)
	@Column(name="FIRST_NAME")
	private String firstName;

//	@Size(max = 120)
	@Column(name="MIDDLE_NAME")
	private String middleName;

//	@NotBlank
	@Column(name="LAST_NAME")
//	@Size(max = 120)
	private String lastName;

//	@NotBlank
//	@Size(max = 120)
	@Column(name="CONTACT_NUMBER")
	private String contactNumber;

//	@NotBlank
	@Size(max = 120)
	@Column(name="DATE_OF_BIRTH")
	private String dateOfBirth;
	
//	@Size(max = 120)
	@Column(name="VERIFICATION_CODE")
	private String verificationCode;


	@Column(name="VERIFICATION_CODE_GENERATED_TIME")
	private LocalDateTime verificationCodeGeneratedTime;
	
	@Column(name="IS_EMAIL_VERIFIED")
	private boolean isEmailVerified;

//	@NotBlank
//	@Size(max = 120)
//	@Column(name="CURRENT_LOCATION")
//	private String currentLocation;
//
////	@Column(nullable = false)
////	private MultipartFile file;
//

//	@NotBlank
	@Column(name="CAPTCHA")
	private String captcha;

	@Column(name="STATUS")
	private String status;

	@Column(name="DELETED")
	private boolean deleted;

	@Transient
	@Column
	private Set<Loan> loanSet = new LinkedHashSet<>();
//	private List<Loan> loanSet = new ArrayList<>();































	@Column(name="CREATED_BY")
	private String createdBy;

	@Column(name="CREATED_DATE")
	private LocalDateTime createdDate;

	@Column(name="MODIFIED_BY")
	private String modifiedBy;

	@Column(name="MODIFIED_DATE")
	private LocalDateTime modifiedDate;


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

}
