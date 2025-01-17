/**
 *  @file Service.java
 *  @author Jakub Neruda <xnerud01@stud.fit.vutbr.cz>
 */
package core;

import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

import datatypes.CommodityType;
import datatypes.CommodityState;
import datatypes.CommodityPrice;
import queries.CommodityQueries;

/**
 *  @brief Commodity of type Service
 */
public class Service extends Commodity {	
	private Service(queries.db.Commodity com) {
		super(com);
	}
	
	public Service() {
		try {
			resHandle = query.newCommodity(CommodityType.SERVICE, CommodityState.AVAILABLE, "", "");
		}
		catch (Exception e) {
			CommodityQueries.rollback();
			throw e;
		}
		
		CommodityQueries.update();
	}

	public Service(Commodity com) {
		super(com);
	}
	
	public Service(String sysid, String description, List<CommodityPrice> prices) {
		try {
			resHandle = query.newCommodity(CommodityType.SERVICE, CommodityState.AVAILABLE, sysid, description);
			
			for (CommodityPrice price : prices) {
				setPrice(price);
			}
		}
		catch (Exception e) {
			CommodityQueries.rollback();
			throw e;
		}
		
		CommodityQueries.update();
	}
	
	/**
	 * @brief Get list of Services that are available in given time frame
	 * @param from  Start of time frame
	 * @param until End of time frame
	 */
	public static List<Commodity> showAvailable(Timestamp from, Timestamp until) {
		CommodityQueries query = CommodityQueries.getQuery();
		List<queries.db.Commodity> allCommodities = query.getAllItems();
		List<Commodity> result = new ArrayList<Commodity>();
		
		for (queries.db.Commodity com : allCommodities) {
			Commodity mycom = new Commodity(com);
			
			if (!mycom.isReserved(from, until) && mycom.getType() == CommodityType.SERVICE) {
				result.add(new Service(com));
			}
		}
		
		return result;
	}
}
