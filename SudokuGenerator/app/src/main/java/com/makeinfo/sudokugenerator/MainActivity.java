package com.makeinfo.sudokugenerator;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.startapp.android.publish.StartAppAd;
import com.startapp.android.publish.StartAppSDK;


public class MainActivity extends ActionBarActivity {

   // EditText ed;
    EditText tv;
    Button gen,copy;
    ProgressBar pb;
    int[][] bs = new int[9][9];
    private StartAppAd startAppAd = new StartAppAd(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StartAppSDK.init(this, "112014756", "202241970", true);
        setContentView(R.layout.activity_main);
        //ed = (EditText) findViewById(R.id.editText);
        tv = (EditText) findViewById(R.id.textView);
        gen = (Button) findViewById(R.id.button);
        copy = (Button) findViewById(R.id.copy);

        pb = (ProgressBar) findViewById(R.id.progressBar);
        pb.setVisibility(View.INVISIBLE);
        tv.setText("Click Generate");
        tv.setGravity(Gravity.CENTER_HORIZONTAL);
        //tv.setKeyListener(null);
        //final Tasker task = new Tasker();
        copy.setEnabled(false);
        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                android.content.ClipData clip = android.content.ClipData.newPlainText("Clip",tv.getText().toString());
                Toast.makeText(getApplicationContext(),"Copied Selection",Toast.LENGTH_SHORT).show();
                clipboard.setPrimaryClip(clip);
            }
        });
        gen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // task.execute(bs,bs,bs);
                Toast.makeText(getApplicationContext(),"Generating Combination...",Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(),"Please wait...",Toast.LENGTH_SHORT).show();
                 tv.setText("");
                new  Tasker().execute();

            }
        });
}
    class Tasker extends AsyncTask<Void, Void, Void> {

                @Override
        protected Void doInBackground(Void... params) {
            bs =SudokuGen.sudoGen();
                    return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            for (int i =0;i<9;i++){
                for (int j=0;j<9;j++){
                    if(j==3)
                        tv.append("| ");
                    if(j==6) tv.append("| ");

                    tv.append(String.valueOf(bs[i][j]));
                }
                tv.append("\n");
                if(i==2) tv.append("- - - - - - - - - - -\n");
                if(i==5) tv.append("- - - - - - - - - - -\n");
            }
            pb.setVisibility(View.INVISIBLE);
            copy.setEnabled(true);
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            copy.setEnabled(false);
            pb.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {

        Context context = this;
        final AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("About");
        alert.setMessage("Do you want to quit ?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.cancel();
                MainActivity.this.finish();

            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.cancel();
            }
        });
        AlertDialog aialog = alert.create();
        aialog.show();
       // super.onBackPressed();
    }
    @Override
    public void onResume() {
        super.onResume();
        startAppAd.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        startAppAd.onPause();
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
        if (id == R.id.our_apps) {

            Uri uri = Uri.parse("https://play.google.com/store/apps/developer?id=MakeInfo" );
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            try {
                startActivity(goToMarket);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=MakeInfo")));
            }
        }

        if (id == R.id.rate) {
            Uri uri = Uri.parse("market://details?id=com.makeinfo.sudokugenerator" );
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            try {
                startActivity(goToMarket);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=com.makeinfo.sudokugenerator")));
            }
            return true;

        }

        return super.onOptionsItemSelected(item);
    }
}
