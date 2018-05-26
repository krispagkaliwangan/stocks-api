package ph.krisp.stocks.utils;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

public class CalcUtilsTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
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
