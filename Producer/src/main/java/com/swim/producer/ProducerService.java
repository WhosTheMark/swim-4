/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swim.producer;

import static java.lang.Thread.sleep;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.ejb.Stateless;
import javax.jws.WebParam;
import jmsproducer.JMSManager;

/**
 *
 * @author Thomas
 */
@WebService(serviceName = "ProducerService")
@Stateless()
public class ProducerService {

    private Model model;

    public ProducerService() {
        this.model = Model.getInstance();
        JMSManager.getInstance();
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\nMS manager instanciated");
    }

    public ProducerService(int dataSize) {
        this.model = Model.getInstance();
        this.model.setDataSize(dataSize);
    }

    /**
     * Producer Service
     *
     * @param from
     * @param messageRequest
     * @return A string of the size defined in the conf
     */
    @WebMethod(operationName = "request")
    public String getRequest(@WebParam(name = "from") String from, @WebParam(name = "messageRequest") String messageRequest) {
        if (model.getState() == Model.State.RUN) {
            long currentTime = System.currentTimeMillis();
            long elapsedTime = currentTime - model.getStartTime();
            try {
                sleep(model.getProcessingTime(from, elapsedTime));
            } catch (InterruptedException ex) {
                Logger.getLogger(ProducerService.class.getName()).log(Level.SEVERE, null, ex);
            }
            String response = getModel().getData();
            System.out.println("response : " + response);

            return response;
        } else {
            System.out.println("response : null");
            return null;
        }
    }

    /**
     * @return the model
     */
    @WebMethod(exclude = true)
    public Model getModel() {
        return model;
    }
}
