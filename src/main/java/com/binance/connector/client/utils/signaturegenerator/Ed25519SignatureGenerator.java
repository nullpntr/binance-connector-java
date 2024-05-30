package com.binance.connector.client.utils.signaturegenerator;

import com.binance.connector.client.utils.ParameterChecker;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Security;
import java.util.Base64;
import org.bouncycastle.crypto.params.Ed25519PrivateKeyParameters;
import org.bouncycastle.crypto.signers.Ed25519Signer;
import org.bouncycastle.crypto.util.PrivateKeyFactory;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;

public final class Ed25519SignatureGenerator implements SignatureGenerator {

    private Ed25519PrivateKeyParameters privateKey;
    private final int offset = 0;
    private Ed25519Signer signer;

    public Ed25519SignatureGenerator(String privateKey) throws FileNotFoundException, IOException {

        ParameterChecker.checkParameterType(privateKey, String.class, "privateKey");

        Security.addProvider(new BouncyCastleProvider());
        PemReader pemReader = new PemReader(new FileReader(privateKey));
        PemObject pemObject = pemReader.readPemObject();
        byte[] privateKeyBytes = pemObject.getContent();
        this.privateKey = (Ed25519PrivateKeyParameters) PrivateKeyFactory.createKey(privateKeyBytes);
        pemReader.close();

        this.signer = new Ed25519Signer();
        this.signer.init(true, this.privateKey);
    }

    public String getSignature(String data) {
        byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
        signer.update(dataBytes, offset, dataBytes.length);
        byte[] signatureBytes = signer.generateSignature();
        return Base64.getEncoder().encodeToString(signatureBytes);
    }
}
