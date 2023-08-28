package com.ahmet.mikrotik;

import com.jcraft.jsch.*;

import java.io.IOException;
import java.io.InputStream;

public class SSHManager {
    private String host;
    private int port;
    private String username;
    private String password;
    private Session session;

    public SSHManager(String host, int port, String username, String password) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    public void connect() throws JSchException {
        JSch jsch = new JSch();
        session = jsch.getSession(username, host, port);
        session.setPassword(password);
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();
    }

    public String executeCommand(String command) throws JSchException, IOException {
        if (session == null || !session.isConnected()) {
            connect();
        }

        Channel channel = session.openChannel("exec");
        ((ChannelExec) channel).setCommand(command);

        InputStream in = channel.getInputStream();
        channel.connect();

        StringBuilder output = new StringBuilder();
        byte[] buffer = new byte[1024];
        int bytesRead;

        while ((bytesRead = in.read(buffer)) > 0) {
            output.append(new String(buffer, 0, bytesRead));
        }

        channel.disconnect();
        return output.toString();
    }

    public void disconnect() {
        if (session != null && session.isConnected()) {
            session.disconnect();
        }
    }
}