package com.crystalplanet.obsidianpoker.app;

import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import com.crystalplanet.obsidianpoker.game.Player;

public class NextPlayerDialog extends Dialog implements View.OnClickListener {

    private PokerGameActivity context;

    private Player player;

    public NextPlayerDialog(PokerGameActivity context, Player player) {
        super(context);

        this.context = context;
        this.player = player;

        setCanceledOnTouchOutside(false);
        setCancelable(false);

        setContentView(R.layout.dialog_next_player);

        Button button = (Button) findViewById(R.id.nextPlayerPromptBtnReady);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nextPlayerPromptBtnReady:
                dismiss();
                context.showPlayerUI(player);
                return;
        }
    }
}
