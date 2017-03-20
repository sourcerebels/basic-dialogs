package com.sourcerebels.basicdialogs;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;

public class ProgressDialogFragment extends DialogFragment {

    public static final String TAG = "ProgressDialogFragment";

    private static final String TITLE = "title";
    private static final String MESSAGE = "message";

    public ProgressDialogFragment() {
        setRetainInstance(true);
    }

    public static void show(FragmentManager fragmentManager, String title, String message) {
        Bundle args = new Bundle();
        args.putString(TITLE, title);
        args.putString(MESSAGE, message);
        ProgressDialogFragment fragment = new ProgressDialogFragment();
        fragment.setArguments(args);
        fragment.show(fragmentManager, TAG);
    }

    public static void hide(FragmentManager fragmentManager) {
        DialogFragment fragment = (DialogFragment) fragmentManager.findFragmentByTag(TAG);
        if (fragment != null) {
            fragment.dismiss();
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = getArguments().getString(TITLE, "");
        String message = getArguments().getString(MESSAGE, "");

        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setCancelable(false);
        dialog.setIndeterminate(true);

        // Disable the back button
        DialogInterface.OnKeyListener keyListener = new DialogInterface.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface dialog, int keyCode,
                                 KeyEvent event) {

                return keyCode == KeyEvent.KEYCODE_BACK;
            }
        };
        dialog.setOnKeyListener(keyListener);
        return dialog;
    }
}