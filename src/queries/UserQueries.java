/**
 * 
 */
package queries;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import queries.db.User;

/**
 * 
 * @author xmatej52
 *
 */
public class UserQueries extends Queries<User> {

	
	private static UserQueries self;
	
	public static synchronized UserQueries getQuery() {
		if ( self == null ) {
			self = new UserQueries();
		}
		return self;
	}
	
	private UserQueries() {
		super();
		beginTransaction();
	}
	
	@Override
	public void deleteItem(int id) {
		User u = getItem(id);
		entitymanager.remove( u );
		flush2Db();
	}
	
	public List<User> find( String firstName, String lastName ) {
		List<Object> params = new ArrayList<Object>();
		params.add(firstName);
		params.add(lastName);
		return select( "SELECT u FROM User u WHERE u.firstName LIKE CONCAT('%',?1,'%') and u.lastName LIKE CONCAT('%',?2,'%')", params );
	}

	@Override
	public List<User> getAllItems() {
		return select( "SELECT u FROM User u", new ArrayList<Object>() );
	}

	@Override
	public User getItem(int id) {
		return entitymanager.find( User.class, id );
	}

	/**
	 * Retrieve specific user from database
	 * @param email email of user
	 * @return object representing user or null if no user was found
	 */
	public User getUserByEmail( String email ) {
		List<Object> params = new ArrayList<Object>();
		params.add(email);
		List<User> users = select( "SELECT u FROM User u WHERE u.email = ?1", params );
		if ( users.size() > 1 ) {
			throw new RuntimeException( "queries.UserQueries::getUserByEmail: There are more than one user with same email adress." );
		}
		else if ( users.size() == 1 ) {
			return users.get(0);
		}
		else {
			return null;
		}
	}

	/**
	 * Change name of user
	 * @param user user that shall be updated
	 * @param firstName new first name
	 * @param lastName new last name
	 */
	public void changeName( User user, String firstName, String lastName ) {
		user.setFirstName(firstName);
		user.setLastName(lastName);
		flush2Db();
	}

	/**
	 * Creates new user in database
	 * @param firstName Name of the user
	 * @param lastName Surname of the user
	 * @param email Unique email of the user
	 * @return Instance representing user with getters and setters.
	 */
	public User newUser( String firstName, String lastName, String email ) {
		User u = new User(firstName, lastName, email, datatypes.UserRole.VISITOR );
		entitymanager.persist(u);
		flush2Db();
		return u;
	}
	
	/**
	 * Creates new user in database
	 * @param firstName Name of the user
	 * @param lastName Surname of the user
	 * @param email Unique email of the user
	 * @param password Password of the user
	 * @return Instance representing user with getters and setters.
	 */
	public User newUser( String firstName, String lastName, String email, String password ) {
		User u = new User(firstName, lastName, email, datatypes.UserRole.REGISTRATED, password );
		entitymanager.persist(u);
		flush2Db();
		return u;
	}
	
	@Override
	protected List<User> select(String query, List<Object> params) {
		TypedQuery<User> q = entitymanager.createQuery(query, User.class);
		for( int i = 0; i < params.size(); i++ ) {
			q.setParameter(i+1, params.get(i) );
		}
		return q.getResultList();
	}
	
	/**
	 * Change login info of user
	 * @param user user that shall be updated
	 * @param email new email of user
	 * @param password new password of user
	 */
	public void setLogin( User user, String email, String password ) {
		user.setEmail(email);
		user.setPassword(password);
		flush2Db();
	}
	
	/**
	 * Updates role of user
	 * @param user user that shall be updated
	 * @param newRole new role
	 */
	public void setRole( User user, datatypes.UserRole newRole ) {
		user.setRole(newRole);
		flush2Db();
	}
}
