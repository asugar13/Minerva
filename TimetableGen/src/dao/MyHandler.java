package dao;

import java.io.IOException;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

import spark.utils.IOUtils;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

class MyHandler implements HttpHandler {
    public void handle(HttpExchange exchange) throws IOException {
        InputStream requestInput = exchange.getRequestBody();      
        StringWriter writer = new StringWriter();
        IOUtils.copy(requestInput, writer);
        String theString = writer.toString();
        System.out.println(theString);

        
        String requestMethod = exchange.getRequestMethod().toUpperCase();
        //System.out.println(requestMethod);
        JSONObject test = new JSONObject();
        test.put("hey","what's up");
        String toSend = test.toString();
        //System.out.println(test);
       
        Headers header = exchange.getResponseHeaders();
		header.add("Access-Control-Allow-Origin", "*");
		header.add("Access-Control-Allow-Headers", "content-type");
		
        exchange.sendResponseHeaders(200, toSend.length());
        OutputStream os = exchange.getResponseBody();
        os.write(toSend.getBytes());
        os.close();
    }

}

