/**
 * @author Jiri Matejka (xmatej52)
 */
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
	protected static EntityManager entitymanager = Persistence.createEntityManagerFactory( "PIS" ).createEntityManager();

	/**
	 * Starts transaction
	 */
	protected static void beginTransaction() {
		if ( !entitymanager.getTransaction().isActive() ) {
			entitymanager.getTransaction().begin();
		}
	}
	/**
	 * Commit the current resource transaction, writing any unflushed changes to the database.
	 */
	protected static void flush2Db() {
		entitymanager.flush();
	}

	/**
	 * Rollback all the changes in database and begins new transaction.
	 */
	public static void rollback() {
		if ( entitymanager.getTransaction().isActive() ) {
			entitymanager.getTransaction().rollback();
			beginTransaction();
		}
	}

	/**
	 * Rollback all the changes in database.
	 * @param startNew Starts new transaction after rollback
	 */
	public static void rollback( boolean startNew ) {
		if ( entitymanager.getTransaction().isActive() ) {
			entitymanager.getTransaction().rollback();
			if ( startNew ) {
				beginTransaction();
			}
		}
	}

	/**
	 * Commit the current resource transaction, writing any unflushed changes to the database.
	 */
	public static void update() {
		entitymanager.getTransaction().commit();
		beginTransaction();
	}

	/**
	 * Commit the current resource transaction, writing any unflushed changes to the database and starts new transaction of startNew is false, no new transaction will be started
	 * @param startNew starts new transaction after commit
	 */
	public static void update( boolean startNew ) {
		entitymanager.getTransaction().commit();
		if ( startNew ) {
			beginTransaction();
		}
	}

	public Queries() {}

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
	 * Loads item from database
	 * @param id id of item in database table
	 * @return object that represents item (objects from queries.db package)
	 */
	public abstract Type getItem( int id );

	/**
	 * Process query (ONLY select statement) in database and returns list of selected items
	 * @param query query that will be proceed
	 * @param params parameters of query
	 * @return list of items representing database objects with getters and setters.
	 */
	protected abstract List<Type> select( String query, List<Object> params );

	/**
	 * Serialize string, so it will be safe to put it directly to web
	 * @param str
	 * @return
	 */
	public String serializate( String str ) {
		return str;
	}
}
