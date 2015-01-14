/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swim.producer;

import com.swim.messaging.MessageHandler;
import static java.lang.Thread.sleep;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.ejb.Stateless;

import messaging.ProducerBehaviour;

import org.apache.commons.lang.StringUtils;

/**
 *
 * @author Thomas
 */
@WebService(serviceName = "ProducerService")
@Stateless()
public class ProducerService {

    private int responseSize = 12; // In bytes
    private int processingTime = 2000; //in milliseconds
    private String name = "";
    private long startTime;
    private Map<String, List<ProducerBehaviour>> producerBehaviours = new HashMap<>();

    public ProducerService() {
        super();

    }

    public ProducerService(int responseSize, int waitTime) {
        this.responseSize = responseSize;
        this.processingTime = waitTime;
        MessageHandler messageHandler = new MessageHandler(this);
    }

    @WebMethod(exclude = true)
    public void setResponseSize(int responseSize) {
        this.responseSize = responseSize;
    }

    @WebMethod(exclude = true)
    public void setProcessingTime(int processingTime) {
        this.processingTime = processingTime;
    }
    private final Charset UTF8_CHARSET = Charset.forName("UTF-8");

    String decodeUTF8(byte[] bytes) {
        return new String(bytes, UTF8_CHARSET);
    }

    byte[] encodeUTF8(String string) {
        return string.getBytes(UTF8_CHARSET);
    }

    private String createData() {
        byte[] utf8Bytes = new byte[getResponseSize()];
        for (int i = 0; i < getResponseSize(); i++) {
            utf8Bytes[i] = (byte) 'a';
        }
        String response = decodeUTF8(utf8Bytes);
        return response;
    }

    /**
     * Producer Service
     *
     * @return A string of the size defined in the conf
     */
    @WebMethod(operationName = "request")
    public String getRequest() {

        try {
            sleep(getProcessingTime());
        } catch (InterruptedException ex) {
            Logger.getLogger(ProducerService.class.getName()).log(Level.SEVERE, null, ex);
        }
        String idConsumer = "0"; // TODO : retrieve the ID of the consumer from the request

        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - startTime;
        int dataSize = getDataSize(idConsumer, elapsedTime);
        String response = createData();
        System.out.println("response : " + response);

        return response;
    }

    public int getDataSize(String idConsumer, long elapsedTime) {
        int dataSize = 0;
        List<ProducerBehaviour> behavioursList = getProducerBehaviours().get(idConsumer);
        int j = 0;
        boolean notFound = true;
        while (behavioursList.size() > j && notFound) {
            if (behavioursList.get(j).getBegin()<elapsedTime && behavioursList.get(j).getEnd()>=elapsedTime) {
                notFound= false;
                dataSize = behavioursList.get(j).getDatasize();
            }
            j++;
        }
        return dataSize;
    }

    /**
     * @return the responseSize
     */
    public int getResponseSize() {
        return responseSize;
    }

    /**
     * @return the processingTime
     */
    public int getProcessingTime() {
        return processingTime;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the producerBehaviours
     */
    public Map<String, List<ProducerBehaviour>> getProducerBehaviours() {
        return producerBehaviours;
    }

    /**
     * @param producerBehaviours the producerBehaviours to set
     */
    public void setProducerBehaviours(Map<String, List<ProducerBehaviour>> producerBehaviours) {
        this.producerBehaviours = producerBehaviours;
    }

}
