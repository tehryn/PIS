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
	private Boolean errorInsert = false;
	private Boolean errorEdit = false;

	// Used for user creating/registration
	private String newUserFirstName;
	private String newUserLastName;
	private String newUserEmail;
	private String newUserPassword;
	private UserRole newUserRole;	// not for registration -> REGISTRATED
	private Boolean errorRegister = false;
    
    // Logging in
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private UserRole role = UserRole.VISITOR;
    private User loggedUser;
    private Boolean errorLogin = false;
    
    // Reservations for user
    private User reservationsUser = null;
    
    public Boolean isSetReservationsUser() {
    	return reservationsUser != null;
    }
    
    public void freeReservationsUser() {
    	reservationsUser = null;
    }
    
    public void setupReservationsUser(String userEmail) throws Exception {
    	try {
    		reservationsUser = new User(userEmail);
    	} catch (Exception e) {
    		reservationsUser = null;
    		throw e;
    	}
    }
    
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
			errorInsert = true;
			return "null";
		}
		
		errorInsert = false;
		return "insert";
	}

	public String actionUpdate() {
		
		errorEdit = false;
		return "update";
	}

	public String actionEdit() {
		return "edit";
	}
    
    // logout
    public String actionLogout() {
    	loggedUser = null;
    	role = UserRole.VISITOR;
    	reservationsUser = null;
    	return "/index.xhtml?faces-redirect=true";
    }
    
	// login
	public String actionLogin() {
		try {
			loggedUser = new core.User(email, password);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Login successful"));
			this.role = loggedUser.getRole();
			
			// Can't log in as VISITOR
			if (this.role == UserRole.VISITOR) {
				loggedUser = null;	// TODO check
				errorLogin = true;
				return "null";
			}
		} catch (Exception e) {
			errorLogin = true;
			return "null";
		}
		errorLogin = false;
		reservationsUser = null;
		return "/index.xhtml?faces-redirect=true";
	}
	
	// register
	public String actionRegister() {
		try {
			loggedUser = new core.User(firstName, lastName, email, password);
		} catch (Exception e) {
			errorRegister = true;
			return "null";
		}
		
		errorRegister = false;
		this.actionLogin();
		return "/index.xhtml?faces-redirect=true";
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
	
    public User getReservationsUser() {
		return reservationsUser;
	}

	public Boolean getErrorEdit() {
		return errorEdit;
	}

	public void setErrorEdit(Boolean errorEdit) {
		this.errorEdit = errorEdit;
	}

	public Boolean getErrorInsert() {
		return errorInsert;
	}

	public void setErrorInsert(Boolean errorInsert) {
		this.errorInsert = errorInsert;
	}

	public Boolean getErrorRegister() {
		return errorRegister;
	}

	public void setErrorRegister(Boolean errorRegister) {
		this.errorRegister = errorRegister;
	}

	public Boolean getErrorLogin() {
		return errorLogin;
	}

	public void setErrorLogin(Boolean errorLogin) {
		this.errorLogin = errorLogin;
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
