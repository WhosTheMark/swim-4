package esbcomunication;

import messaging.ConsumerBehaviour;

/**
 * This class executes just one behavior and it create threads to send the messages
 * according to a frequency. Each task is scheduled by {@link BehaviorScheduler}.
 * @author Marcos Campos
 */
class BehaviorTask implements Runnable {

    private final ConsumerBehaviour behavior;
    private final ESBDeliveryInformation deliveryInfo;


    BehaviorTask(ConsumerBehaviour behavior, ESBDeliveryInformation deliveryInfo) {
        this.behavior = behavior;
        this.deliveryInfo = deliveryInfo;
    }

    /**
     * Runs one Behavior. It creates the message that all the threads will use
     * calculate the number of cycles that it will do and then sends the messages.
     */
    @Override
    public void run() {
        String message = buildMessage(behavior.getDatasize());
        int numberOfCycles = calculateNumberOfCycles();
        sendMessages(message, numberOfCycles);
    }

    /**
     * Calculates the number of cycles using begin, end and frequency values.
     * If the frequency is 0  it means it will not wait to send messages, so
     * the number of cycles equals the duration of the behavior.
     * @return the number of cycles the behavior will do.
     */
    private int calculateNumberOfCycles() {

        // -1 So the interval is opened at the end. [begin,end)
        int duration = behavior.getEnd() - behavior.getBegin() - 1;

        if(behavior.getFrequency() != 0) {
            return duration / behavior.getFrequency();
        }

        return duration;
    }

    /**
     * Sends all the messages of the behavior.
     * @param message the message all the threads will send.
     * @param numberOfCycles the number of messages it will send.
     */
    private void sendMessages(String message, int numberOfCycles){

        for(int i = 0; i <= numberOfCycles; ++i){
            SendMessageRunnable runnable = new SendMessageRunnable(deliveryInfo,message);
            new Thread(runnable).run();

            try {
                Thread.sleep(behavior.getFrequency());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Builds the message that will be sent. It builds the message using the
     * following format:
     *
     * <ns2:request xmlns:ns2="http://producer.swim.com/">
     *      <from>consumerId</from>
     *      <messageRequest>aaaaaaaaa</messageRequest>
     *  </ns2:request>
     *
     * @param datasize the size of the message.
     * @return the message to be sent.
     */
    private String buildMessage(int datasize){

        StringBuilder builder = new StringBuilder();

        builder.append("<ns2:request xmlns:ns2=\"http://producer.swim.com/\"><from>");

        builder.append(deliveryInfo.getConsumerId());
        builder.append("</from><messageRequest>");

        for(int i = 0; i < datasize; ++i){
            builder.append('a');
        }

        builder.append("</messageRequest></ns2:request>");

        return builder.toString();
    }
}
