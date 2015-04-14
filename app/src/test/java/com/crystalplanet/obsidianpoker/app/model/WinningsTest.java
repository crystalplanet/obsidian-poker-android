package com.crystalplanet.obsidianpoker.app.model;

import junit.framework.TestCase;

import java.util.*;

public class WinningsTest extends TestCase {

    public void testWinningsSingleWinner() {
        ArrayList<Player> players = new ArrayList<Player>() {{
            add(getPlayer(new Chips(50), bestCards));
            add(getPlayer(new Chips(50), cards));
            add(getPlayer(new Chips(50), cards2));
        }};

        Pot pot = new Pot();
        for (Player player : players)
            pot.takeBet(player, new Chips(100));

        Winnings winnings = new Winnings(players, commonCards, pot);

        assertEquals(new Chips(300), winnings.playersShare(players.get(0)));
        assertEquals(new Chips(0), winnings.playersShare(players.get(1)));
        assertEquals(new Chips(0), winnings.playersShare(players.get(2)));

        winnings.payOut();

        assertEquals(new Chips(350), players.get(0).chips());
        assertEquals(new Chips(50), players.get(1).chips());
        assertEquals(new Chips(50), players.get(2).chips());
    }

    public void testWinningsTie() {
        ArrayList<Player> players = new ArrayList<Player>() {{
            add(getPlayer(new Chips(50), bestCards));
            add(getPlayer(new Chips(50), bestCards2));
            add(getPlayer(new Chips(50), cards));
            add(getPlayer(new Chips(50), cards2));
        }};

        Pot pot = new Pot();
        for (Player player : players)
            pot.takeBet(player, new Chips(100));

        Winnings winnings = new Winnings(players, commonCards, pot);

        assertEquals(new Chips(200), winnings.playersShare(players.get(0)));
        assertEquals(new Chips(200), winnings.playersShare(players.get(1)));
        assertEquals(new Chips(0), winnings.playersShare(players.get(2)));
        assertEquals(new Chips(0), winnings.playersShare(players.get(3)));

        winnings.payOut();

        assertEquals(new Chips(250), players.get(0).chips());
        assertEquals(new Chips(250), players.get(1).chips());
        assertEquals(new Chips(50), players.get(2).chips());
        assertEquals(new Chips(50), players.get(3).chips());
    }

    public void testUnequalBets() {
        ArrayList<Player> players = new ArrayList<Player>() {{
            add(getPlayer(new Chips(50), bestCards));
            add(getPlayer(new Chips(50), cards));
            add(getPlayer(new Chips(50), cards2));
        }};

        Pot pot = new Pot();
        pot.takeBet(players.get(0), new Chips(100));
        pot.takeBet(players.get(1), new Chips(200));
        pot.takeBet(players.get(2), new Chips(200));

        Winnings winnings = new Winnings(players, commonCards, pot);

        assertEquals(new Chips(300), winnings.playersShare(players.get(0)));
        assertEquals(new Chips(200), winnings.playersShare(players.get(2)));
        assertEquals(new Chips(0), winnings.playersShare(players.get(1)));

        winnings.payOut();

        assertEquals(new Chips(350), players.get(0).chips());
        assertEquals(new Chips(50), players.get(1).chips());
        assertEquals(new Chips(250), players.get(2).chips());
    }

    public void testUnevenChips() {
        ArrayList<Player> players = new ArrayList<Player>() {{
            add(getPlayer(new Chips(50), bestCards));
            add(getPlayer(new Chips(50), bestCards2));
            add(getPlayer(new Chips(50), cards2));
        }};

        Pot pot = new Pot();
        pot.takeBet(players.get(0), new Chips(100));
        pot.takeBet(players.get(1), new Chips(100));
        pot.takeBet(players.get(2), new Chips(77));

        Winnings winnings = new Winnings(players, commonCards, pot);

        assertEquals(new Chips(138), winnings.playersShare(players.get(0)));
        assertEquals(new Chips(138), winnings.playersShare(players.get(1)));
        assertEquals(new Chips(0), winnings.playersShare(players.get(2)));

        winnings.payOut();

        assertEquals(new Chips(188), players.get(0).chips());
        assertEquals(new Chips(188), players.get(1).chips());
        assertEquals(new Chips(50), players.get(2).chips());

    }

    private Player getPlayer(Chips chips, Set<Card> cards) {
        Player player = new Player(null, chips, null);

        for (Card card : cards)
            player.drawCard(card);

        return player;
    }

    private HashSet<Card> bestCards = new HashSet<Card>() {{
        add(new Card(CardSuit.CUBS, CardRank.ACE));
        add(new Card(CardSuit.SPADES, CardRank.THREE));
    }};

    private HashSet<Card> bestCards2 = new HashSet<Card>() {{
        add(new Card(CardSuit.SPADES, CardRank.ACE));
        add(new Card(CardSuit.HEARTS, CardRank.THREE));
    }};

    private HashSet<Card> bestCards3 = new HashSet<Card>() {{
        add(new Card(CardSuit.DIAMONDS, CardRank.ACE));
        add(new Card(CardSuit.CUBS, CardRank.THREE));
    }};

    private HashSet<Card> cards = new HashSet<Card>() {{
        add(new Card(CardSuit.CUBS, CardRank.QUEEN));
        add(new Card(CardSuit.DIAMONDS, CardRank.QUEEN));
    }};

    private HashSet<Card> cards2 = new HashSet<Card>() {{
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
