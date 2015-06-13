package com.crystalplanet.obsidianpoker.app;

import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import com.crystalplanet.obsidianpoker.app.util.Coord;
import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import com.crystalplanet.obsidianpoker.game.GameObserver;
import com.crystalplanet.obsidianpoker.game.Player;
import com.crystalplanet.obsidianpoker.game.PlayerHandler;
import com.crystalplanet.obsidianpoker.game.PokerRound;
import com.crystalplanet.obsidianpoker.game.card.Card;
import com.crystalplanet.obsidianpoker.game.card.CardRank;
import com.crystalplanet.obsidianpoker.game.card.CardSuit;
import com.crystalplanet.obsidianpoker.game.chips.Chips;
import com.crystalplanet.obsidianpoker.service.*;
import com.crystalplanet.obsidianpoker.view.ImageView;
import com.crystalplanet.obsidianpoker.view.ScaledLayout;
import com.crystalplanet.obsidianpoker.view.TextView;

import java.util.ArrayList;
import java.util.List;

public class PokerGameActivity extends Activity implements PlayerHandler, GameObserver {

    private PlayerManager pm = new PlayerManager();

    private PokerGame game;

    private PokerRound round;

    private Coord down;

    private Coord up;

    private boolean setUp = true;

    private Player handledPlayer;

    private boolean[] showCredit = new boolean[11];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        setContentView(R.layout.activity_poker_game);

        TextView bigButton = (TextView) findViewById(R.id.button_0);
        bigButton.setText(getString(R.string.action_choose_seat));
        bigButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void getNextAction(Player player) {
        NextPlayerDialog dialog = new NextPlayerDialog(this, player);
        dialog.setTitle(player + getString(R.string.title_dialog_next_player));
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        dialog.getWindow().getDecorView().setSystemUiVisibility(getWindow().getDecorView().getSystemUiVisibility());
        dialog.show();
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
    }

    public void showPlayerUI(Player player) {
        TextView button;

        button = (TextView) findViewById(R.id.button_1);
        button.setVisibility(View.VISIBLE);
        button.setText(getString(R.string.action_fold));

        button = (TextView) findViewById(R.id.button_2);
        button.setVisibility(player.canCheck() || player.canCall() ? View.VISIBLE : View.GONE);
        button.setText(getString(player.canCheck() ? R.string.action_check : R.string.action_call));

        button = (TextView) findViewById(R.id.button_3);
        button.setVisibility(player.canBet() || player.canRaise() ? View.VISIBLE : View.GONE);
        button.setText(getString(player.canBet() ? R.string.action_bet : R.string.action_raise));

        button = (TextView) findViewById(R.id.button_4);
        button.setVisibility(player.canAllIn() ? View.VISIBLE : View.GONE);
        button.setText(getString(R.string.action_all_in));

        int seat = pm.getSeat(player);

        getHiddenCards(seat).setVisibility(View.GONE);
        List<ImageView> cards = getCards(seat);

        List<Card> playerCards = new ArrayList<Card>(player.cards());

        cards.get(0).setImage(getCardDrawable(playerCards.get(0)));
        cards.get(1).setImage(getCardDrawable(playerCards.get(1)));

        cards.get(0).setVisibility(View.VISIBLE);
        cards.get(1).setVisibility(View.VISIBLE);

        handledPlayer = player;
    }

    public void hidePlayerUI(Player player) {
        findViewById(R.id.button_1).setVisibility(View.GONE);
        findViewById(R.id.button_2).setVisibility(View.GONE);
        findViewById(R.id.button_3).setVisibility(View.GONE);
        findViewById(R.id.button_4).setVisibility(View.GONE);

        int seat = pm.getSeat(player);

        for (ImageView card : getCards(seat))
            card.setVisibility(View.GONE);

        getHiddenCards(seat).setVisibility(View.VISIBLE);

        handledPlayer = null;
    }

    @Override
    public void update() {
        for (int i=0; i<11; ++i) {
            if (pm.getPlayer(i) != null) {
                getInfo(i).setText(showCredit[i] ? pm.getPlayer(i).chips().toString() : pm.getPlayer(i).toString());
                getInfo(i).setVisibility(View.VISIBLE);
                getSeat(i).setImage(
                        round != null && round.currentPlayer() == pm.getPlayer(i) ? R.drawable.seat_current : R.drawable.seat_taken
                );
                getDealerChip(i).setVisibility(
                        round != null && round.dealer() == pm.getPlayer(i) ? View.VISIBLE : View.GONE
                );

                getHiddenCards(i).setVisibility(View.GONE);
                getChips(i).setVisibility(View.GONE);
                getChipsText(i).setVisibility(View.GONE);

                if (!pm.getPlayer(i).isFolded()) {
                    if (round.currentStage() != null) getHiddenCards(i).setVisibility(View.VISIBLE);

                    List<ImageView> cards = getCards(i);
                    List<Card> playerCards = new ArrayList<Card>(pm.getPlayer(i).cards());

                    for (int j=0; j<2; ++j) {
                        cards.get(j).setVisibility(View.GONE);
                        if (round.currentStage() == null) {
                            cards.get(j).setImage(getCardDrawable(playerCards.get(j)));
                            cards.get(j).setVisibility(View.VISIBLE);
                        }
                    }

                    if (
                        round.currentStage() == null &&
                        round.playersWinnings(pm.getPlayer(i)) != null &&
                        round.playersWinnings(pm.getPlayer(i)).compareTo(new Chips(0)) > 0
                    )  {
                        getChips(i).setImage(getChipsDrawable(round.playersWinnings(pm.getPlayer(i))));
                        getChips(i).setVisibility(View.VISIBLE);

                        getChipsText(i).setText(round.playersWinnings(pm.getPlayer(i)).toString());
                        getChipsText(i).setVisibility(View.VISIBLE);
                    }
                }

                if (round != null && round.playersBet(pm.getPlayer(i)).compareTo(new Chips(0)) > 0) {
                    getChips(i).setImage(getChipsDrawable(round.playersBet(pm.getPlayer(i))));
                    getChips(i).setVisibility(View.VISIBLE);

                    getChipsText(i).setText(round.playersBet(pm.getPlayer(i)).toString());
                    getChipsText(i).setVisibility(View.VISIBLE);
                }
            }
        }

        for (int i=0; i<5; ++i) {
            findViewById(getResources().getIdentifier(
                    "shared_card_" + i,
                    "id",
                    getApplication().getPackageName()
            )).setVisibility(View.GONE);
        }

        if (round != null) {
            TextView pot = (TextView) findViewById(R.id.pot_text);
            pot.setVisibility(View.VISIBLE);
            pot.setText("Pot: " + round.mainPotSize());

            List<Card> cards = round.commonCards();
            for (int i = 0; i < cards.size(); ++i) {
                if (cards.get(i) != null) {
                    ImageView card = (ImageView) findViewById(getResources().getIdentifier(
                            "shared_card_" + i,
                            "id",
                            getApplication().getPackageName()
                    ));

                    card.setImage(getCardDrawable(cards.get(i)));
                    card.setVisibility(View.VISIBLE);
                }
            }

            findViewById(R.id.button_0).setVisibility(round.currentStage() == null ? View.VISIBLE : View.GONE);
        }
    }

    public void startGame() {
        GameObserverManager om = new GameObserverManager();
        GameConfiguration config = new GameConfiguration();

        om.add(this);

        config.setSmallBlind(new Chips(25));

        game = new PokerGame(
            new PokerRoundFactory(),
            config,
            new DeckProvider(),
            pm,
            om
        );

        setUp = false;

        ((TextView) findViewById(R.id.button_0)).setText(getString(R.string.action_deal));
    }

    public void newRound() {
        round = game.nextRound();
        round.run();
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                down = new Coord(e.getX(), e.getY());
                return true;

            case MotionEvent.ACTION_UP:
                up = new Coord(e.getX(), e.getY());
                if (Math.abs(down.x - up.x) < 10 && Math.abs(down.y - up.y) < 10) {
                    for (int i=0; i<11; ++i) {
                        ImageView seat = getSeat(i);
                        if (viewContains(seat, e.getX(), e.getY())) {
                            if (setUp && pm.getPlayer(i) == null) {
                                NewPlayerDialog dialog = new NewPlayerDialog(this, pm, i);
                                dialog.setTitle(R.string.title_dialog_new_player);
                                dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
                                dialog.getWindow().getDecorView().setSystemUiVisibility(getWindow().getDecorView().getSystemUiVisibility());
                                dialog.show();
                                dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
                                return true;
                            }

                            if (pm.getPlayer(i) != null) {
                                if (showCredit[i]) {
                                    getInfo(i).setText(pm.getPlayer(i).toString());
                                    showCredit[i] = false;
                                    return true;
                                }

                                getInfo(i).setText(pm.getPlayer(i).chips().toString());
                                showCredit[i] = true;
                                return true;
                            }
                        }
                    }

                    if (handledPlayer != null) {
                        if (viewContains(findViewById(R.id.button_1), e.getX(), e.getY())) {
                            Player player = handledPlayer;
                            hidePlayerUI(player);

                            try {
                                player.fold();
                                return true;
                            } catch (RuntimeException err) {
                                showPlayerUI(player);
                                Toast.makeText(
                                    getApplicationContext(),
                                    err.getMessage(),
                                    Toast.LENGTH_LONG
                                ).show();
                            }
                        }

                        if (viewContains(findViewById(R.id.button_2), e.getX(), e.getY())) {
                            Player player = handledPlayer;
                            hidePlayerUI(player);

                            try {
                                if (player.canCheck()) {
                                    player.check();
                                    return true;
                                }

                                if (player.canCall()) {
                                    player.call();
                                    return true;
                                }
                            } catch (RuntimeException err) {
                                showPlayerUI(player);
                                Toast.makeText(
                                    getApplicationContext(),
                                    err.getMessage(),
                                    Toast.LENGTH_LONG
                                ).show();
                            }
                        }

                        if (viewContains(findViewById(R.id.button_3), e.getX(), e.getY())) {
                            BetDialog dialog = new BetDialog(this, handledPlayer, round);
                            dialog.setTitle(getString(handledPlayer.canBet() ? R.string.title_dialog_bet : R.string.title_dialog_raise));
                            dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
                            dialog.getWindow().getDecorView().setSystemUiVisibility(getWindow().getDecorView().getSystemUiVisibility());
                            dialog.show();
                            dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
                            return true;
                        }

                        if (viewContains(findViewById(R.id.button_4), e.getX(), e.getY())) {
                            Player player = handledPlayer;
                            hidePlayerUI(player);

                            try {
                                if (player.canAllIn()) {
                                    player.allIn();
                                    return true;
                                }
                            } catch (RuntimeException err) {
                                showPlayerUI(player);
                                Toast.makeText(
                                        getApplicationContext(),
                                        err.getMessage(),
                                        Toast.LENGTH_LONG
                                ).show();
                            }
                        }
                    }

                    if (!setUp && viewContains(findViewById(R.id.button_0), e.getX(), e.getY())) {
                        // gets triggered even when invisible
                        findViewById(R.id.button_0).setVisibility(View.GONE);
                        newRound();
                    }
                }
                return true;
            default:
                return true;
        }
    }

    private ImageView getSeat(int player) {
        return (ImageView) findViewById(getResources().getIdentifier(
                "player_" + player + "_seat",
                "id",
                getApplication().getPackageName()
        ));
    }

    private TextView getInfo(int player) {
        return (TextView) findViewById(getResources().getIdentifier(
            "player_" + player + "_text",
            "id",
            getApplication().getPackageName()
        ));
    }

    private ImageView getDealerChip(int player) {
        return (ImageView) findViewById(getResources().getIdentifier(
            "player_" + player + "_dealer",
            "id",
            getApplication().getPackageName()
        ));
    }

    private ImageView getChips(int player) {
        return (ImageView) findViewById(getResources().getIdentifier(
            "player_" + player + "_chips",
            "id",
            getApplication().getPackageName()
        ));
    }

    private TextView getChipsText(int player) {
        return (TextView) findViewById(getResources().getIdentifier(
            "player_" + player + "_chips_text",
            "id",
            getApplication().getPackageName()
        ));
    }

    private List<ImageView> getCards(int player) {
        ArrayList<ImageView> cards = new ArrayList<ImageView>();

        for (int i = 1; i < 3; ++i)
            cards.add((ImageView) findViewById(getResources().getIdentifier(
                "player_" + player + "_card_" + i,
                "id",
                getApplication().getPackageName()
            )));

        return cards;
    }

    private ScaledLayout getHiddenCards(int player) {
        return (ScaledLayout) findViewById(getResources().getIdentifier(
                "player_" + player + "_cards_hidden",
                "id",
                getApplication().getPackageName()
        ));
    }

    private boolean viewContains(View view, float x, float y) {
        int[] position = new int[2];
        view.getLocationOnScreen(position);

        return x > position[0] && x < (position[0] + view.getWidth()) &&
            y > position[1] && y < (position[1] + view.getHeight());
    }

    private int getCardDrawable(Card card) {
        if (card.suit().equals(CardSuit.SPADES)) {
            if (card.rank().equals(CardRank.ACE)) return R.drawable.card_1;
            if (card.rank().equals(CardRank.TWO)) return R.drawable.card_2;
            if (card.rank().equals(CardRank.THREE)) return R.drawable.card_3;
            if (card.rank().equals(CardRank.FOUR)) return R.drawable.card_4;
            if (card.rank().equals(CardRank.FIVE)) return R.drawable.card_5;
            if (card.rank().equals(CardRank.SIX)) return R.drawable.card_6;
            if (card.rank().equals(CardRank.SEVEN)) return R.drawable.card_7;
            if (card.rank().equals(CardRank.EIGHT)) return R.drawable.card_8;
            if (card.rank().equals(CardRank.NINE)) return R.drawable.card_9;
            if (card.rank().equals(CardRank.TEN)) return R.drawable.card_10;
            if (card.rank().equals(CardRank.JACK)) return R.drawable.card_11;
            if (card.rank().equals(CardRank.QUEEN)) return R.drawable.card_12;
            if (card.rank().equals(CardRank.KING)) return R.drawable.card_13;
        }

        if (card.suit().equals(CardSuit.HEARTS)) {
            if (card.rank().equals(CardRank.ACE)) return R.drawable.card_14;
            if (card.rank().equals(CardRank.TWO)) return R.drawable.card_15;
            if (card.rank().equals(CardRank.THREE)) return R.drawable.card_16;
            if (card.rank().equals(CardRank.FOUR)) return R.drawable.card_17;
            if (card.rank().equals(CardRank.FIVE)) return R.drawable.card_18;
            if (card.rank().equals(CardRank.SIX)) return R.drawable.card_19;
            if (card.rank().equals(CardRank.SEVEN)) return R.drawable.card_20;
            if (card.rank().equals(CardRank.EIGHT)) return R.drawable.card_21;
            if (card.rank().equals(CardRank.NINE)) return R.drawable.card_22;
            if (card.rank().equals(CardRank.TEN)) return R.drawable.card_23;
            if (card.rank().equals(CardRank.JACK)) return R.drawable.card_24;
            if (card.rank().equals(CardRank.QUEEN)) return R.drawable.card_25;
            if (card.rank().equals(CardRank.KING)) return R.drawable.card_26;
        }

        if (card.suit().equals(CardSuit.DIAMONDS)) {
            if (card.rank().equals(CardRank.ACE)) return R.drawable.card_27;
            if (card.rank().equals(CardRank.TWO)) return R.drawable.card_28;
            if (card.rank().equals(CardRank.THREE)) return R.drawable.card_29;
            if (card.rank().equals(CardRank.FOUR)) return R.drawable.card_30;
            if (card.rank().equals(CardRank.FIVE)) return R.drawable.card_31;
            if (card.rank().equals(CardRank.SIX)) return R.drawable.card_32;
            if (card.rank().equals(CardRank.SEVEN)) return R.drawable.card_33;
            if (card.rank().equals(CardRank.EIGHT)) return R.drawable.card_34;
            if (card.rank().equals(CardRank.NINE)) return R.drawable.card_35;
            if (card.rank().equals(CardRank.TEN)) return R.drawable.card_36;
            if (card.rank().equals(CardRank.JACK)) return R.drawable.card_37;
            if (card.rank().equals(CardRank.QUEEN)) return R.drawable.card_38;
            if (card.rank().equals(CardRank.KING)) return R.drawable.card_39;
        }

        if (card.suit().equals(CardSuit.CUBS)) {
            if (card.rank().equals(CardRank.ACE)) return R.drawable.card_40;
            if (card.rank().equals(CardRank.TWO)) return R.drawable.card_41;
            if (card.rank().equals(CardRank.THREE)) return R.drawable.card_42;
            if (card.rank().equals(CardRank.FOUR)) return R.drawable.card_43;
            if (card.rank().equals(CardRank.FIVE)) return R.drawable.card_44;
            if (card.rank().equals(CardRank.SIX)) return R.drawable.card_45;
            if (card.rank().equals(CardRank.SEVEN)) return R.drawable.card_46;
            if (card.rank().equals(CardRank.EIGHT)) return R.drawable.card_47;
            if (card.rank().equals(CardRank.NINE)) return R.drawable.card_48;
            if (card.rank().equals(CardRank.TEN)) return R.drawable.card_49;
            if (card.rank().equals(CardRank.JACK)) return R.drawable.card_50;
            if (card.rank().equals(CardRank.QUEEN)) return R.drawable.card_51;
            if (card.rank().equals(CardRank.KING)) return R.drawable.card_52;
        }

        return 0;
    }

    private int getChipsDrawable(Chips chips) {
        if (chips.compareTo(new Chips(5)) < 0) return R.drawable.chips_1;
        if (chips.compareTo(new Chips(25)) < 0) return R.drawable.chips_5;
        if (chips.compareTo(new Chips(100)) < 0) return R.drawable.chips_25;
        if (chips.compareTo(new Chips(500)) < 0) return R.drawable.chips_100;
        if (chips.compareTo(new Chips(1000)) < 0) return R.drawable.chips_500;
        if (chips.compareTo(new Chips(5000)) < 0) return R.drawable.chips_1k;
        if (chips.compareTo(new Chips(25000)) < 0) return R.drawable.chips_5k;
        if (chips.compareTo(new Chips(100000)) < 0) return R.drawable.chips_25k;
        if (chips.compareTo(new Chips(500000)) < 0) return R.drawable.chips_100k;
        if (chips.compareTo(new Chips(1000000)) < 0) return R.drawable.chips_500k;
        if (chips.compareTo(new Chips(5000000)) < 0) return R.drawable.chips_1m;
        if (chips.compareTo(new Chips(25000000)) < 0) return R.drawable.chips_5m;
        if (chips.compareTo(new Chips(100000000)) < 0) return R.drawable.chips_25m;
        if (chips.compareTo(new Chips(500000000)) < 0) return R.drawable.chips_100m;
        return R.drawable.chips_500m;
    }
}
