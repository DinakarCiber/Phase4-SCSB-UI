package org.recap.model.search;

/**
 * Created by rajeshbabuk on 11/10/17.
 */
public class BulkRequestResponse {

    private Integer bulkRequestId;
    private String screenMessage;
    private boolean success;

    /**
     * Gets bulk request id.
     *
     * @return the bulk request id
     */
    public Integer getBulkRequestId() {
        return bulkRequestId;
    }

    /**
     * Sets bulk request id.
     *
     * @param bulkRequestId the bulk request id
     */
    public void setBulkRequestId(Integer bulkRequestId) {
        this.bulkRequestId = bulkRequestId;
    }

    /**
     * Gets screen message.
     *
     * @return the screen message
     */
    public String getScreenMessage() {
        return screenMessage;
    }

    /**
     * Sets screen message.
     *
     * @param screenMessage the screen message
     */
    public void setScreenMessage(String screenMessage) {
        this.screenMessage = screenMessage;
    }

    /**
     * Is success boolean.
     *
     * @return the boolean
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Sets success.
     *
     * @param success the success
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }
}
