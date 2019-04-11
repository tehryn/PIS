package back;

import java.io.Serializable;

import java.util.List;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

import core.Room;

@Named
@SessionScoped
public class RoomBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private queries.db.Commodity room;
	private Room roomMgr;

	public RoomBean() {
		roomMgr = new Room();
		room = new queries.db.Commodity();
	}

	public queries.db.Commodity getRoom() {
		return room;
	}

	public void setRoom(queries.db.Commodity room) {
		this.room = room;
	}

	public List<queries.db.Commodity> getRooms() {
		return roomMgr.getAll();
	}

	public String actionNew() {
		room = new queries.db.Commodity();
		return "new";
	}

	public String actionInsertNew() {
		roomMgr.save(room);
		return "insert";
	}

	public String actionUpdate() {
		roomMgr.save(room);
		return "update";
	}

	public String actionEdit(queries.db.Commodity room) {
		setRoom(room);
		return "edit";
	}

	public String actionDelete(queries.db.Commodity room) {
		roomMgr.remove(room);
		return "delete";
	}
}
