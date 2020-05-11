/* Game screen displays a grid with configurations set by the user in the options screen.
 * The default grid size is 4 x 6. When the user wins, a dialog pops up, allowing the user to
 * go back to the Main Menu.
 *  */




package ca.game.myapplication.GameUI;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import ca.game.myapplication.GameLogic.Settings;
import ca.game.myapplication.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ca.game.myapplication.GameLogic.Settings;
import ca.game.myapplication.R;

public class GameScreen extends AppCompatActivity {

    private final Settings settings = Settings.getInstance();
    private int NUM_ROWS = settings.getRow();
    private int NUM_COLS = settings.getCol();
    private int NUM_ZOMBIES = settings.getZombie_count();
    private int zombie_discovered = 0;
    private int scan_count = 0 ;

    private Button buttons[][] = new Button[NUM_ROWS][NUM_COLS];
    private List zombies = new ArrayList();
    private List scannedCells = new ArrayList();

    public static Intent makeIntent(Context context) {
        return new Intent(context, GameScreen.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        TextView zombie_left = findViewById(R.id.zombie_left);
        zombie_left.setText(""+NUM_ZOMBIES);

        final MediaPlayer groan = MediaPlayer.create(this, R.raw.groan);
        final MediaPlayer gunshot = MediaPlayer.create(this, R.raw.gunshot);
        final MediaPlayer win = MediaPlayer.create(this, R.raw.win);

        generate_coordinates();
        System.out.println(Arrays.deepToString(zombies.toArray()));
        populateButtons(groan, gunshot, win);
    }

    public void populateButtons(final MediaPlayer groan, final MediaPlayer gunshot, final MediaPlayer win){
        TableLayout table = findViewById(R.id.tableForButtons);

        for (int row =0; row<NUM_ROWS; row++){
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f
            ));
            table.addView(tableRow);

            for (int col = 0; col <NUM_COLS; col++){
                final int FINAL_COL = col;
                final int FINAL_ROW = row;
                Button button = new Button(this);
                button.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f));

                // Make text not clip on small buttons
                button.setPadding(0,0,0,0);
                button.setOnClickListener(new View.OnClickListener(){
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onClick(View v){
                        gridButtonClicked(buttons,FINAL_COL, FINAL_ROW, groan, gunshot, win);
                    }
                });
                tableRow.addView(button);
                buttons[row][col]= button;
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)

    private void gridButtonClicked(Button[][] buttons, int col, int row, MediaPlayer groan, MediaPlayer gunshot, MediaPlayer win) {

        Button button = buttons[row][col];

        //Lock Button Sizes
        lockButtonSizes(buttons);

        int[] coordinate = new int[2];
        coordinate[0] = col;
        coordinate[1]= row;

        TextView zombie_left = findViewById(R.id.zombie_left);
        TextView zombie_found = findViewById(R.id.zombie_found);

        gunshot.start();

        // If the user clicks a cell with a hidden zombie
        if(isInList(zombies, coordinate)) {

            groan.start();

            int newWidth = button.getWidth();
            int newHeight = button.getHeight();
            Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.zombie3);
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true);
            Resources resource = getResources();
            button.setBackground(new BitmapDrawable(resource, scaledBitmap));

            System.out.println(Arrays.deepToString(zombies.toArray()));

            int i = getIndex(zombies, coordinate);
            zombies.remove(i);

            NUM_ZOMBIES = NUM_ZOMBIES -1;
            zombie_discovered = zombie_discovered + 1;
            zombie_left.setText(""+NUM_ZOMBIES);
            zombie_found.setText(""+zombie_discovered);

            //Update scan counts
            scan_after_zombieClicked(coordinate);

            //If all zombies are found
            if (zombies.isEmpty()){
                win.start();
                setupSetMessage();
            }
        }

        // If the user clicks a cell with no zombie.
        else{
            if (!isInList(scannedCells, coordinate)) {
                TextView count_scan = findViewById(R.id.count_scan);
                scan_count += 1;
                count_scan.setText("" + scan_count);
                scan(coordinate);
            }
            scan(coordinate);
        }
    }

    private void setupSetMessage() {
        FragmentManager manager = getSupportFragmentManager();
        MessageFragment dialog = new MessageFragment();
        dialog.setCancelable(false);
        dialog.show(manager, "victory");
    }

    private void scan_after_zombieClicked (int [] coordinate){
        for (int row =0; row< NUM_ROWS; row++){
            int [] check =new int[2];
            check[0] = coordinate[0];
            check[1]=row;

            if(isInList(scannedCells, check)){
                scan(check);
            }
        }

        for (int col = 0; col< NUM_COLS; col++) {
            int[] check2 = new int[2];
            check2[0]=col;
            check2[1]=coordinate[1];

            if(isInList(scannedCells, check2)){
                scan(check2);
            }
        }
    }


    private void scan(int [] coordinate){
        int count =0;

        for (int i = 0; i < NUM_ROWS; i++){
            int[] check = new int[2];
            check[0]=coordinate[0];
            check[1]=i;

            if(isInList(zombies, check)){
                count++;
            }
        }
        for (int j = 0; j< NUM_COLS; j++) {
            int[] check2 = new int[2];
            check2[0]=j;
            check2[1]=coordinate[1];

            if(isInList(zombies, check2)){
                count++;
            }
        }

        buttons[coordinate[1]][coordinate[0]].setText(""+count);

        scannedCells.add(coordinate);

    }

    private void lockButtonSizes(Button[][] buttons) {
        for (int row = 0; row<NUM_ROWS;row++){
            for(int col =0;col<NUM_COLS;col++){
                Button button =buttons[row][col];

                int width = button.getWidth();
                button.setMinWidth(width);
                button.setMaxWidth(width);

                int height = button.getHeight();
                button.setMinHeight(height);
                button.setMaxWidth(height);
            }
        }
    }

    private int generate_ranNum(int max){
        int num = (int) (Math.random()*((max)+1));
        return num;
    }

    private void generate_coordinates() {
        for (int i = 0; i < NUM_ZOMBIES; i++) {
            int[] coordinates = new int[2];
            coordinates[0] = generate_ranNum(NUM_COLS-1);
            coordinates[1] = generate_ranNum(NUM_ROWS-1);

            if(!isInList(zombies,coordinates)){
                zombies.add(coordinates);
            }

            else{
                i--;
            }
        }
    }

    public static boolean isInList(final List<int[]> list, final int[] candidate){
        for(final int[] item : list){
            if(Arrays.equals(item, candidate)){
                return true;
            }
        }
        return false;
    }

    private static int getIndex(final List<int[]>list, final int[] candidate) {
        int index = -1;
        for (int i = 0; i < list.size(); i++) {
            int[] array = list.get(i);
            if (Arrays.equals(candidate, array)) {
                index = i;
                break;
            }
        }
        return index;
    }
}





