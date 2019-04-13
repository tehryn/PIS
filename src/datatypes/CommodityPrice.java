/**
 * @file CommodityPrice.java
 * @author Jakub Neruda <xnerud01@stud.fit.vutbr.cz>
 */
package datatypes;

/**
 * @brief Class representing part of total CommodityPrice
 */
public class CommodityPrice {
	private float value;
	private CommodityPriceCounter valuePer;
	private Currency currency;
	
	public CommodityPrice(float value, CommodityPriceCounter valuePer, Currency currency) {
		this.value = value;
		this.valuePer = valuePer;
		this.currency = currency;
	}
	
	public float getValue() {
		return value;
	}
	
	public CommodityPriceCounter getValuePer() {
		return valuePer;
	}
	
	public Currency getCurrency() {
		return currency;
	}
}
