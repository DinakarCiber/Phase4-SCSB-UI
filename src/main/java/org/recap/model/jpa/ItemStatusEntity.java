package org.recap.model.jpa;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by pvsubrah on 6/11/16.
 */
@Entity
@Table(name = "item_status_t", schema = "recap", catalog = "")
@AttributeOverride(name = "id", column = @Column(name = "ITEM_STATUS_ID"))
public class ItemStatusEntity extends AbstractEntity<Integer>{

    @Column(name = "STATUS_CODE")
    private String statusCode;

    @Column(name = "STATUS_DESC")
    private String statusDescription;

    /**
     * Gets status code.
     *
     * @return the status code
     */
    public String getStatusCode() {
        return statusCode;
    }

    /**
     * Sets status code.
     *
     * @param statusCode the status code
     */
    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * Gets status description.
     *
     * @return the status description
     */
    public String getStatusDescription() {
        return statusDescription;
    }

    /**
     * Sets status description.
     *
     * @param statusDescription the status description
     */
    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }
}
