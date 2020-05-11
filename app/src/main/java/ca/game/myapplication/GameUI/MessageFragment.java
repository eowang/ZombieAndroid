/* Message is displayed when the user finishes the game.
 * The user can click on the ok button to go back to the Main Menu.
 * */


package ca.game.myapplication.GameUI;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatDialogFragment;

import ca.game.myapplication.GameUI.MainScreen;
import ca.game.myapplication.R;

public class MessageFragment extends AppCompatDialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {



        //Create the view to show
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.message_layout, null);

        //Create a button listener
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent1 = MainScreen.makeIntent(getContext());
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent1);

            }
        };

        //Build the alert dialog
        return new AlertDialog.Builder(getActivity())
//            .setTitle("Changing Message")
                .setView(v)
                .setPositiveButton(android.R.string.ok,listener)
                .create();

    }
}
