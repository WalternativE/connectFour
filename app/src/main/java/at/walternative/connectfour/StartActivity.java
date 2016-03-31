package at.walternative.connectfour;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button startSingleplayer = (Button) findViewById(R.id.start_button_singleplayer);
        assert startSingleplayer != null;

        startSingleplayer.setOnClickListener(new StartGameButtonListener());

        Button startMultilayer = (Button) findViewById(R.id.start_button_twoplayer);
        assert startMultilayer != null;

        startMultilayer.setOnClickListener(new StartGameButtonListener());
    }

    private class StartGameButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            String tag = v.getTag().toString();

            Intent intent = new Intent(StartActivity.this, GameActivity.class);
            intent.putExtra("player", tag);

            startActivity(intent);
        }
    }
}
