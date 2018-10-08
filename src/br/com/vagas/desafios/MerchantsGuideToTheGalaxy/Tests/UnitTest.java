/*
 * Merchant's Guide To The Galaxy Unit Tests
 */
package br.com.vagas.desafios.MerchantsGuideToTheGalaxy.Tests;

import br.com.vagas.desafios.MerchantsGuideToTheGalaxy.Context;
import br.com.vagas.desafios.MerchantsGuideToTheGalaxy.Interpreter;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Engenheiro de Software (v1495403) - Merchant'sGuideToTheGalaxy
 * @author Wu Liang Kuan
 */
public class UnitTest {
    @Test
    public void assertStandardInput() {
        Interpreter interpreter = new Interpreter(new Context());

        // assert statements
        assertEquals("",interpreter.getAnswer("glob is I"));
        assertEquals("",interpreter.getAnswer("prok is V"));
        assertEquals("",interpreter.getAnswer("pish is X"));
        assertEquals("",interpreter.getAnswer("tegj is L"));
        assertEquals("",interpreter.getAnswer("glob glob Silver is 34 Credits"));
        assertEquals("",interpreter.getAnswer("glob prok Gold is 57800 Credits"));
        assertEquals("",interpreter.getAnswer("pish pish Iron is 3910 Credits"));
        assertEquals("pish tegj glob glob is 42",interpreter.getAnswer("how much is pish tegj glob glob ?"));
        assertEquals("glob prok Silver is 68 Credits",interpreter.getAnswer("how many Credits is glob prok Silver ?"));
        assertEquals("glob prok Gold is 57800 Credits",interpreter.getAnswer("how many Credits is glob prok Gold ?"));
        assertEquals("glob prok Iron is 782 Credits",interpreter.getAnswer("how many Credits is glob prok Iron ?"));
        assertEquals("I have no idea what you are talking about",interpreter.getAnswer("how much wood could a woodchuck chuck if a woodchuck could chuck wood ?"));
    }
    @Test
    public void interpreterShouldBeCaseInsensitive() {
        Interpreter interpreter = new Interpreter(new Context());

        // assert statements
        assertEquals("",interpreter.getAnswer("Glob IS I"));
        assertEquals("",interpreter.getAnswer("pRok iS v"));
        assertEquals("",interpreter.getAnswer("piSh Is X"));
        assertEquals("",interpreter.getAnswer("tegJ is L"));
        assertEquals("",interpreter.getAnswer("gLOb GloB SiLveR is 34 Credits"));
        assertEquals("",interpreter.getAnswer("GloB pROk gOlD is 57800 cRedits"));
        assertEquals("",interpreter.getAnswer("Pish pISH IrON is 3910 crEdits"));
        assertEquals("pish tegj glob glob is 42",interpreter.getAnswer("hOw MuCh iS pish tegj glob glob ?"));
        assertEquals("glob prok Silver is 68 Credits",interpreter.getAnswer("How maNy Credits IS glob prok Silver ?"));
        assertEquals("glob prok Gold is 57800 Credits",interpreter.getAnswer("hoW manY Credits Is glob prok Gold ?"));
        assertEquals("glob prok Iron is 782 Credits",interpreter.getAnswer("HOW MANY Credits iS glob prok Iron ?"));
    }
    @Test
    public void roundCurrency() {
        Interpreter interpreter = new Interpreter(new Context());

        // assert statements
        assertEquals("",interpreter.getAnswer("glob is I"));
        assertEquals("",interpreter.getAnswer("glob glob Silver is 1 Credit"));
        assertEquals("glob glob glob Silver is 1 Credits",interpreter.getAnswer("how many Credits is glob glob glob Silver ?"));
    }
    @Test
    public void checkRomanNumeral() {
        Interpreter interpreter = new Interpreter(new Context());

        // assert statements
        assertEquals("MMCMXLIII is 2943",interpreter.getAnswer("how much is MMCMXLIII?"));
        assertEquals("M M C M X L I I I is 2943",interpreter.getAnswer("how much is M M C M X L I I I?"));
        assertEquals("mmcmxliii is 2943",interpreter.getAnswer("how much is mmcmxliii?"));
        assertEquals("m m c m x l i i i is 2943",interpreter.getAnswer("how much is m m c m x l i i i?"));
    }
}
