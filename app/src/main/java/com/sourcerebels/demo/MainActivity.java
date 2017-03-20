package com.sourcerebels.demo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.sourcerebels.basicdialogs.AlertDialogFragment;
import com.sourcerebels.basicdialogs.ConfirmationDialogFragment;
import com.sourcerebels.basicdialogs.ProgressDialogFragment;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity implements ConfirmationDialogFragment.OnConfirmationListener {

    private static final int REQUEST_CODE_EXIT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_confirm_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmationDialogFragment.show(getSupportFragmentManager(),
                        "Exit", "Really exit?", REQUEST_CODE_EXIT);
            }
        });

        findViewById(R.id.btn_alert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialogFragment.show(getSupportFragmentManager(), "Dialog Title",
                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                                "Etiam mollis elit et pretium bibendum.");
            }
        });

        findViewById(R.id.btn_progress).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialogFragment.show(getSupportFragmentManager(),
                        "Progress Dialog", "Please wait");
                new DummyAsyncTask(getSupportFragmentManager()).execute();
            }
        });

    }

    @Override
    public void onConfirmation(int requestCode, Parcelable entity) {
        if (requestCode == REQUEST_CODE_EXIT) {
            finish();
        }
    }

    @Override
    public void onCancel(int requestCode, Parcelable entity) {
        if (requestCode == REQUEST_CODE_EXIT) {
            Toast.makeText(this, "Cancelled by user", Toast.LENGTH_SHORT).show();
        }
    }

    private static class DummyAsyncTask extends AsyncTask<Void, Void, Void> {

        private FragmentManager fragmentManager;

        public DummyAsyncTask(FragmentManager fragmentManager) {
            this.fragmentManager = fragmentManager;
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                //DO nothing
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ProgressDialogFragment.hide(fragmentManager);
        }
    }
}
