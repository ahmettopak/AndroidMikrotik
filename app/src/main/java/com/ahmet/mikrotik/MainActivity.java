package com.ahmet.mikrotik;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.List;
import java.util.Map;

import me.legrange.mikrotik.ApiConnection;
import me.legrange.mikrotik.MikrotikApiException;

public class MainActivity extends AppCompatActivity {
    ApiConnection con;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            con = ApiConnection.connect("192.168.3.20"); // connect to router
            con.login("admin","password"); // log in to router
            con.execute("/system/reboot"); // execute a command
            con.close(); // disconnect from router


            List<Map<String, String>> rs = con.execute("/interface/print");
            for (Map<String,String> r : rs) {
                System.out.println(r);
            }

            //Filter Result
//            for (Map<String, String> map : rs) {
//                System.out.println(map.get("name"));
//            }
        } catch (MikrotikApiException e) {
            e.printStackTrace();
        }

    }
}