package com.cts.moviebookingapp.repository;

import java.util.List;


import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cts.moviebookingapp.model.Ticket;

@Repository
@Transactional
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
	
	@Query(value = "select t from Ticket t where t.movie_id_fk = :movieId")
	public List<Ticket> getAllTransactions(int movieId);
	
	@Modifying
	@Query(value = "delete from Ticket where movie_id_fk = :movieId")
	public void deleteTicket(int movieId);
	

}
