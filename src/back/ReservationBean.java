package back;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
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
	private Date sinceDate;
	private Date untilDate;
	private Date sinceTime;
	private Date untilTime;	
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
	String userEmail;	// Email of user who will get new reservations from Receptionist
	
	// Errors
	private Boolean errorSinceNotLessUntil = false;
	private Boolean errorSinceIsNotFuture = false;
	private Boolean errorCollisionInReservationList = false;
	private Boolean errorRequest = false;
	private Boolean errorCancel = false;
	private Boolean errorAccept = false;
	private Boolean errorDeny = false;
	private Boolean errorUserEmail = false;
	
    @Inject
	private UserBean userBean;

    @PostConstruct
    public void init() {
        // Put original constructor code here.
    	reservedItems = new ArrayList<ReservedCommodity>();
		reservations = Reservation.findReservationsOfUser(userBean.getLoggedUser());
    	sinceTime = new Date();
    	untilTime = new Date();
    	sinceDate = new Date();
    	untilDate = new Date();
    	since = new Date();
    	until = new Date();
    	
    	Calendar cal = Calendar.getInstance();
    	
    	sinceTime = cal.getTime();
    	untilTime = cal.getTime();
    	sinceDate = cal.getTime();
    	untilDate = cal.getTime();
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
    	reservedItems = new ArrayList<ReservedCommodity>();
    	allReservations = Reservation.getPendingReservations();
	}
    
    public String actionReserve() throws Exception {
    	if (userBean.isSetReservationsUser()) {
    		newReservation = new Reservation(userBean.getReservationsUser());
    	} else {
    		newReservation = new Reservation(userBean.getLoggedUser());
    	}
    	
    	for(ReservedCommodity item: reservedItems) {
    		newReservation.addItem(item);
        }
    	
    	try {
    		newReservation.request();
    	} catch (Exception e) {
    		errorRequest = true;
    		return "null";
    	}
    	errorRequest = false;
    	
    	if (userBean.isSetReservationsUser()) {
    		return "/reservations/reservation_list.xhtml?faces-redirect=true";
    	} else {
    		reservations = Reservation.findReservationsOfUser(userBean.getLoggedUser());
    		return "/user/reservation_list.xhtml?faces-redirect=true";
    	}
	}
    
    public String actionNew() {
    	userBean.freeReservationsUser();
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
    	
    	if (userBean.isSetReservationsUser()) {
    		return "/reservations/reservation_new_for_user.xhtml?faces-redirect=true";
    	} else {
    		return "/user/reservation_new.xhtml?faces-redirect=true";
    	}
	}
    
    public String actionReserveService() {
    	if (!canAddToReservedItems(reservedService)) {
    		return "null";
    	}
    	reservedItems.add(new ReservedCommodity(reservedService, since, until));
    	
		if (userBean.isSetReservationsUser()) {
    		return "/reservations/reservation_new_for_user.xhtml?faces-redirect=true";
    	} else {
    		return "/user/reservation_new.xhtml?faces-redirect=true";
    	}
	}
    
    public String actionCancelReservation() throws Exception {
    	try {
    		cancelledReservation.cancel();
    	} catch (Exception e) {
    		errorCancel = true;
    		return "null";
    	}
    	errorCancel = false;
		return "/user/reservation_list.xhtml?faces-redirect=true";
	}
    
    public String actionRemoveItem() {
    	reservedItems.remove(removedItem);
    	
    	if (userBean.isSetReservationsUser()) {
    		return "/reservations/reservation_new_for_user.xhtml?faces-redirect=true";
    	} else {
    		return "/user/reservation_new.xhtml?faces-redirect=true";
    	}
	}
    
    public String actionAcceptReservation() throws Exception {
    	try {
    		acceptedReservation.accept();
    	} catch (Exception e) {
    		errorAccept = true;
    		return "null";
    	}
    	errorAccept = false;
		return "/reservations/reservation_list.xhtml?faces-redirect=true";
	}
    
    public String actionDenyReservation() throws Exception {
    	try {
    		deniedReservation.reject();
		} catch (Exception e) {
			errorDeny = true;
			return "null";
		}
		errorDeny = false;
		return "/reservations/reservation_list.xhtml?faces-redirect=true";
	}
    
    public String actionNewReservationForUser () {
    	
    	try {
			userBean.setupReservationsUser(userEmail);
		} catch (Exception e) {
			errorUserEmail = true;
			return "null";
		}
    	
    	errorUserEmail = false;
    	return "/reservations/reservation_new_for_user.xhtml?faces-redirect=true";
    }
    
    public String actionStorno(String fromUrl) {
    	if (userBean.isSetReservationsUser()) {/*
    		if (fromUrl.equals("/user/reservation_new_room.xhtml"))
    			return "/reservations/reservation_new_for_user.xhtml?faces-redirect=true";
    		if (fromUrl.equals("/user/reservation_new_service.xhtml"))*/
    		return "/reservations/reservation_new_for_user.xhtml?faces-redirect=true";
    	} else {/*
    		if (fromUrl.equals("/user/reservation_new_room.xhtml"))
    			return "/user/reservation_new.xhtml?faces-redirect=true";
    		if (fromUrl.equals("/user/reservation_new_service.xhtml"))*/
			return "/user/reservation_new.xhtml?faces-redirect=true";
    	}
    	//return "null";    	
    }
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 	// Private methods
    
    public Date dateTime(Date date, Date time) {

        Calendar aDate = Calendar.getInstance();
        aDate.setTime(date);

        Calendar aTime = Calendar.getInstance();
        aTime.setTime(time);

        Calendar aDateTime = Calendar.getInstance();
        aDateTime.set(Calendar.DAY_OF_MONTH, aDate.get(Calendar.DAY_OF_MONTH));
        aDateTime.set(Calendar.MONTH, aDate.get(Calendar.MONTH));
        aDateTime.set(Calendar.YEAR, aDate.get(Calendar.YEAR));
        aDateTime.set(Calendar.HOUR, aTime.get(Calendar.HOUR));
        aDateTime.set(Calendar.MINUTE, aTime.get(Calendar.MINUTE));
        aDateTime.set(Calendar.SECOND, aTime.get(Calendar.SECOND));

        return aDateTime.getTime();
    }
    
    private void updateSinceUntil() {
    	since = dateTime(sinceDate, sinceTime);
    	until = dateTime(untilDate, untilTime);
    }

    private Boolean canAddToReservedItems(Commodity newItem) {
    	updateSinceUntil();
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
    	updateSinceUntil();
    	errorSinceNotLessUntil = !since.before(until);
    	errorSinceIsNotFuture = !since.after(new Date()); // new Date() == now
    	return (!errorSinceNotLessUntil) && (!errorSinceIsNotFuture);
    }
    
 	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 	// Getters and Setters
    
    

	public ArrayList<ReservedCommodity> getReservedItems() {
		return reservedItems;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public Boolean getErrorUserEmail() {
		return errorUserEmail;
	}

	public void setErrorUserEmail(Boolean errorUserEmail) {
		this.errorUserEmail = errorUserEmail;
	}

	public Boolean getErrorRequest() {
		return errorRequest;
	}

	public void setErrorRequest(Boolean errorRequest) {
		this.errorRequest = errorRequest;
	}

	public Boolean getErrorCancel() {
		return errorCancel;
	}

	public void setErrorCancel(Boolean errorCancel) {
		this.errorCancel = errorCancel;
	}

	public Boolean getErrorAccept() {
		return errorAccept;
	}

	public void setErrorAccept(Boolean errorAccept) {
		this.errorAccept = errorAccept;
	}

	public Boolean getErrorDeny() {
		return errorDeny;
	}

	public void setErrorDeny(Boolean errorDeny) {
		this.errorDeny = errorDeny;
	}

	public Date getSinceDate() {
		return sinceDate;
	}

	public void setSinceDate(Date sinceDate) {
		this.sinceDate = sinceDate;
	}

	public Date getUntilDate() {
		return untilDate;
	}

	public void setUntilDate(Date untilDate) {
		this.untilDate = untilDate;
	}

	public Date getSinceTime() {
		return sinceTime;
	}

	public void setSinceTime(Date sinceTime) {
		this.sinceTime = sinceTime;
	}

	public Date getUntilTime() {
		return untilTime;
	}

	public void setUntilTime(Date untilTime) {
		this.untilTime = untilTime;
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

	
}
