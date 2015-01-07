/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swim.producer;

import static java.lang.Thread.sleep;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.ejb.Stateless;

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

    public ProducerService() {
        super();
    }

    public ProducerService(int responseSize, int waitTime) {
        this.responseSize = responseSize;
        this.processingTime = waitTime;
    }

    @WebMethod(exclude=true)
    public void setResponseSize(int responseSize) {
        this.responseSize = responseSize;
    }

    @WebMethod(exclude=true)
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
        byte[] utf8Bytes = new byte[responseSize];
        for (int i = 0; i < responseSize; i++) {
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
            sleep(processingTime);
        } catch (InterruptedException ex) {
            Logger.getLogger(ProducerService.class.getName()).log(Level.SEVERE, null, ex);
        }

        String response = createData();
        System.out.println("response : " + response);

        return response;
    }

}
