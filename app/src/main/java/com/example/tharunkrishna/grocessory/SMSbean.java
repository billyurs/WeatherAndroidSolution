package com.example.tharunkrishna.grocessory;

/**
 * Created by Tharun Krishna on 12/20/2015.
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SMSbean {
    String message;
    String numbers;

    public String send() {
        try {
            // Construct data
            String user = "username=" + "raghucssit@gmail.com";
            String hash = "&hash=" + "2846fb8a13c7d7cee4117e8aa7adc3d45d5afd8e";
            String sender = "&sender=" + "TXTLCL";

            // Send data
            HttpURLConnection conn = (HttpURLConnection) new URL("http://api.textlocal.in/send/?").openConnection();
            String data = user + hash + numbers + message + sender;
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
            conn.getOutputStream().write(data.getBytes("UTF-8"));
            final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            final StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = rd.readLine()) != null) {
                stringBuffer.append(line);
            }
            rd.close();

            return stringBuffer.toString();
        } catch (Exception e) {
            System.out.println("Error SMS " + e);
            return "Error " + e;
        }
    }

    public void setMessage(String message) {
        this.message = "&message=" + message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setNumber(String number) {
        this.numbers = "&numbers=" + number;
    }

    public String getNumber() {
        return this.numbers;
    }

    public static void main(String args[]) {
        // SMSBean sms = new SMSBean();
        // System.out.println("Your response is : " + sms.send());
    }
}
