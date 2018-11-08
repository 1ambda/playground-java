package com.github.lambda.gateway.domain.catalog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * CatalogActionFacade handles all actions for catalog-bounded context operations.
 */
@Service
@Transactional
public class CatalogActionFacade {
  @Autowired
  public CatalogActionFacade() {
  }
}
