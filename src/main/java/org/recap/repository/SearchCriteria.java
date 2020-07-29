package org.recap.repository;

import lombok.Data;

/**
 * Class to hold the Query Criteria and Specification details.
 *
 * @author Ramanujam (CEI)
 */

@Data
public class SearchCriteria {
  private String key;
  private Object value;
  private SearchOperation operation;

  public SearchCriteria() {
  }

  public SearchCriteria(String key, Object value, SearchOperation operation) {
    this.key = key;
    this.value = value;
    this.operation = operation;
  }
}
