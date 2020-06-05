package org.recap.model.jpa;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by pvsubrah on 6/11/16.
 */
@Entity
@Table(name = "institution_t", schema = "recap", catalog = "")
@AttributeOverride(name = "id", column = @Column(name = "INSTITUTION_ID"))
public class InstitutionEntity extends AbstractEntity<Integer> {

    @Column(name = "INSTITUTION_CODE")
    private String institutionCode;

    @Column(name = "INSTITUTION_NAME")
    private String institutionName;

    /**
     * Gets institution code.
     *
     * @return the institution code
     */
    public String getInstitutionCode() {
        return institutionCode;
    }

    /**
     * Sets institution code.
     *
     * @param institutionCode the institution code
     */
    public void setInstitutionCode(String institutionCode) {
        this.institutionCode = institutionCode;
    }

    /**
     * Gets institution name.
     *
     * @return the institution name
     */
    public String getInstitutionName() {
        return institutionName;
    }

    /**
     * Sets institution name.
     *
     * @param institutionName the institution name
     */
    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }
}
