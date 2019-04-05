/**
 * @file Commodity.java
 * @author Jakub Neruda <xnerud01@stud.fit.vutbr.cz>
 */
package core;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import queries.CommodityQueries;
import queries.UserQueries;
import datatypes.CommodityPrice;
import datatypes.CommodityState;
import datatypes.CommodityType;

/**
 * @brief Class representing billable part of the system that can be reserved
 */
public class Commodity {
	protected CommodityQueries query = CommodityQueries.getQuery();
	protected queries.db.Commodity resHandle = null;
	
	protected Commodity() {}
	
	protected Commodity(queries.db.Commodity handle) {
		resHandle = handle;
	}
	
	/**
	 * @brief Get list of commodities of certain type
	 * @param type Type of commodity to filter by
	 */
	public static List<Commodity> getCommodities(CommodityType type) {
		CommodityQueries query = CommodityQueries.getQuery();
		List<queries.db.Commodity> commodities = query.getAllItems();
		List<Commodity> result = new ArrayList<Commodity>();
		
		for (queries.db.Commodity com : commodities) {
			if (com.getType() == type) {
				result.add(new Commodity(com));
			}
		}
		
		return result;
	}
	
	/**
	 * @brief Get commodity by its database id
	 */
	public static Commodity getCommodityById(int id) {
		CommodityQueries query = CommodityQueries.getQuery();
		queries.db.Commodity handle = query.getItem(id);
		return new Commodity(handle);
	}
	
	public CommodityState getAvailability() {
		return resHandle.getAvailability();
	}
	
	/**
	 * @brief Get price of commodity in all available currencies
	 */
	public List<CommodityPrice> getPrice() {
		List<queries.db.CommodityPrice> prices = resHandle.getCommodityPrices();
		List<CommodityPrice> result = new ArrayList<CommodityPrice>();
		
		for (queries.db.CommodityPrice price : prices) {
			result.add(new CommodityPrice(price.getValue(), price.getValuePer(), price.getCurrency()));
		}
		
		return result;
	}
	
	public CommodityType getType() {
		return resHandle.getType();
	}
	
	public String getSysid() {
		return resHandle.getSysid();
	}
	
	/**
	 * @brief Returns database handle to Commodity table row
	 * @warning Do not use this! This method is meant only
	 * for internal workings of Core package!
	 */
	public queries.db.Commodity getHandle() {
		return resHandle;
	}
	
	// TODO: public getInfo() {}
	
	public void setAvailability(CommodityState state) {
		try {
			resHandle.setAvailability(state);
		}
		catch (Exception e) {
			UserQueries.rollback();
			throw e;
		}
		
		UserQueries.update();
	}
	
	public void setPrice(CommodityPrice price) {
		try {
			query.setPrice(resHandle, price.getValue(), price.getValuePer(), price.getCurrency());
		}
		catch (Exception e) {
			UserQueries.rollback();
			throw e;
		}
		
		UserQueries.update();
	}
	
	public boolean isReserved(Date from, Date until) {
		queries.ReservationQueries resq = queries.ReservationQueries.getQuery();
		return resq.isCommodityReserved(resHandle, from, until);
	}
}
