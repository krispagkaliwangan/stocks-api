package ph.krisp.stocks.prediction;

import java.util.List;

import ph.krisp.stocks.model.StockRecord;

/**
 * A simple prediction class where action is constant whatever the input
 * 
 * @author kris.pagkaliwangan
 *
 */
public class SimplePrediction implements Prediction {

	@Override
	public String getAction(List<StockRecord> records) {
		return "U";
	}

}
