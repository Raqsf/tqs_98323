package ua.stock;

import java.util.ArrayList;
import java.util.List;

/**
 * StocksPortfolio
 */
public class StocksPortfolio {

    private List<Stock> stocks;
    private IStockmarketService stockmarket;

    public StocksPortfolio(IStockmarketService stockmarket) {
        this.stockmarket = stockmarket;
        stocks = new ArrayList<Stock>();
    }

    public void addStock(Stock s) {
        stocks.add(s);
    }

    public double getTotalValue() {
        double total = 0;
        for(Stock s: stocks) {
            total += s.getQuantity() * stockmarket.lookUpPrice(s.getLabel());
        }
        return total;
    }
}