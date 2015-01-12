package esbcomunication;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Class to send messages to a producer through the bus.
 * @author Marcos Campos
 */
public class MessageSender {

    private String producerAddress;

    /**
     * Constructor to create a MessageSender object.
     * @param address the address of the producer in the bus.
     */
    public MessageSender(String address){
        this.producerAddress = address;
    }

    /**
     * Send the message to a producer through Mule ESB.
     * @param message The message to send.
     * @return the reply of the request.
     * @throws MalformedURLException if the address of the bus is malformed.
     * @throws IOException if cannot connect to bus or the message is malformed.
     */
    public String send(String message) throws MalformedURLException, IOException {

        HttpURLConnection connection = setHTTPHeader(message);
        sendRequest(connection, message);
        return getResponse(connection);
    }

    private HttpURLConnection setHTTPHeader(String message)
            throws MalformedURLException, IOException {

        URL url = new URL(producerAddress);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type","text/plain; charset=UTF-8");
        connection.setRequestProperty("Content-Length", messageLength(message));
        return connection;
    }

    private void sendRequest(HttpURLConnection connection, String message)
            throws IOException {

        connection.setDoOutput(true);
        DataOutputStream stream = new DataOutputStream(connection.getOutputStream());
        stream.writeBytes(message);
        stream.flush();
        stream.close();
    }

    private String messageLength(String message) {
        return Integer.toString(message.getBytes().length);
    }

    private String getResponse(HttpURLConnection connection)
            throws IOException {

        InputStreamReader inputStream = new InputStreamReader(connection.getInputStream());
        BufferedReader reader = new BufferedReader(inputStream);
        StringBuilder response = new StringBuilder();
        String inputLine;

        while ((inputLine = reader.readLine()) != null) {
            response.append(inputLine);
        }

        reader.close();
        return response.toString();
    }

}
