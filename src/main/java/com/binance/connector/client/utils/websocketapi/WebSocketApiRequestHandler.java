package com.binance.connector.client.utils.websocketapi;

import com.binance.connector.client.exceptions.BinanceConnectorException;
import com.binance.connector.client.utils.JSONParser;
import com.binance.connector.client.utils.UrlBuilder;
import com.binance.connector.client.utils.WebSocketConnection;
import com.binance.connector.client.utils.signaturegenerator.SignatureGenerator;
import java.util.Map;
import org.json.JSONObject;

public class WebSocketApiRequestHandler {
    private final SignatureGenerator signatureGenerator;
    private final String apiKey;
    private WebSocketConnection connection;

    public WebSocketApiRequestHandler(WebSocketConnection connection, String apiKey, SignatureGenerator signatureGenerator) {
        if (connection == null) {
            throw new BinanceConnectorException("[WebSocketApiRequestHandler] WebSocketConnection cannot be null");
        }
        this.connection = connection;
        this.apiKey = apiKey;
        this.signatureGenerator = signatureGenerator;
    }

    public void publicRequest(String method, JSONObject parameters) {
        connection.send(JSONParser.buildJSONString(method, parameters));
    }

    public void apiRequest(String method, JSONObject parameters) {
        // check is session logon executed so not to sign requests
        //FIXME in this case session.logon doesn't work as expected from documentation
//        if (!connection.isAuthorized()) {
            parameters.put("apiKey", this.apiKey);
//        }
        connection.send(JSONParser.buildJSONString(method, parameters));
    }

    public void signedRequest(String method, JSONObject parameters) {
        parameters.put("timestamp", System.currentTimeMillis());
        // check is session logon executed so not to sign requests
        if (!connection.isAuthorized()) {
            parameters.put("apiKey", this.apiKey);
            // signature
//                ParameterChecker.checkParameterType(this.signatureGenerator, SignatureGenerator.class, "signatureGenerator");
            String payload = UrlBuilder.joinQueryParameters(JSONParser.sortJSONObject(parameters.toMap()));
            String signature = this.signatureGenerator.getSignature(payload);
            parameters.put("signature", signature);
        }
        connection.send(JSONParser.buildJSONString(method, parameters));
    }

//    public void request(RequestType requestType, String method, JSONObject parameters) {
////        ParameterChecker.checkParameterType(method, String.class, "method");
//        switch (requestType) {
//            case PUBLIC:
//                break;
//            case WITH_API_KEY:
////                ParameterChecker.checkParameterType(this.apiKey, String.class, "apiKey");
//                // check is session logon executed so not to sign requests
//                if (!connection.isAuthorized()) {
//                    parameters.put("apiKey", this.apiKey);
//                }
//                break;
//            case SIGNED:
////                ParameterChecker.checkParameterType(this.apiKey, String.class, "apiKey");
//                parameters.put("timestamp", System.currentTimeMillis());
//                // check is session logon executed so not to sign requests
//                if (!connection.isAuthorized()) {
//                    parameters.put("apiKey", this.apiKey);
//                    // signature
////                ParameterChecker.checkParameterType(this.signatureGenerator, SignatureGenerator.class, "signatureGenerator");
//                    String payload = UrlBuilder.joinQueryParameters(JSONParser.sortJSONObject(parameters.toMap()));
//                    String signature = this.signatureGenerator.getSignature(payload);
//                    parameters.put("signature", signature);
//                }
//                break;
//            default:
//                throw new BinanceConnectorException("[WebSocketApiRequestHandler] Invalid request type: " + requestType);
//        }
//        connection.send(JSONParser.buildJSONString(UUID.randomUUID().toString(), method, parameters));
//    }

    public void signedRequest(String method, Map<String, Object> parameters) {
        parameters.put("timestamp", System.currentTimeMillis());
        // check is session logon executed so not to sign requests
        if (!connection.isAuthorized()) {
            parameters.put("apiKey", this.apiKey);
            String payload = UrlBuilder.joinQueryParameters(JSONParser.sortJSONObject(parameters));
            String signature = this.signatureGenerator.getSignature(payload);
            parameters.put("signature", signature);
        }
        connection.send(JSONParser.buildJSONString(method, parameters));
    }

//    public void request(RequestType requestType, String method, Map<String, Object> parameters) {
//        switch (requestType) {
//            case PUBLIC:
//                break;
//            case WITH_API_KEY:
//                // check is session logon executed so not to sign requests
//                if (!connection.isAuthorized()) {
//                    parameters.put("apiKey", this.apiKey);
//                }
//                break;
//            case SIGNED:
//                parameters.put("timestamp", System.currentTimeMillis());
//                // check is session logon executed so not to sign requests
//                if (!connection.isAuthorized()) {
//                    parameters.put("apiKey", this.apiKey);
//                    String payload = UrlBuilder.joinQueryParameters(JSONParser.sortJSONObject(parameters));
//                    String signature = this.signatureGenerator.getSignature(payload);
//                    parameters.put("signature", signature);
//                }
//                break;
//            default:
//                throw new BinanceConnectorException("[WebSocketApiRequestHandler] Invalid request type: " + requestType);
//        }
//        connection.send(JSONParser.buildJSONString(UUID.randomUUID().toString(), method, parameters));
//    }
}
