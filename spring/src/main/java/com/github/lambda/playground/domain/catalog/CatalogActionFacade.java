package com.github.lambda.playground.domain.catalog;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** CatalogActionFacade handles all actions for catalog-bounded context operations. */
@Service
@Transactional
public class CatalogActionFacade {
  @Autowired
  public CatalogActionFacade() {
  }


}
