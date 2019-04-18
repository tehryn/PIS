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
	User editedUser;

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
			new core.User(firstName, lastName, email, password).setRole(newUserRole);
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

	public String actionDelete() {
		editedUser.remove();
		return "delete";
    }

	// Used for user creating/registration
    String firstName;
	String lastName;
    String email;
    String password;
    UserRole newUserRole;	// not for registration -> REGISTRATED
    
    // Current logged in user
    UserRole role = UserRole.VISITOR;
    private User loggedUser;
        
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Public functions
    
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
		return "register";
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Private functions

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Getters and Setters
	
	
	public UserRole getRole() {
		return role;
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
