package back;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

import core.Hotel;
import core.Room;
import datatypes.CommodityPrice;
import datatypes.CommodityPriceCounter;
import datatypes.CommodityState;
import datatypes.CommodityType;
import datatypes.Currency;

@Named
@SessionScoped
public class RoomBean implements Serializable {
	private static final long serialVersionUID = 1L;

	// New room
	private String newSysid;
	private String newDescription;
	private float newPrice;	// NIGHT, CZK
	
	// Editing room
	private String editedSysid;
	private String editedDescription;
	private float editedPrice;	// NIGHT, CZK
	private Room editedRoom;
	
	private Hotel hotelMgr;
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Public methods

	public RoomBean() {
		hotelMgr = Hotel.getHotel();
	}

	public List<Room> getRooms() {
		return hotelMgr.getRooms();
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
			hotelMgr.newCommodity(newSysid, newDescription, CommodityType.ROOM, prices);
		} catch (Exception e) {
			// TODO
			e.printStackTrace();
		}
		return "insert";
	}

	public String actionUpdate() {
		editedRoom.setDescription(editedDescription);
		editedRoom.setPrice(new CommodityPrice(editedPrice, CommodityPriceCounter.NIGHT, Currency.CZK));
		
		return "update";
	}

	public String actionEdit() {
		editedSysid = editedRoom.getSysid();
		editedDescription = editedRoom.getDescription();
		editedPrice = editedRoom.getPrice().get(0).getValue();
		
		return "edit";
	}

	public String actionEnable() {
		editedRoom.setAvailability(CommodityState.AVAILABLE);
		
		return "delete";
	}
	
	public String actionBlock() {
		editedRoom.setAvailability(CommodityState.BLOCKED);
		
		return "delete";
	}

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Private methods

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Getters and Setters
	
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



	public float getEditedPrice() {
		return editedPrice;
	}



	public void setEditedPrice(float editedPrice) {
		this.editedPrice = editedPrice;
	}



	public Room getEditedRoom() {
		return editedRoom;
	}



	public void setEditedRoom(Room editedRoom) {
		this.editedRoom = editedRoom;
	}



	public float getNewPrice() {
		return newPrice;
	}



	public void setNewPrice(float newPrice) {
		this.newPrice = newPrice;
	}
	

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

	
	
	
	
	
}
