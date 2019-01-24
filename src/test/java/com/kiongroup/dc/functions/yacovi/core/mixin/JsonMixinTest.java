package com.kiongroup.dc.functions.yacovi.core.mixin;

import static org.junit.Assert.*;

import com.kiongroup.dc.functions.yacovi.core.model.YaCoViHttpErrorResponse;
import com.microsoft.azure.functions.HttpStatus;
import org.junit.Test;

public class JsonMixinTest {

  @Test
  public void toJsonTest() {
    String json = new YaCoViHttpErrorResponse(HttpStatus.UNAUTHORIZED.name()).toJson();
    assertTrue(json.startsWith("{"));
    assertTrue(json.contains(HttpStatus.UNAUTHORIZED.name()));
  }

}
