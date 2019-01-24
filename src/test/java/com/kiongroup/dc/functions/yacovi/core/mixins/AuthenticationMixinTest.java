package com.kiongroup.dc.functions.yacovi.core.mixins;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.kiongroup.dc.functions.yacovi.core.mixin.AuthenticationMixin;
import java.text.ParseException;
import org.junit.Test;


/**
 * Unit test for Function class.
 */
public class AuthenticationMixinTest implements AuthenticationMixin {

    private static final String DUMMY_BEARER_TOKEN = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6Im5iQ3dXMTF3M1hrQi14VWFYd0tSU0xqTUhHUSIsImtpZCI6Im5iQ3dXMTF3M1hrQi14VWFYd0tSU0xqTUhHUSJ9.eyJhdWQiOiI5MDQ2Y2U1Ny0zMDgyLTRkOGItYmVjNC0zYjc2YWM3M2Y0YmMiLCJpc3MiOiJodHRwczovL3N0cy53aW5kb3dzLm5ldC8xM2M3MjhlMC1iYjBjLTRjZjctOGUxMC01YjMyNzI3OWQ2ZDkvIiwiaWF0IjoxNTQ4MzM4NTQ2LCJuYmYiOjE1NDgzMzg1NDYsImV4cCI6MTU0ODM0MjQ0NiwiYWlvIjoiQVNRQTIvOEtBQUFBbUdCU0JyTFQ0STdXM1VwNXNqYTEreGNrVm0rZytnaFdkcFRyTHAzOFIzUT0iLCJhbXIiOlsicHdkIl0sImZhbWlseV9uYW1lIjoiU2NodWVybWFubiIsImdpdmVuX25hbWUiOiJTdmVuIiwiaXBhZGRyIjoiODAuMTQ5LjY4LjUyIiwibmFtZSI6IlNjaHVlcm1hbm4sIFN2ZW4iLCJub25jZSI6ImQ3OTMxNDcxLTQyNTAtNGUwZS1hZjFkLTgyY2UyYjRmM2Y4ZiIsIm9pZCI6ImQwOGNjN2M4LTY3ODAtNDU4MS04ODg2LTBjMGNlMWRlZDc3YSIsIm9ucHJlbV9zaWQiOiJTLTEtNS0yMS0yMTk1NzM0NC0xMjYwMjIxMDUyLTExNTk0MjIyMjUtMzAxMjMyIiwic3ViIjoiUWYwWW1vdUt3bUl3dHR5MzVqTEU1MU5qQXdWSW1LNTgzc0txOWNON0haNCIsInRpZCI6IjEzYzcyOGUwLWJiMGMtNGNmNy04ZTEwLTViMzI3Mjc5ZDZkOSIsInVuaXF1ZV9uYW1lIjoic3Zlbi5zY2h1ZXJtYW5uQGtpb25ncm91cC5jb20iLCJ1cG4iOiJzdmVuLnNjaHVlcm1hbm5Aa2lvbmdyb3VwLmNvbSIsInV0aSI6IkFWdmJhRE14VFVXRFJkTHZVVUFpQUEiLCJ2ZXIiOiIxLjAifQ.PwdbHJvb_mvLIX4JfH1HYR3Uev711ZOoBvhhg7OUwF4rSz88su2wDN3XrHKvncSI8LlHZQErEilhlqRQfKZdR7K095pFqYTsTeXAh-0qqyRpdCdm3v6GZa17NpRvSwi8nRNRVsDAZIFuQa2gRIu-PBHCXURni-x0vTm0SgphIO-34R2HdGhM5th05HTgJIp3IGEkxpptUQ14Opofn4RNI5oW5lnLQHkWN_MXzq2HGQQQYUZOBbPpxMGzBGfOI7rO8MUMZlM0N4rIY12yFU4dGSi7mJY-rWPDa0C0ZnbT6_80UdrP8ctgvKjXhjWQv8Bi98zErMjOAb1fXl6tA9Y3tA";

    @Test
    public void verifyJwtSignature()
        throws Exception {

        assertFalse(this.verifyJwtSignature(DUMMY_BEARER_TOKEN));

    }

    @Test
    public void verifyTokenClaimsTest()
        throws Exception {

        assertFalse(this.verifyJwtClaims(DUMMY_BEARER_TOKEN));

    }

    @Test
    public void isJwtValidTest() {

        assertFalse(this.isJwtValid(DUMMY_BEARER_TOKEN));

    }

}
