package examples.websocketapi.market;

import com.binance.connector.client.WebSocketApiClient;
import com.binance.connector.client.impl.WebSocketApiClientImpl;

public final class RecentTrades {

    private RecentTrades() {
    }

    private static final int waitTime = 60000;

    public static void main(String[] args) throws InterruptedException {
        WebSocketApiClient client = new WebSocketApiClientImpl();
        client.connect(((event) -> {
            System.out.println(event);
        }));

        client.market().recentTrades("BTCUSDT", null);

        Thread.sleep(waitTime);
        client.close();

    }
}
