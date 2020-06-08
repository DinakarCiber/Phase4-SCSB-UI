package org.recap.model.jpa;

import java.io.Serializable;

/**
 * Base interface for classes which need to distinguish themselves for identity
 * and comparison purposes. It assumes that the persistent class that extends
 * this interface corresponds to 1 record in the database.
 *
 * @param <ID>
 *          the interface/class of the entity ID. It must extend/implement
 *          <code>Serializable</code>.
 */
public interface Identifiable<ID extends Serializable> extends Serializable {

    /**
     * Returns the unique identifier of the object, which is usually their primary
     * key in the database table. It will support the different types of primary
     * keys, including composite keys.
     *
     * @return the unique ID of the object.
     */
    ID getId();

    /**
     * Sets the unique identifier of the object. This method will generally be
     * called in the code for testing purposes. When retrieving a persistent object,
     * the persistence framework will automatically populate this field.
     *
     * @param id
     *          the unique identifier of the object.
     */
    void setId(ID id);

}
