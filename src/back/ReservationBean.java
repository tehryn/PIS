package back;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import core.Commodity;
import core.Reservation;
import core.ReservedCommodity;
import core.Room;
import core.Service;

@Named
@SessionScoped
public class ReservationBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	// Work with reservations
	private Reservation newReservation;
	private ArrayList<ReservedCommodity> reservedItems;
	private Date since;
	private Date until;
	private List<Commodity> freeRooms;
	private List<Commodity> freeServices;
	private Room reservedRoom;
	private Service reservedService;
	private Reservation reservationsMgr;
	private List<Reservation> reservations;
	private Reservation cancelledReservation;
	private ReservedCommodity removedItem;
	
	// Reservation management
	private List<Reservation> allReservations;
	private Reservation acceptedReservation;
	private Reservation deniedReservation;
	
	
    @Inject
	private UserBean userBean; 

    @PostConstruct
    public void init() {
        // Put original constructor code here.
    	reservedItems = new ArrayList<ReservedCommodity>();
    	reservations = reservationsMgr.findReservationsOfUser(userBean.getLoggedUser());
    }
	
    public ReservationBean() {
    }
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 	// Public methods
    
    public List<Commodity> userReservedCommodities() {
		return null;
    }
    
    public void preRenderList() {
    	reservations = reservationsMgr.findReservationsOfUser(userBean.getLoggedUser());
	}
    
    public void preRenderAllList() {
    	allReservations = reservationsMgr.getPendingReservations();
	}
    
    public String actionReserve() {
    	newReservation = new Reservation(userBean.getLoggedUser());
    	
    	for(ReservedCommodity item: reservedItems) {
    		newReservation.addItem(item);
        }
    	
    	newReservation.request();
    	reservations = reservationsMgr.findReservationsOfUser(userBean.getLoggedUser());
    	
		return "/user/reservation_list.xhtml";
	}
    
    public String actionNew() {
    	reservedItems.clear();
		return "new";
	}
    
    public String actionAddRoom() {
		return "/user/reservation_new_room.xhtml";
	}
    
    public String actionAddService() {
		return "/user/reservation_new_service.xhtml";
	}
    
    public String actionSearchFreeRooms() {
    	freeRooms = Room.showAvailable(new java.sql.Timestamp(since.getTime()), new java.sql.Timestamp(until.getTime()));
		return "/user/reservation_new_room_results.xhtml";
	}
    
    public String actionSearchFreeServices() {
    	freeServices = Service.showAvailable(new java.sql.Timestamp(since.getTime()), new java.sql.Timestamp(until.getTime()));
		return "/user/reservation_new_service_results.xhtml";
	}
    
    public String actionReserveRoom() {
    	for(ReservedCommodity item: reservedItems) {
    		if (item.getItem().getSysid().equals(reservedRoom.getSysid()) &&
                	item.getFrom().equals(since) &&
                	item.getUntil().equals(until))
    			return "/user/reservation_new.xhtml";	// TODO The room is already in the list message
        }
    	reservedItems.add(new ReservedCommodity(reservedRoom, since, until));
    	
		return "/user/reservation_new.xhtml";
	}
    
    public String actionReserveService() {
    	for(ReservedCommodity item: reservedItems) {
    		if (item.getItem().getSysid().equals(reservedService.getSysid()) &&
    				item.getFrom().equals(since) &&
    				item.getUntil().equals(until))
    			return "/user/reservation_new.xhtml";	// TODO The service is already in the list message
        }
    	reservedItems.add(new ReservedCommodity(reservedService, since, until));
    	
		return "/user/reservation_new.xhtml";
	}
    
    public String actionCancelReservation() {
    	cancelledReservation.cancel();
    	
		return "/user/reservation_list.xhtml";
	}
    
    public String actionRemoveItem() {
    	reservedItems.remove(removedItem);
    	
		return "/user/reservation_new.xhtml";
	}
    
    public String actionAcceptReservation() {
    	acceptedReservation.accept();
    	
		return "/reservations/reservation_list.xhtml";
	}
    
    public String actionDenyReservation() {
    	acceptedReservation.accept();
    	
		return "/reservations/reservation_list.xhtml";
	}
    
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 	// Private methods

 	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 	// Getters and Setters
    
    

	public ArrayList<ReservedCommodity> getReservedItems() {
		return reservedItems;
	}

	public Reservation getAcceptedReservation() {
		return acceptedReservation;
	}

	public void setAcceptedReservation(Reservation acceptedReservation) {
		this.acceptedReservation = acceptedReservation;
	}

	public Reservation getDeniedReservation() {
		return deniedReservation;
	}

	public void setDeniedReservation(Reservation deniedReservation) {
		this.deniedReservation = deniedReservation;
	}

	public List<Reservation> getAllReservations() {
		return allReservations;
	}

	public void setAllReservations(List<Reservation> allReservations) {
		this.allReservations = allReservations;
	}

	public ReservedCommodity getRemovedItem() {
		return removedItem;
	}

	public void setRemovedItem(ReservedCommodity removedItem) {
		this.removedItem = removedItem;
	}

	public Reservation getCancelledReservation() {
		return cancelledReservation;
	}

	public void setCancelledReservation(Reservation cancelledReservation) {
		this.cancelledReservation = cancelledReservation;
	}

	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}

	public void setReservedItems(ArrayList<ReservedCommodity> reservedItems) {
		this.reservedItems = reservedItems;
	}

	public List<Commodity> getFreeServices() {
		return freeServices;
	}

	public void setFreeServices(List<Commodity> freeServices) {
		this.freeServices = freeServices;
	}

	public Service getReservedService() {
		return reservedService;
	}

	public void setReservedService(Service reservedService) {
		this.reservedService = reservedService;
	}

	public Room getReservedRoom() {
		return reservedRoom;
	}

	public void setReservedRoom(Room reservedRoom) {
		this.reservedRoom = reservedRoom;
	}

	public List<Commodity> getFreeRooms() {
		return freeRooms;
	}

	public void setFreeRooms(List<Commodity> freeRooms) {
		this.freeRooms = freeRooms;
	}

	public Date getSince() {
		return since;
	}

	public void setSince(Date since) {
		this.since = since;
	}

	public Date getUntil() {
		return until;
	}

	public void setUntil(Date until) {
		this.until = until;
	}

	
}
