package queries.db;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * 
 * @author xmatej52
 *
 */
@Entity
@Table
public class Reservation extends Object {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@JoinColumn(name="user")
	private User user;
	
	@OneToMany(mappedBy = "reservation", orphanRemoval=true, cascade = CascadeType.ALL)
	private List<ReservedCommodity> items;

	@Enumerated(EnumType.ORDINAL)
	private datatypes.ReservationStatus status;

	public Reservation() {
		super();
	}
	
	public Reservation(User user) {
		super();
		this.user = user;
		this.status = datatypes.ReservationStatus.REQUESTED;
		this.items = new ArrayList<ReservedCommodity>();
	}

	public int getId() {
		return id;
	}

	public List<ReservedCommodity> getItems() {
		return items;
	}
	
	public datatypes.ReservationStatus getStatus() {
		return status;
	}
	
	public User getUser() {
		return user;
	}

	public void setStatus(datatypes.ReservationStatus status  ) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return "Reservation [id=" + id + ", user=" + user + ", items=" + items + ", status=" + status + "]";
	}
}
