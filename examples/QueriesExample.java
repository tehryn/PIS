import java.util.Calendar;
import java.util.Date;
import java.util.List;

import queries.CommodityQueries;
import queries.Queries;
import queries.UserQueries;
import queries.ReservationQueries;
import queries.db.Commodity;
import queries.db.Reservation;
import queries.db.User;

public class QueriesExample {
	public static void main(String[] args) {
		// Queries are singletons, static method must be called
		UserQueries        qu = UserQueries.getQuery();	
		CommodityQueries   qc = CommodityQueries.getQuery();
		ReservationQueries qr = ReservationQueries.getQuery();
		
		// New user
		User u = null;
		try {
			u = qu.newUser("testUser", "Testicek", "test@pis.cz", "heslo");			
		} catch ( Exception e ) {
			Queries.rollback();
			u = qu.getUserByEmail( "test@pis.cz" );
		}
		System.out.println( u );
		
		// New commodity
		Commodity c = null;
		try {
			c = qc.newCommodity(datatypes.CommodityType.ROOM, datatypes.CommodityState.AVAILABLE, "testRoom");			
		} catch ( Exception e ) {
			Queries.rollback();
			c = qc.getCommodityBySysid( "testRoom" );
		}
		qc.setPrice(c, (float) 1000, datatypes.CommodityPriceCounter.NIGHT, datatypes.Currency.CZK);
		qc.setPrice(c, (float) 50, datatypes.CommodityPriceCounter.NIGHT, datatypes.Currency.EUR);
		System.out.println( c );
		
		// new reservation
		List<Reservation> rs = qr.userRezervations(u);
		if ( rs.size() == 0 ) {
			Reservation r = qr.newReservation(u);
			Date today = Calendar.getInstance().getTime();
			qr.addCommodity(r, c, today, today);
		}
		// toto je jen v ramci testovani - mame line vyhodnocovani, musime osahat objekt, aby se inicializoval a volal v metode toString
		// v projektu to volat nemusite a pokud nahodou budete nuceni, tak mi napiste a ja zrusim line vyhodnocovani
		for ( int i = 0; i < rs.size(); i++ ) {
			rs.get(i).getItems().size();
		}
		System.out.println( rs );
		
		// Lets store new items into database
		Queries.update();
		
		// Add/update some prices to commodities
		List<Commodity> allCommodities = qc.getAllItems();
		System.out.println( allCommodities );
		for( int i = 0; i < allCommodities.size(); i++ ) {
			if ( allCommodities.get(i).getType() == datatypes.CommodityType.ROOM ) {
				qc.setPrice(allCommodities.get(i), (float) 125.5, datatypes.CommodityPriceCounter.NIGHT, datatypes.Currency.CZK);
			}
			else {
				qc.setPrice(allCommodities.get(i), (float) 400.99, datatypes.CommodityPriceCounter.HOUR, datatypes.Currency.CZK);				
			}
		}
		
		// all items
		System.out.println( qu.getAllItems());
		System.out.println( qc.getAllItems());
		System.out.println( qr.getAllItems());

		// lets commit and and transaction
		Queries.update( false );
	}
}