package esbcomunication;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.MalformedURLException;

import org.junit.Test;

public class MessageSenderTest {

    // NOTE Change the address of the bus when needed
    final String ESB_ADDRESS = "http://localhost:9081";

    final String MALFORMED_ADDRESS = "localhost?";
    final String WRONG_ADDRES = "http://localhost:6060";

    // TODO Change the messages according to WSDL
    final String MESSAGE =    "<ns2:hello xmlns:ns2=\"http://serv/\">"
                            + "<name>World</name>"
                            + "</ns2:hello>";
    final String REPLY =  "<?xml version='1.0' encoding='UTF-8'?>"
                        + "<ns2:helloResponse xmlns:ns2=\"http://serv/\">"
                        + "<return>Hello World !</return>"
                        + "</ns2:helloResponse>";
    final String INVALID_MSG = "<tag>value</tag>";


    @Test
    public void sendMessageTest() {

        MessageSender sender = new MessageSender(ESB_ADDRESS);
        String reply = null;
        try {
            reply = sender.send(MESSAGE);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        assertEquals(REPLY, reply);
    }

    @Test(expected = IOException.class)
    public void sendInvalidMessageTest() throws IOException {
        MessageSender sender = new MessageSender(ESB_ADDRESS);
        sender.send(INVALID_MSG);
        fail();
    }

    @Test(expected = MalformedURLException.class)
    public void sendURITest() throws IOException {
        MessageSender sender = new MessageSender(MALFORMED_ADDRESS);
        sender.send(MESSAGE);
        fail();
    }

    @Test(expected = IOException.class)
    public void sendToWrongAddressTest() throws IOException {
        MessageSender sender = new MessageSender(WRONG_ADDRES);
        sender.send(MESSAGE);
        fail();
    }

}
