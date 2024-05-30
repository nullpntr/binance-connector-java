package com.binance.connector.client.utils.signaturegenerator;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Hex;

public final class HmacSignatureGenerator implements SignatureGenerator {

  private static final String HMAC_SHA256 = "HmacSHA256";

  private final Mac mac;

  public HmacSignatureGenerator(String apiSecret) {
    try {
      SecretKeySpec secretKeySpec = new SecretKeySpec(apiSecret.getBytes(), HMAC_SHA256);
      mac = Mac.getInstance(HMAC_SHA256);
      mac.init(secretKeySpec);
    } catch (NoSuchAlgorithmException | InvalidKeyException e) {
      throw new RuntimeException(e);
    }
  }

  public String getSignature(String data) {
    try {
      return Hex.encodeHexString(mac.doFinal(data.getBytes()));
    } catch (Exception e) {
      throw new RuntimeException("Failed to calculate hmac-sha256", e);
    }
  }
}
