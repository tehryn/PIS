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
    public void actionLogout() {
    	loggedUser = null;
    	role = UserRole.VISITOR;
    }
    
	// login
	public String actionLogin() {
		try {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("[DEBUG] email: " + email + " password: " + password));
			
			loggedUser = new core.User(email, password);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Login successful"));
			this.role = loggedUser.getRole();
			
			//FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("[DEBUG] UserRole.MANAGER: " + UserRole.MANAGER));
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("[DEBUG] loggedUser.getRole(): " + loggedUser.getRole()));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
			return "loginFailed";
		}
		
		return "loginSuccessful";
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
