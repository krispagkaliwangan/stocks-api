package ph.krisp.stocks.loader;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.input.ReversedLinesFileReader;
import org.junit.Before;
import org.junit.Test;

import ph.krisp.stocks.utils.WebUtils;

public class ReverseLineReaderTest {
	
	private static final String DIR = WebUtils.getOutputPath();
	
	@Before
	public void setUp() throws Exception {
	}
	

	@Test
	public void testReverseLinesFileReader() throws IOException {
		File file = new File(DIR + "NOW" + ".csv");
		int n_lines = 2;
		int counter = 0; 
		ReversedLinesFileReader object = new ReversedLinesFileReader(file);
		while(counter < n_lines) {
		    System.out.println(object.readLine());
		    counter++;
		}
	}

	@Test
	public void testCSVApache() throws IOException {
		 Reader reader = Files.newBufferedReader(Paths.get(DIR + "NOW" + ".csv"));
         CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader());
		 
         List<CSVRecord> csvRecords = csvParser.getRecords();
         int depth = 3;
         int limit = csvRecords.size() - depth;
		 for(int i=csvRecords.size()-1; i>=limit; i--) {
			 System.out.println(csvRecords.get(i).get("date"));
		 }
		 
		 csvParser.close();
	}
	
}
