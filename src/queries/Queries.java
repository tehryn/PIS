package queries;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 * 
 * @author xmatej52
 * 
 */
public abstract class Queries<Type> {
	/**
	 * Serialize string, so it will be safe to put it directly to web
	 * @param str 
	 * @return
	 */
	public String serializate( String str ) {
		return str;
	}
	
	protected Boolean inTransaction;
	protected EntityManager entitymanager;
	public Queries() {
		this.entitymanager = Persistence.createEntityManagerFactory( "PIS" ).createEntityManager();
	}
	
	/**
	 * Loads item from database
	 * @param id id of item in database table
	 * @return object that represents item (objects from queries.db package)
	 */
	public abstract Type getItem( int id );
	
	/**
	 * Deletes item from database
	 * @param id of item in database table
	 */
	public abstract void deleteItem( int id );
	
	/**
	 * Loads content of all items in database table into List
	 * @return List of objects representing items () (objects from queries.db package)
	 */
	public abstract List<Type> getAllItems();
	
	/**
	 * Process query (ONLY select statement) in database and returns list of selected items
	 * @param query query that will be proceed
	 * @param params parameters of query
	 * @return list of items representing database objects with getters and setters.
	 */
	protected abstract List<Type> select( String query, List<Object> params );

	/**
	 * Starts transaction
	 */
	protected void beginTransaction() {
		entitymanager.getTransaction().begin();
		inTransaction = true;
	}
	
	/**
	 * Commit the current resource transaction, writing any unflushed changes to the database.
	 */
	protected void commit() {
		entitymanager.flush();
	}
	
	/**
	 * Rollback all the changes in database.
	 */
	protected void rollback() {
		 entitymanager.getTransaction().rollback();
	}
	
	/**
	 * Commit the current resource transaction, writing any unflushed changes to the database.
	 */
	public void update() {
		entitymanager.getTransaction().commit();
	}
}
