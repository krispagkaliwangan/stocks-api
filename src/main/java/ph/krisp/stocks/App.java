package ph.krisp.stocks;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        try {
			Document doc = Jsoup.connect("https://www.investagrams.com/Stock/SMPH")
								.userAgent("Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.139 Safari/537.36")
								.get();
			System.out.println(doc.title());
			
			// select stock information
			Elements tables = doc.body().select(".col-xs-4 > table > tbody > tr");
			
			for(Element table : tables) {
				
				Element label = table.select("td").first().child(0);
				Element value = table.select(".stock-price").first();
				System.out.println(label.ownText() + " " + value.ownText());
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
}
