package esbcomunication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import jmsconsumer.JMSManager;
import messaging.ConsumerBehaviour;
import messaging.Message;
import messaging.MessageType;

/**
 * This class schedules when each behavior will start. This class cannot be
 * created directly, to do this use {@link BehaviorSchedulerBuilder} class.
 * @author Marcos Campos
 */
public class BehaviorScheduler {

    private List<ConsumerBehaviour> behaviors;
    private ESBDeliveryInformation deliveryInfo;

    /** Max number of threads in the pool.*/
    private static final int MAX_NUMBER_THREADS = 4;

    private static final int MAX_HOURS_TO_WAIT = 3;

    /**
     * Class to build a {@link BehaviorScheduler} object.
     * @author Marcos Campos
     */
    public static class BehaviorSchedulerBuilder {

        private List<ConsumerBehaviour> behaviors = null;
        private String consumerId;
        private String producerId;
        private String esbAddress;

        public BehaviorSchedulerBuilder() {}

        /**
         * Adds a single behavior to the list. Can be called several times to
         * add all the behaviors needed.
         * @param behavior to add.
         * @return the reference to the builder.
         */
        public BehaviorSchedulerBuilder addBehavior(ConsumerBehaviour behavior){

            if (behaviors == null) {
                behaviors = new ArrayList<>();
            }

            behaviors.add(behavior);
            return this;
        }

        public BehaviorSchedulerBuilder setConsumerId(String consumerId){
            this.consumerId = consumerId;
            return this;
        }

        public BehaviorSchedulerBuilder setProducerId(String producerId){
            this.producerId = producerId;
            return this;
        }

        public BehaviorSchedulerBuilder setESBAddress(String esbAddress){
            this.esbAddress = esbAddress;
            return this;
        }

        public BehaviorSchedulerBuilder setBehaviors(List<ConsumerBehaviour> behaviors){
            this.behaviors = behaviors;
            return this;
        }

        /**
         * Builds a {@link BehaviorScheduler} object.
         * @throws IllegalStateException if any value to create the object is missing.
         */
        public BehaviorScheduler build() {

            if(behaviors == null || consumerId == null || producerId == null
                    || esbAddress == null) {
                throw new IllegalStateException("Some parameters were not set when"
                        + "build() was called.");
            }

            return new BehaviorScheduler(behaviors,consumerId,producerId,
                    esbAddress);
        }
    }

    private BehaviorScheduler(List<ConsumerBehaviour> behaviors, String consumerId,
            String producerId, String esbAddress) {

        this.behaviors = behaviors;
        this.deliveryInfo = new ESBDeliveryInformation(consumerId,producerId,esbAddress);
    }

    /**
     * Schedules all the behaviors added by the builder.
     */
    public void scheduleBehaviors() {

        ScheduledExecutorService executor =
                Executors.newScheduledThreadPool(MAX_NUMBER_THREADS);

        for(ConsumerBehaviour behavior : behaviors){
            BehaviorTask task = new BehaviorTask(behavior, deliveryInfo);
            executor.schedule(task, behavior.getBegin(), TimeUnit.MILLISECONDS);
        }

        waitForBehaviors(executor);
        sendFinishedMessage();
    }

    /**
     * Sends the message to alert the java application that the consumer finished.
     */
    private void sendFinishedMessage() {

        Message msg = new Message(deliveryInfo.getConsumerId(),null);
        msg.setType(MessageType.STOP);

        try {
            JMSManager.getInstance().getSender().send(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Waits for all the tasks to finish.
     * @param executor the pool with all tasks.
     */
    private void waitForBehaviors(ScheduledExecutorService executor) {
        try {
            executor.awaitTermination(MAX_HOURS_TO_WAIT, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
