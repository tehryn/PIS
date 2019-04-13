import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

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
		//com.mysql.cj.jdbc.MysqlDataSource.setServerTimezone( "UTC" );
		//TimeZone.setDefault(TimeZone.getTimeZone("Etc/UTC"));
		UserQueries        qu = UserQueries.getQuery();	
		CommodityQueries   qc = CommodityQueries.getQuery();
		ReservationQueries qr = ReservationQueries.getQuery();
		
		// New user
		User u = qu.getUserByEmail( "test@pis.cz" );
		if ( u == null ) {
			System.out.println( "Uzivatel neexistuje, zakladam..." );			
			try {
				u = qu.newUser("testUser", "Testicek", "test@pis.cz", "heslo");			
			} catch ( Exception e ) {
				Queries.rollback();
				System.out.println( "Nelze vytvorit uzivatele" );
				throw e;
			}	
		}
		System.out.println( u );
		
		// New commodity
		Commodity c = qc.getCommodityBySysid( "testRoom" );
		if ( c == null ) {
			System.out.println( "Komodita neexistuje, zakladam..." );
			try {
				c = qc.newCommodity(datatypes.CommodityType.ROOM, datatypes.CommodityState.AVAILABLE, "testRoom");			
			} catch ( Exception e ) {
				System.out.println( "Nelze zalozit uzivatele" );
				Queries.rollback();
				throw e;
			}			
		}
		qc.setPrice(c, (float) 1000, datatypes.CommodityPriceCounter.NIGHT, datatypes.Currency.CZK);
		qc.setPrice(c, (float) 50, datatypes.CommodityPriceCounter.NIGHT, datatypes.Currency.EUR);
		System.out.println( c );
		
		// new reservation
		List<Reservation> rs = qr.userRezervations(u);
		Date today = Calendar.getInstance().getTime();
		Date tmorrow = new Date( today.getTime() + 86400000 );
		if ( rs.size() == 0 ) {
			System.out.println( "Uzivatel nema zadnou rezervaci, vytvarim novou..." );
			Reservation r = qr.newReservation(u);
			if ( qr.isCommodityReserved( c, today, tmorrow ) ) {
				throw new RuntimeException("Komodita je rezervovana, ale nemela by byt...");
			}
			else {
				System.out.println( "Komodita neni rezervovana, coz je OK" );
			}
			qr.addCommodity(r, c, today, tmorrow);
		}
		// toto je jen v ramci testovani - mame line vyhodnocovani, musime osahat objekt, aby se inicializoval a volal v metode toString
		// v projektu to volat nemusite a pokud nahodou budete nuceni, tak mi napiste a ja zrusim line vyhodnocovani
		for ( int i = 0; i < rs.size(); i++ ) {
			rs.get(i).getItems().size();
		}
		System.out.println( rs );
		
		// Lets store new items into database
		Queries.update();
		if ( qr.isCommodityReserved( c, today, tmorrow ) ) {
			System.out.println( "Komodita je rezervovana, tady uz je to ok..." );
		}
		else {
			throw new RuntimeException("Komodita neni rezervovana, ale mela by byt...");			
		}
		
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