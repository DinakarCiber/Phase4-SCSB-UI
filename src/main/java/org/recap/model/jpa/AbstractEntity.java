package org.recap.model.jpa;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public abstract class AbstractEntity<ID extends Serializable> extends IdentifiableBase<ID> {

}
