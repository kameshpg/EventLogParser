package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import dao.LogDAO;
import datamodel.Log;

public class EventLogParser {

	private static String filePath;

	private static final Logger LOGGER = LogManager.getLogger(EventLogParser.class);

	public EventLogParser(String[] args) {
		if (args.length != 0)
			filePath = args[0];
	}

	public EventLogParser() {
		
	}

	public static void main(String[] args) {
		try {
			EventLogParser parser = new EventLogParser(args);
			parser.parseLogs(filePath);
			
			parser.getAllLogs().forEach(obj -> LOGGER.info("Diplaying first few records from the db"+obj.toString()));
			
		} catch (Exception e) {

			e.getMessage();
			e.printStackTrace();
		}
		
		
	}

	@SuppressWarnings("deprecation")
	public boolean parseLogs(String path)  {

		if (path == null || path.equals(""))
			return false;
		LOGGER.info("Processing file "+filePath);
		final File fileToProcess = new File(path);

		if (!fileToProcess.exists())
			return false;
		
			try {

				FileInputStream in = new FileInputStream(fileToProcess);
				readEventEntries(in);
			} catch (Exception e) {

				LOGGER.error(e.getMessage());
				e.printStackTrace();
				return false;
			} 
		
		return true;
	}

	private void readEventEntries(FileInputStream in) throws IOException {
		
		JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        List<Log> events = new ArrayList<Log>();
        final Gson gson = new Gson();
        reader.beginArray();
        while (reader.hasNext()) {
        	Log event = gson.fromJson(reader, Log.class);
        	if(event.getState().equals("STARTED")) {
        		event.setTimestampStart(event.getTimestamp());
        	}else {
        		event.setTimestampEnd(event.getTimestamp());
        	}
        	events.add(event);
        }
        reader.endArray();
        reader.close();
        saveEvents(events);
        LOGGER.debug("File "+ filePath +" parsed and no of events read = " + events.size());

	}

	private void saveEvents(List<Log> events) {
		LogDAO logDAO = new LogDAO();
		try {
			logDAO.saveOrUpdateEvents(events);
		} catch (Exception e) {
			LOGGER.error("ERROR: saveOrUpdateEvents () failed !" + e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	public List<Log> getAllLogs(){
		
		LogDAO logDAO = new LogDAO();
		
		return logDAO.getAllLogs(0, 10);
		
	}
}
