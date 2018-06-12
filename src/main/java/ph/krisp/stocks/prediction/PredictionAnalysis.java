package ph.krisp.stocks.prediction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import ph.krisp.stocks.model.StockPrediction;
import ph.krisp.stocks.model.StockRecord;

/**
 * Class containing methods in analyzing predictions
 * 
 * @author kris.pagkaliwangan
 *
 */
public class PredictionAnalysis {

	private Prediction prediction;
	private StockFilter stockFilter;
	
	public PredictionAnalysis(Prediction prediction,
			StockFilter stockFilter) {
		this.prediction = prediction;
		this.stockFilter = stockFilter;
	}

	public void process() {
		List<StockPrediction> predictions = new ArrayList<>();
		for (int i = 1; i < depth; i++) {
			// filter
			StockFilter sa = new StockFilter(analysis);
			sa.filterByDepth(depth, depth - i);
			List<StockRecord> records = sa.getOutput().get(stockCodes[0]);
			
			// create new prediction
			if(!records.isEmpty()) {
				StockRecord latest = records.get(records.size()-1);
				String action = "U";
				StockPrediction prediction = new StockPrediction(latest.getCode(), latest.getDate(), action);
				predictions.add(prediction);
				
				// update prev prediction
				int prevIndex = predictions.size()-1;
				if(predictions.get(prevIndex) != null) {
					StockPrediction prevPrediction = predictions.get(prevIndex);
					BigDecimal change = (BigDecimal) latest.getInfo("%Change");
					String nextDay = change.compareTo(BigDecimal.ZERO) >= 0 ? "U" : "D";
					prevPrediction.setNextDay(nextDay);
				}
			}
			
		}
		
		int tcounter = 0;
		for(StockPrediction prediction : predictions) {
			System.out.println(prediction.getDate() + " : " + prediction.getAction()
					+ " : " + prediction.getNextDay() + " : " + prediction.isMatch());
			if(prediction.isMatch()) {
				tcounter++;
			}
		}
		System.out.println("Result = " + tcounter + "/" + predictions.size());
	}
	
	
	
	
}
