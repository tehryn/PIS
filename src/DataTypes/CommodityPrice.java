package datatypes;

import datatypes.Currency;
import datatypes.CommodityPriceCounter;

public class CommodityPrice {
	public float value;
	public Currency currency;
	public CommodityPriceCounter valuePer;
	
	public CommodityPrice() {
		value = 0.f;
		currency = Currency.CZK;
		valuePer = CommodityPriceCounter.ITEM;
	}
	
	public CommodityPrice(float value, Currency currency, CommodityPriceCounter valuePer) {
		this.value = value;
		this.currency = currency;
		this.valuePer = valuePer;
	}
}