package esbcomunication;

class ESBDeliveryInformation {

    private final String consumerId;
    private final String producerId;
    private final String esbAddress;
    private final ESBMessageSender sender;

    ESBDeliveryInformation(String consumerId, String producerId,
            String esbAddress) {

        this.consumerId = consumerId;
        this.producerId = producerId;
        this.esbAddress = esbAddress;
        this.sender = new ESBMessageSender(buildSendingAddress());
    }

    String getConsumerId() {
        return consumerId;
    }

    String getProducerId() {
        return producerId;
    }

    String getEsbAddress() {
        return esbAddress;
    }

    ESBMessageSender getESBSender() {
        return sender;
    }

    private String buildSendingAddress() {
        return esbAddress + "/" + producerId;
    }
}