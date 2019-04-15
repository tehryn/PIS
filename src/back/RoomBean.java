package back;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import core.Hotel;
import core.Room;
import datatypes.CommodityPrice;
import datatypes.CommodityPriceCounter;
import datatypes.Currency;

@Named
@SessionScoped
public class RoomBean implements Serializable {
	private static final long serialVersionUID = 1L;

	
	String sysid;
	String description;
	
	//private Room editedRoom;
	private Hotel hotelMgr;

	public RoomBean() {
		hotelMgr = Hotel.getHotel();
	}

	
/*
	public Room getEditedRoom() {
		return editedRoom;
	}

	public void setEditedRoom(Room editedRoom) {
		this.editedRoom = editedRoom;
	}
*/


	public List<Room> getRooms() {
		return hotelMgr.getRooms();
	}

	public String actionNew() {
		//room = new Room();
		return "new";
	}

	public String actionInsertNew() {
		ArrayList<CommodityPrice> prices = new ArrayList<CommodityPrice>(){{
			add(new CommodityPrice(69, CommodityPriceCounter.NIGHT, Currency.CZK));
			add(new CommodityPrice(42.f, CommodityPriceCounter.NIGHT, Currency.EUR));
			add(new CommodityPrice(0.01f, CommodityPriceCounter.NIGHT, Currency.USD));
		}};
			
		new Room(sysid, description, prices);
		return "insert";
	}

	public String actionUpdate() {
		// TODO
		//FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("[DEBUG] actionUpdate() neni hotovy!!!"));
		//editedRoom.setDescription(description);
		return "update";
	}

	public String actionEdit() {
		return "edit";
	}

	public String actionDelete() {
		return "delete";
	}

	
	
	
	public String getSysid() {
		return sysid;
	}

	public void setSysid(String sysid) {
		this.sysid = sysid;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
}
