package queries.db;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import datatypes.UserRole;

/**
 * 
 * @author xmatej52
 *
 */
@Entity
@Table(name="user")
public class User extends Object {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id;
	
	@Enumerated(EnumType.ORDINAL)
	private UserRole role;
	
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	
	public User() {
		super();
	}

	public User(String firstName, String lastName, String email, UserRole role) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.role = role;
	}
	
	public User(String firstName, String lastName, String email, UserRole role, String password) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.role = role;
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}

	public String getFirstName() {
		return firstName;
	}
	
	public int getId() {
		return id;
	}
	
	public String getLastName() {
		return lastName;
	}

	public String getPassword() {
		return password;
	}

	public UserRole getRole() {
		return role;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setFirstName( String newValue ) {
		this.firstName = newValue;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", role="
				+ role + ", password=" + password + "]";
	}
	
}
