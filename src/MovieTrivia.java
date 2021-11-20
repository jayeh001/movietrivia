import java.util.ArrayList;
import java.util.Arrays;

import file.MovieDB;
import movies.Actor;
import movies.Movie;

/**
 * Movie trivia class providing different methods for querying and updating a movie database.
 */
public class MovieTrivia {
	
	/**
	 * Create instance of movie database
	 */
	MovieDB movieDB = new MovieDB();
	
	
	public static void main(String[] args) {
		
		//create instance of movie trivia class
		MovieTrivia mt = new MovieTrivia();
		
		//setup movie trivia class
		mt.setUp("moviedata.txt", "movieratings.csv");
	}
	
	/**
	 * Sets up the Movie Trivia class
	 * @param movieData .txt file
	 * @param movieRatings .csv file
	 */
	public void setUp(String movieData, String movieRatings) {
		//load movie database files
		movieDB.setUp(movieData, movieRatings);
		
		//print all actors and movies
		printAllActors();
		printAllMovies();
		insertActor("bob", new String [] {"a","b"}, movieDB.getActorsInfo());
		printAllActors();
	}
	
	/**
	 * Prints a list of all actors and the movies they acted in.
	 */
	public void printAllActors () {
		System.out.println(movieDB.getActorsInfo());
	}
	
	/**
	 * Prints a list of all movies and their ratings.
	 */
	public void printAllMovies () {
		System.out.println(movieDB.getMoviesInfo());
	}
	
	
	// TODO add additional methods as specified in the instructions PDF
	
	/*
	 * This inserts an actor at end of arrayList
	 */
	public void insertActor (String actor, String [] movies, ArrayList <Actor> actorsInfo) {
		//trim and lowercase for actor
		actor = actor.trim().toLowerCase();
		//iterate through movies array and trim, lowercase each
		for (int n = 0; n < movies.length; n++) {
			movies[n] = movies[n].trim().toLowerCase();
		}
		
		for (int i = 0; i < actorsInfo.size(); i++) {
			Actor thisActor = actorsInfo.get(i);
			
			//if actor already exists, we update the movies casted
			if (thisActor.getName().equals(actor)){				
				for (int j = 0; j < movies.length; j++) {
					String thisMovie = movies[j];
					if (thisActor.getMoviesCast().contains(thisMovie) == false) {
						thisActor.getMoviesCast().add(thisMovie);
					}									
				}
			return;			
			}
		}
		//creates new actor if none was found in actorsInfo list
		Actor newActor = new Actor(actor);
		newActor.getMoviesCast().addAll(Arrays.asList(movies));
		actorsInfo.add(newActor);
		}
	/*
	 * inserts given ratings to movie
	 */
	public void insertRating (String movie, int [] ratings, ArrayList <Movie>moviesInfo) {
		// do nothing for incorrect ratings set up
		if (ratings == null || ratings.length != 2 || ratings[0] < 0 || ratings[1] < 0 || ratings[0] > 100 || ratings[1] > 100  ) {
			return;
		}
		//remove white space, remove upper case
		movie = movie.trim().toLowerCase();
		//checks to see if movie exists already and updates ratings
		for (int i = 0; i < moviesInfo.size(); i ++) {
			
			if (moviesInfo.get(i).getName().equals(movie)) {
				moviesInfo.get(i).setCriticRating(ratings[0]);
				moviesInfo.get(i).setAudienceRating(ratings[1]);
				return;
			}
		}
		//if movie does not already exist, create instance
		Movie newMovie = new Movie(movie, ratings[0], ratings[1]);
		moviesInfo.add(newMovie);
	}
	/*
	 * returns list of movies for given actor
	 */
	public ArrayList <String> selectWhereActorIs(String actor, ArrayList <Actor> actorsInfo){
		actor = actor.trim().toLowerCase();
		for (int i = 0; i < actorsInfo.size(); i ++) {
			if (actorsInfo.get(i).getName().equals(actor)) {
				return actorsInfo.get(i).getMoviesCast();				
			}
		}
		return new ArrayList <String>();
	}
	/*
	 * returns list of actors for given movie
	 */
	public ArrayList<String> selectWhereMovieIs(String movie, ArrayList <Actor> actorsInfo){
		movie = movie.trim().toLowerCase();
		ArrayList <String> actorsInMovie = new ArrayList<String>();
		for (int i = 0; i < actorsInfo.size(); i ++) {
			if (actorsInfo.get(i).getMoviesCast().contains(movie)){
				actorsInMovie.add(actorsInfo.get(i).getName());
				
			}
		}
		return actorsInMovie;
	}
	/*
	 * returns list of movies based upon filters we set in method
	 */
	public ArrayList<String> selectWhereRatingIs(char comparison, int targetRating, boolean isCritic, ArrayList <Movie> moviesInfo){
		ArrayList<String> moviesRefined = new ArrayList<String>();
		//checks to see if target rating is valid
		if (targetRating < 0 || targetRating > 100) {
			return moviesRefined;
		}
		//checks to see if comparison char is valid
		boolean checkComparison = false;
		char[] charArray = new char [] {'=', '<', '>'};
		for (char c: charArray) {
			if (c == comparison) {
				checkComparison = true;
			}
		}
		if (checkComparison == false) {
			return moviesRefined;
		}
		//compares rating for each movie in the movies info arraylist
		for (int i = 0; i < moviesInfo.size(); i ++) {
			if (comparison == '=') {
				if (isCritic == true) {

					if (moviesInfo.get(i).getCriticRating() == targetRating) {
						moviesRefined.add(moviesInfo.get(i).getName());
					}
				}else {
					if (moviesInfo.get(i).getAudienceRating() == targetRating) {

						moviesRefined.add(moviesInfo.get(i).getName());
					}
				}
			}
			//runs only if comparison is <
			if (comparison == '<') {
				if (isCritic == true) {
					if (moviesInfo.get(i).getCriticRating() < targetRating) {
						moviesRefined.add(moviesInfo.get(i).getName());
					}
				}
			
				if (isCritic == false) {
					if (moviesInfo.get(i).getAudienceRating() < targetRating) {
						moviesRefined.add(moviesInfo.get(i).getName());
					}		
				}
			}
			//runs if comparison is >
			if (comparison == '>') {
				if (isCritic == true) {
					if (moviesInfo.get(i).getCriticRating() > targetRating) {
						moviesRefined.add(moviesInfo.get(i).getName());
					}
				}
			
				if (isCritic == false) {
					if (moviesInfo.get(i).getAudienceRating() > targetRating) {
						moviesRefined.add(moviesInfo.get(i).getName());
					}		
				}
			}			
		}
		
		return moviesRefined;	
	}
	/*
	 *  returns list of all actors that have worked with the actor
	 */
	public ArrayList<String> getCoActors(String actor, ArrayList <Actor> actorsInfo){
		actor = actor.trim().toLowerCase();
		ArrayList <String> coActorList = new ArrayList <String> ();
		ArrayList<String> moviesWithActor = selectWhereActorIs(actor, actorsInfo);
		for (int i = 0; i < moviesWithActor.size(); i++) {
			coActorList.addAll(selectWhereMovieIs(moviesWithActor.get(i), actorsInfo));
			coActorList.remove(actor);
		}
		return coActorList;
	}
	/*
	 * returns list of movies where both actors were cast
	 */
	public ArrayList<String> getCommonMovie(String actor1, String actor2, ArrayList <Actor>actorsInfo){
		//remove caps and white space
		actor1 = actor1.trim().toLowerCase();
		actor2 = actor2.trim().toLowerCase();
		//compares if actor2 list of movies contains any from actor 1
		ArrayList<String> commonMovies = new ArrayList<String> ();
		ArrayList<String> actor1Movies = selectWhereActorIs (actor1, movieDB.getActorsInfo());
		ArrayList<String> actor2Movies = selectWhereActorIs (actor2, movieDB.getActorsInfo());
		for (int i = 0; i < actor1Movies.size(); i++) {
			if (actor2Movies.contains(actor1Movies.get(i))){
				commonMovies.add(actor1Movies.get(i));
			}
		}
		return commonMovies;
	}
	/*
	 * returns list of movies that got higher than 85 for both critics and the audience
	 */
	public ArrayList<String> goodMovies(ArrayList <Movie> moviesInfo){
		ArrayList<String> criticsRated = new ArrayList<String> ();
		ArrayList<String> userRated = new ArrayList<String> ();
		ArrayList<String> finalList = new ArrayList<String> ();
		//checks for all ratings >= 85 for user and critic scores
		// if moviesInfo == getMOviesInfo
		
		
		criticsRated = selectWhereRatingIs('>', 85, true, moviesInfo);
		criticsRated.addAll(selectWhereRatingIs('=', 85, true, moviesInfo));
		userRated = selectWhereRatingIs('>', 85, false, moviesInfo);
		userRated.addAll(selectWhereRatingIs('=', 85, false, moviesInfo));
		//criticsRated = selectWhereRatingIs('>', 85, true, movieDB.getMoviesInfo());
		//criticsRated.addAll(selectWhereRatingIs('=', 85, true, movieDB.getMoviesInfo()));
		//userRated = selectWhereRatingIs('>', 85, false, movieDB.getMoviesInfo());
		//userRated.addAll(selectWhereRatingIs('=', 85, false, movieDB.getMoviesInfo()));
		for (int i = 0; i < criticsRated.size(); i++) {
			if (userRated.contains(criticsRated.get(i))) {
				finalList.add(criticsRated.get(i));
			}
		}
		return finalList;
	}
	/*
	 * returns list of actors that performed in both movies provided
	 */
	public ArrayList<String> getCommonActors(String movie1, String movie2, ArrayList <Actor> actorsInfo){
		movie1 = movie1.trim().toLowerCase();
		movie2 = movie2.trim().toLowerCase();
		ArrayList<String> actorsList = new ArrayList<String>();
		//create two lists for each movie's actors
		ArrayList<String> movie1Actors = selectWhereMovieIs(movie1,actorsInfo);
		ArrayList<String> movie2Actors = selectWhereMovieIs(movie2,actorsInfo);
		//compares lists to see if actors appear in both
		for (int i = 0; i < movie1Actors.size(); i++) {
			if (movie2Actors.contains(movie1Actors.get(i))) {
				actorsList.add(movie1Actors.get(i));
			}
		}
		return actorsList;	
	}
	/*
	 * returns mean value of critic and audience ratings
	 */
	public static double [] getMean(ArrayList <Movie> moviesInfo) {
		double criticScore = 0;
		double audienceScore = 0;
		//iterate through list to add up all scores
		for (int i = 0; i < moviesInfo.size(); i++) {
			criticScore += moviesInfo.get(i).getCriticRating();

			audienceScore += moviesInfo.get(i).getAudienceRating();	
			
		}
		
		criticScore = criticScore/moviesInfo.size();
		
		audienceScore = audienceScore/moviesInfo.size();
		
		System.out.println("after dividng: " + audienceScore);
		double [] myScores = new double [] {criticScore,audienceScore};
		return myScores;
	}

	
	
}

	
	

