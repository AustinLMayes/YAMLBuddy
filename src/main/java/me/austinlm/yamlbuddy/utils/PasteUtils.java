package me.austinlm.yamlbuddy.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class PasteUtils {
    /*
     * Uploads a string to DPaste and returns the link to it.
     */
    public static String uploadToDPaste(String data) {
        String report_url = "";

        try {
            URL urls = new URL("http://dpaste.com/api/v2/");
            HttpURLConnection conn = (HttpURLConnection) urls.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setRequestMethod("POST");
            conn.addRequestProperty("Content-type", "application/x-www-form-urlencoded");
            conn.setInstanceFollowRedirects(false);
            conn.setDoOutput(true);
            OutputStream out = conn.getOutputStream();

            byte[] postData = (
                    "content=" + URLEncoder.encode(data, "utf-8")).getBytes();

            out.write(postData);
            out.flush();
            out.close();

            if (conn.getResponseCode() == 201) {
                InputStream receive = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(receive));
                String line;
                StringBuffer response = new StringBuffer();
                while ((line = reader.readLine()) != null) {
                    return line;
                }
                reader.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
