package com.cts.moviebookingapp.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Movie {
	
	@Id
	private int movieId;
	private String movieName;
	private String theaterName;
	private int seatsAvailable;
	private int seatsBooked;
	
	@OneToMany(targetEntity = Ticket.class)
	private List<Ticket> transactions;
	
	public List<Ticket> getTransactions() {
		return transactions;
	}
	public void setTransactions(List<Ticket> transactions) {
		this.transactions = transactions;
	}
	public int getMovieId() {
		return movieId;
	}
	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}
	public String getMovieName() {
		return movieName;
	}
	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}
	public String getTheaterName() {
		return theaterName;
	}
	public void setTheaterName(String theaterName) {
		this.theaterName = theaterName;
	}
	public int getSeatsAvailable() {
		return seatsAvailable;
	}
	public void setSeatsAvailable(int seatsAvailable) {
		this.seatsAvailable = seatsAvailable;
	}
	public int getSeatsBooked() {
		return seatsBooked;
	}
	public void setSeatsBooked(int seatsBooked) {
		this.seatsBooked = seatsBooked;
	}
	
}
