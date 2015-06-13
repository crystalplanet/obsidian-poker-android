package com.crystalplanet.obsidianpoker.app;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.crystalplanet.obsidianpoker.game.chips.Chips;
import com.crystalplanet.obsidianpoker.service.PlayerFactory;
import com.crystalplanet.obsidianpoker.service.PlayerManager;

public class NewPlayerDialog extends Dialog implements View.OnClickListener {

    private PokerGameActivity context;

    private PlayerManager playerManager;

    private int seatChosen;

    public NewPlayerDialog(PokerGameActivity context, PlayerManager playerManager, int seatChosen) {
        super(context);

        this.context = context;
        this.playerManager = playerManager;
        this.seatChosen = seatChosen;

        setContentView(R.layout.dialog_new_player);

        findViewById(R.id.button_next_player).setOnClickListener(this);
        findViewById(R.id.button_finish).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        EditText playerName = (EditText) findViewById(R.id.playerNamePromptPlayerName);
        String player = playerName.getText().toString();

        if (player.length() < 3 || player.length() > 9) {
            Toast.makeText(
                context.getApplicationContext(),
                context.getResources().getString(R.string.validation_name_length),
                Toast.LENGTH_LONG
            ).show();

            return;
        }

        switch (v.getId()) {
            case R.id.button_finish:
                if (playerManager.getPlayers().isEmpty()) {
                    Toast.makeText(
                        context.getApplicationContext(),
                        context.getResources().getString(R.string.validation_players_count),
                        Toast.LENGTH_LONG
                    ).show();
                    return;
                }

                addPlayer(player, seatChosen);
                dismiss();
                context.update();
                context.startGame();
                return;

            case R.id.button_next_player:
                addPlayer(player, seatChosen);
                dismiss();
                context.update();
                return;
        }
    }

    private void addPlayer(String player, int seat) {
        PlayerFactory playerFactory = new PlayerFactory();
        playerManager.addPlayer(playerFactory.newPlayer(player, new Chips(1000), context), seat);
    }
}
