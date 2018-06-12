package ph.krisp.stocks.prediction;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ph.krisp.stocks.loader.StockLoader;

public class PredictionAnalysisTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testProcess() {
		int depth = 40;
		String[] stockCodes = {"SMPH", "JFC", "MEG", "NOW"};
		Prediction prediction = new SimplePrediction();
		StockFilter stockFilter = new StockFilter(stockCodes, depth);
		PredictionAnalysis pa = new PredictionAnalysis(prediction, stockFilter);
		
		pa.process();
	}

}
