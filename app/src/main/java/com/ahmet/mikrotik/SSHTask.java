package com.ahmet.mikrotik;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.jcraft.jsch.*;

import java.io.InputStream;

public class SSHTask extends AsyncTask<Void, Void, Void> {

    MainActivity mainActivity;
    public SSHTask (MainActivity mainActivity){

        this.mainActivity = mainActivity;
    }
    @Override
    protected Void doInBackground(Void... voids) {
        JSch jsch = new JSch();
        Session session = null;
        ChannelExec channel = null;

        try {
            // SSH bağlantısı için gerekli bilgiler
            String username = "admin";
            String password = "";
            String host = "192.168.3.20";
            int port = 22;

            // SSH oturumu açma
            session = jsch.getSession(username, host, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            // Komutu yürütme
            channel = (ChannelExec) session.openChannel("exec");
            String command = "interface wireless registration print stats";
            channel.setCommand(command);

            channel.connect();

            // Komut çıktısını okuma
            byte[] buffer = new byte[1024];
            InputStream in = channel.getInputStream();
            int numRead;
            while ((numRead = in.read(buffer)) > 0) {
                String output = new String(buffer, 0, numRead);
                // İşlemlerinizi burada gerçekleştirin
                Log.d("SSH_OUTPUT", output);
                Toast.makeText(mainActivity, output, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (channel != null) {
                channel.disconnect();
            }
            if (session != null) {
                session.disconnect();
            }
        }
        return null;
    }
}
