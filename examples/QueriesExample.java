import java.util.List;

import queries.CommodityQueries;
import queries.UserQueries;
import queries.db.Commodity;

public class QueriesExample {
	public static void main(String[] args) {
		// Queries are singletons, static method must be called
		UserQueries q = UserQueries.getQuery();
		
		System.out.println( q.getAllItems());
		System.out.println( q.find( "", "tejka" ));
		
		CommodityQueries qc = CommodityQueries.getQuery();
		List<Commodity> allCommodities = qc.getAllItems();
		System.out.println( allCommodities );
		for( int i = 0; i < allCommodities.size(); i++ ) {
			if ( allCommodities.get(i).getType() == datatypes.CommodityType.ROOM ) {
				qc.setPrice(allCommodities.get(i).getId(), (float) 125.5, datatypes.CommodityPriceCounter.NIGHT, datatypes.Currency.CZK);
			}
			else {
				qc.setPrice(allCommodities.get(i), (float) 400.99, datatypes.CommodityPriceCounter.HOUR, datatypes.Currency.CZK);				
			}
		}
		qc.newCommodity(datatypes.CommodityType.ROOM, datatypes.CommodityState.AVAILABLE, "P201");
		System.out.println( allCommodities );
		
		// Every change you make is local. When you call update, data in database will be updated as well
		q.update();
		qc.update();
	}
}