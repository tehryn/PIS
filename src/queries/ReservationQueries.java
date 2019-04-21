/**
 * 
 */
package queries;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;

import queries.db.Commodity;
import queries.db.Reservation;
import queries.db.ReservedCommodity;
import queries.db.User;

/**
 * @author xmatej52
 *
 */
public class ReservationQueries extends Queries<Reservation> {
	
	private static ReservationQueries self;
	
	public static synchronized ReservationQueries getQuery() {
		if ( self == null ) {
			self = new ReservationQueries();
		}
		return self;
	}
	
	private ReservationQueries() {
		super();
		beginTransaction();
	}
	
	/**
	 * Reserves commodity for specific time
	 * @param reservation reservation where commodity will be reserved
	 * @param commodity commodity that will be reserved
	 * @param from starting datetime of reservation
	 * @param until ending datetime of reservation
	 * @return false when commodity is unavailable and was not reserved, otherwise true 
	 */
	public boolean addCommodity( Reservation reservation, Commodity commodity, Date from, Date until ) {
		if ( from.after( until ) ) {
			return false;
		}
		List<ReservedCommodity> resCommodities = reservation.getItems();
		for( int i = 0; i < resCommodities.size(); i++ ) {
			ReservedCommodity item = resCommodities.get(i);
			if ( between( item.getFrom(), from, until ) || between( item.getUntil(), from, until ) ) {
				return false;
			}
		}
		ReservedCommodity rc = new ReservedCommodity( reservation, commodity, from, until );
		resCommodities.add(rc);
		flush2Db();
		return true;
	}
	
	/**
	 * Tests if testDate is in interval of dates startDate and endDate
	 * @param testDate date to be tested
	 * @param startDate starts of interval (startDate < endDate)
	 * @param endDate end of interval (endDate > startDate)
	 * @return
	 */
	private boolean between(Date testDate, Date startDate, Date endDate) {
		return startDate.compareTo(startDate) * testDate.compareTo(endDate) > 0;
	}
	
	@Override
	public void deleteItem( int id ) {
		Reservation u = getItem(id);
		entitymanager.remove( u );
		flush2Db();
	}
	
	@Override
	public List<Reservation> getAllItems() {
		return select( "SELECT u FROM Reservation u", new ArrayList<Object>() );
	}
	
	@Override
	public Reservation getItem( int id ) {
		return entitymanager.find( Reservation.class, id );
	}
	
	/**
	 * Creates new reservation for specific User
	 * @param userId Id of User
	 * @return Object representing new reservation with empty items and status REQUESTED
	 */
	public Reservation newReservation( int userId ) {
		User u = entitymanager.find( User.class, userId );
		return newReservation(u);
	}
	
	/**
	 * Creates new reservation for specific User
	 * @param user object representing user
	 * @return Object representing new reservation with empty items and status REQUESTED
	 */
	public Reservation newReservation( User user ) {
		Reservation r = new Reservation( user );
		entitymanager.persist(r);
		flush2Db();
		return r;
	}
	
	/**
	 * Retrieve reservations with REQUESTED status
	 * @return List of objects that represents reservations
	 */
	public List<Reservation> pendingReservations() {
		List<Object> params = new ArrayList<Object>();
		params.add(datatypes.ReservationStatus.REQUESTED);
		return select( "SELECT r FROM Reservation r WHERE r.status = ?1 ", params );
	}
	
	/**
	 * Removes Commodity from reservation
	 * @param reservedCommodity Reservation request for commodity
	 */
	public void removeCommodity( ReservedCommodity reservedCommodity ) {
		entitymanager.remove(reservedCommodity);
		flush2Db();
	}
	
	@Override
	protected List<Reservation> select(String query, List<Object> params) {
		TypedQuery<Reservation> q = entitymanager.createQuery(query, Reservation.class);
		for( int i = 0; i < params.size(); i++ ) {
			q.setParameter(i+1, params.get(i) );
		}
		return q.getResultList();
	}
	
	/**
	 * Change current status of reservations
	 * @param reservation reservation that shall be changed
	 * @param status new reservation status
	 */
	public void setReservationStatus( Reservation reservation, datatypes.ReservationStatus status ) {
		reservation.setStatus(status);
		flush2Db();
	}
	
	/**
	 * Retrieve list of all user 
	 * @param userId id Of user
	 * @return List of objects that represents reservations
	 */
	public List<Reservation> userRezervations( int userId ) {
		User u = entitymanager.find( User.class, userId );
		return userRezervations(u);
	}
	
	/**
	 * Retrieve all reservations of specific user from database
	 * @param user object representing User
	 * @return List of objects that represents reservations
	 */
	public List<Reservation> userRezervations( User user ) {
		List<Object> params = new ArrayList<Object>();
		params.add(user);
		return select("SELECT r FROM Reservation r WHERE r.user = ?1 ", params);
	}
	
	/**
	 * Determines if commodity is reserved between 2 dates
	 * @param commodity object representing commodity
	 * @param from first date
	 * @param to second date
	 * @return true if commodity is reserved, otherwise false
	 */
	public boolean isCommodityReserved( Commodity commodity, Date from, Date to ) {
		List<Object> params = new ArrayList<Object>();
		params.add(commodity);
		params.add(from);
		params.add(to);
		params.add(datatypes.ReservationStatus.APPROVED);
		params.add(datatypes.ReservationStatus.REQUESTED);
		String query = "SELECT rc FROM ReservedCommodity rc WHERE "
				+ "rc.commodity = ?1 "
				+ "AND ( "
					+ "( ?2 BETWEEN rc.from AND rc.until ) "
					+ "OR ( ?3 BETWEEN rc.from AND rc.until ) "
				+ ")"
				+ "AND rc.reservation IN ( SELECT r FROM Reservation r WHERE r.status IN (?4, ?5) )";
		return select(query, params).size() != 0;
	}

}
