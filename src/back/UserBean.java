package back;

import java.io.Serializable;

import java.util.List;

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
}
