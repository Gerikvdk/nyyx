package com.example.trastnedviga.Model;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.example.trastnedviga.R;

public class LoadingDialog {

   private static Activity activity;
   private static AlertDialog dialog;


    public LoadingDialog(Activity myActivity){
        activity = myActivity;



    }

    public static void startLoadingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.custom_dialog, null));
        builder.setCancelable(true);


        dialog = builder.create();
        dialog.show();

    }

    public static void dissmissDialog() {
        dialog.dismiss();
    }

    void StartLoadingDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.custom_dialog, null));
        builder.setCancelable(true);

        dialog = builder.create();
        dialog.show();
    }

   public static void  dismissDialog(){
        dialog.dismiss();

    }


}
