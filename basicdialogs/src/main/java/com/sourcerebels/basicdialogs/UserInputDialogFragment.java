package com.sourcerebels.basicdialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Generic alert dialog fragment.
 */
public class UserInputDialogFragment extends DialogFragment {

    public static final String TAG = "AlertDialogFragment";

    private static final String ARGUMENT_TITLE = "title";
    private static final String ARGUMENT_MESSAGE = "message";
    private static final String ARGUMENT_REQUEST_CODE = "requestCode";

    private String title;
    private String message;
    private int requestCode;

    private EditText userInput;
    private UserInputDialogListener listener;

    public static void show(FragmentManager fragmentManager, String title, String message,
                            int requestCode) {
        Bundle args = new Bundle();
        args.putString(ARGUMENT_TITLE, title);
        args.putString(ARGUMENT_MESSAGE, message);
        args.putInt(ARGUMENT_REQUEST_CODE, requestCode);
        UserInputDialogFragment fragment = new UserInputDialogFragment();
        fragment.setArguments(args);
        fragment.show(fragmentManager, TAG);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof UserInputDialogListener) {
            listener = (UserInputDialogListener) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        title = args.getString(ARGUMENT_TITLE);
        message = args.getString(ARGUMENT_MESSAGE);
        requestCode = args.getInt(ARGUMENT_REQUEST_CODE);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getActivity();
        View view = View.inflate(context, R.layout.dialog_user_input, null);

        TextView messageTv = (TextView) view.findViewById(R.id.user_input_dialog_message_text);
        messageTv.setText(message);

        userInput = (EditText) view.findViewById(R.id.user_input_dialog_input_tv);

        return new AlertDialog.Builder(context)
                .setTitle(title)
                .setView(view)
                .setCancelable(true)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (listener != null) {
                            listener.onUserInput(requestCode, userInput.getText().toString());
                        }
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Do nothing
                    }
                })
                .create();
    }

    public interface UserInputDialogListener {
        void onUserInput(int requestCode, String input);
    }
}
