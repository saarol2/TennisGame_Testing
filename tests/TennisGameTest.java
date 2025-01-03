import static org.junit.Assert.*;

import java.lang.reflect.Method;

import org.junit.Test;

public class TennisGameTest {
	
// Here is the format of the scores: "player1Score - player2Score"
// "love - love"
// "15 - 15"
// "30 - 30"
// "deuce"
// "15 - love", "love - 15"
// "30 - love", "love - 30"
// "40 - love", "love - 40"
// "30 - 15", "15 - 30"
// "40 - 15", "15 - 40"
// "player1 has advantage"
// "player2 has advantage"
// "player1 wins"
// "player2 wins"
	@Test
	public void testTennisGame_Start() {
		//Arrange
		TennisGame game = new TennisGame();
		//Act
		String score = game.getScore() ;
		// Assert
		assertEquals("Initial score incorrect", "love - love", score);		
	}
	
	@Test
	public void testTennisGame_EahcPlayerWin4Points_Score_Deuce() throws TennisGameException {
		//Arrange
		TennisGame game = new TennisGame();
		
		game.player1Scored();
		game.player1Scored();
		game.player1Scored();
		
		game.player2Scored();
		game.player2Scored();
		game.player2Scored();
		
		game.player1Scored();
		game.player2Scored();
		//Act
		String score = game.getScore() ;
		// Assert
		assertEquals("Tie score incorrect", "deuce", score);		
	}
	
	@Test (expected = TennisGameException.class)
	public void testTennisGame_Player1WinsPointAfterGameEnded_ResultsException() throws TennisGameException {
		//Arrange
		TennisGame game = new TennisGame();
		//Act
		game.player1Scored();
		game.player1Scored();
		game.player1Scored();
		game.player1Scored();
		//Act
		// This statement should cause an exception
		game.player1Scored();			
	}		
	
	@Test (expected = TennisGameException.class)
	public void testTennisGame_Player2WinsPointAfterGameEnded_ResultsException() throws TennisGameException {
		//Arrange
		TennisGame game = new TennisGame();
		//Act
		game.player2Scored();
		game.player2Scored();
		game.player2Scored();
		game.player2Scored();
		//Act
		// This statement should cause an exception
		game.player2Scored();			
	}	
	
	@Test
	public void testTennisGame_player1Wins() throws TennisGameException{
		TennisGame game = new TennisGame();
		//Act
		game.player1Scored();
		game.player1Scored();
		game.player1Scored();
		game.player1Scored();
		
		String score = game.getScore();
		assertEquals("player1 wins", score);
	}
	
	@Test
	public void testTennisGame_player2Wins() throws TennisGameException{
		TennisGame game = new TennisGame();
		//Act
		game.player2Scored();
		game.player2Scored();
		game.player2Scored();
		game.player2Scored();
		
		String score = game.getScore();
		assertEquals("player2 wins", score);
	}
	
	@Test
	public void testTennisGame_player1Advantage() throws TennisGameException{
		TennisGame game = new TennisGame();
		//Act
		game.player1Scored();
		game.player1Scored();
		game.player1Scored();
		
		game.player2Scored();
		game.player2Scored();
		game.player2Scored();
		
		game.player1Scored();
		
		String score = game.getScore();
		assertEquals("player1 has advantage", score);
	}
	
	@Test
	public void testTennisGame_player2Advantage() throws TennisGameException{
		TennisGame game = new TennisGame();
		//Act
		game.player1Scored();
		game.player1Scored();
		game.player1Scored();
		
		game.player2Scored();
		game.player2Scored();
		game.player2Scored();
		
		game.player2Scored();
		
		String score = game.getScore();
		assertEquals("player2 has advantage", score);
	}
	
	@Test
	public void testTennisGame_player1ScoresOnce() throws TennisGameException{
		TennisGame game = new TennisGame();
		//Act
		game.player1Scored();
		String score = game.getScore();
		assertEquals("love - 15", score);
	}
	
	@Test
	public void testTennisGame_player1ScoresTwice() throws TennisGameException{
		TennisGame game = new TennisGame();
		//Act
		game.player1Scored();
		game.player1Scored();
		String score = game.getScore();
		assertEquals("love - 30", score);
	}
	
	@Test
    public void testGetScoreDirectly() throws Exception {
        TennisGame game = new TennisGame();

        // Use reflection to access the private method
        Method getScoreMethod = TennisGame.class.getDeclaredMethod("getScore", int.class);
        getScoreMethod.setAccessible(true);

        // Test for points 0 (love)
        assertEquals("love", getScoreMethod.invoke(game, 0));

        // Test for points 1 (15)
        assertEquals("15", getScoreMethod.invoke(game, 1));

        // Test for points 2 (30)
        assertEquals("30", getScoreMethod.invoke(game, 2));

        // Test for points 3 (40)
        assertEquals("40", getScoreMethod.invoke(game, 3));

        // Test for points greater than 3 (still returns "40")
        assertEquals("40", getScoreMethod.invoke(game, 4));
        assertEquals("40", getScoreMethod.invoke(game, 5));
    }
	
}
