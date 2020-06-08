package org.recap.model.jpa;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by SheikS on 8/8/2016.
 */
@Entity
@Table(name = "REPORT_DATA_T", schema = "RECAP", catalog = "")
@AttributeOverride(name = "id", column = @Column(name = "REPORT_DATA_ID"))
public class ReportDataEntity extends AbstractEntity<Integer> {

    @Column(name = "HEADER_NAME")
    private String headerName;

    @Column(name = "HEADER_VALUE")
    private String headerValue;

    @Column(name = "RECORD_NUM")
    private String recordNum;

    /**
     * Gets record num.
     *
     * @return the record num
     */
    public String getRecordNum() {
        return recordNum;
    }

    /**
     * Sets record num.
     *
     * @param recordNum the record num
     */
    public void setRecordNum(String recordNum) {
        this.recordNum = recordNum;
    }

    /**
     * Gets header name.
     *
     * @return the header name
     */
    public String getHeaderName() {
        return headerName;
    }

    /**
     * Sets header name.
     *
     * @param headerName the header name
     */
    public void setHeaderName(String headerName) {
        this.headerName = headerName;
    }

    /**
     * Gets header value.
     *
     * @return the header value
     */
    public String getHeaderValue() {
        return headerValue;
    }

    /**
     * Sets header value.
     *
     * @param headerValue the header value
     */
    public void setHeaderValue(String headerValue) {
        this.headerValue = headerValue;
    }

}
