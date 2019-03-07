/**
 * \file User.java
 * \author Jakub Neruda
 * \brief Class representing single user
 */
package core;

import datatypes.*;
import queries.UserQueries;
import java.util.List;
import java.util.ArrayList;

/**
 * @author Jakub Neruda
 */
public class User {
	private UserQueries query = UserQueries.getQuery();
	private queries.db.User userHandle = null;
	
	private User(queries.db.User handle) {
		userHandle = handle;
	}
	
	public static List<User> findUsers(String firstName, String lastName) {
		UserQueries query = UserQueries.getQuery();
		List<User> result = new ArrayList<User>();
		
		for (queries.db.User handle : query.find(firstName, lastName)) {
			result.add(new User(handle));
		}
		
		return result;
	}
	
	/**
	 * @brief Unregistered user ctor
	 * 
	 * @param firstName First name of the user
	 * @param lastName  Second name of the user
	 * @param email     Email of the user
	 * 
	 * @details Unregistered user is created when user wants to
	 * reserve a commodity without an account. He *must* enter
	 * his full name and email.
	 * 
	 * If he later wants to register, he must do so with the same
	 * name and email and this new account will be connected with
	 * previous "unregistered". This will give the user access to
	 * his old reservations.
	 */
	public User(String firstName, String lastName, String email) {
		userHandle = query.newUser(firstName, lastName, email);
	}
	
	/**
	 *  @brief Login ctor
	 *  
	 *  @param email     Email of registered user
	 *  @param passoword Password of registered user
	 *  
	 *  @details After user has registered himself (using register ctor)
	 *  He can log in at any time using this ctor.
	 *  
	 *  If the user does not exist (email is not in database) or bad
	 *  password was provided, a RuntimeException is thrown.
	 */
	public User(String email, String password) {
		try {
			userHandle = query.getUserByEmail(email);
			String pass = userHandle.getPassword();
			
			if (pass != password) throw new RuntimeException("Bad password");
		}
		catch (Exception e) {
			throw new RuntimeException("Email or password is incorrect!");
		}
	}
	
	/**
	 * @brief Create new registered user ctor
	 * 
	 * @param firstName First name of the user
	 * @param lastName  Second name of the user
	 * @param email     Email of the user
	 * @param password  Password for login
	 */
	public User(String firstName, String lastName, String email, String password) {
		userHandle = query.newUser(firstName, lastName, email, password);
	}
	
	/**
	 * @brief Find user by email ctor
	 * 
	 * @param email Email of the user
	 * 
	 * @details If no user with that email is in database, exception is
	 * thrown. If multiple users with that email are in database, exception
	 * is thrown.
	 */
	public User(String email) {
		userHandle = query.getUserByEmail(email);
		if (userHandle == null) {
			throw new RuntimeException("User not found!");
		}
	}
	
	/**
	 * @brief Find user by id ctor
	 * 
	 * @param id Id of the user
	 */
	public User(int id) {
		userHandle = query.getItem(id);
	}
	
	public int getID() {
		return userHandle.getId();
	}
	
	public String getEmail() {
		return userHandle.getEmail();
	}
	
	public String getFullName() {
		return getFirstName() + ' ' + getLastName();
	}
	
	public String getFirstName() {
		return userHandle.getFirstName();
	}
	
	public String getLastName() {
		return userHandle.getLastName();
	}
	
	public UserRole getRole() {
		return userHandle.getRole();
	}
	
	public void setRole(UserRole role) {
		userHandle.setRole(role);
	}
	
	public void setName(String firstName, String lastName) {
		userHandle.setFirstName(firstName);
		userHandle.setLastName(lastName);
	}
	
	public void setEmail(String email) {
		userHandle.setEmail(email);
	}
	
	/**
	 * @brief
	 */
	public void remove() {
		query.deleteItem(userHandle.getId());
		userHandle = null;
	}
}
