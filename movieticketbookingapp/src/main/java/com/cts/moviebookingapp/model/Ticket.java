package com.cts.moviebookingapp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;

@Entity
public class Ticket {

	@Id
	@GeneratedValue
	private int transactionId;
	private int userName;
	private int movie_id_fk;
	private String movieName;
	private int totalSeats;
	private int availableSeats;
	private int seatsBooked;
	
	public int getTotalSeats() {
		return totalSeats;
	}
	public void setTotalSeats(int totalSeats) {
		this.totalSeats = totalSeats;
	}
	public int getAvailableSeats() {
		return availableSeats;
	}
	public void setAvailableSeats(int availableSeats) {
		this.availableSeats = availableSeats;
	}
	public int getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}
	public int getMovie_id_fk() {
		return movie_id_fk;
	}
	public void setMovie_id_fk(int movie_id_fk) {
		this.movie_id_fk = movie_id_fk;
	}
	public String getMovieName() {
		return movieName;
	}
	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}
	public int getSeatsBooked() {
		return seatsBooked;
	}
	public void setSeatsBooked(int seatsBooked) {
		this.seatsBooked = seatsBooked;
	}
	
	@PrePersist
	public void prePersist() {
		if(totalSeats == 0) {
			this.totalSeats = 100;
		}
	}
	public int getUserName() {
		return userName;
	}
	public void setUserName(int userName) {
		this.userName = userName;
	}
	
}
