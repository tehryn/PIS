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
	
	public static synchronized CommodityQueries getQuery() {
		if ( self == null ) {
			self = new CommodityQueries();
		}
		return self;
	}
	
	private CommodityQueries() {
		super();
		beginTransaction();
	}
	
	@Override
	public void deleteItem(int id) {
		Commodity c = getItem(id);
		entitymanager.remove( c );
		flush2Db();
	}

	@Override
	public List<Commodity> getAllItems() {
		return select( "SELECT c FROM Commodity c", new ArrayList<Object>() );
	}

	/**
	 * Finds commodity by sysid
	 * @param sysid sysid of commodity
	 * @return commodity with specific sysid
	 */
	public Commodity getCommodityBySysid(String sysid) {
		List<Object> params = new ArrayList<Object>();
		params.add(sysid);
		List<Commodity> commodities = select( "SELECT c FROM Commodity c WHERE c.sysid = ?1", params );
		if ( commodities.size() > 1 ) {
			throw new RuntimeException( "queries.CommodityQueries::getCommodityBySysid: There are more than one commodity with same email sysid." );
		}
		else if ( commodities.size() == 1 ) {
			return commodities.get(0);
		}
		else {
			return null;
		}
	}

	@Override
	public Commodity getItem(int id) {
		return entitymanager.find( Commodity.class, id );
	}
	
	/**
	 * Creates new commodity.
	 * @param type commodity type
	 * @param state commodity state
	 * @param sysid commodity sysid
	 * @return Instance representing new commodity.
	 */
	public Commodity newCommodity(CommodityType type, CommodityState state, String sysid) {
		Commodity c = new Commodity( type, state, sysid );
		entitymanager.persist(c);
		flush2Db();
		return c;
	}

	public Commodity newCommodity(CommodityType type, CommodityState state, String sysid, String desc) {
		Commodity c = new Commodity( type, state, sysid, desc );
		entitymanager.persist(c);
		flush2Db();
		return c;
	}
	
	/**
	 * Removes support of currency for specific commodity
	 * @param commodity commodity, that will be changed
	 * @param currency currency, that will not be longer supported in commodity
	 */
	public void removeSupportedCurrency( Commodity commodity, Currency currency ) {
		List <CommodityPrice> prices = commodity.getCommodityPrices();
		int i = 0;
		for(i = 0; i < prices.size(); i++) {
			if (prices.get(i).getCurrency() == currency) {
				prices.remove(i);
				flush2Db();
				break;
			}
		}
	}
	
	@Override
	protected List<Commodity> select(String query, List<Object> params) {
		TypedQuery<Commodity> q = entitymanager.createQuery(query, Commodity.class);
		for( int i = 0; i < params.size(); i++ ) {
			q.setParameter(i+1, params.get(i) );
		}
		return q.getResultList();
	}
	
	/**
	 * Sets new state of commodity availability
	 * @param commodity commodity that we be changed
	 * @param newState new state
	 */
	public void setAvailability( Commodity commodity, CommodityState newState ) {
		commodity.setAvailability(newState);
		flush2Db();
	}

	/**
	 * Updates or sets new price for specific currency and commodity
	 * @param commodity commodity itself
	 * @param value Price of commodity
	 * @param counter Type, how is final price counted
	 * @param currency Currency of value.
	 */
	public void setPrice(Commodity commodity, float value, CommodityPriceCounter counter, Currency currency) {
		List <CommodityPrice> prices = commodity.getCommodityPrices();
		int i = 0;
		for(i = 0; i < prices.size(); i++) {
			if (prices.get(i).getCurrency() == currency) {
				prices.remove(i);
				flush2Db();
				break;
			}
		}
		CommodityPrice price = new CommodityPrice(value, currency, counter, commodity);
		prices.add( price );
		flush2Db();
	}
}