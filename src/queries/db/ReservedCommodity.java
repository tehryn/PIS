package queries.db;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class ReservedCommodity extends Object {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="reservation")
	private Reservation reservation;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="commodity")
	private Reservation commodity;
	
	private LocalDateTime from;
	private LocalDateTime until;
	
	public ReservedCommodity(Reservation reservation, Reservation commodity, LocalDateTime from, LocalDateTime until) {
		super();
		this.reservation = reservation;
		this.commodity = commodity;
		this.from = from;
		this.until = until;
	}
	
	public ReservedCommodity() {
		super();
	}

	public LocalDateTime getFrom() {
		return from;
	}

	public void setFrom(LocalDateTime from) {
		this.from = from;
	}

	public LocalDateTime getUntil() {
		return until;
	}

	public void setUntil(LocalDateTime until) {
		this.until = until;
	}

	public Reservation getRezervation() {
		return reservation;
	}

	public Reservation getCommodity() {
		return commodity;
	}
	
}
