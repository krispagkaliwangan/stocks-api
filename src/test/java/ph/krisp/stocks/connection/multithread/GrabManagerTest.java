package ph.krisp.stocks.connection.multithread;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;

public class GrabManagerTest {

	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void test() throws IOException, InterruptedException {
		GrabManager grabManager = new GrabManager(2, 300);
		grabManager.go(new URL("http://news.yahoo.com"));
		grabManager.write("urllist.txt");
	}

}
