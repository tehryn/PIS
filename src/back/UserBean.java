package back;

import java.io.IOException;
import java.io.Serializable;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

import core.User;
import datatypes.UserRole;

@Named
@SessionScoped
public class UserBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private User user;

	public UserBean() {
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<User> getUsers() {
		return User.getUsers();
	}

	public String actionNew() {
		user = new User();
		return "new";
	}

	public String actionInsertNew() {
		// TODO
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
		// TODO
		return "delete";
    }

	//private queries.db.User user;
    String firstName;
	String lastName;
    String email;
    String password;

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

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	public void redirect() throws IOException {
	    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
	    externalContext.redirect("http://stackoverflow.com");
	}
	
	// login
	public String actionLogin() {
		try {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("[DEBUG] email: " + email + " password: " + password));
			new core.User(email, password);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Login successful"));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
			//e.printStackTrace();
			return "loginFailed";
		}
		
		return "loginSuccessful";
	}
	
	// register
	public String actionRegister() {
		try {
			new core.User(firstName, lastName, email, password);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
		}
		
		return "register";
	}
}
