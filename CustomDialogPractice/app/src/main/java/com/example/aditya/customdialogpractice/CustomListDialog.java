package com.example.aditya.customdialogpractice;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by Aditya on 03-Jan-18.
 */

public class CustomListDialog extends DialogFragment {
    //View title;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //title = this.getLayoutInflater().inflate(R.layout.custom_title,null);  -- App crash, cant find reason in andriod monitor
        AlertDialog.Builder lv_dialog = new AlertDialog.Builder(getActivity());
        //lv_dialog.setCustomTitle(title);
        lv_dialog.setTitle("Options");
        lv_dialog.setItems(R.array.Options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(),"Item selected at pos: "+which,Toast.LENGTH_LONG).show();
            }
        });


        lv_dialog.setPositiveButton(R.string.select, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(),"Positive button clicked!",Toast.LENGTH_LONG).show();
            }
        });


        lv_dialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(),"Negative button clicked!",Toast.LENGTH_LONG).show();
            }
        });



        return lv_dialog.create();
    }
}
