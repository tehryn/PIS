/**
 * 
 */
package queries;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import datatypes.CommodityPriceCounter;
import datatypes.CommodityState;
import datatypes.CommodityType;
import datatypes.Currency;
import queries.db.Commodity;
import queries.db.CommodityPrice;

/**
 * @author xmatej52
 *
 */
public class CommodityQueries extends Queries<Commodity> {

	private static CommodityQueries self;
	
	private CommodityQueries() {
		super();
		beginTransaction();
	}
	
	public static synchronized CommodityQueries getQuery() {
		if ( self == null ) {
			self = new CommodityQueries();
		}
		return self;
	}
	
	@Override
	public Commodity getItem(int id) {
		return this.entitymanager.find( Commodity.class, id );
	}

	@Override
	public void deleteItem(int id) {
		Commodity c = getItem(id);
		entitymanager.remove( c );
		commit();
	}

	@Override
	public List<Commodity> getAllItems() {
		return select( "SELECT c FROM Commodity c", new ArrayList<Object>() );
	}

	@Override
	protected List<Commodity> select(String query, List<Object> params) {
		TypedQuery<Commodity> q = entitymanager.createQuery(query, Commodity.class);
		for( int i = 0; i < params.size(); i++ ) {
			q.setParameter(i+1, params.get(i) );
		}
		return q.getResultList();
	}
	
	public Commodity newCommodity(CommodityType type, CommodityState state, List<CommodityPrice> commodityPrices, String sysid ) {
		return new Commodity( type, state, commodityPrices, sysid );
	}
	
	public Commodity newCommodity(CommodityType type, CommodityState state, String sysid) {
		return new Commodity( type, state, new ArrayList<CommodityPrice>(), sysid);
	}
	
	public void setPrice(int id, float value, CommodityPriceCounter counter, Currency currency) {
		Commodity c = getItem( id );
		setPrice(c, value, counter, currency);
	}
	
	public void setPrice(Commodity commodity, float value, CommodityPriceCounter counter, Currency currency) {
		List <CommodityPrice> prices = commodity.getCommodityPrices();
		int i = 0;
		for(i = 0; i < prices.size(); i++) {
			if (prices.get(i).getCurrency() == currency) {
				//prices.remove(i);
				entitymanager.remove( prices.get(i) );
			}
		}
		prices.add(new CommodityPrice(value, currency, counter, commodity));
		commodity.setCommodityPrices(prices);
		commit();
	}
	
	
}
