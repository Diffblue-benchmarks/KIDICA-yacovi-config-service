package com.kiongroup.dc.functions.yacovi.core.exceptions;

public class AzureTenantKeysUnavailableException extends Exception{

  public AzureTenantKeysUnavailableException(Exception e) {
    super(e);
  }

  public AzureTenantKeysUnavailableException() {
    super();
  }
}
