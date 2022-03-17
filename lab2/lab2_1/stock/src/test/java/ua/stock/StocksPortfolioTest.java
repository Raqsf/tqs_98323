package ua.stock;

//import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@ExtendWith(MockitoExtension.class)
public class StocksPortfolioTest {
    
    @Mock
    private IStockmarketService stockmarket;

    @InjectMocks
    private StocksPortfolio portfolio;
    
    @Before
    public void setUp() throws Exception {
        
        MockitoAnnotations.initMocks(this);
        
    }
    
    @Test
    public void testGetTotalValue() {
        when(stockmarket.lookUpPrice("AAPL")).thenReturn(5.0);
        when(stockmarket.lookUpPrice("V")).thenReturn(2.55);

        portfolio.addStock(new Stock("AAPL",4));
        portfolio.addStock(new Stock("V", 1));
        double result = portfolio.getTotalValue();

        // assertEquals(22.55, result);
        assertThat(result, is(22.55));
        verify( stockmarket, times(2)).lookUpPrice(anyString());
    }
}
