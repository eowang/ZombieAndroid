/* Welcome screen displaying the game title, author names, and animations.
 * User can click the skip button to stop the animation and go to the Main Menu.
 * The screen automatically switches to the Main Menu 4 seconds after the animations end. */

package ca.game.myapplication.GameUI;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;

import ca.game.myapplication.R;


public class MainActivity extends AppCompatActivity {

    private Handler mHandler = new Handler();
    private boolean clicked = false;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity(); // Close all activites
        System.exit(0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_main);
        animateZombie();
        animateZombie2();
        skip();

        //skip to main menu after animations
        autoSkip();
    }

    //Automatically move to main menu after animations end, referenced from: https://stackoverflow.com/questions/24745546/android-change-activity-after-few-seconds
    private void autoSkip() {
        mHandler.postDelayed(new Runnable() {
            public void run() {
                if (!clicked) {
                    Intent intent = MainScreen.makeIntent(MainActivity.this);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        }, 10000);
    }

    private void animateZombie2() {
        Animation anim = new TranslateAnimation(0, 1000, 0 ,0 );
        anim.setDuration(3000);
        anim.setFillAfter(true);

        final ImageView splash2 = findViewById(R.id.zombie2);
        splash2.startAnimation(anim);
        splash2.setVisibility(View.VISIBLE);
    }


    //Basic rotation animation referenced from: https://stackoverflow.com/questions/2032304/android-imageview-animation
    private void animateZombie() {
        RotateAnimation anim = new RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(5);
        anim.setDuration(700);

        // Start animating the image
        final ImageView splash = findViewById(R.id.zombieAnim);
        splash.startAnimation(anim);
    }

    private void skip() {
        ImageButton btn = findViewById(R.id.skipBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clicked = true;
                //Stop animation when skip is clicked
                final ImageView splash = findViewById(R.id.zombieAnim);
                splash.setAnimation(null);
                final ImageView splash2 = findViewById(R.id.zombie2);
                splash2.setAnimation(null);

                Intent intent = MainScreen.makeIntent(MainActivity.this);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finishAffinity();
            }
        });

    }

}
