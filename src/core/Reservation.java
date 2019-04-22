/**
 * @file Reservation.java
 * @author Jakub Neruda <xnerud01@stud.fit.vutbr.cz>
 */
package core;

import java.util.List;
import java.util.Date;
import java.util.ArrayList;

import queries.ReservationQueries;
import queries.UserQueries;

import datatypes.Currency;
import datatypes.ReservationStatus;
import datatypes.CommodityPrice;

/**
 * @brief Set of reserved commodities
 */
public class Reservation {
	private ReservationQueries query = ReservationQueries.getQuery();
	private queries.db.Reservation resHandle = null;
	
	private Reservation(queries.db.Reservation reservation) {
		resHandle = reservation;
	}
	
	/**
	 * @brief Get list of reservations bound to user
     *
	 * @param user User whose reservations should be returned
	 */
	public static List<Reservation> findReservationsOfUser(User user) {
		ReservationQueries query = ReservationQueries.getQuery();
		List<queries.db.Reservation> reservations = query.getAllItems();
		List<Reservation> result = new ArrayList<Reservation>();
		
		for (queries.db.Reservation res : reservations) {
			if (res.getUser().getId() == user.getID()) {
				result.add(new Reservation(res));
			}
		}
		
		return result;
	}
	
	/**
	 * @brief Get list of pending reservations
	 */
	public static List<Reservation> getPendingReservations() {
		ReservationQueries query = ReservationQueries.getQuery();
		List<queries.db.Reservation> reservations = query.pendingReservations();
		List<Reservation> result = new ArrayList<Reservation>();
		
		for (queries.db.Reservation res : reservations) {
			result.add(new Reservation(res));
		}
		
		return result;
	}
	
	/**
	 * @brief Get existing reservation
	 * 
	 * @param id ID of reservation in database
	 */
	public Reservation(int id) {
		resHandle = query.getItem(id);
	}
	
	/**
	 * @brief Create new reservation for user
	 * 
	 * @param user User to bind reservation to
	 */
	public Reservation(User user) {
		try {
			resHandle = query.newReservation(user.getID());
		}
		catch (Exception e) {
			UserQueries.rollback();
			throw e;
		}
		
		UserQueries.update();
	}
	
	/**
	 *  @brief Change status of reservation to REQUESTED
	 */
	public void request() {
		try {
			resHandle.setStatus(ReservationStatus.REQUESTED);
		}
		catch (Exception e) {
			UserQueries.rollback();
			throw e;
		}
		
		UserQueries.update();
	}
	
	/**
	 *  @brief Change status of reservation to APPROVED
	 */
	public void accept() {
		try {
			resHandle.setStatus(ReservationStatus.APPROVED);
		}
		catch (Exception e) {
			UserQueries.rollback();
			throw e;
		}
		
		UserQueries.update();
	}
	
	/**
	 *  @brief Change status of reservation to REJECTED
	 */
	public void reject() {
		try {
			resHandle.setStatus(ReservationStatus.REJECTED);
		}
		catch (Exception e) {
			UserQueries.rollback();
			throw e;
		}
		
		UserQueries.update();
	}
	
	/**
	 *  @brief Change status of reservation to CANCELED
	 */
	public void cancel() {
		try {
			resHandle.setStatus(ReservationStatus.CANCELED);
		}
		catch (Exception e) {
			UserQueries.rollback();
			throw e;
		}
		
		UserQueries.update();
	}
	
	/**
	 * @brief Add commodity to overall reservation
	 */
	public void addItem(ReservedCommodity item) {
		try {
			query.addCommodity(resHandle, item.getItem().getHandle(), item.getFrom(), item.getUntil());
		}
		catch (Exception e) {
			UserQueries.rollback();
			throw e;
		}
		
		UserQueries.update();
	}
	
	/**
	 * @brief Get sum of all prices of reserved commodities
	 * @param currency Which currency to use for summing
	 */
	public float getPrice(Currency currency) {
		List<queries.db.ReservedCommodity> resComs = resHandle.getItems();
		
		float result = 0.f;
		
		for (queries.db.ReservedCommodity res : resComs) {
			float tmp = new core.ReservedCommodity( res ).getPrice(currency);
			if ( tmp > 0 ) {
				result += tmp;
			}
			else {
				return -1.f;
			}
		}
		
		return result;
	}
	
	public datatypes.ReservationStatus getStatus() {
		return resHandle.getStatus();
	}
	
	public core.User getAuthor() {
		queries.db.User user = resHandle.getUser();
		
		return new core.User(user.getId());
	}
	
	/**
	 * @brief Calculate earliest time of reservation for all reserved commodities
	 * @return From the set of reserved commodities, earliest from time is returned
	 */
	public Date calcFrom() {
		List<queries.db.ReservedCommodity> items = resHandle.getItems();
		
		Date min = items.get(0).getFrom();
		for (int i = 1; i < items.size(); i++) {
			Date d = items.get(i).getFrom();
			if (min.getTime() > d.getTime()) min = d;
		}
		
		return min;
	} 
	
	/**
	 * @brief Calculate latest time of reservation for all reserved commodities
	 * @return From the set of reserved commodities, latest from time is returned
	 */
	public Date calcUntil() {
		List<queries.db.ReservedCommodity> items = resHandle.getItems();
		
		Date max = items.get(0).getUntil();
		for (int i = 1; i < items.size(); i++) {
			Date d = items.get(i).getUntil();
			if (max.getTime() < d.getTime()) max = d;
		}
		
		return max;
	}
}
