/**
 * @file ReservedCommodity.java
 * @author Jakub Neruda <xnerud01@stud.fit.vutbr.cz>
 */
package core;

import java.util.List;
import java.time.Period;
import java.util.Date;
import java.lang.Math;

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
			if (price.getCurrency() == currency) {
				datatypes.CommodityPriceCounter modifiedBy = price.getValuePer();
				switch ( modifiedBy ) {
					case HOUR: return (int) Math.ceil( ( this.until.getTime() - this.from.getTime() ) / 3600000.0 ) * price.getValue();
					case NIGHT: return (int) Math.ceil( ( this.until.getTime() - this.from.getTime() ) / 86400000.0 ) * price.getValue();
					case ITEM:  return price.getValue();
					case SECOND: return (int) Math.ceil( ( this.until.getTime() - this.from.getTime() ) / 1000.0 ) * price.getValue();
					case MINUTE: return (int) Math.ceil( ( this.until.getTime() - this.from.getTime() ) / 60000.0 ) * price.getValue();
				}
			}
		}
		
		return -1.f;
	}
}
