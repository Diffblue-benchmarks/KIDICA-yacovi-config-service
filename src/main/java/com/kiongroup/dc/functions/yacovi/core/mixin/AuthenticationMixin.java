package com.kiongroup.dc.functions.yacovi.core.mixin;

import com.kiongroup.dc.functions.yacovi.core.exceptions.AzureTenantKeysUnavailableException;
import com.kiongroup.dc.functions.yacovi.core.exceptions.JwtVerificationException;
import com.kiongroup.dc.functions.yacovi.core.exceptions.ParseBearerTokenException;
import com.kiongroup.dc.functions.yacovi.core.model.AzureTenantLoginKey;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.SignedJWT;
import com.nimbusds.jwt.proc.BadJWTException;
import com.nimbusds.jwt.proc.DefaultJWTClaimsVerifier;
import java.io.ByteArrayInputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;
import javax.xml.bind.DatatypeConverter;

public interface AuthenticationMixin extends AzureTenantKeysMixin {

  default Boolean isJwtValid(final String token) {
    try {
      return isJwtValid(parseTokenFromString(token));
    } catch (Exception e) {
      return Boolean.FALSE;
    }
  }

  default Boolean isJwtValid(final SignedJWT signedJWT) {
    try {
      return verifyJwtClaims(signedJWT) && verifyJwtSignature(signedJWT);
    } catch (JwtVerificationException e) {
      return Boolean.FALSE;
    }
  }

  default SignedJWT parseTokenFromString(final String token) throws ParseBearerTokenException {
    String sanitizedToken = (token.startsWith("Bearer ")) ? token.split("Bearer ")[1] : token;
    try {
      return SignedJWT.parse(sanitizedToken);
    } catch (ParseException e) {
      throw new ParseBearerTokenException(e);
    }
  }

  default Boolean verifyJwtSignature(final String token) throws JwtVerificationException {
    try {
      return verifyJwtSignature(parseTokenFromString(token));
    } catch (ParseBearerTokenException e) {
      return Boolean.FALSE;
    }
  }

  default Boolean verifyJwtSignature(final SignedJWT signedJWT) throws JwtVerificationException {
    try {
      AzureTenantLoginKey x509Cert = this.getAzureTenantKeys()
          .getKeys()
          .stream()
          .filter(azureTenantLoginKey -> azureTenantLoginKey.getX5t()
              .equals(signedJWT.getHeader().getKeyID()))
          .findFirst()
          .orElseThrow(AzureTenantKeysUnavailableException::new);

      RSAPublicKey publicKey = getRsaPublicKey(x509Cert.getX5c().get(0));
      RSASSAVerifier rsassaVerifier = new RSASSAVerifier(publicKey);

      return signedJWT.verify(rsassaVerifier);
    } catch (CertificateException | JOSEException | AzureTenantKeysUnavailableException e) {
      return Boolean.FALSE;
    }
  }

  default Boolean verifyJwtClaims(final String token) throws ParseBearerTokenException {
    return verifyJwtClaims(parseTokenFromString(token));
  }

  default Boolean verifyJwtClaims(final SignedJWT signedJWT) {
    DefaultJWTClaimsVerifier defaultJWTClaimsVerifier = new DefaultJWTClaimsVerifier();
    try {
      defaultJWTClaimsVerifier.verify(signedJWT.getJWTClaimsSet());
    } catch (BadJWTException | ParseException e) {
      return Boolean.FALSE;
    }
    return Boolean.TRUE;
  }

  default RSAPublicKey getRsaPublicKey(final String x509Cert) throws CertificateException {
    CertificateFactory factory = CertificateFactory.getInstance("X.509");
    X509Certificate cert = (X509Certificate) factory.generateCertificate(new ByteArrayInputStream(
        DatatypeConverter.parseBase64Binary(x509Cert)));
    return (RSAPublicKey) cert.getPublicKey();
  }

}
