package back;

import java.io.Serializable;
import java.util.List;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

import core.Hotel;
import core.Commodity;

@Named
@SessionScoped
public class HotelBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private Hotel hotel;

	public HotelBean() {
		hotel = Hotel.getHotel();
	}

	public List<Commodity> getRooms() {
		return hotel.getRooms();
	}
}
