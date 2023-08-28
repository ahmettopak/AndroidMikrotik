package com.ahmet.mikrotik;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jcraft.jsch.JSchException;

import java.io.IOException;
import android.os.Handler;


public class MainActivity extends AppCompatActivity {
//    ApiConnection con;
//    TextView textView;
//    Button button;
//    Mikrotik mikrotik;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        mikrotik = new Mikrotik(this);
//        textView = findViewById(R.id.textView);
//        button = findViewById(R.id.button);
//        try {
//            //con = ApiConnection.connect("192.168.3.20"); // connect to router
//            //con.login("admin",""); // log in to router
//           // con.execute("/system/reboot"); // execute a command
//           // con.close(); // disconnect from router
//
//            SSHTask sshTask = new SSHTask(this);
//
//            //mikrotik.connect();
//            button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    sshTask.execute();
//                    //textView.setText(mikrotik.getSignalLevel() + "A");
//                }
//            });
//
//
//            //Filter Result
////            for (Map<String, String> map : rs) {
////                System.out.println(map.get("name"));
////            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }


    private SSHManager sshManager;
    String result;
    int a =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize SSHManager
        sshManager = new SSHManager("192.168.3.20", 22, "admin", "");
        TextView textView = findViewById(R.id.textView);

        // Perform SSH actions in a background thread (e.g., AsyncTask)
//        new AsyncTask<Void, Void, String>() {
//            @Override
//            protected String doInBackground(Void... voids) {
//                try {
//                    sshManager.connect();
//
//                    result = sshManager.executeCommand("interface wireless registration print stats");
//                    //sshManager.disconnect();
//                    return result;
//                } catch (JSchException | IOException e) {
//                    e.printStackTrace();
//                    return "Error: " + e.getMessage();
//                }
//            }
//
//            @Override
//            protected void onPostExecute(String result) {
//                // Handle the SSH result (e.g., display it in a TextView)
//                textView.setText(result);
//            }
//        }.execute();
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
                textView.setText(result + a);

            }
        }.execute();
// Call this method to start the periodic task

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
            }
        });
    }
}