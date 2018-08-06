package juice.okhttp;

import juice.okhttp.internal.HttpRequestExecutor;
import okhttp3.OkHttpClient;
import java.io.IOException;

/**
 * @author Ricky Fung
 */
public class OkHttpTemplate {
    private final HttpRequestExecutor httpRequestExecutor;

    public OkHttpTemplate(final OkHttpClient client) {
        this.httpRequestExecutor = new HttpRequestExecutor(client);
    }

    public <T> T getForObject(String url, Class<T> classOfT) throws IOException {
        return httpRequestExecutor.getForObject(url, classOfT);
    }

    public <T> T postForObject(String url, Class<T> classOfT) throws IOException {
        return null;
    }

}
