/*
 * Merchant's Guide To The Galaxy
 */
package br.com.vagas.desafios.MerchantsGuideToTheGalaxy;

import java.util.HashMap;
import java.util.Map;

/**
 * Engenheiro de Software (v1495403) - Merchant'sGuideToTheGalaxy
 * @author Wu Liang Kuan
 */
public class Context {
    private final Map<String,String> definitions;
    private final Map<String,Double> exchange;
    private final Map<String,String> keywords;
    private String currentCurrency;
    public Context(){
        definitions = new HashMap<>();
        exchange = new HashMap<>();
        keywords = new HashMap<>();
        currentCurrency = null;
    }

    public Map<String, String> getDefinitions() {
        return definitions;
    }

    public Map<String, Double> getExchange() {
        return exchange;
    }

    public Map<String, String> getKeywords() {
        return keywords;
    }

    public String getCurrentCurrency() {
        return currentCurrency;
    }

    public void setCurrentCurrency(String currentCurrency) {
        this.currentCurrency = currentCurrency;
    }
}
