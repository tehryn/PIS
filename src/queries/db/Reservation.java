package queries.db;

import java.util.List;

import javax.persistence.Entity;
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
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user")
	private User user;
	
	@OneToMany(mappedBy = "commodity")
	private List<ReservedCommodity> items;

	public Reservation(User user, List<ReservedCommodity> items) {
		super();
		this.user = user;
		this.items = items;
	}
	
	public Reservation() {
		super();
	}

	public List<ReservedCommodity> getItems() {
		return items;
	}

	public void setItems(List<ReservedCommodity> items) {
		this.items = items;
	}

	public int getId() {
		return id;
	}

	public User getUser() {
		return user;
	}
}
