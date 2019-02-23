package queries.db;

import datatypes.Currency;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import datatypes.CommodityPriceCounter;

/**
 *  @brief Part of commodity price
 */
@Entity
@Table
public class CommodityPrice extends Object {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private float value;
	
	@Enumerated(EnumType.ORDINAL)
	private Currency currency;
	
	@Enumerated(EnumType.ORDINAL)
	private CommodityPriceCounter valuePer;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="commodity", nullable=false)
	private Commodity commodity;
	
	public CommodityPrice() {
		value = 0.f;
		currency = Currency.CZK;
		valuePer = CommodityPriceCounter.ITEM;
	}
	
	public CommodityPrice(float value, Currency currency, CommodityPriceCounter valuePer, Commodity commodity) {
		this.value = value;
		this.currency = currency;
		this.valuePer = valuePer;
		this.commodity = commodity;
	}

	public Commodity getCommodity() {
		return commodity;
	}
	
	public int getId() {
		return id;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public CommodityPriceCounter getValuePer() {
		return valuePer;
	}

	public void setValuePer(CommodityPriceCounter valuePer) {
		this.valuePer = valuePer;
	}

	@Override
	public String toString() {
		return "CommodityPrice [id=" + id + ", value=" + value + ", currency=" + currency + ", valuePer=" + valuePer
				+ ", commodity=" + commodity.getSysid() + "]";
	}
	
}