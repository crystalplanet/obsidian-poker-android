package com.crystalplanet.obsidianpoker.app;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import com.crystalplanet.obsidianpoker.game.Player;
import com.crystalplanet.obsidianpoker.game.PokerRound;
import com.crystalplanet.obsidianpoker.game.chips.Chips;

public class BetDialog extends Dialog implements View.OnClickListener {

    private PokerGameActivity context;

    private Player player;

    private PokerRound round;

    private TextView amountText;

    private SeekBar scrollBar;

    private int amount;

    private int min;

    private int max;

    public BetDialog(PokerGameActivity context, Player player, PokerRound round) {
        super(context);

        this.context = context;
        this.player = player;
        this.round = round;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_bet);

        Button button;

        button = (Button) findViewById(R.id.button_bet_add);
        button.setOnClickListener(this);

        button = (Button) findViewById(R.id.button_bet_substract);
        button.setOnClickListener(this);

        button = (Button) findViewById(R.id.button_bet_confirm);
        button.setOnClickListener(this);
        button.setText(getContext().getString(player.canBet() ? R.string.action_bet : R.string.action_raise));

        button = (Button) findViewById(R.id.button_bet_cancel);
        button.setOnClickListener(this);
        button.setText(getContext().getString(R.string.action_cancel));

        amountText = (TextView) findViewById(R.id.text_bet_amount);

        scrollBar = (SeekBar) findViewById(R.id.slider_bet_amount);

        Chips minBet = round.currentBet().add(round.currentBet()).substract(round.playersBet(player));
        Chips playersChips = player.chips();

        min = Integer.parseInt(minBet.toString().replace(" ", "").replace("$", ""));
        max = Integer.parseInt(playersChips.toString().replace(" ", "").replace("$", ""));

        scrollBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                double range = max-min;
                double percent = (double)progress/(double)scrollBar.getMax();
                amount = min + (int) Math.round(range*percent);
                updateView(false);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        updateView(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_bet_add:
                if (amount < max) ++amount;
                break;

            case R.id.button_bet_substract:
                if (amount > min) --amount;
                break;

            case R.id.button_bet_confirm:
                context.hidePlayerUI(player);

                try {
                    if (player.canBet()) {
                        dismiss();
                        player.bet(new Chips(amount + min));
                        return;
                    }

                    if (player.canRaise()) {
                        dismiss();
                        player.raise(new Chips(amount + min));
                        return;
                    }
                } catch (RuntimeException err) {
                    context.showPlayerUI(player);
                    Toast.makeText(
                            getContext().getApplicationContext(),
                            err.getMessage(),
                            Toast.LENGTH_LONG
                    ).show();
                }
                return;

            case R.id.button_bet_cancel:
                dismiss();
                return;
        }

        updateView(true);
    }

    private void updateView(boolean updateSlider) {
        amountText.setText(new Chips(amount + min).add(round.playersBet(player)).toString());

        if (updateSlider)
            scrollBar.setProgress((int)((double)(amount-min)*(double)scrollBar.getMax()/(double)(max-min)));
    }
}
