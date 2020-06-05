package org.recap.model.jpa;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by rajeshbabuk on 26/10/16.
 */
@Entity
@Table(name = "request_type_t", schema = "recap", catalog = "")
@AttributeOverride(name = "id", column = @Column(name = "REQUEST_TYPE_ID"))
public class RequestTypeEntity extends AbstractEntity<Integer> {

    @Column(name = "REQUEST_TYPE_CODE")
    private String requestTypeCode;

    @Column(name = "REQUEST_TYPE_DESC")
    private String requestTypeDesc;

    /**
     * Gets request type code.
     *
     * @return the request type code
     */
    public String getRequestTypeCode() {
        return requestTypeCode;
    }

    /**
     * Sets request type code.
     *
     * @param requestTypeCode the request type code
     */
    public void setRequestTypeCode(String requestTypeCode) {
        this.requestTypeCode = requestTypeCode;
    }

    /**
     * Gets request type desc.
     *
     * @return the request type desc
     */
    public String getRequestTypeDesc() {
        return requestTypeDesc;
    }

    /**
     * Sets request type desc.
     *
     * @param requestTypeDesc the request type desc
     */
    public void setRequestTypeDesc(String requestTypeDesc) {
        this.requestTypeDesc = requestTypeDesc;
    }
}
