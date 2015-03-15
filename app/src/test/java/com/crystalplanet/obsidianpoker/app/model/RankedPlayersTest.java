package com.crystalplanet.obsidianpoker.app.model;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class RankedPlayersTest extends TestCase {

    public void testSingleWinner() {
        ArrayList<Player> players = new ArrayList<Player>();

        players.add(playerWithCards(playerCards2));
        players.add(playerWithCards(bestPlayerCards));
        players.add(playerWithCards(playerCards));

        RankedPlayers rankedPlayers = new RankedPlayers(commonCards, players);

        int count = 0;

        for (Set<Player> sortedPlayers : rankedPlayers) {
            ++count;
            assertEquals(1, sortedPlayers.size());
        }

        assertEquals(3, count);
    }

    public void testTiedPlayers() {
        ArrayList<Player> players = new ArrayList<Player>();

        players.add(playerWithCards(bestPlayerCards2));
        players.add(playerWithCards(bestPlayerCards));
        players.add(playerWithCards(playerCards));

        RankedPlayers rankedPlayers = new RankedPlayers(commonCards, players);

        int count = 0;

        for (Set<Player> sortedPlayers : rankedPlayers) {
            assertEquals(2 - count++, sortedPlayers.size());
        }

        assertEquals(2, count);
    }

    public void testBestPlayers() {
        ArrayList<Player> players = new ArrayList<Player>();

        players.add(playerWithCards(playerCards2));
        players.add(playerWithCards(bestPlayerCards2));
        players.add(playerWithCards(bestPlayerCards));
        players.add(playerWithCards(playerCards));

        RankedPlayers rankedPlayers = new RankedPlayers(commonCards, players);

        Set<Player> best = new HashSet<Player>();
        best.add(players.get(1));
        best.add(players.get(2));

        assertEquals(best, rankedPlayers.first());
    }

    private Player playerWithCards(Set<Card> cards) {
        Player player = new Player();

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
        add(new Card(CardSuit.SPADES, CardRank.KING));
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
