package http;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import businessobject.TimeSlot;
import enums.Day;
import enums.SemesterType;

public class TestMain {

	public static void main(String[] args) throws ParseException {
		JSONParser parser = new JSONParser();
		JSONObject arg = new JSONObject();
		arg.put("test", "value");
		ArrayList<JSONObject> testList = new ArrayList<>(Arrays.asList((JSONObject) parser.parse("{\"test thing\": \"something i guess\"\"another thing\": 1,\"some list\": [{\"obval1\": 5,\"obval2\": \"5\"}, {\"obval1\": 6,\"obval2\": \"6\"}]}"),
				(JSONObject) parser.parse("{\"test thing\": \"something i guess\"\"another thing\": 0,\"some list\": [{\"obval1\": 5,\"obval2\": \"5\"}, {\"obval1\": 6,\"obval2\": \"6\"}]}")));
		arg.put("intlist", testList);
		JSONObject thing = (JSONObject) parser.parse("{\"test thing\": \"something i guess\"\"another thing\": 1,\"some list\": [{\"obval1\": 3,\"obval2\": \"3\"}, {\"obval1\": 6,\"obval2\": \"6\"}]}");
		arg.put("another json object", thing);
		Map<SemesterType, JSONObject> testMap = new HashMap<>();
		testMap.put(SemesterType.YEAR, thing);
		testMap.put(SemesterType.FALL, thing);
		arg.putAll(testMap);
		System.out.println(arg.toJSONString());
	}

}
