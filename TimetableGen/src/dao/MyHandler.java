package dao;

import java.io.IOException;
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
    public void handle(HttpExchange t) throws IOException {
        InputStream is = t.getRequestBody();
        StringWriter writer = new StringWriter();
        IOUtils.copy(is, writer);
        String theString = writer.toString();
        System.out.println(theString);
        
        String requestMethod = t.getRequestMethod().toUpperCase();
        System.out.println(requestMethod);
       // read(is); // .. read the request body
        JSONObject test = new JSONObject();
        test.put("hey","what's up");
        String toSend = test.toString();
        //System.out.println(test);
        
      
        
        
        Headers header = t.getResponseHeaders();
		header.add("Access-Control-Allow-Origin", "*");
        t.sendResponseHeaders(200, toSend.length());
        OutputStream os = t.getResponseBody();
        os.write(toSend.getBytes());
        os.close();
    }
    
//    private static Map<String, List<String>> getRequestParameters(final URI requestUri) {
//        final Map<String, List<String>> requestParameters = new LinkedHashMap<>();
//        final String requestQuery = requestUri.getRawQuery();
//        if (requestQuery != null) {
//            final String[] rawRequestParameters = requestQuery.split("[&;]", -1);
//            for (final String rawRequestParameter : rawRequestParameters) {
//                final String[] requestParameter = rawRequestParameter.split("=", 2);
//                final String requestParameterName = decodeUrlComponent(requestParameter[0]);
//                requestParameters.putIfAbsent(requestParameterName, new ArrayList<>());
//                final String requestParameterValue = requestParameter.length > 1 ? decodeUrlComponent(requestParameter[1]) : null;
//                requestParameters.get(requestParameterName).add(requestParameterValue);
//            }
//        }
//        return requestParameters;
//    }
//    
//    private static String decodeUrlComponent(final String urlComponent) {
//        try {
//            return URLDecoder.decode(urlComponent, StandardCharsets.UTF_8.name());
//        } catch (final UnsupportedEncodingException ex) {
//            throw new InternalError(ex);
//        }
//    }
}

