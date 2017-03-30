package handlers;

import spark.Request;
import spark.Response;
import spark.Route;

public class TestRoute implements Route {
	int i = 0;

	@Override
	public Object handle(Request request, Response response) throws Exception {
		i ++;
		return i;
	}
	

}
