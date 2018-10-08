/*
 * Merchant's Guide To The Galaxy
 */
package br.com.vagas.desafios.MerchantsGuideToTheGalaxy;

/**
 * Engenheiro de Software (v1495403) - Merchant'sGuideToTheGalaxy
 * @author Wu Liang Kuan
 */
public class RomanNumeral {
    public static final int I = 1; 
    public static final int V = 5; 
    public static final int X = 10; 
    public static final int L = 50; 
    public static final int C = 100; 
    public static final int D = 500; 
    public static final int M = 1000; 
    /**
     * Roman Numeral validator
     * @param str the string representation of a roman numeral
     */
    public static boolean isValidRomanNumeral(String str){
        return str.toUpperCase().replaceAll("[ ]*", "").trim()
                  .matches("^M{0,3}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$");
    }
    
    public static int getDecimalValue(String str){
        if(isValidRomanNumeral(str)){
            char[] charArray = str.toUpperCase().replaceAll("[ ]*", "").trim().toCharArray();
            int[] intArray = new int[charArray.length];
            int decimalValue = 0;
            int lastDecimal = 0;
            for (int i = 0; i < charArray.length; i++) {
                switch(charArray[i]){
                    case 'I': intArray[i] = I; break;
                    case 'V': intArray[i] = V; break;
                    case 'X': intArray[i] = X; break;
                    case 'L': intArray[i] = L; break;
                    case 'C': intArray[i] = C; break;
                    case 'D': intArray[i] = D; break;                        
                    case 'M': intArray[i] = M; break;                        
                }
            }
            for (int i = intArray.length; i --> 0; ) {
                decimalValue = (intArray[i]<lastDecimal)?decimalValue-intArray[i]:decimalValue+intArray[i];
                lastDecimal = intArray[i];
            }
            return decimalValue;
        }
        return -1;
    }
}
