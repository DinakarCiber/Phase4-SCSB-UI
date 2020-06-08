package org.recap.model.jpa;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * Abstract implementation for {@link Identifiable} POJOs. It includes default
 * implementations for the <code>toString()</code>, <code>equals(Object)</code>
 * and <code>hashCode</code> methods.
 *
 * @param <ID>
 *          the interface/class of the entity ID. It must extend/implement
 *          <code>Serializable</code>.
 */
@MappedSuperclass
public abstract class IdentifiableBase<ID extends Serializable> implements Identifiable<ID> {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected ID id;

    /**
     * Instantiates a new identifiable base.
     */
    public IdentifiableBase() {
        super();
    }

    /**
     * Instantiates a new identifiable base.
     *
     * @param id
     *          the id
     */
    public IdentifiableBase(ID id) {
        super();
        this.id = id;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.practice.springcloud.model.Identifiable#getId()
     */
    @Override
    public ID getId() {
        return id;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.practice.springcloud.model.Identifiable#setId(java.io.Serializable)
     */
    @Override
    public void setId(ID id) {

        this.id = id;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @SuppressWarnings("rawtypes")
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }

        boolean equals = false;
        if (getClass().isAssignableFrom(obj.getClass())) {
            Identifiable that = (Identifiable) obj;
            equals = new EqualsBuilder().append(this.getId(), that.getId()).isEquals();
        }
        return equals;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        if (this.getId() == null) {
            return new HashCodeBuilder().appendSuper(super.hashCode()).toHashCode();
        }
        return new HashCodeBuilder().append(this.getId()).toHashCode();
    }

    /**
     * To string.
     *
     * @return the string
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", this.getId()).toString();
    }

}
