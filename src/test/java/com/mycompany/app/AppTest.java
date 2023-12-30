package com.mycompany.app;

// import org.junit.jupiter.api.Test;

// import static org.junit.jupiter.api.Assertions.assertEquals;

// /**
//  * Unit test for simple App.
//  */
// public class AppTest
// {
//     @Test
//     public void testAppConstructor() {
//         App app1 = new App();
//         App app2 = new App();
//         assertEquals(app1.getMessage(), app2.getMessage());
//     }

//     @Test
//     public void testAppMessage()
//     {
//         App app = new App();
//         assertEquals("Hello World!", app.getMessage());
//     }
// }

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class AppTest {

    private static HttpServerMock serverMock;
    private static final int PORT = 8001; // Using a different port for testing

    @BeforeClass
    public static void setUp() throws Exception {
        serverMock = new HttpServerMock(PORT);
        serverMock.start();
    }

    @AfterClass
    public static void tearDown() {
        serverMock.stop();
    }

    @Test
    public void testAppResponse() throws IOException {
        URL url = new URL("http://localhost:" + PORT + "/");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        assertEquals(200, responseCode);

        String response = readResponse(connection.getInputStream());
        assertTrue(response.contains("Hello CBSA!!!!!!"));
    }

    private String readResponse(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = input.read(buffer)) != -1) {
            output.write(buffer, 0, length);
        }
        return output.toString("UTF-8");
    }
}

// Mock class to run HttpServer in a separate thread for testing purposes
class HttpServerMock {
    private HttpServer server;
    private int port;

    HttpServerMock(int port) {
        this.port = port;
    }

    void start() throws IOException {
        server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", new App.MyHandler());
        server.setExecutor(null);
        server.start();
    }

    void stop() {
        server.stop(0);
    }
}

