package com.kiongroup.dc.functions.yacovi.core.mixins;

import static org.junit.Assert.*;

import com.kiongroup.dc.functions.yacovi.core.exceptions.AzureTenantKeysUnavailableException;
import com.kiongroup.dc.functions.yacovi.core.mixin.AzureTenantKeysMixin;
import org.junit.Test;

import com.kiongroup.dc.functions.yacovi.core.model.AzureTenantLoginKeys;

public class AzureTenantKeysMixinTest implements AzureTenantKeysMixin {

  @Test
  public void getAzureTenantKeysTest() throws AzureTenantKeysUnavailableException {
    AzureTenantLoginKeys azureTenantKeys = this.getAzureTenantKeys();
    assertTrue(azureTenantKeys.getKeys().size() > 0);
  }

}
