package esbcomunication;

import java.io.IOException;
import java.io.StringReader;
import java.net.SocketTimeoutException;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import jmsconsumer.JMSManager;
import messaging.Message;
import messaging.MessageError;
import messaging.MessageResult;

import org.xml.sax.InputSource;

/**
 * Thread to send a single message to a producer. When the response arrives it
 * sends the result to the JMS queue using {@link MessageResult}. If the message 
 * never arrives an {@link MessageError} is sent.
 * @author Marcos Campos
 */
class SendMessageRunnable implements Runnable {

    private final ESBDeliveryInformation deliberyInfo;
    private final String message;
    private String reply;


    SendMessageRunnable(ESBDeliveryInformation deliberyInfo, String message) {
        this.deliberyInfo = deliberyInfo;
        this.message = message;
    }

    String getReply() {
        return reply;
    }

    /**
     * Calculates the response time between the consumer and the producer.
     * Sends the result to the Java App through JMS.
     */
    @Override
    public void run() {

        Message jmsMessage = null;
        long beforeSendTime = System.currentTimeMillis();

        try {

            reply = deliberyInfo.getESBSender().send(message);
            long afterSendTime = System.currentTimeMillis();

            // TODO extra nulls for "from" and "to"
            jmsMessage = new MessageResult(null,null,deliberyInfo.getConsumerId(),
                                deliberyInfo.getProducerId(),beforeSendTime,
                                afterSendTime,message.length(),getResponseDataSize(reply),
                                MessageResult.STATUS_OK);

        } catch (SocketTimeoutException e) {
            // Ran out of time, send lost message to the JavaAPP
            // TODO extra nulls for "from" and "to"
            jmsMessage = new MessageResult(null,null,deliberyInfo.getConsumerId(),
                                deliberyInfo.getProducerId(),beforeSendTime,
                                -1,message.length(),-1,
                                MessageResult.STATUS_TIMEOUT);

        } catch (Exception e) {
            e.printStackTrace();
        }

        if(jmsMessage != null) {
            sendToJavaApp(jmsMessage);
        }

    }

    /**
     * Sends the message through JMS.
     * @param jmsMessage the message to send.
     */
    private void sendToJavaApp(Message jmsMessage) {

        try {
            JMSManager.getInstance().getSender().send(jmsMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the size of the response.
     * @param respone the string with the response of the producer.
     * @return the size of the response.
     * @throws Exception if the response is malformed.
     */
    private int getResponseDataSize(String respone) throws Exception {
        return getResponseFromXML(reply).length();
    }


    /**
     * Gets the response from the xml returned by the producer.
     * @param xml the xml returned by the producer.
     * @return the response.
     * @throws Exception if the response is malformed.
     * TODO the response format depends on the WSDL of the producer.
     */
    private String getResponseFromXML(String xml) throws Exception {

        xml = xml.replaceAll("ns2:", "");

        String xpath = "/helloResponse/return";
        XPath xPath = XPathFactory.newInstance().newXPath();
        return xPath.evaluate(xpath, new InputSource(new StringReader(xml)));

    }
}
