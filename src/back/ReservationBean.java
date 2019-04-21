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
	private List<Reservation> reservations;
	private Reservation cancelledReservation;
	private ReservedCommodity removedItem;
	
	// Reservation management
	private List<Reservation> allReservations;
	private Reservation acceptedReservation;
	private Reservation deniedReservation;
	
	// Errors
	private Boolean errorSinceNotLessUntil = false;
	private Boolean errorSinceIsNotFuture = false;
	private Boolean errorCollisionInReservationList = false;
	
	
    @Inject
	private UserBean userBean; 

    @PostConstruct
    public void init() {
        // Put original constructor code here.
    	reservedItems = new ArrayList<ReservedCommodity>();
    	reservations = Reservation.findReservationsOfUser(userBean.getLoggedUser());
    }
	
    public ReservationBean() {
    }
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 	// Public methods
    
    public List<Commodity> userReservedCommodities() {
		return null;
    }
    
    public void preRenderList() {
    	reservations = Reservation.findReservationsOfUser(userBean.getLoggedUser());
	}
    
    public void preRenderAllList() {
    	allReservations = Reservation.getPendingReservations();
	}
    
    public String actionReserve() {
    	newReservation = new Reservation(userBean.getLoggedUser());
    	
    	for(ReservedCommodity item: reservedItems) {
    		newReservation.addItem(item);
        }
    	
    	newReservation.request();
    	reservations = Reservation.findReservationsOfUser(userBean.getLoggedUser());
    	
		return "/user/reservation_list.xhtml?faces-redirect=true";
	}
    
    public String actionNew() {
    	reservedItems.clear();
		return "new";
	}
    
    public String actionAddRoom() {
		return "/user/reservation_new_room.xhtml?faces-redirect=true";
	}
    
    public String actionAddService() {
		return "/user/reservation_new_service.xhtml?faces-redirect=true";
	}
    
    public String actionSearchFreeRooms() {
    	if (!isCommoditySearchTimeOK()){
    		return "null";
    	}
    	
    	errorCollisionInReservationList = false;
    	freeRooms = Room.showAvailable(new java.sql.Timestamp(since.getTime()), new java.sql.Timestamp(until.getTime()));
		return "/user/reservation_new_room_results.xhtml?faces-redirect=true";
	}
    
    public String actionSearchFreeServices() {
    	if (!isCommoditySearchTimeOK()){
    		return "null";
    	}
    	
    	errorCollisionInReservationList = false;
    	freeServices = Service.showAvailable(new java.sql.Timestamp(since.getTime()), new java.sql.Timestamp(until.getTime()));
		return "/user/reservation_new_service_results.xhtml?faces-redirect=true";
	}
        
    public String actionReserveRoom() {
    	if (!canAddToReservedItems(reservedRoom)) {
    		return "null";
    	}
    	reservedItems.add(new ReservedCommodity(reservedRoom, since, until));
    	
		return "/user/reservation_new.xhtml?faces-redirect=true";
	}
    
    public String actionReserveService() {
    	if (!canAddToReservedItems(reservedService)) {
    		return "null";
    	}
    	reservedItems.add(new ReservedCommodity(reservedService, since, until));
    	
		return "/user/reservation_new.xhtml?faces-redirect=true";
	}
    
    public String actionCancelReservation() {
    	// TODO Can be ACCEPTED/REJECTED reservation cancelled?
    	cancelledReservation.cancel();
    	
		return "/user/reservation_list.xhtml?faces-redirect=true";
	}
    
    public String actionRemoveItem() {
    	reservedItems.remove(removedItem);
    	
		return "/user/reservation_new.xhtml?faces-redirect=true";
	}
    
    public String actionAcceptReservation() {
    	acceptedReservation.accept();
    	
		return "/reservations/reservation_list.xhtml?faces-redirect=true";
	}
    
    public String actionDenyReservation() {
    	deniedReservation.reject();
    	
		return "/reservations/reservation_list.xhtml?faces-redirect=true";
	}
    
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 	// Private methods

    private Boolean canAddToReservedItems(Commodity newItem) {
    	for(ReservedCommodity item: reservedItems) {
    		if (item.getItem().getSysid().equals(newItem.getSysid())) {
    			// Time intervals overlap
    			errorCollisionInReservationList = !((item.getUntil().before(since) || item.getUntil().equals(since)) ||
    											  (until.before(item.getFrom()) || until.equals(item.getFrom())));
    			if (errorCollisionInReservationList)
    				return !errorCollisionInReservationList;
    		}
        }
    	errorCollisionInReservationList = false;
    	return !errorCollisionInReservationList;
    }
    
    private Boolean isCommoditySearchTimeOK() {
    	errorSinceNotLessUntil = !since.before(until);
    	errorSinceIsNotFuture = !since.after(new Date()); // new Date() == now
    	return (!errorSinceNotLessUntil) && (!errorSinceIsNotFuture);
    }
    
 	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 	// Getters and Setters
    
    

	public ArrayList<ReservedCommodity> getReservedItems() {
		return reservedItems;
	}

	public Boolean getErrorCollisionInReservationList() {
		return errorCollisionInReservationList;
	}

	public void setErrorCollisionInReservationList(Boolean errorCollisionInReservationList) {
		this.errorCollisionInReservationList = errorCollisionInReservationList;
	}

	public Boolean getErrorSinceNotLessUntil() {
		return errorSinceNotLessUntil;
	}

	public void setErrorSinceNotLessUntil(Boolean errorSinceNotLessUntil) {
		this.errorSinceNotLessUntil = errorSinceNotLessUntil;
	}

	public Boolean getErrorSinceIsNotFuture() {
		return errorSinceIsNotFuture;
	}

	public void setErrorSinceIsNotFuture(Boolean errorSinceIsNotFuture) {
		this.errorSinceIsNotFuture = errorSinceIsNotFuture;
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
