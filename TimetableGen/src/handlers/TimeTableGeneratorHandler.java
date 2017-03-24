package handlers;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Set;

import com.sun.net.httpserver.HttpServer;

import businessobject.CourseSelection;
import businessobject.TimetableConfiguration;
import generation.TimetableConfigurationGenerator;

public class TimeTableGeneratorHandler implements TimetableGenerationHandler {
	
	private TimetableConfigurationGenerator generator;
	
	
	public TimeTableGeneratorHandler(TimetableConfigurationGenerator generator1){
		this.generator = generator1;
	}
	@Override
	public Set<TimetableConfiguration> generateTimetables(Set<CourseSelection> courseSelections) {
		// TODO Auto-generated method stub
		
		Set<TimetableConfiguration> configurations = generator.generateConfigurations(courseSelections);
		
		
		
		
		
		return configurations;
	}

}