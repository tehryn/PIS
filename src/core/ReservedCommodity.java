/**
 * @file ReservedCommodity.java
 * @author Jakub Neruda <xnerud01@stud.fit.vutbr.cz>
 */
package core;

import java.util.List;
import java.util.Date;

import datatypes.CommodityPrice;
import datatypes.Currency;

/**
 *  @brief Commodity is reserved between time <from, until>
 */
public class ReservedCommodity {
	private Commodity item = null;
	Date from = null;
	Date until = null;
	
	public ReservedCommodity(Commodity item, Date from, Date until) {
		this.item = item;
		this.from = from;
		this.until = until;
	}
	
	public Commodity getItem() {
		return item;
	}
	
	public Date getFrom() {
		return from;
	}
	
	public Date getUntil() {
		return until;
	}
	
	public float getPrice(Currency currency) {
		List<CommodityPrice> prices = item.getPrice();
		for (CommodityPrice price : prices) {
			if (price.getCurrency() == currency) return price.getValue();
		}
		
		return -1.f;
	}
}
