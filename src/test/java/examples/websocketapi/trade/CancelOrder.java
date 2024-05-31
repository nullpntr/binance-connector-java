package examples.websocketapi.trade;

import com.binance.connector.client.WebSocketApiClient;
import com.binance.connector.client.enums.DefaultUrls;
import com.binance.connector.client.impl.WebSocketApiClientImpl;
import com.binance.connector.client.utils.signaturegenerator.HmacSignatureGenerator;
import examples.PrivateConfig;
import java.util.HashMap;
import java.util.Map;

public final class CancelOrder {

    private CancelOrder() {
    }

    private static final int orderId = 3083708;
    private static final int waitTime = 60000;

    public static void main(String[] args) throws InterruptedException {

        HmacSignatureGenerator signatureGenerator = new HmacSignatureGenerator(PrivateConfig.TESTNET_SECRET_KEY);
        WebSocketApiClient wsApiClient = new WebSocketApiClientImpl(PrivateConfig.TESTNET_API_KEY, signatureGenerator, DefaultUrls.TESTNET_WS_API_URL);

        wsApiClient.connect(((message) -> {
            System.out.println(message);
        }));

        Map<String, Object> params = new HashMap<>();
        params.put("orderId", orderId);
      
        wsApiClient.trade().cancelOrder("BTCUSDT", params);
      
        Thread.sleep(waitTime);
      
        wsApiClient.close();
    }
}

