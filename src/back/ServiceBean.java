package back;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import core.Hotel;
import core.Service;
import datatypes.CommodityPrice;
import datatypes.CommodityPriceCounter;
import datatypes.CommodityState;
import datatypes.CommodityType;
import datatypes.Currency;

@Named
@SessionScoped
public class ServiceBean implements Serializable {
	private static final long serialVersionUID = 1L;

	// New service
	private String newSysid;
	private String newDescription;
	private CommodityPriceCounter newUnit;
	private float newPrice;	// CZK
	
	// Editing service
	private String editedSysid;
	private String editedDescription;
	private CommodityPriceCounter editedUnit;
	private float editedPrice;	// CZK
	private Service editedService;
	
	private Hotel hotelMgr;

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Public methods
	
	public ServiceBean() {
		hotelMgr = Hotel.getHotel();
	}

	public List<Service> getServices() {
		return hotelMgr.getServices();
	}

	public String actionNew() {
		return "new";
	}

	public String actionInsertNew() {
		ArrayList<CommodityPrice> prices = new ArrayList<CommodityPrice>(){/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
			add(new CommodityPrice(newPrice, CommodityPriceCounter.NIGHT, Currency.CZK));
		}};
		
		try {
			hotelMgr.newCommodity(newSysid, newDescription, CommodityType.SERVICE, prices);
		} catch (Exception e) {
			// TODO
			e.printStackTrace();
		}
		return "insert";
	}

	public String actionUpdate() {
		editedService.setDescription(editedDescription);
		editedService.setPrice(new CommodityPrice(editedPrice, editedUnit, Currency.CZK));
		
		return "update";
	}

	public String actionEdit() {
		CommodityPrice price = editedService.getPrice().get(0);
		
		editedSysid = editedService.getSysid();
		editedDescription = editedService.getDescription();
		editedUnit = price.getValuePer();
		editedPrice = price.getValue();
		
		return "edit";
	}

	public String actionEnable() {
		editedService.setAvailability(CommodityState.AVAILABLE);
		
		return "delete";
	}
	
	public String actionBlock() {
		editedService.setAvailability(CommodityState.BLOCKED);
		
		return "delete";
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Private methods

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Getters and Setters
	
	public String getNewSysid() {
		return newSysid;
	}

	public void setNewSysid(String newSysid) {
		this.newSysid = newSysid;
	}

	public String getNewDescription() {
		return newDescription;
	}

	public void setNewDescription(String newDescription) {
		this.newDescription = newDescription;
	}

	public CommodityPriceCounter getNewUnit() {
		return newUnit;
	}

	public void setNewUnit(CommodityPriceCounter newUnit) {
		this.newUnit = newUnit;
	}

	public float getNewPrice() {
		return newPrice;
	}

	public void setNewPrice(float newPrice) {
		this.newPrice = newPrice;
	}

	public String getEditedSysid() {
		return editedSysid;
	}

	public void setEditedSysid(String editedSysid) {
		this.editedSysid = editedSysid;
	}

	public String getEditedDescription() {
		return editedDescription;
	}

	public void setEditedDescription(String editedDescription) {
		this.editedDescription = editedDescription;
	}

	public CommodityPriceCounter getEditedUnit() {
		return editedUnit;
	}

	public void setEditedUnit(CommodityPriceCounter editedUnit) {
		this.editedUnit = editedUnit;
	}

	public float getEditedPrice() {
		return editedPrice;
	}

	public void setEditedPrice(float editedPrice) {
		this.editedPrice = editedPrice;
	}

	public Service getEditedService() {
		return editedService;
	}

	public void setEditedService(Service editedService) {
		this.editedService = editedService;
	}
	
	
	
	
	
}
