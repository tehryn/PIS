package back;

import java.io.Serializable;

import java.util.List;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

import core.Hotel;
import core.Room;

@Named
@SessionScoped
public class RoomBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private Room room;
	private Hotel hotelMgr;

	public RoomBean() {
		hotelMgr = Hotel.getHotel();
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public List<Room> getRooms() {
		return hotelMgr.getRooms();
	}

	public String actionNew() {
		room = new Room();
		return "new";
	}

	public String actionInsertNew() {
		// TODO
		return "insert";
	}

	public String actionUpdate() {
		// TODO
		return "update";
	}

	public String actionEdit() {
		return "edit";
	}

	public String actionDelete() {
		return "delete";
	}
}
