package ph.krisp.stocks.utils;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ph.krisp.stocks.loader.StockLoader;
import ph.krisp.stocks.model.StockRecord;

public class CalcUtilsTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testCalculateAverage() {
		List<StockRecord> records = StockLoader.loadSingleStockRecord("JFC", 40);
		assertTrue(CalcUtils.calculateAverage(records, "Volume").compareTo(BigDecimal.ONE) > 0);
	
		assertTrue(CalcUtils.calculateAverage(records, "test").compareTo(BigDecimal.ZERO) == 0);
		
		List<StockRecord> toAverage = records.subList(0, records.size()-1);

		
		BigDecimal average = CalcUtils.calculateAverage(toAverage, "Volume");
		BigDecimal last = (BigDecimal) records.get(records.size()-1).getInfo("Volume");
		BigDecimal diff = last.divide(average, 5, RoundingMode.HALF_UP);
		
		System.out.println("Average = " + average);
		System.out.println("last = " + last);
		System.out.println("diff = " + diff);
		
		assertTrue(CalcUtils.calculateAverage(records, "Volume")
				.compareTo(CalcUtils.calculateAverage(toAverage, "Volume")) > 0);
		
	}
	
	@Test
	public void testParseDate() throws ParseException {
		String text = "May 25, 2018 12:00:00 AM";
		
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss a");
		Date d = sdf.parse("May 25, 2018 12:00:00 AM");
		assertEquals(d, CalcUtils.parseDate(text));
		
		assertEquals("\"May 25, 2018 12:00:00 AM\"", JsonUtils.objectToJson(d));
		
	}
	
	@Test
	public void testParseNumber() {
		String text = "1.2K";
		assertEquals(new BigDecimal("1.2E+3"), CalcUtils.parseNumber(text));

		text = "100.1";
		assertEquals(new BigDecimal("100.1"), CalcUtils.parseNumber(text));
		
		text = "123,456";
		assertEquals(new BigDecimal("123456"), CalcUtils.parseNumber(text));
		
		text = "-0.45%";
		assertEquals(new BigDecimal("-0.45"), CalcUtils.parseNumber(text));
		
		text = "1.245m";
		assertEquals(new BigDecimal("1.245E+6"), CalcUtils.parseNumber(text));
		
		text = "1.245km";
		assertEquals(null, CalcUtils.parseNumber(text));
		
		text = "-0.45K";
		assertEquals(new BigDecimal("-4.5E+2"), CalcUtils.parseNumber(text));
	}

}
