package org.recap.repository;

/**
 * ENUM which defines the list of operation, which will be performed on the domain object using the
 * org.springframework.data.jpa.domain.Specification.
 *
 * @author Ramanujam (CEI)
 */
public enum  SearchOperation {
  GREATER_THAN,
  LESS_THAN,
  GREATER_THAN_EQUAL,
  LESS_THAN_EQUAL,
  NOT_EQUAL,
  EQUAL,
  CONTAINS,
  ENDS_WITH,
  STARTS_WITH,
  IN,
  NOT_IN
}
