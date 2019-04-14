/**
 * @file Hotel.java
 * @author Jakub Neruda <xnerud01@stud.fit.vutbr.cz>
 */
package core;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import datatypes.CommodityType;
import datatypes.CommodityPrice;

/**
 * 
 */
public class Hotel {
	private static Hotel self;
	
	public static synchronized Hotel getHotel() {
		if ( self == null ) {
			self = new Hotel();
		}
		return self;
	}
	
	private Hotel() {}
	
	public List<Room> getRooms() {
		List<Commodity> rooms = Commodity.getCommodities(CommodityType.ROOM);
		List<Room> result = new ArrayList<Room>();
		
		for (Commodity room : rooms) {
			result.add(new Room(room));
		}
		
		return result;
	}
	
	public List<Room> getRooms(Date from, Date until) {
		List<Commodity> rooms = Commodity.getCommodities(CommodityType.ROOM);
		List<Room> result = new ArrayList<Room>();
		
		for (Commodity room : rooms) {
			if (room.isReserved(from, until)) {
				result.add((Room)room);
			}
		}
		
		return result;
	}
	
	public List<Service> getServices() {
		List<Commodity> services = Commodity.getCommodities(CommodityType.SERVICE);
		List<Service> result = new ArrayList<Service>();
		
		for (Commodity service : services) {
			result.add(new Service(service));
		}
		
		return result;
	}
	public List<Service> getServices(Date from, Date until) {
		List<Commodity> services = Commodity.getCommodities(CommodityType.SERVICE);
		List<Service> result = new ArrayList<Service>();
		
		for (Commodity service : services) {
			if (service.isReserved(from, until)) {
				result.add((Service)service);
			}
		}
		
		return result;
	}
	
	public Commodity newCommodity(String sysid, String description, CommodityType type, List<CommodityPrice> prices) throws Exception {
		 switch (type) {
		 case SERVICE:
			 return new Service(sysid, description, prices);
			 
		 case ROOM:
			 return new Room(sysid, description, prices);
		 }
		 
		 throw new Exception("Invalid commodity type!");
	}
}
