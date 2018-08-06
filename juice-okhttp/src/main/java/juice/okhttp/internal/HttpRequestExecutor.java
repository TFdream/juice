package juice.okhttp.internal;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;

/**
 * @author Ricky Fung
 */
public class HttpRequestExecutor {
    private final OkHttpClient client;

    public HttpRequestExecutor(OkHttpClient client) {
        this.client = client;
    }

    public <T> T getForObject(String url, Class<T> classOfT) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code:" + response);
            }
            System.out.println(response.body().string());
        }
        return null;
    }
}
