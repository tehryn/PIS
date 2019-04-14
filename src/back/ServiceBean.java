package back;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Named;

import core.Hotel;
import core.Service;

@Stateless
@Named
public class ServiceBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private Service service;
	private Hotel hotelMgr;

	public ServiceBean() {
		hotelMgr = Hotel.getHotel();
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public List<Service> getServices() {
		return hotelMgr.getServices();
	}

	public String actionNew() {
		service = new Service();
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
