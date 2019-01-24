package com.kiongroup.dc.functions.yacovi.core.mixin;

import com.kiongroup.dc.functions.yacovi.core.exceptions.AzureTenantKeysUnavailableException;
import com.kiongroup.dc.functions.yacovi.core.model.AzureTenantLoginKeys;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

public interface AzureTenantKeysMixin {
  default AzureTenantLoginKeys getAzureTenantKeys() throws AzureTenantKeysUnavailableException {
    try {
      return ClientBuilder
          .newClient()
          .target("https://login.microsoftonline.com/13c728e0-bb0c-4cf7-8e10-5b327279d6d9/discovery/v2.0/keys")
          .request(MediaType.APPLICATION_JSON)
          .get(AzureTenantLoginKeys.class);
    } catch (Exception e) {
      throw new AzureTenantKeysUnavailableException(e);
    }
  }

}
