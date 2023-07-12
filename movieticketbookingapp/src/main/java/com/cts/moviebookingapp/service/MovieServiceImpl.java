package com.cts.moviebookingapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.moviebookingapp.exceptions.DuplicateMovieIdException;
import com.cts.moviebookingapp.model.Kafka;
import com.cts.moviebookingapp.model.Movie;
import com.cts.moviebookingapp.model.Notification;
import com.cts.moviebookingapp.repository.MovieRepository;
import com.cts.moviebookingapp.repository.NotificationRepository;

@Service
public class MovieServiceImpl implements MovieService {
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private NotificationRepository notificationRepository;
	
	@Autowired
	private Kafka kafka; 
	
	@Override
	public Movie addMovie(Movie movie) throws DuplicateMovieIdException {
		Optional<Movie> movieObj = movieRepository.findById(movie.getMovieId());
		if(movieObj.isPresent()) {
				throw new DuplicateMovieIdException();
		}
		return movieRepository.saveAndFlush(movie);
	}

	@Override
	public List<Movie> getAllMovies() {
		List<Movie> movieList = movieRepository.findAll();
		return movieList;
	}

	@Override
	public boolean deleteMovieById(int movieId) {
		try {
			movieRepository.deleteById(movieId);
		} catch(Exception exception) {
			return false;
		}
		return true;
	}

	@Override
	public Movie getMovieById(int movieId) {
		Optional<Movie> movie = movieRepository.findById(movieId);
		if(movie.isPresent()) {
			return movie.get();
		}
		return null;
	}

	@Override
	public boolean updateMovie(Movie movie) {
		Movie existingMovie = movieRepository.findById(movie.getMovieId()).get();
		if(existingMovie != null) {
			existingMovie.setSeatsAvailable(movie.getSeatsAvailable());
			existingMovie.setSeatsBooked(movie.getSeatsBooked());
			movieRepository.saveAndFlush(existingMovie);
			return true;
		}
		return false;
	}

	@Override
	public void updateTransactionList(Movie movie) {
		movieRepository.saveAndFlush(movie);
	}

	/* @Override
	public void addNotification(String message) {
		Notification notification = new Notification(message);
		notificationRepository.saveAndFlush(notification);
	} */
	
	public void addNotification(String message) {
		kafka.setKafkaTemplate(message);
	}

}
