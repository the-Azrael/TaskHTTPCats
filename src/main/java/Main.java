import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.HttpHeaders;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Main {
    private static Cat getCatFromJSON(String jsonString) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.fromJson(jsonString, Cat.class);
    }

    private static List<Cat> parseCatsFromJSON(String json) {
        List<Cat> list = new ArrayList<>();
        try {
            JSONParser parser = new JSONParser();
            JSONArray objects = (JSONArray) parser.parse(json);
            for (Object obj : objects) {
                Cat cat = getCatFromJSON(obj.toString());
                list.add(cat);
            }
            return list;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private static void showCats(List<Cat> lst, int upvotes, String user) {
        Stream<Cat> catsStream = lst.stream();
        catsStream
                .filter(cat -> cat.getUpvotes() >= upvotes)
                .filter(cat -> cat.getUser().equalsIgnoreCase(user))
                .sorted((cat1, cat2) -> Integer.compare(cat2.getUpvotes(), cat1.getUpvotes()))
                .forEach(System.out::println);
    }

    public static void main(String[] args) {
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)
                        .setSocketTimeout(30000)
                        .setRedirectsEnabled(false)
                        .build()
                )
                .build();
        HttpGet request = new HttpGet(
                "https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats");
        request.setHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.getMimeType());

        try {
            CloseableHttpResponse response = httpClient.execute(request);
            String body = new String(response.getEntity().getContent().readAllBytes(), StandardCharsets.UTF_8);
            showCats(parseCatsFromJSON(body), 0, "Elena Ivanova");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
