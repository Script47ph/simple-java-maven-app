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

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import spark.Spark;

import static org.junit.Assert.assertEquals;
import static spark.Spark.awaitInitialization;

public class SimpleAppTest {

    @BeforeClass
    public static void setUp() {
        SimpleApp.main(null);
        awaitInitialization();
    }

    @AfterClass
    public static void tearDown() {
        Spark.stop();
    }

    @Test
    public void testHelloEndpoint() {
        TestResponse response = TestUtil.request("GET", "/hello");
        assertEquals(200, response.status);
        assertEquals("Hello World!", response.body);
    }

    // Helper class for testing
    private static class TestResponse {
        public final String body;
        public final int status;

        public TestResponse(String body, int status) {
            this.body = body;
            this.status = status;
        }
    }

    private static class TestUtil {
        public static TestResponse request(String method, String path) {
            try {
                java.net.URL url = new java.net.URL("http://localhost:8080" + path);
                java.net.HttpURLConnection connection = (java.net.HttpURLConnection) url.openConnection();
                connection.setRequestMethod(method);

                connection.connect();

                String body = null;
                if (connection.getResponseCode() != 204) { // No Content
                    body = new java.util.Scanner(connection.getInputStream(), java.nio.charset.StandardCharsets.UTF_8).useDelimiter("\\A").next();
                }

                return new TestResponse(body, connection.getResponseCode());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
