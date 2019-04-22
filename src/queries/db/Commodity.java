/**
 * @author Jiri Matejka (xmatej52)
 */
package queries.db;

import java.util.ArrayList;
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
@Table(name="commodity")
public class Commodity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Enumerated(EnumType.ORDINAL)
	private CommodityType type;

	@Enumerated(EnumType.ORDINAL)
	private CommodityState availability;

	private String sysid;

	private String description;

	@OneToMany(mappedBy = "commodity", cascade = CascadeType.ALL, orphanRemoval=true)
	private List<CommodityPrice> commodityPrices;

	protected Commodity() {
		super();
	}

	public Commodity( CommodityType type, CommodityState state, String sysid ) {
		this.type = type;
		this.availability = state;
		this.commodityPrices = new ArrayList<CommodityPrice>();
		this.sysid = sysid;
	}

	public Commodity( CommodityType type, CommodityState state, String sysid, String desc ) {
		this.type = type;
		this.availability = state;
		this.commodityPrices = new ArrayList<CommodityPrice>();
		this.sysid = sysid;
		this.description = desc;
	}

	public CommodityState getAvailability() {
		return availability;
	}

	public List<CommodityPrice> getCommodityPrices() {
		return commodityPrices;
	}

	public int getId() {
		return id;
	}

	public String getSysid() {
		return sysid;
	}

	public CommodityType getType() {
		return type;
	}

	public void setAvailability(CommodityState availability) {
		this.availability = availability;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Commodity [id=" + id + ", type=" + type + ", availability=" + availability + ", sysid='" + sysid
				+ "', commodityPrices=" + commodityPrices + "]";
	}
}
