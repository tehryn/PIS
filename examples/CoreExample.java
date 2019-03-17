import core.*;

import java.util.ArrayList;
import java.sql.Date;

import datatypes.CommodityPrice;
import datatypes.CommodityPriceCounter;
import datatypes.CommodityType;
import datatypes.Currency;
import datatypes.UserRole;

public class CoreExample {

	public static void main(String[] args) throws Exception {
		// Hotel is singleton
		Hotel hotel = Hotel.getHotel();
		
		// Create a room in our hotel
		Room room = (Room)hotel.newCommodity("pokoj1408", CommodityType.ROOM, new ArrayList<CommodityPrice>(){{
			add(new CommodityPrice(69, CommodityPriceCounter.NIGHT, Currency.CZK));
			add(new CommodityPrice(42.f, CommodityPriceCounter.NIGHT, Currency.EUR));
			add(new CommodityPrice(0.01f, CommodityPriceCounter.NIGHT, Currency.USD));
		}});
		
		// Create a service in our hotel
		Service service = (Service)hotel.newCommodity("tantrickaMasaz69", CommodityType.SERVICE, new ArrayList<CommodityPrice>(){{
			add(new CommodityPrice(2000.f, CommodityPriceCounter.HOUR, Currency.CZK));
			add(new CommodityPrice(60.f, CommodityPriceCounter.HOUR, Currency.EUR));
			add(new CommodityPrice(69.f, CommodityPriceCounter.HOUR, Currency.USD));
		}});
		
		// Create new user. Go see different constructors in docs!
		User user = new User("Flynn", "Taggart", "doomer@uac.com", "IDSPISPOPD");
		user.setRole(UserRole.RECEPTIONIST);
		
		// User wants to reserve a room
		Reservation reservation = new Reservation(user);
		ReservedCommodity reservedRoom = new ReservedCommodity(room, Date.valueOf("2019-03-16"), Date.valueOf("2019-03-18"));
		reservation.addItem(reservedRoom);
		
		// User has appetite for massage :)
		// Using whole day for reservation because initialization to smaller bits in java with this object is pain in ass
		// Using frontend working with UNIX timestamps will help alot
		ReservedCommodity reservedService = new ReservedCommodity(service, Date.valueOf("2019-03-17"), Date.valueOf("2019-03-18"));
		reservation.addItem(reservedService);
		
		// Admin should approve the reservation
		reservation.accept();
	}

}
