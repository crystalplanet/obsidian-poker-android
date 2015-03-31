package com.crystalplanet.obsidianpoker.app.model;

import junit.framework.TestCase;

import java.util.*;

public class PlayersRankingTest extends TestCase {

    public void testSingleWinner() {
        Stack<Player> correctOrder = new Stack<Player>();
        correctOrder.push(playerWithCards(playerCards));
        correctOrder.push(playerWithCards(playerCards2));
        correctOrder.push(playerWithCards(bestPlayerCards));

        ArrayList<Player> players = new ArrayList<Player>(correctOrder);
        Collections.shuffle(players);

        PlayersRanking playersRanking = new PlayersRanking(commonCards, players);

        for (Set<Player> sortedPlayers : playersRanking) {
            assertEquals(1, sortedPlayers.size());
            assertTrue(sortedPlayers.contains(correctOrder.pop()));
        }
    }

    public void testTiedPlayers() {
        ArrayList<Player> players = new ArrayList<Player>();
        players.add(playerWithCards(playerCards));
        players.add(playerWithCards(bestPlayerCards));
        players.add(playerWithCards(bestPlayerCards2));
        players.add(playerWithCards(playerCards2));

        PlayersRanking playersRanking = new PlayersRanking(commonCards, players);

        Iterator<Set<Player>> it = playersRanking.iterator();

        Set<Player> winners = it.next();
        assertEquals(2, winners.size());
        assertTrue(winners.contains(players.get(1)));
        assertTrue(winners.contains(players.get(2)));

        winners = it.next();
        assertEquals(1, winners.size());
        assertTrue(winners.contains(players.get(3)));

        winners = it.next();
        assertEquals(1, winners.size());
        assertTrue(winners.contains(players.get(0)));

        assertFalse(it.hasNext());
    }

    public void testBestPlayers() {
        ArrayList<Player> players = new ArrayList<Player>();

        players.add(playerWithCards(playerCards2));
        players.add(playerWithCards(bestPlayerCards2));
        players.add(playerWithCards(bestPlayerCards));
        players.add(playerWithCards(playerCards));

        PlayersRanking playersRanking = new PlayersRanking(commonCards, players);

        Set<Player> best = new HashSet<Player>();
        best.add(players.get(1));
        best.add(players.get(2));

        assertEquals(best, playersRanking.first());
    }

    private Player playerWithCards(Set<Card> cards) {
        Player player = new Player(null, null, null);

        player.clear();
        for (Card card : cards) player.addCard(card);

        return player;
    }

    private HashSet<Card> bestPlayerCards = new HashSet<Card>() {{
        add(new Card(CardSuit.CUBS, CardRank.ACE));
        add(new Card(CardSuit.SPADES, CardRank.THREE));
    }};

    private HashSet<Card> bestPlayerCards2 = new HashSet<Card>() {{
        add(new Card(CardSuit.SPADES, CardRank.ACE));
        add(new Card(CardSuit.HEARTS, CardRank.THREE));
    }};

    private HashSet<Card> playerCards = new HashSet<Card>() {{
        add(new Card(CardSuit.CUBS, CardRank.QUEEN));
        add(new Card(CardSuit.DIAMONDS, CardRank.QUEEN));
    }};

    private HashSet<Card> playerCards2 = new HashSet<Card>() {{
        add(new Card(CardSuit.DIAMONDS, CardRank.KING));
        add(new Card(CardSuit.CUBS, CardRank.EIGHT));
    }};

    private HashSet<Card> commonCards = new HashSet<Card>() {{
        add(new Card(CardSuit.HEARTS, CardRank.KING));
        add(new Card(CardSuit.SPADES, CardRank.KING));
        add(new Card(CardSuit.HEARTS, CardRank.FIVE));
        add(new Card(CardSuit.SPADES, CardRank.FOUR));
        add(new Card(CardSuit.DIAMONDS, CardRank.TWO));
    }};
}
