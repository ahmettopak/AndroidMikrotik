package com.ahmet.mikrotik;

import static kotlinx.coroutines.BuildersKt.withContext;
import static kotlinx.coroutines.CoroutineScopeKt.CoroutineScope;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jcraft.jsch.JSchException;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import android.os.Handler;


public class MainActivity extends AppCompatActivity {

    private SSHManager sshManager;
    String result;
    private ImageView imageView;

    boolean isPostExecute = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

              sshManager = new SSHManager("192.168.3.21", 22, "admin", "");
              TextView textView = findViewById(R.id.textView);
              isPostExecute = false;
              imageView = findViewById(R.id.imageView);

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                new AsyncTask<Void, Void, String>() {
                    @Override
                    protected String doInBackground(Void... voids) {
                        try {
                            sshManager.connect();

                            result = sshManager.executeCommand("interface wireless registration print stats");
                            return result;
                        } catch (JSchException | IOException e) {
                            e.printStackTrace();
                            return "Error: " + e.getMessage();
                        }
                    }

                    @Override
                    protected void onPostExecute(String result) {
                        // Handle the SSH result (e.g., display it in a TextView)
                        textView.setText(result);
                        isPostExecute = true;


                        imageView.setImageResource(R.drawable.ic_launcher_background);

                        Toast.makeText(MainActivity.this, "döngü " + isPostExecute, Toast.LENGTH_SHORT).show();
                    }
                }.execute();

            }
        },500,3000);

    }
}