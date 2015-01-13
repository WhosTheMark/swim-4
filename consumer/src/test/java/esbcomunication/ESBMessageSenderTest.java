package esbcomunication;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.MalformedURLException;

import org.junit.Test;

import esbcomunication.ESBMessageSender;

public class ESBMessageSenderTest {

    final static String MALFORMED_ADDRESS = "localhost?";
    final static String WRONG_ADDRES = "http://localhost:6060";
    final static String MESSAGE = "I'm a message";

    @Test(expected = MalformedURLException.class)
    public void sendToBadURLTest() throws IOException {
        ESBMessageSender sender = new ESBMessageSender(MALFORMED_ADDRESS);
        sender.send(MESSAGE);
        fail();
    }

    @Test(expected = IOException.class)
    public void sendToWrongAddressTest() throws IOException {
        ESBMessageSender sender = new ESBMessageSender(WRONG_ADDRES);
        sender.send(MESSAGE);
        fail();
    }

}
