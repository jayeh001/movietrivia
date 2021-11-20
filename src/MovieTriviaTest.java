import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import file.MovieDB;


class MovieTriviaTest {
	
	//instance of movie trivia object to test
	MovieTrivia mt;
	//instance of movieDB object
	MovieDB movieDB = new MovieDB();
	
	@BeforeEach
	void setUp() throws Exception {
		//initialize movie trivia object
		mt = new MovieTrivia ();
		
		//set up movie trivia object
		mt.setUp("moviedata.txt", "movieratings.csv");
		
		//set up movieDB object
		movieDB.setUp("moviedata.txt", "movieratings.csv");
	}

	@Test
	void testSetUp() { 
		assertEquals(6, movieDB.getActorsInfo().size());
		assertEquals(7, movieDB.getMoviesInfo().size());
		
		assertEquals("meryl streep", movieDB.getActorsInfo().get(0).getName());
		assertEquals(3, movieDB.getActorsInfo().get(0).getMoviesCast().size());
		assertEquals("doubt", movieDB.getActorsInfo().get(0).getMoviesCast().get(0));
		
		assertEquals("doubt", movieDB.getMoviesInfo().get(0).getName());
		assertEquals(79, movieDB.getMoviesInfo().get(0).getCriticRating());
		assertEquals(78, movieDB.getMoviesInfo().get(0).getAudienceRating());
	}
	
	@Test
	void testInsertActor () {
		mt.insertActor("test1", new String [] {"testmovie1", "testmovie2"}, movieDB.getActorsInfo());
		assertEquals(7, movieDB.getActorsInfo().size());	
		assertEquals("test1", movieDB.getActorsInfo().get(movieDB.getActorsInfo().size() - 1).getName());
		assertEquals(2, movieDB.getActorsInfo().get(movieDB.getActorsInfo().size() - 1).getMoviesCast().size());
		assertEquals("testmovie1", movieDB.getActorsInfo().get(movieDB.getActorsInfo().size() - 1).getMoviesCast().get(0));
		

		mt.insertActor("Kenny", new String [] {"FURY"}, movieDB.getActorsInfo());
		assertEquals(8,movieDB.getActorsInfo().size());
		assertEquals("kenny", movieDB.getActorsInfo().get(movieDB.getActorsInfo().size() - 1).getName());
		
		mt.insertActor("Brad PITT", new String [] {"FURY"}, movieDB.getActorsInfo());
		assertEquals("fury", movieDB.getActorsInfo().get(movieDB.getActorsInfo().size() - 3).getMoviesCast().get(2));
		
		mt.insertActor("merYL STREEP", new String [] {"TESTING"}, movieDB.getActorsInfo());
		assertEquals("testing", movieDB.getActorsInfo().get(0).getMoviesCast().get(3));
		assertEquals(8,movieDB.getActorsInfo().size());
	}
	
	@Test
	void testInsertRating () {
		mt.insertRating("testmovie", new int [] {79, 80}, movieDB.getMoviesInfo());
		assertEquals(8, movieDB.getMoviesInfo().size());	
		assertEquals("testmovie", movieDB.getMoviesInfo().get(movieDB.getMoviesInfo().size() - 1).getName());
		assertEquals(79, movieDB.getMoviesInfo().get(movieDB.getMoviesInfo().size() - 1).getCriticRating());
		assertEquals(80, movieDB.getMoviesInfo().get(movieDB.getMoviesInfo().size() - 1).getAudienceRating());
		

		mt.insertRating("THE DARK KNIGHT", new int [] {10, 80}, movieDB.getMoviesInfo());
		assertEquals(9, movieDB.getMoviesInfo().size());
		assertEquals("the dark knight", movieDB.getMoviesInfo().get(movieDB.getMoviesInfo().size() - 1).getName());
		assertEquals(10, movieDB.getMoviesInfo().get(movieDB.getMoviesInfo().size() - 1).getCriticRating());
		
		mt.insertRating("I am lEGEND", new int [] {45, 90}, movieDB.getMoviesInfo());
		assertEquals(45, movieDB.getMoviesInfo().get(movieDB.getMoviesInfo().size() - 1).getCriticRating());
		assertEquals(90, movieDB.getMoviesInfo().get(movieDB.getMoviesInfo().size() -1).getAudienceRating());
	
		mt.insertRating("DOUBT", new int [] {90, 50}, movieDB.getMoviesInfo());
		assertEquals(10, movieDB.getMoviesInfo().size());
		assertEquals(90, movieDB.getMoviesInfo().get(0).getCriticRating());
	}
	
	@Test
	void testSelectWhereActorIs () {
		assertEquals(3, mt.selectWhereActorIs("meryl streep", movieDB.getActorsInfo()).size());
		assertEquals("doubt", mt.selectWhereActorIs("meryl streep", movieDB.getActorsInfo()).get(0));
		
		assertEquals("fight club",mt.selectWhereActorIs("brad PITT", movieDB.getActorsInfo()).get(1));
		assertEquals("seven",mt.selectWhereActorIs("brad PITT", movieDB.getActorsInfo()).get(0));
		
		assertEquals(Collections.emptyList(),mt.selectWhereActorIs("bob fish", movieDB.getActorsInfo()));
		assertEquals(Collections.emptyList(),mt.selectWhereActorIs("sam the dog", movieDB.getActorsInfo()));
		
		assertEquals("cast away",mt.selectWhereActorIs("TOM HANKS", movieDB.getActorsInfo()).get(2));
		assertEquals("the post",mt.selectWhereActorIs("TOM HANKS", movieDB.getActorsInfo()).get(0));

	}
	
	@Test
	void testSelectWhereMovieIs () {
		assertEquals(2, mt.selectWhereMovieIs("doubt", movieDB.getActorsInfo()).size());
		assertEquals(true, mt.selectWhereMovieIs("doubt", movieDB.getActorsInfo()).contains("meryl streep"));
		assertEquals(true, mt.selectWhereMovieIs("doubt", movieDB.getActorsInfo()).contains("amy adams"));
		
		mt.insertActor("Kenny", new String [] {"FURY"}, movieDB.getActorsInfo());
		assertEquals(1, mt.selectWhereMovieIs("fury", movieDB.getActorsInfo()).size());
		assertEquals(true, mt.selectWhereMovieIs("fury", movieDB.getActorsInfo()).contains("kenny"));
		
		assertEquals("tom hanks", mt.selectWhereMovieIs("the post", movieDB.getActorsInfo()).get(1));
		assertEquals(2, mt.selectWhereMovieIs("the post", movieDB.getActorsInfo()).size());
		
		assertEquals(0, mt.selectWhereMovieIs("city of god", movieDB.getActorsInfo()).size());
		mt.insertActor("ZELDA", new String [] {"city of god"}, movieDB.getActorsInfo());
		assertEquals(1, mt.selectWhereMovieIs("city of god", movieDB.getActorsInfo()).size());
		assertEquals(true, mt.selectWhereMovieIs("city of god", movieDB.getActorsInfo()).contains("zelda"));
	}
	
	@Test
	void testSelectWhereRatingIs () {
		assertEquals(6, mt.selectWhereRatingIs('>', 0, true, movieDB.getMoviesInfo()).size());
		assertEquals(0, mt.selectWhereRatingIs('=', 65, false, movieDB.getMoviesInfo()).size());
		assertEquals(2, mt.selectWhereRatingIs('<', 30, true, movieDB.getMoviesInfo()).size());
		
		assertEquals(Collections.emptyList(), mt.selectWhereRatingIs('>', 999, true, movieDB.getMoviesInfo()));
		assertEquals(Collections.emptyList(), mt.selectWhereRatingIs('=', -1, true, movieDB.getMoviesInfo()));
		assertEquals(Collections.emptyList(), mt.selectWhereRatingIs('<', -2, true, movieDB.getMoviesInfo()));
		
		assertEquals("arrival", mt.selectWhereRatingIs('>', 90, true, movieDB.getMoviesInfo()).get(0));
		assertEquals("et", mt.selectWhereRatingIs('=', 85, true, movieDB.getMoviesInfo()).get(0));
		assertEquals("popeye", mt.selectWhereRatingIs('<', 1, true, movieDB.getMoviesInfo()).get(0));
		
		assertEquals("seven", mt.selectWhereRatingIs('<', 30, false, movieDB.getMoviesInfo()).get(0));
		assertEquals("arrival", mt.selectWhereRatingIs('=', 82, false, movieDB.getMoviesInfo()).get(0));
		assertEquals("rocky ii", mt.selectWhereRatingIs('>', 90, false, movieDB.getMoviesInfo()).get(0));
	}
	
	@Test
	void testGetCoActors () {
		assertEquals(2, mt.getCoActors("meryl streep", movieDB.getActorsInfo()).size());
		assertTrue(mt.getCoActors("meryl streep", movieDB.getActorsInfo()).contains("tom hanks"));
		assertTrue(mt.getCoActors("meryl streep", movieDB.getActorsInfo()).contains("amy adams"));
		
		assertEquals(0, mt.getCoActors("brandon krakowsky", movieDB.getActorsInfo()).size());
		assertEquals(0, mt.getCoActors("travis scott cactus", movieDB.getActorsInfo()).size());
		assertEquals(Collections.emptyList(), mt.getCoActors("bobby the fish", movieDB.getActorsInfo()));
		
		assertEquals(1, mt.getCoActors("tom hanks", movieDB.getActorsInfo()).size());
		assertTrue(mt.getCoActors("tom hanks", movieDB.getActorsInfo()).contains("meryl streep"));
		
		assertEquals(1, mt.getCoActors("amy adams", movieDB.getActorsInfo()).size());
		assertTrue(mt.getCoActors("amy adams", movieDB.getActorsInfo()).contains("meryl streep"));

	}
	
	@Test
	void testGetCommonMovie () {
		assertEquals(1, mt.getCommonMovie("meryl streep", "tom hanks", movieDB.getActorsInfo()).size());
		assertTrue(mt.getCommonMovie("meryl streep", "tom hanks", movieDB.getActorsInfo()).contains("the post"));
		
		assertEquals(1, mt.getCommonMovie("meryl streep", "amy adams", movieDB.getActorsInfo()).size());
		assertEquals("doubt", mt.getCommonMovie("meryl streep", "amy adams", movieDB.getActorsInfo()).get(0));
		
		assertEquals(Collections.emptyList(), mt.getCommonMovie("brad pitt", "bobby the fish", movieDB.getActorsInfo()));
		assertEquals(Collections.emptyList(), mt.getCommonMovie("brad pitt", "GOD kanye west", movieDB.getActorsInfo()));
		assertEquals(Collections.emptyList(), mt.getCommonMovie("brandon krakowsky", "robin williams", movieDB.getActorsInfo()));
		
		assertEquals(0, mt.getCommonMovie("brad pitt", "amy adams", movieDB.getActorsInfo()).size());
		assertEquals(Collections.emptyList(), mt.getCommonMovie("brad pitt", "amy adams", movieDB.getActorsInfo()));
	}
	
	@Test
	void testGoodMovies () {
		assertEquals(3, mt.goodMovies(movieDB.getMoviesInfo()).size());
		assertTrue(mt.goodMovies(movieDB.getMoviesInfo()).contains("jaws"));
		
		
		mt.insertRating("spiderman", new int [] {98, 99}, movieDB.getMoviesInfo());
		assertTrue(mt.goodMovies(movieDB.getMoviesInfo()).contains("spiderman"));
		
		mt.insertRating("bob fish", new int [] {1, 1}, movieDB.getMoviesInfo());
		assertFalse(mt.goodMovies(movieDB.getMoviesInfo()).contains("bob fish"));
		
		assertFalse(mt.goodMovies(movieDB.getMoviesInfo()).contains("the post"));
		assertTrue(mt.goodMovies(movieDB.getMoviesInfo()).contains("et"));
	}
	
	@Test
	void testGetCommonActors () {
		assertEquals(1, mt.getCommonActors("doubt", "the post", movieDB.getActorsInfo()).size());
		assertTrue(mt.getCommonActors("doubt", "the post", movieDB.getActorsInfo()).contains("meryl streep"));
		
		assertTrue(mt.getCommonActors("the post", "cast away", movieDB.getActorsInfo()).contains("tom hanks"));
		assertEquals(1, mt.getCommonActors("cast away", "the post", movieDB.getActorsInfo()).size());
		
		assertEquals(2, mt.getCommonActors("doubt", "doubt", movieDB.getActorsInfo()).size());
		assertTrue(mt.getCommonActors("doubt", "doubt", movieDB.getActorsInfo()).contains("amy adams"));
		
		assertEquals(0, mt.getCommonActors("seven", "popeye", movieDB.getActorsInfo()).size());
		assertEquals(0, mt.getCommonActors("seven", "cast away", movieDB.getActorsInfo()).size());
	}
	
	@Test
	void testGetMean () {
		assertEquals(65.714, mt.getMean(movieDB.getMoviesInfo())[1], .01);
		assertEquals(67.857, mt.getMean(movieDB.getMoviesInfo())[0], .01);
		
		mt.insertRating("spiderman", new int [] {98, 99}, movieDB.getMoviesInfo());
		assertEquals(69.875, mt.getMean(movieDB.getMoviesInfo())[1], .01);
		
		mt.insertRating("spiderman2", new int [] {2, 2}, movieDB.getMoviesInfo());
		assertEquals(62.333, mt.getMean(movieDB.getMoviesInfo())[1], .01);
		assertEquals(63.888, mt.getMean(movieDB.getMoviesInfo())[0], .01);
		
		mt.insertRating("spiderman66", new int [] {51, 4}, movieDB.getMoviesInfo());
		assertEquals(56.500, mt.getMean(movieDB.getMoviesInfo())[1], .01);
		assertEquals(62.600, mt.getMean(movieDB.getMoviesInfo())[0], .01);
	}
}
