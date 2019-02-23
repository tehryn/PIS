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
public class RezervatedCommodity extends Object {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="rezervation")
	private Rezervation rezervation;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="commodity")
	private Rezervation commodity;
	
	private LocalDateTime from;
	private LocalDateTime until;
	
	public RezervatedCommodity(Rezervation rezervation, Rezervation commodity, LocalDateTime from, LocalDateTime until) {
		super();
		this.rezervation = rezervation;
		this.commodity = commodity;
		this.from = from;
		this.until = until;
	}
	
	public RezervatedCommodity() {
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

	public Rezervation getRezervation() {
		return rezervation;
	}

	public Rezervation getCommodity() {
		return commodity;
	}
	
}
