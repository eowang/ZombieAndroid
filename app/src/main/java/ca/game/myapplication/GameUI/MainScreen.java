/* Main screen displays three options: start game, options, and help. */

package ca.game.myapplication.GameUI;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import ca.game.myapplication.R;

public class MainScreen extends AppCompatActivity {

    public static Intent makeIntent(Context context) {
        return new Intent(context, ca.game.myapplication.GameUI.MainScreen.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        playButton();
        optionsButton();
        helpButton();

    }

    private void helpButton() {
        ImageButton help_btn = findViewById(R.id.helpBtn);

        help_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = HelpScreen.makeIntent(MainScreen.this);
                startActivity(intent3);
            }
        });
    }

    private void optionsButton() {
        ImageButton options_btn = findViewById(R.id.optionsBtn);

        options_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = OptionsScreen.makeIntent(MainScreen.this);
                startActivity(intent2);
            }
        });

    }

    private void playButton() {
        ImageButton play_btn = findViewById(R.id.playBtn);

        play_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = GameScreen.makeIntent(MainScreen.this);
                startActivity(intent1);
            }
        });
    }


}
