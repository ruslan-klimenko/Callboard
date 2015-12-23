package org.agileengine.callboard.model.persistence;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public abstract class AbstractModel {
    @Override
    public String toString() { return ReflectionToStringBuilder.toString(this); }
    @Override
    public boolean equals(Object o) { return EqualsBuilder.reflectionEquals(this, o); }
    @Override
    public int hashCode() { return HashCodeBuilder.reflectionHashCode(this); }
}
