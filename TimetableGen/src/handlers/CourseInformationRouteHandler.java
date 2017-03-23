package handlers;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;

import businessobject.CourseListing;
import dao.CourseListingDao;
import spark.Request;
import spark.Response;
import spark.Route;

public class CourseInformationRouteHandler implements Route{
	
	private CourseListingDao listingDao;
	
	public CourseInformationRouteHandler(CourseListingDao listingDao) {
		this.listingDao = listingDao;
	}
	
	@Override
	public Object handle(Request arg0, Response arg1) throws Exception {
		listingDao.getAllCourseListings();
		List<CourseListing> courseListings = new ArrayList<>(listingDao.getAllCourseListings().values());
		JSONObject json = new JSONObject();
		//json.put("courses", value);
		return null;
	}

}
