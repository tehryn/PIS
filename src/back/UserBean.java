package back;

import java.io.Serializable;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

import core.User;
import datatypes.UserRole;

@Named
@SessionScoped
public class UserBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	// For editing or removing user
	private User editedUser;

	// Used for user creating/registration
	private String newUserFirstName;
	private String newUserLastName;
	private String newUserEmail;
	private String newUserPassword;
	private UserRole newUserRole;	// not for registration -> REGISTRATED
    
    // Logging in
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private UserRole role = UserRole.VISITOR;
    private User loggedUser;
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Public methods
    
    public UserBean() {
	}

	public List<User> getUsers() {
		return User.getUsers();
	}

	public String actionNew() {
		return "new";
	}

	public String actionInsertNew() {
		try {
			new core.User(newUserFirstName, newUserLastName, newUserEmail, newUserPassword).setRole(newUserRole);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
		}
		
		return "insert";
	}

	public String actionUpdate() {
		// TODO
		return "update";
	}

	public String actionEdit() {
		return "edit";
	}
    
    // logout
    public String actionLogout() {
    	loggedUser = null;
    	role = UserRole.VISITOR;
    	return "/index.xhtml?faces-redirect=true";
    }
    
	// login
	public String actionLogin() {
		try {
			loggedUser = new core.User(email, password);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Login successful"));
			this.role = loggedUser.getRole();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
			return "loginFailed";
		}
		
		return "/index.xhtml?faces-redirect=true";
	}
	
	// register
	public String actionRegister() {
		try {
			loggedUser = new core.User(firstName, lastName, email, password);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
		}
		
		this.actionLogin();
		return "/index.xhtml";
	}
	
	public boolean hasRole(UserRole role) {
		if (role == null)
			return true;
		
		int role1 = 0;
		int role2 = 0;
		
		if (this.role == UserRole.REGISTRATED)
			role1 = 1;
		else if (this.role == UserRole.RECEPTIONIST)
			role1 = 2;
		else if (this.role == UserRole.MANAGER)
			role1 = 3;
		else if (this.role == UserRole.ADMIN)
			role1 = 4;
		
		if (role == UserRole.REGISTRATED)
			role2 = 1;
		else if (role == UserRole.RECEPTIONIST)
			role2 = 2;
		else if (role == UserRole.MANAGER)
			role2 = 3;
		else if (role == UserRole.ADMIN)
			role2 = 4;
		
		return role1 >= role2;
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Private methods

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Getters and Setters
	
	
	
	public UserRole getRole() {
		return role;
	}
	
    public User getLoggedUser() {
		return loggedUser;
	}

	public String getNewUserFirstName() {
		return newUserFirstName;
	}

	public void setNewUserFirstName(String newUserFirstName) {
		this.newUserFirstName = newUserFirstName;
	}

	public String getNewUserLastName() {
		return newUserLastName;
	}

	public void setNewUserLastName(String newUserLastName) {
		this.newUserLastName = newUserLastName;
	}

	public String getNewUserEmail() {
		return newUserEmail;
	}

	public void setNewUserEmail(String newUserEmail) {
		this.newUserEmail = newUserEmail;
	}

	public String getNewUserPassword() {
		return newUserPassword;
	}

	public void setNewUserPassword(String newUserPassword) {
		this.newUserPassword = newUserPassword;
	}

	public User getEditedUser() {
		return editedUser;
	}

	public void setEditedUser(User editedUser) {
		this.editedUser = editedUser;
	}

	public UserRole getNewUserRole() {
		return newUserRole;
	}
	public void setNewUserRole(UserRole newUserRole) {
		this.newUserRole = newUserRole;
	}
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
