package at.walternative.connectfour;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class GameActivity extends Activity {

    private static final int COL_COUNT = 7;

    private final LinearLayout[] columns = new LinearLayout[COL_COUNT];

    private Game game;
    private String mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        mode = intent.getExtras().getString("player");
        Toast.makeText(this, "Started in mode: " + mode, Toast.LENGTH_SHORT).show();

        game = new Game();

        LinearLayout toolbar = (LinearLayout) findViewById(R.id.game_toolbar);
        assert toolbar != null;

        for (int i = 0; i < COL_COUNT; i++) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);

            Button button = new Button(this);

            button.setLayoutParams(layoutParams);

            button.setOnClickListener(new ButtonClickListener());

            button.setText(String.valueOf(i + 1));
            button.setTag(String.valueOf(i));

            toolbar.addView(button);
        }

        LinearLayout board = (LinearLayout) findViewById(R.id.game_board);
        assert board != null;

        for (int i = 0; i < board.getChildCount(); i++) {
            columns[i] = (LinearLayout) board.getChildAt(i);
        }
    }

    public ImageView createImageView(Game.Player player) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);

        ImageView imageView = new ImageView(this);

        if (player == Game.Player.RED) {
            imageView.setImageResource(R.drawable.red);
        } else {
            imageView.setImageResource(R.drawable.yellow);
        }

        imageView.setLayoutParams(layoutParams);

        return imageView;
    }

    private class ButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Integer columnIndex = Integer.parseInt(v.getTag().toString());

            if (game.move(columnIndex)) {
                columns[columnIndex].addView(createImageView(game.getCurrentPlayer()), 0);
            } else {
                return;
            }

            if ("single".equals(mode)) {
                int columnRender = game.computerMove();
                columns[columnRender].addView(createImageView(game.getCurrentPlayer()), 0);
            }
        }
    }
}
