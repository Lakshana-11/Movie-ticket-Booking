package com.cts.moviebookingapp.service;

import java.util.List;

import com.cts.moviebookingapp.model.Ticket;

public interface TicketService {
	
	public List<Ticket> getAllTranscations(int movieId);
	
	public boolean bookTicket(Ticket ticket);
	
	public boolean deleteTicket(int movieId);
	

}
