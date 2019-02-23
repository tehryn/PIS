package queries.db;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import datatypes.CommodityState;
import datatypes.CommodityType;


@Entity
@Table
public class Commodity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Enumerated(EnumType.ORDINAL)
	private CommodityType type;
	
	@Enumerated(EnumType.ORDINAL)
	private CommodityState availability;
	
	private String sysid;
	
	@OneToMany(mappedBy = "commodity", cascade = CascadeType.ALL)
	private List<CommodityPrice> commodityPrices;

	public Commodity() {
		super();
	}
	
	public Commodity( CommodityType type, CommodityState state, List<CommodityPrice> commodityPrices, String sysid ) {
		this.type = type;
		this.availability = state;
		this.commodityPrices = commodityPrices;
		this.sysid = sysid;
	}
	
	public CommodityType getType() {
		return type;
	}

	public void setType(CommodityType type) {
		this.type = type;
	}

	public CommodityState getAvailability() {
		return availability;
	}

	public void setAvailability(CommodityState availability) {
		this.availability = availability;
	}

	public List<CommodityPrice> getCommodityPrices() {
		return commodityPrices;
	}

	public void setCommodityPrices(List<CommodityPrice> commodityPrices) {
		this.commodityPrices = commodityPrices;
	}

	public int getId() {
		return id;
	}

	public String getSysid() {
		return sysid;
	}

	public void setSysid(String sysid) {
		this.sysid = sysid;
	}

	@Override
	public String toString() {
		return "Commodity [id=" + id + ", type=" + type + ", availability=" + availability + ", sysid=" + sysid
				+ ", commodityPrices=" + commodityPrices + "]";
	}
}
