package com.swim.messaging;

public class MessageConfigurationProducer extends Message {

    private int duration;
    private int dataSize;

    // Dummy constructor. Useful for Jackson
    public MessageConfigurationProducer() {
        
    }

    public MessageConfigurationProducer(String from, String to, int duration,
            int dataSize) {
        super(from, to);
        this.duration = duration;
        this.dataSize = dataSize;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDataSize() {
        return dataSize;
    }

    public void setDataSize(int dataSize) {
        this.dataSize = dataSize;
    }
}
