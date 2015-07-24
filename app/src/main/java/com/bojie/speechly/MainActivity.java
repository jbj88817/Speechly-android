package com.bojie.speechly;

import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener,
        DialogInterface.OnClickListener{

    private TextView mTextTime;
    private Handler mHandler;
    private long timeRemaining = 5000;
    private ToggleButton mToggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextTime = (TextView) findViewById(R.id.textView);
        AssetManager assetManager = getAssets();
        Typeface customFont = Typeface.createFromAsset(assetManager, "fonts/source_sans_pro.light.ttf");
        mTextTime.setTypeface(customFont);
        // ToggleButton
        mToggleButton = (ToggleButton) findViewById(R.id.toggleButton);
        mToggleButton.setOnCheckedChangeListener(this);

        mHandler = new Handler();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Log.d("Bojie", "run was called");
                timeRemaining -= 1000;
                if (timeRemaining > 0) {
                    mHandler.postDelayed(this, 1000);
                }

            }
        };

        mHandler.postDelayed(runnable, 1000);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            Toast.makeText(this, "ON", Toast.LENGTH_SHORT).show();
            LayoutInflater inflater = LayoutInflater.from(this);
            View view = inflater.inflate(R.layout.user_input, null);
            EditText textUserInput = (EditText) view.findViewById(R.id.text_input);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Please Enter The Time");
            builder.setView(R.layout.user_input);
            builder.setPositiveButton("OK", this);
            builder.setNegativeButton("Cancel", this);
            builder.show();
        } else {
            Toast.makeText(this, "OFF", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case DialogInterface.BUTTON_POSITIVE:
                Toast.makeText(this, "ok, clicked", Toast.LENGTH_SHORT).show();
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                Toast.makeText(this, "cancel clicked", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
