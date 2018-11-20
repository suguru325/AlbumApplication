package com.example.yamashita.albumapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class CustomDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle saveInstanceState) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setMessage("パーミッションが許可されていないため、アプリを終了します。")
                    .setPositiveButton("アプリを終了", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(getActivity() != null) {
                    getActivity().finish();
                    getActivity().moveTaskToBack(true);
                }
            }
        });
        return dialogBuilder.create();
    }
}
