package com.sourcerebels.basicdialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;

/**
 * Confirmation Dialog Fragment.
 */
public class ConfirmationDialogFragment extends DialogFragment {

    public static final String TAG = "ConfirmationDialog";

    public static final int DEFAULT_REQUEST = 0;

    private static final String ARGUMENT_TITLE = "title";
    private static final String ARGUMENT_MESSAGE = "message";
    private static final String ARGUMENT_REQUEST_CODE = "requestCode";
    private static final String ARGUMENT_ENTITY = "entity";

    private String title;
    private String message;
    private int requestCode;

    private OnConfirmationListener listener;
    private Parcelable entity;

    public static void show(FragmentManager fragmentManager, String title, String message) {
        show(fragmentManager, title, message, DEFAULT_REQUEST);
    }

    public static void show(FragmentManager fragmentManager, String title, String message,
                            int requestCode) {
        show(fragmentManager, title, message, requestCode, null);
    }

    public static void show(FragmentManager fragmentManager, String title, String message,
                            Parcelable entity) {
        show(fragmentManager, title, message, DEFAULT_REQUEST, entity);
    }

    public static void show(FragmentManager fragmentManager, String title, String message,
                            int requestCode, Parcelable entity) {
        Bundle args = new Bundle();
        args.putString(ARGUMENT_TITLE, title);
        args.putString(ARGUMENT_MESSAGE, message);
        args.putInt(ARGUMENT_REQUEST_CODE, requestCode);
        args.putParcelable(ARGUMENT_ENTITY, entity);

        ConfirmationDialogFragment fragment = new ConfirmationDialogFragment();
        fragment.setArguments(args);
        fragment.show(fragmentManager, TAG);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnConfirmationListener) {
            this.listener = (OnConfirmationListener) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        title = args.getString(ARGUMENT_TITLE);
        message = args.getString(ARGUMENT_MESSAGE);
        requestCode = args.getInt(ARGUMENT_REQUEST_CODE);
        entity = args.getParcelable(ARGUMENT_ENTITY);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getContext())
                .setMessage(message)
                .setCancelable(false)
                .setTitle(title)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (listener != null) {
                            listener.onConfirmation(requestCode, entity);
                        }
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (listener != null) {
                            listener.onCancel(requestCode, entity);
                        }
                    }
                }).create();
    }

    public interface OnConfirmationListener {

        void onConfirmation(int requestCode, Parcelable entity);

        void onCancel(int requestCode, Parcelable entity);
    }
}
