package com.swim.producer;

import java.nio.charset.Charset;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import messaging.ProducerBehaviour;

/**
 *
 * @author Thomas
 */
public class Model {

    private final Charset UTF8_CHARSET = Charset.forName("UTF-8");

    private long startTime;
    private String id;
    private int dataSize;
    private String name;
    private String data ="";
    private Map<String, List<ProducerBehaviour>> producerBehaviours = new HashMap<>();

    /**
     * Instance unique non préinitialisée
     */
    private static Model INSTANCE = null;

    /**
     * Point d'accès pour l'instance unique du singleton
     */
    public static synchronized Model getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Model();
        }
        return INSTANCE;
    }

    /**
     * @return the state
     */
    public State getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(State state) {
        this.state = state;
        startTime = System.currentTimeMillis();
    }

    /**
     * @return the startTime
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public enum State {
        WAITCONFIG,
        WAITSTART,
        RUN,
        FINISH
    }

    private State state;

    private Model() {
        this.state = State.WAITCONFIG;
        // populateWithFakeData(); //Only for testing
    }

    String decodeUTF8(byte[] bytes) {
        return new String(bytes, UTF8_CHARSET);
    }

    byte[] encodeUTF8(String string) {
        return string.getBytes(UTF8_CHARSET);
    }

    private String createData(int dataSize) {
        byte[] utf8Bytes = new byte[dataSize];
        for (int i = 0; i < dataSize; i++) {
            utf8Bytes[i] = (byte) 'a';
        }
        String response = decodeUTF8(utf8Bytes);
        return response;
    }

    public int getProcessingTime(String idConsumer, long elapsedTime) {
        int processingTime = 0;
        List<ProducerBehaviour> behavioursList = getProducerBehaviours().get(idConsumer);
        int j = 0;
        boolean notFound = true;
        while (behavioursList.size() > j && notFound) {
            if (behavioursList.get(j).getBegin() < elapsedTime) {
                notFound = false;
                processingTime = behavioursList.get(j).getProcessingTime();
            }
            j++;
        }
        return processingTime;
    }

    /**
     * @return the dataSize
     */
    public int getDataSize() {
        return dataSize;
    }

    /**
     * @param dataSize the dataSize to set
     */
    public void setDataSize(int dataSize) {
        this.dataSize = dataSize;
        this.data = createData(dataSize);
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

    /**
     * @return the data
     */
    public String getData() {
        return data;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }
}
