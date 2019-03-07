/**
 * 
 */
package core;

import java.util.List;
import java.util.ArrayList;
import queries.ReservationQueries;

/**
 * @author doomista
 *
 */
public class Reservation {
	private ReservationQueries query = ReservationQueries.getQuery();
	private queries.db.Reservation resHandle;
	
	private Reservation(queries.db.Reservation reservation) {
		resHandle = reservation;
	}
	
	public static List<Reservation> findReservationsOfUser(int userId) {
		ReservationQueries query = ReservationQueries.getQuery();
		List<queries.db.Reservation> reservations = query.getAllItems();
		List<Reservation> result = new ArrayList<Reservation>();
		
		for (queries.db.Reservation res : reservations) {
			if (res.getUser().getId() == userId) {
				result.add(new Reservation(res));
			}
		}
		
		return result;
	}
}
