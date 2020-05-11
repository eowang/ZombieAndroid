/* Help screen displays the authors of the app, basic rules, and citations to all images and
 * sound effects used in the game. */


package ca.game.myapplication.GameUI;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import ca.game.myapplication.R;

import ca.game.myapplication.R;

public class HelpScreen extends AppCompatActivity {

    public static Intent makeIntent(ca.game.myapplication.GameUI.MainScreen context) {
        return new Intent(context, HelpScreen.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_help_screen);


    }


}
