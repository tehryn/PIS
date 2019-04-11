/**
 *  @file Room.java
 *  @author Jakub Neruda <xnerud01@stud.fit.vutbr.cz>
 */
package core;

import java.util.List;
import java.util.ArrayList;
import java.sql.Timestamp;

import datatypes.CommodityPrice;
import datatypes.CommodityState;
import datatypes.CommodityType;
import queries.CommodityQueries;

/**
 * @brief Commodity of type Room
 */
public class Room extends Commodity {
	private Room(queries.db.Commodity com) {
		super(com);
	}
	
	public Room() {
	}
	
	public Room(String sysid, String description, List<CommodityPrice> prices) {
		resHandle = query.newCommodity(CommodityType.ROOM, CommodityState.AVAILABLE, sysid, description);
		
		for (CommodityPrice price : prices) {
			setPrice(price);
		}
	}
	
	public List<queries.db.Commodity> getAll() {
		CommodityQueries query = CommodityQueries.getQuery();
		List<queries.db.Commodity> allCommodities = query.getAllItems();
		List<queries.db.Commodity> result = new ArrayList<queries.db.Commodity>();
		
		for (queries.db.Commodity com : allCommodities) {
			Commodity mycom = new Commodity(com);
			
			if (mycom.getType() == CommodityType.ROOM) {
				result.add(com);
			}
		}
		
		return result;
	}
	
	public void save(queries.db.Commodity room) {
		// TODO
	}
	
	public void remove(queries.db.Commodity room) {
		// TODO
	}
	
	/**
	 * @brief Get list of Rooms that are available in given time frame
	 * @param from  Start of time frame
	 * @param until End of time frame
	 */
	List<Commodity> showAvailable(Timestamp from, Timestamp until) {
		CommodityQueries query = CommodityQueries.getQuery();
		List<queries.db.Commodity> allCommodities = query.getAllItems();
		List<Commodity> result = new ArrayList<Commodity>();
		
		for (queries.db.Commodity com : allCommodities) {
			Commodity mycom = new Commodity(com);
			
			if (mycom.isReserved(from, until) && mycom.getType() == CommodityType.ROOM) {
				result.add(new Room(com));
			}
		}
		
		return result;
	}
}
