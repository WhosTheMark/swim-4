package com.swim.producer;

import java.nio.charset.Charset;
import java.util.ArrayList;
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

    private int dataSize ; //in BYTES
    private String name = "";
    private final String data;
    private Map<String, List<ProducerBehaviour>> producerBehaviours = new HashMap<>();

    public Model(int dataSize) {
        this.dataSize = dataSize;
        this.data = createData(dataSize);
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
    
    
    private void populateWithFakeData(){
        List<ProducerBehaviour> producerBehaviourList = new ArrayList<>();
        producerBehaviourList.add(new ProducerBehaviour(0, 3, 12));
        producerBehaviourList.add(new ProducerBehaviour(3, 6, 20));
        HashMap<String, List<ProducerBehaviour>> producersBehaviours = new HashMap<>();
        producersBehaviours.put("aa", producerBehaviourList);
        producerBehaviourList = new ArrayList<>();
        producerBehaviourList.add(new ProducerBehaviour(2, 3000, 200));
        producersBehaviours.put("bb", producerBehaviourList);
        this.producerBehaviours= producersBehaviours;
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
}
