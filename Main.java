import javax.swing.*;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main {

    public static JsonNode foo() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create("https://iss.moex.com/iss/securities.json?iss.meta=off")).build();

        /*HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://openjdk.java.net/"))
                .build();*/

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return new ObjectMapper().readTree(response.body());
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Table");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JsonNode jNode;
        try {
            jNode = foo();
        } catch (IOException e) {
            return;
        } catch (InterruptedException e) {
            return;
        }

        TablePanel tablePanel = new TablePanel(jNode);
        tablePanel.setOpaque(true);

        frame.setContentPane(tablePanel);
        frame.pack();
        frame.setVisible(true);

    }
}