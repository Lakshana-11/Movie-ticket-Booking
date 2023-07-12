package com.cts.moviebookingapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.moviebookingapp.model.Ticket;
import com.cts.moviebookingapp.repository.TicketRepository;

@Service
public class TicketServiceImpl implements TicketService {
	
	@Autowired
	private TicketRepository ticketRepository;

	@Override
	public List<Ticket> getAllTranscations(int movieId) {
		List<Ticket> transactionList = ticketRepository.getAllTransactions(movieId);
		return transactionList;
	}

	@Override
	public boolean bookTicket(Ticket ticket) {
		ticketRepository.saveAndFlush(ticket);
		return true;
	}

	@Override
	public boolean deleteTicket(int movieId) {
		ticketRepository.deleteTicket(movieId);
		return true;
	}

}
