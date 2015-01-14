package esbcomunication;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class ESBMessageSenderIT {

    // TODO Change the address of the bus when needed
    final static String ESB_ADDRESS = "http://localhost:9081";

    // TODO Change the messages according to WSDL
    final static String MESSAGE = "<ns2:hello xmlns:ns2=\"http://serv/\">"
                                + "<name>World</name>"
                                + "</ns2:hello>";
    final static String REPLY =  "<?xml version='1.0' encoding='UTF-8'?>"
                               + "<ns2:helloResponse xmlns:ns2=\"http://serv/\">"
                               + "<return>Hello World !</return>"
                               + "</ns2:helloResponse>";

    final static String INVALID_MSG = "<tag>value</tag>";

    @Test
    public void sendMessageTest() {

        String reply = null;
        ESBMessageSender sender = new ESBMessageSender(ESB_ADDRESS);

        try {
            reply = sender.send(MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(REPLY, reply);
    }

    @Test(expected = IOException.class)
    public void sendInvalidMessageTest() throws IOException {
        ESBMessageSender sender = new ESBMessageSender(ESB_ADDRESS);
        sender.send(INVALID_MSG);
        fail();
    }
}
