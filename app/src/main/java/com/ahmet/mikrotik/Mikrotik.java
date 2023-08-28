package com.ahmet.mikrotik;

import android.widget.Toast;

import java.net.SocketException;
import java.util.List;
import java.util.Map;

import javax.net.SocketFactory;

import me.legrange.mikrotik.ApiConnection;
import me.legrange.mikrotik.ApiConnectionException;
import me.legrange.mikrotik.MikrotikApiException;

public class Mikrotik {
    public static final String MIKROTIK_IP = "192.168.3.20";
    private final int MIKROTIK_PORT = 8728;
    private final String USERNAME = "admin";
    private final String PASSWORD = "";

    //TODO Eşleme için password ve yayıncı ip'leri statik olmalı.
    // Değişirse ip kullanılan yerler ve şifre değişmeli. Komutlar çalışıyor.

    private ApiConnection con;
    public String signalStrength = "";
    private MainActivity mainActivity;


    public Mikrotik(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }

    void connect() throws MikrotikApiException {

        con = ApiConnection.connect(SocketFactory.getDefault(), MIKROTIK_IP, MIKROTIK_PORT, 2000);
        con.login(USERNAME, PASSWORD);
        //   configureMikrotikStation("HALIL23-002", "HALIL002","HALIL002");
    }

    void disconnect() throws ApiConnectionException {
        synchronized (this) {
            if (con != null) {
                con.close();
                con = null;
            }
        }
    }

    public int getSignalLevel(){

        try {
            List<Map<String, String>> results = con.execute("/interface/wireless/registration-table/print where interface=wlan1 return signal-strength");
            signalStrength = results.get(0).get("signal-strength");


            if(signalStrength == null){
                return -1;
            }
            else{
                return  Integer.parseInt(signalStrength);

            }
        }
        catch (Exception e){
            return -2;

        }
    }

}
