package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import main.EventLogParser;

public class EventLogParserTest {
	
	@Test
	public void testParseLogsWithValidJSon() throws IOException {
		ClassLoader classLoader = getClass().getClassLoader();
		String filePath = null;
		try {
			//filePath = IOUtils.toString(classLoader.getResourceAsStream("TestEvents.json"));
			filePath = classLoader.getResource("TestEvents.json").getPath();
		}catch(Exception e) {
			fail("Test fialure: exception reading file"+ e.getMessage());
		}
		EventLogParser classUnderTest = new EventLogParser();
		assertTrue(classUnderTest.parseLogs(filePath),
				"parseLogs() should return 'false'");
	}
	
	@Test
	public void testGetData(){
		
		EventLogParser classUnderTest = new EventLogParser();
		assertTrue(!(classUnderTest.getAllLogs().size()<0),
				"parseLogs() should return 'false'");
		
	}
}
