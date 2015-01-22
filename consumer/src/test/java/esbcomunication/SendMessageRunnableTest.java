package esbcomunication;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import java.net.SocketTimeoutException;

import jmsconsumer.ConsumerSender;
import jmsconsumer.JMSManager;
import messaging.MessageResult;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Marcos Campos
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(JMSManager.class)
public class SendMessageRunnableTest {

    @Mock private ESBMessageSender senderMock;
    @Mock private ESBDeliveryInformation deliberyInfoMock;

    private static JMSManager manager;
    private static ConsumerSender consumerSenderMock;

    private static final String MESSAGE = "I'm a message";
    private static final String REPLY =   "<ns2:hello xmlns:ns2=\"http://serv/\">"
                                        + "<name>World</name>"
                                        + "</ns2:hello>";

    /*
     * Mocks JMS queue and the information used to send messages.
     */
    @Before
    public void setUp() {
        mockStatic(JMSManager.class);
        manager = mock(JMSManager.class);
        consumerSenderMock = mock(ConsumerSender.class);
        when(manager.getSender()).thenReturn(consumerSenderMock);
        when(JMSManager.getInstance()).thenReturn(manager);
        when(deliberyInfoMock.getESBSender()).thenReturn(senderMock);
    }

    /*
     * Tests normal behavior.
     */
    @Test
    public void testRun() throws Exception {
        when(senderMock.send(MESSAGE)).thenReturn(REPLY);
        String reply = runAndGetReply();
        verify(consumerSenderMock).send(isA(MessageResult.class));
        assertEquals(REPLY, reply);
    }

    /*
     * Tests behavior when message is lost.
     */
    @Test
    public void testRunTimeout() throws Exception {
        SocketTimeoutException exception = new SocketTimeoutException();
        when(senderMock.send(MESSAGE)).thenThrow(exception);
        String reply = runAndGetReply();
        verify(consumerSenderMock).send(isA(MessageResult.class));
        assertNull(reply);
    }

    /*
     * Creates the object to test and executes the method.
     */
    private String runAndGetReply() throws Exception {
        SendMessageRunnable runnable = new SendMessageRunnable(deliberyInfoMock,MESSAGE);
        runnable.run();
        String reply = runnable.getReply();
        verify(senderMock).send(MESSAGE);
        return reply;
    }

}
