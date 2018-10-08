/*
 * Merchant's Guide To The Galaxy
 */
package br.com.vagas.desafios.MerchantsGuideToTheGalaxy;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Engenheiro de Software (v1495403) - Merchant'sGuideToTheGalaxy
 * @author Wu Liang Kuan
 */
public class Interpreter {
    private Context currentContext;
    private final String variable = "[A-Za-z][A-Za-z0-9]*";
    private List<String> invalidSentences = new ArrayList<>();
        
    /**
     * The interpreter constructor
     * @param c the context from where the interpreter will access information
     */
    public Interpreter(Context c){
        currentContext = c;
        currentContext.getKeywords().put(Key.HOW_MUCH.getKey(), "how much is ");
        currentContext.getKeywords().put(Key.HOW_MANY.getKey(), "how many ");
        currentContext.getKeywords().put(Key.IS.getKey(), " is ");
    }
    
    /**
     * The enum for status flag
     */
    private enum Flag{
        INVALID_SENTENCE,
        NUMERICAL_EQUIVALENCE,
        CURRENCY_EQUIVALENCE,
        HOW_MUCH_QUESTION,
        HOW_MANY_QUESTION
    }
    
    /**
     * The enum for reserved words
     */
    private enum Key{
        IS(" _IS_ "),
        HOW_MUCH("_HOWMUCH_ "),
        HOW_MANY("_HOWMANY_ ");
        private final String key;
        private Key(String str){
            key = str;
        }
        public String getKey(){
            return key;
        }
    }
    
    /**
     * The interpreter answer getter
     * @param sentence the sentence to be analysed
     * @return the answer for the given sentence 
     */
    public String getAnswer(String sentence){
        String finalAns;
        //Lexical analysis
        String tokens = lexicalAnalysis(sentence);
        //Syntatic analysis
        Flag actionCode = syntaticAnalysis(tokens);
        //Semantic analysis
        finalAns = semanticAnalysis(actionCode,tokens);
        
        if(null == finalAns)invalidSentences.add(sentence);
               
        finalAns = (null != finalAns)?finalAns:"I have no idea what you are talking about";
        
        List<String> reprocessedSentences = new ArrayList<>();
        
        if(actionCode == Flag.CURRENCY_EQUIVALENCE || actionCode == Flag.NUMERICAL_EQUIVALENCE){
            if(!invalidSentences.isEmpty()){
                for (String invalidSentence : invalidSentences) {
                    //Lexical analysis
                    String tks = lexicalAnalysis(invalidSentence);
                    //Syntatic analysis
                    Flag actCode = syntaticAnalysis(tks);
                    //Semantic analysis
                    finalAns = semanticAnalysis(actCode,tks);   
                    
                    if(null != finalAns){
                        reprocessedSentences.add(invalidSentence);
                    }                        
                    else
                        finalAns = "";
                }
                if(!reprocessedSentences.isEmpty())
                    for (String reprocessedSentence : reprocessedSentences) {
                        invalidSentences.remove(reprocessedSentence);
                    }
            }
        }
        return finalAns;
    }
    
    /**
     * Provide lexical analysis on a given sentence
     * @param sentence the sentence to be analysed
     * @return a tokens string
     */
    private String lexicalAnalysis(String sentence){
        String tokens = sentence.replaceAll("[ ]+", " ").trim();
        if(tokens.matches("(([0-9]+ )|("+variable+" ?[.,!? ]?))+")){
            tokens = tokens.replaceAll("[.,!]", ""); //Remove unnecessary characters
            tokens = replaceAllReservedWords(tokens);
            return tokens;
        }
        return null;
    }
    
    /**
     * Provide syntatic analysis on a given tokens string
     * @param tokens the tokens to be analysed
     * @return a action code
     */
    private Flag syntaticAnalysis(String tokens){
        if(null == tokens) return Flag.INVALID_SENTENCE;
        if(tokens.matches("(?i)^"+variable+Key.IS.getKey()+variable+"$")){
            return Flag.NUMERICAL_EQUIVALENCE;
        }
        else if(tokens.matches("(?i)^("+variable+" )+"+variable+Key.IS.getKey()+"[0-9]+ "+variable+"$")){
            return Flag.CURRENCY_EQUIVALENCE;
        }
        else if(tokens.matches(("(?i)^"+Key.HOW_MUCH.getKey()+"("+variable+" ?)+ ?[?]$"))){
            return Flag.HOW_MUCH_QUESTION;
        }
        else if(tokens.matches("(?i)^"+Key.HOW_MANY.getKey()+variable+Key.IS.getKey()+"("+variable+" ?)+ ?[?]$")){
            return Flag.HOW_MANY_QUESTION;
        }
        return Flag.INVALID_SENTENCE;
    }
    
    /**
     * Provide semantic analysis on a given tokens string
     * @param actionCode the action to be executed
     * @param tokens the tokens from where arguments will be extracted
     * @return the analysis result string
     */
    private String semanticAnalysis(Flag actionCode, String tokens){
        Pattern p;
        Matcher m;
        if(null == actionCode)
            return null;
        else if(actionCode != Flag.INVALID_SENTENCE){
            switch (actionCode) {
                case NUMERICAL_EQUIVALENCE:
                    p = Pattern.compile("(?i)("+variable+")"+Key.IS.getKey()+"("+variable+")");
                    m = p.matcher(tokens.subSequence(0, tokens.length()));
                    if (m.find())
                        numericalEquivalenceOperation(m.group(1),m.group(2));
                    break;
                case CURRENCY_EQUIVALENCE:
                    p = Pattern.compile("(?i)(("+variable+" )+)("+variable+")"+Key.IS.getKey()+"([0-9]+) ("+variable+")");
                    m = p.matcher(tokens.subSequence(0, tokens.length()));
                    if (m.find())
                        if(!currencyEquivalenceOperation(m.group(1).trim(),m.group(3),m.group(4),m.group(5)))
                            return null;
                    break;
                case HOW_MUCH_QUESTION:
                    p = Pattern.compile("(?i)"+Key.HOW_MUCH.getKey()+"(("+variable+" ?)+) ?[?]");
                    m = p.matcher(tokens.subSequence(0, tokens.length()));
                    if (m.find())
                        return howMuchIs(m.group(1).trim());
                    break;
                case HOW_MANY_QUESTION:
                    p = Pattern.compile("(?i)"+Key.HOW_MANY.getKey()+"("+variable+")"+Key.IS.getKey()+"(("+variable+" )+)("+variable+") ?[?]");
                    m = p.matcher(tokens.subSequence(0, tokens.length()));
                    if (m.find())
                        return howManyCurIs(m.group(1),m.group(2).trim(),m.group(4));
                    break;
                default:
                    break;
            }
            return "";
        }
        return null;
    }
    
    /**
     * Replace all reserved words by terminal symbols
     * @param str the sentence to be operated
     * @return processed string
     */
    private String replaceAllReservedWords(String str){
        String ans = str;
        for (String key : currentContext.getKeywords().keySet()) {
            ans = ans.replaceAll("(?i)"+currentContext.getKeywords().get(key), key);
        }
        return ans;
    }
    
    /**
     * Numerical Equivalence Operation is a method to link a string to a value
     * @param key the string to be associated
     * @param value the associated value
     */
    private void numericalEquivalenceOperation(String key, String value){
        currentContext.getDefinitions().put(key.toLowerCase(), value);
    }
    
    /**
     * Currency Equivalence Operation is a method to link a currency to the exchange rate for current used currency
     * @param extCurrencyValue the external currency value
     * @param extCurrencyUnit the external currency unit
     * @param curCurrencyValue the current currency value
     * @param curCurrencyUnit the current currency unit
     * @retun a boolean flag showing whether the exchange rate was successfuly calculated
     */
    private boolean currencyEquivalenceOperation(String extCurrencyValue, 
                                                 String extCurrencyUnit, 
                                                 String curCurrencyValue, 
                                                 String curCurrencyUnit){
        if(null == currentContext.getCurrentCurrency())
            currentContext.setCurrentCurrency((curCurrencyUnit.endsWith("s"))?curCurrencyUnit.substring(0, curCurrencyUnit.length()-1):curCurrencyUnit);
        else if(!curCurrencyUnit.matches("(?i)^"+currentContext.getCurrentCurrency()+"[s]?$")) return false;
        int extValue = parseExtCurrencyValue(extCurrencyValue);
        if(extValue>0){
            double exchangeRate = (double)Integer.parseInt(curCurrencyValue)/extValue;
            currentContext.getExchange().put(extCurrencyUnit.toLowerCase(), exchangeRate);
            return true;
        }
        return false;
    }
    
    /**
     * Get answer for "how much" like questions
     * @param extCurrencyValue the external currency value
     * @retun if converted successfuly, it returns the answer; otherwise, it returns null
     */
    private String howMuchIs(String extCurrencyValue){
        int extValue = parseExtCurrencyValue(extCurrencyValue);
        return (extValue != -1)?extCurrencyValue+" is "+extValue:null;
    }
    
    /**
     * Get answer for "how many" like questions
     * @param curCurrencyUnit the current currency unit
     * @param extCurrencyValue the external currency value
     * @param extCurrencyUnit the external currency unit
     * @retun if converted successfuly, it returns the answer; otherwise, it returns null
     */
    private String howManyCurIs(String curCurrencyUnit,
                                String extCurrencyValue,
                                String extCurrencyUnit){
        if(curCurrencyUnit.matches("(?i)^"+currentContext.getCurrentCurrency()+"[s]?$")){
            int extValue = parseExtCurrencyValue(extCurrencyValue);
            if(currentContext.getExchange().containsKey(extCurrencyUnit.toLowerCase())){
                return (extValue != -1)?extCurrencyValue+" "+extCurrencyUnit
                        +" is "+(int)(currentContext.getExchange().get(extCurrencyUnit.toLowerCase())*extValue)
                        +" "+curCurrencyUnit:null;
            }
        }
        return null;
    }
    
    /**
     * External currency value parser, it converts the encoded roman numeral to decimal integer
     * @param extCurrencyValue the external currency value
     * @retun the decimal integer equivalent to the given value
     */
    private int parseExtCurrencyValue(String extCurrencyValue){
        for (String key: currentContext.getDefinitions().keySet()) {
            extCurrencyValue = extCurrencyValue.toLowerCase().replaceAll(key, currentContext.getDefinitions().get(key));
        }
        return RomanNumeral.getDecimalValue(extCurrencyValue);
    }
}
