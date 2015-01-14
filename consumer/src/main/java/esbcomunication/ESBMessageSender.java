package esbcomunication;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;

/**
 * Class to send messages to a producer through the bus. For a HTTPURLConnection
 * object there are two timeouts, a timeout for connecting to the URL, and a
 * timeout to wait for the response. These timeouts are calculated using the
 * field TIMEOUT.
 * @author Marcos Campos
 */
class ESBMessageSender {

    /** Timeout in milliseconds */
    private static final int TIMEOUT = 10 * 1000;

    /** The address of the producer in the bus. */
    private final String producerAddress;

    ESBMessageSender(String producerAddress){
        this.producerAddress = producerAddress;
    }

    /**
     * Send the message to a producer through Mule ESB. It sets the corresponding
     * timeouts.
     * @param message The message to send.
     * @return the reply of the request.
     * @throws MalformedURLException if the address of the bus is malformed.
     * @throws IOException if cannot connect to bus or the message is malformed.
     * @throws SocketTimeoutException if the timer runs out.
     */
    String send(String message)
            throws MalformedURLException, IOException, SocketTimeoutException {

        HttpURLConnection connection = setHTTPHeader(producerAddress, message);

        connection.setConnectTimeout(TIMEOUT);

        long timeBeforeRequest = System.currentTimeMillis();
        sendRequest(connection, message);
        long timeAfterRequest = System.currentTimeMillis();

        setResponseTimeout(connection, timeBeforeRequest, timeAfterRequest);

        return getResponse(connection);
    }

    /**
     * Sets the timeout to wait for the response. This timeout is based on how
     * much time took connecting to the address and the time to send a request.
     * @param connection the connection to the ESB.
     * @param timeBeforeRequest the time when the request started.
     * @param timeAfterRequest
     * @see HttpURLConnection#setReadTimeout(int)
     */
    private void setResponseTimeout(HttpURLConnection connection,
            long timeBeforeRequest, long timeAfterRequest) {

        int timePast = (int) (timeAfterRequest - timeBeforeRequest);
        int remainingTime = Math.max(1, TIMEOUT - timePast);
        connection.setReadTimeout(remainingTime);
    }

    private HttpURLConnection setHTTPHeader(String producerAddress, String message)
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
