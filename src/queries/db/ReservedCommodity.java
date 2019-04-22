/**
 * @author Jiri Matejka (xmatej52)
 */
package queries.db;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="reservedcommodity")
public class ReservedCommodity extends Object {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name="reservation")
	private Reservation reservation;

	@ManyToOne
	@JoinColumn(name="commodity")
	private Commodity commodity;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="frm")
	private Date from;

	@Temporal(TemporalType.TIMESTAMP)
	private Date until;

	public ReservedCommodity() {
		super();
	}

	public ReservedCommodity(Reservation reservation, Commodity commodity, Date from, Date until) {
		super();
		this.reservation = reservation;
		this.commodity = commodity;
		this.from = from;
		this.until = until;
	}

	public Commodity getCommodity() {
		return commodity;
	}

	public Date getFrom() {
		return from;
	}

	public int getId() {
		return id;
	}

	public Reservation getRezervation() {
		return reservation;
	}

	public Date getUntil() {
		return until;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public void setUntil(Date until) {
		this.until = until;
	}

	@Override
	public String toString() {
		return "ReservedCommodity [id=" + id + ", reservation=" + reservation.getId() + ", commodity=" + commodity + ", from="
				+ from + ", until=" + until + "]";
	}

}
