/* Options screen allows user to set the grid size and the number of zombies.
 * The user must click on the save button to update the configurations. */

package ca.game.myapplication.GameUI;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import ca.game.myapplication.GameLogic.Settings;
import ca.game.myapplication.R;

import ca.game.myapplication.GameLogic.Settings;

public class OptionsScreen extends AppCompatActivity {
    public Settings settings = Settings.getInstance();

    public static Intent makeIntent(Context context) {
        return new Intent(context, OptionsScreen.class);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options_screen);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        saveButton();
        createRadioButtons();

    }


    private void saveButton() {
        Button check = findViewById(R.id.check);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getZombieID()==-1 || getGridID()==-1){
                    Toast.makeText(OptionsScreen.this, "Please select both the grid size and the zombie count.", Toast.LENGTH_SHORT).show();
                }

                switch(getGridID()){
                    case 1:
                        settings.setCol(6);
                        settings.setRow(4);
                        break;

                    case 2:
                        settings.setCol(10);
                        settings.setRow(5);
                        break;
                    case 3:
                        settings.setCol(15);
                        settings.setRow(6);
                        break;
                }

                switch(getZombieID()){
                    case 4:
                        settings.setZombie_count(6);
                        break;
                    case 5:
                        settings.setZombie_count(10);
                        break;
                    case 6:
                        settings.setZombie_count(15);
                        break;
                    case 7:
                        settings.setZombie_count(20);
                        break;
                }
                Toast.makeText(OptionsScreen.this, "Saved!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createRadioButtons() {
        RadioGroup grid_group = findViewById(R.id.btn_gridsize);
        RadioGroup zombie_group = findViewById(R.id.btn_zombies);

        String[] gridarray = getResources().getStringArray(R.array.grid_size);
        int [] zombiearray = getResources().getIntArray(R.array.num_zombies);

        for (int i = 0 ; i < gridarray.length; i++){
            String size = gridarray[i];
            RadioButton button1 = new RadioButton(this);
            button1.setText(""+size);
            grid_group.addView(button1);
        }

        for (int i = 0 ; i < zombiearray.length; i++){
            int population_size = zombiearray[i];

            RadioButton button = new RadioButton(this);
            button.setText(""+population_size);
            zombie_group.addView(button);
        }
    }


    private int getZombieID(){
        RadioGroup zombie_group = findViewById(R.id.btn_zombies);
        int zombieID = zombie_group.getCheckedRadioButtonId();

        return zombieID%7;
    }

    private int getGridID(){
        RadioGroup grid_group = findViewById(R.id.btn_gridsize);
        int gridID = grid_group.getCheckedRadioButtonId();

        return gridID%7;
    }

}
