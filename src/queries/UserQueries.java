package queries;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import datatypes.UserRole;
import queries.db.User;

public class UserQueries extends Queries<User> {

	
	private static UserQueries self;
	
	private UserQueries() {
		super();
		beginTransaction();
	}
	
	public static synchronized UserQueries getQuery() {
		if ( self == null ) {
			self = new UserQueries();
		}
		return self;
	}
	
	@Override
	public User getItem(int id) {
		return this.entitymanager.find( User.class, id );
	}
	
	public User newUser( String firstName, String lastName, String email, UserRole role, String password ) {
		User u = new User(firstName, lastName, email, role, password );
		commit();
		return u;
	}

	@Override
	public void deleteItem(int id) {
		User u = getItem(id);
		entitymanager.remove( u );
		commit();
	}

	@Override
	public List<User> getAllItems() {
		return select( "SELECT u FROM User u", new ArrayList<Object>() );
	}

	@Override
	protected List<User> select(String query, List<Object> params) {
		TypedQuery<User> q = entitymanager.createQuery(query, User.class);
		for( int i = 0; i < params.size(); i++ ) {
			q.setParameter(i+1, params.get(i) );
		}
		return q.getResultList();
	}

	public List<User> find( String firstName, String lastName ) {
		List<Object> params = new ArrayList<Object>();
		params.add(firstName);
		params.add(lastName);
		return select( "SELECT u FROM User u WHERE u.firstName LIKE CONCAT('%',?1,'%') and u.lastName LIKE CONCAT('%',?2,'%')", params );
	}
	
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
}
