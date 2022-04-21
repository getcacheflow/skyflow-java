package com.skyflow.api.util;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

import java.util.Date;
import org.apache.commons.codec.binary.Base64;
import com.skyflow.api.SkyflowConfiguration;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtil {
  
  private static PrivateKey getPrivateKeyFromPem(String pemKey) throws SkyflowException {

      String PKCS8PrivateHeader = "-----BEGIN PRIVATE KEY-----";
      String PKCS8PrivateFooter = "-----END PRIVATE KEY-----";

      String privateKeyContent = pemKey;
      PrivateKey privateKey = null;
      if (pemKey.contains(PKCS8PrivateHeader)) {
          privateKeyContent = privateKeyContent.replace(PKCS8PrivateHeader, "");
          privateKeyContent = privateKeyContent.replace(PKCS8PrivateFooter, "");
          privateKeyContent = privateKeyContent.replace("\n", "");
          privateKeyContent = privateKeyContent.replace("\r\n", "");
          privateKey = parsePkcs8PrivateKey(Base64.decodeBase64(privateKeyContent));
      } else {
          throw new SkyflowException(ErrorCode.UnableToRetrieveRSA);
      }
      return privateKey;
  }

  /**
   * Create a PrivateKey instance from raw PKCS#8 bytes.
   */
  private static PrivateKey parsePkcs8PrivateKey(byte[] pkcs8Bytes) throws SkyflowException {
      KeyFactory keyFactory;
      PrivateKey privateKey = null;
      try {
          PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pkcs8Bytes);
          keyFactory = KeyFactory.getInstance("RSA");
          privateKey = keyFactory.generatePrivate(keySpec);
      } catch (NoSuchAlgorithmException e) {
          throw new SkyflowException(ErrorCode.NoSuchAlgorithm, e);
      } catch (InvalidKeySpecException e) {
          throw new SkyflowException(ErrorCode.InvalidKeySpec, e);
      }
      return privateKey;
  }

  public static String getSignedJwt(SkyflowConfiguration config) throws SkyflowException {
      final Date createdDate = new Date();
      final Date expirationDate = new Date(createdDate.getTime() + (3600 * 1000));
      String signedToken = Jwts.builder()
              .claim("iss", config.getClientId())
              .claim("key", config.getKeyId())
              .claim("aud", config.getTokenUri())
              .claim("sub", config.getClientId())
              .setExpiration(expirationDate)
              .signWith(SignatureAlgorithm.RS256, getPrivateKeyFromPem(config.getPrivateKeyString()))
              .compact();

      return signedToken;
  }

}
