package com.portfolio.analyzer.service;

import com.portfolio.analyzer.entity.Holding;
import com.portfolio.analyzer.entity.Portfolio;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PortfolioService {

    private final Map<String, Portfolio> portfolios = new ConcurrentHashMap<>();

    public PortfolioService() {
        initMockData();
    }

    private void initMockData() {
        var holdings1 = List.of(
                new Holding("AAPL", "Apple Inc.", "Technology", "US", 15.2, 12.0, 189.84, 800, 151872.0, 1.2, 28.5, 72, 1.18, 22.4),
                new Holding("MSFT", "Microsoft Corp.", "Technology", "US", 13.8, 12.0, 378.91, 364, 137923.24, 0.8, 35.2, 81, 1.05, 20.1),
                new Holding("GOOGL", "Alphabet Inc.", "Technology", "US", 9.4, 8.0, 141.80, 663, 94013.40, -0.3, 22.1, 68, 1.12, 24.8),
                new Holding("JPM", "JPMorgan Chase", "Financial", "US", 8.1, 10.0, 196.54, 412, 80974.48, 0.5, 18.7, 65, 1.22, 19.3),
                new Holding("JNJ", "Johnson & Johnson", "Healthcare", "US", 6.5, 8.0, 155.28, 418, 64907.04, -0.2, 8.3, 76, 0.72, 14.2),
                new Holding("V", "Visa Inc.", "Financial", "US", 5.8, 6.0, 279.12, 208, 58056.96, 0.9, 24.6, 71, 0.98, 18.7),
                new Holding("PG", "Procter & Gamble", "Consumer Staples", "US", 5.2, 6.0, 162.47, 320, 51990.40, 0.1, 12.4, 84, 0.55, 12.8),
                new Holding("NVDA", "NVIDIA Corp.", "Technology", "US", 8.5, 5.0, 875.28, 97, 84902.16, 2.8, 142.5, 62, 1.85, 48.2),
                new Holding("UNH", "UnitedHealth Group", "Healthcare", "US", 5.0, 6.0, 527.84, 95, 50144.80, -0.4, 15.8, 69, 0.82, 16.5),
                new Holding("HD", "Home Depot", "Consumer Discretionary", "US", 4.2, 5.0, 345.67, 122, 42171.74, 0.3, 11.2, 73, 1.08, 21.3),
                new Holding("NESN", "Nestlé SA", "Consumer Staples", "Europe", 3.8, 5.0, 98.45, 386, 38001.70, -0.1, 6.8, 88, 0.45, 11.4),
                new Holding("SAP", "SAP SE", "Technology", "Europe", 3.2, 4.0, 184.32, 174, 32071.68, 0.6, 19.4, 79, 0.92, 19.8),
                new Holding("TSM", "Taiwan Semiconductor", "Technology", "Asia", 4.8, 4.0, 142.56, 337, 48042.72, 1.5, 45.3, 66, 1.35, 32.1),
                new Holding("TCS", "Tata Consultancy", "Technology", "India", 2.5, 3.0, 62.18, 402, 24996.36, 0.4, 16.2, 74, 0.88, 22.6),
                new Holding("CASH", "Cash & Equivalents", "Cash", "US", 4.0, 7.0, 1.0, 40000, 40000.0, 0.0, 4.5, 100, 0.0, 0.0)
        );

        portfolios.put("PF-001", new Portfolio(
                "PF-001", "Global Growth Alpha Fund", "Meridian Wealth Partners",
                "Aggressive Growth", 1000068.68, 14.2, 16.8, 72, 85, 71.4, 34.2,
                "USD", LocalDateTime.of(2025, 11, 15, 9, 30),
                LocalDateTime.of(2023, 3, 1, 0, 0), holdings1
        ));

        var holdings2 = List.of(
                new Holding("AAPL", "Apple Inc.", "Technology", "US", 8.0, 8.0, 189.84, 421, 79922.64, 1.2, 28.5, 72, 1.18, 22.4),
                new Holding("BRK.B", "Berkshire Hathaway", "Financial", "US", 10.0, 10.0, 412.30, 243, 100188.90, 0.3, 15.2, 58, 0.85, 15.6),
                new Holding("JNJ", "Johnson & Johnson", "Healthcare", "US", 9.5, 10.0, 155.28, 612, 95031.36, -0.2, 8.3, 76, 0.72, 14.2),
                new Holding("PG", "Procter & Gamble", "Consumer Staples", "US", 8.5, 8.0, 162.47, 523, 84971.81, 0.1, 12.4, 84, 0.55, 12.8),
                new Holding("XOM", "Exxon Mobil", "Energy", "US", 7.0, 7.0, 104.82, 668, 70019.76, -0.5, 9.1, 32, 0.95, 20.1),
                new Holding("KO", "Coca-Cola", "Consumer Staples", "US", 6.0, 6.0, 61.28, 979, 59973.12, 0.2, 7.8, 82, 0.52, 11.2),
                new Holding("VZ", "Verizon", "Telecom", "US", 5.5, 6.0, 41.52, 1325, 55014.00, -0.1, 5.2, 67, 0.48, 16.8),
                new Holding("PFE", "Pfizer Inc.", "Healthcare", "US", 5.0, 5.0, 28.45, 1758, 50015.10, -0.8, -12.4, 71, 0.78, 24.5),
                new Holding("BOND", "US Treasury Bond ETF", "Fixed Income", "US", 25.0, 25.0, 92.40, 2706, 250034.40, 0.05, 4.8, 95, 0.15, 5.2),
                new Holding("CASH", "Cash & Equivalents", "Cash", "US", 15.5, 15.0, 1.0, 155000, 155000.0, 0.0, 4.5, 100, 0.0, 0.0)
        );

        portfolios.put("PF-002", new Portfolio(
                "PF-002", "Conservative Income Shield", "Heritage Capital Group",
                "Conservative", 1000171.09, 7.5, 6.8, 88, 96, 68.2, 8.4,
                "USD", LocalDateTime.of(2026, 1, 10, 14, 0),
                LocalDateTime.of(2022, 8, 15, 0, 0), holdings2
        ));

        var holdings3 = List.of(
                new Holding("MSFT", "Microsoft Corp.", "Technology", "US", 10.0, 10.0, 378.91, 264, 100032.24, 0.8, 35.2, 81, 1.05, 20.1),
                new Holding("NESN", "Nestlé SA", "Consumer Staples", "Europe", 7.0, 7.0, 98.45, 711, 69997.95, -0.1, 6.8, 88, 0.45, 11.4),
                new Holding("NOVO", "Novo Nordisk", "Healthcare", "Europe", 6.5, 6.0, 124.80, 521, 65020.80, 1.4, 42.1, 85, 0.92, 26.4),
                new Holding("V", "Visa Inc.", "Financial", "US", 6.0, 6.0, 279.12, 215, 60010.80, 0.9, 24.6, 71, 0.98, 18.7),
                new Holding("ASML", "ASML Holding", "Technology", "Europe", 5.5, 5.0, 742.18, 74, 54921.32, 1.1, 38.4, 78, 1.28, 30.2),
                new Holding("UL", "Unilever PLC", "Consumer Staples", "Europe", 5.0, 5.0, 52.84, 946, 49986.64, 0.2, 8.4, 91, 0.42, 10.8),
                new Holding("7203", "Toyota Motor", "Industrials", "Asia", 5.0, 5.0, 178.50, 280, 49980.00, -0.3, 12.8, 70, 0.82, 18.4),
                new Holding("INFY", "Infosys Ltd.", "Technology", "India", 4.0, 4.0, 18.24, 2193, 40000.32, 0.5, 14.6, 77, 0.95, 24.2),
                new Holding("ESG-ETF", "Global ESG Leaders ETF", "ESG Fund", "Global", 20.0, 22.0, 85.40, 2342, 200027.80, 0.3, 16.2, 92, 0.78, 14.6),
                new Holding("GREEN", "Clean Energy ETF", "ESG Fund", "Global", 12.0, 12.0, 42.18, 2845, 120002.10, -0.6, 22.4, 94, 1.15, 28.4),
                new Holding("BOND", "Global Bond ETF", "Fixed Income", "Global", 12.0, 12.0, 48.20, 2490, 120018.00, 0.1, 5.2, 88, 0.18, 6.1),
                new Holding("CASH", "Cash & Equivalents", "Cash", "Global", 7.0, 6.0, 1.0, 70000, 70000.0, 0.0, 4.5, 100, 0.0, 0.0)
        );

        portfolios.put("PF-003", new Portfolio(
                "PF-003", "Sustainable Future ESG Fund", "GreenVista Investments",
                "Balanced", 999997.97, 11.8, 13.2, 84, 92, 83.6, 12.8,
                "USD", LocalDateTime.of(2026, 2, 20, 11, 15),
                LocalDateTime.of(2024, 1, 10, 0, 0), holdings3
        ));
    }

    public List<Portfolio> getAllPortfolios() {
        return List.copyOf(portfolios.values());
    }

    public Optional<Portfolio> getPortfolio(String id) {
        return Optional.ofNullable(portfolios.get(id));
    }
}
