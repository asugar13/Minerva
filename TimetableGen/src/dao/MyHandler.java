package dao;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import org.json.simple.JSONObject;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

class MyHandler implements HttpHandler {
    public void handle(HttpExchange t) throws IOException {
        InputStream is = t.getRequestBody();
       // read(is); // .. read the request body
        String response = "This is the response";
        
//        JSONObject test = new JSONObject();
//        test.put("hey","what's up");
//        System.out.println(test);
        
        
        Headers header =t.getResponseHeaders();
        //header.set("Content-Type", String.format("application/json; charset=%s", StandardCharsets.UTF_8));
		header.add("Access-Control-Allow-Origin", "*");
        //byte[] rawResponseBody = test.getBytes(StandardCharsets.UTF_8);
        t.sendResponseHeaders(200, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}

