package com.kiongroup.dc.functions.core.mixin;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.kiongroup.dc.function.core.model.HttpErrorResponse;
import com.microsoft.azure.functions.HttpStatus;

public class JsonMixinTest {

  @Test
  public void toJsonTest() {
    String json = new HttpErrorResponse(HttpStatus.UNAUTHORIZED.name()).toJson();
    assertTrue(json.startsWith("{"));
    assertTrue(json.contains(HttpStatus.UNAUTHORIZED.name()));
  }

}
