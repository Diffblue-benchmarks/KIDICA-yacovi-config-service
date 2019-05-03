package com.kiongroup.dc.functions.core.mixin;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.kiongroup.dc.function.core.exception.AzureTenantKeysUnavailableException;
import com.kiongroup.dc.function.core.mixin.AzureTenantKeysMixin;
import com.kiongroup.dc.function.core.model.AzureTenantLoginKeys;

public class AzureTenantKeysMixinTest implements AzureTenantKeysMixin {

  @Test
  public void getAzureTenantKeysTest() throws AzureTenantKeysUnavailableException {
    AzureTenantLoginKeys azureTenantKeys = this.getAzureTenantKeys();
    assertTrue(azureTenantKeys.getKeys().size() > 0);
  }

}
