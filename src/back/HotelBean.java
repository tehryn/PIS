package back;

import javax.ejb.Stateless;
import javax.inject.Named;

import core.Hotel;

@Stateless
@Named
public class HotelBean {
	private Hotel hotel;

    public HotelBean() {
        hotel = Hotel.getHotel();
    }
}
