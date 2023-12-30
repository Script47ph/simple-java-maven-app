package com.mycompany.app;

/**
 * Hello world!
 */
// public class App {

//     private static final String MESSAGE = "Hello World!";

//     public App() {}

//     public static void main(String[] args) {
//         System.out.println(MESSAGE);
//     }

//     public String getMessage() {
//         return MESSAGE;
//     }
// }

import static spark.Spark.*;

public class SimpleApp {
    public static void main(String[] args) {
        // Define route
        get("/hello", (req, res) -> "Hello Sekai!");

        // Define port
        port(8080);
    }
}