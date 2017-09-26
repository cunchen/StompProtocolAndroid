package ua.naiksoftware.stomp;

import org.java_websocket.WebSocket;

import java.util.Map;

import okhttp3.OkHttpClient;
import ua.naiksoftware.stomp.client.StompClient;

/**
 * Supported overlays:
 *  - org.java_websocket.WebSocket ('org.java-websocket:Java-WebSocket:1.3.0')
 *  - okhttp3.WebSocket ('com.squareup.okhttp3:okhttp:3.8.0')
 *
 *  You can add own relay, just implement ConnectionProvider for you stomp transport,
 *  such as web socket.
 *
 * Created by naik on 05.05.16.
 */
public class Stomp {

    public static StompClient over(String uri) {
        return over(uri, null, null);
    }

    /**
     *
     * @param uri URI to connect
     * @param connectHttpHeaders HTTP headers, will be passed with handshake query, may be null
     * @return StompClient for receiving and sending messages. Call #StompClient.connect
     */
    public static StompClient over(String uri, Map<String, String> connectHttpHeaders) {
        return over(uri, connectHttpHeaders, null);
    }

    /**
     * {@code webSocketClient} can accept the following type of clients:
     * <ul>
     *     <li>{@code org.java_websocket.WebSocket}: cannot accept an existing client</li>
     *     <li>{@code okhttp3.WebSocket}: can accept a non-null instance of {@code okhttp3.OkHttpClient}</li>
     * </ul>
     * @param uri URI to connect
     * @param connectHttpHeaders HTTP headers, will be passed with handshake query, may be null
     * @param webSocketClient Existing client that will be used to open the WebSocket connection, may be null to use default client
     * @return StompClient for receiving and sending messages. Call #StompClient.connect
     */
    public static StompClient over( String uri, Map<String, String> connectHttpHeaders, Object webSocketClient) {

            OkHttpClient okHttpClient = getOkHttpClient(webSocketClient);

            return new StompClient(new OkHttpConnectionProvider(uri, connectHttpHeaders, okHttpClient));

    }

    private static OkHttpClient getOkHttpClient(Object webSocketClient) {
        if (webSocketClient != null) {
            if (webSocketClient instanceof OkHttpClient) {
                return (OkHttpClient) webSocketClient;
            } else {
                throw new IllegalArgumentException("You must pass a non-null instance of an 'okhttp3.OkHttpClient'. Or pass null to use a default websocket client.");
            }
        } else {
            // default http client
            return new OkHttpClient();
        }
    }
}
