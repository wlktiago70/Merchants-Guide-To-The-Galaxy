/*
 * Merchant's Guide To The Galaxy Unit Tests
 */
package br.com.vagas.desafios.MerchantsGuideToTheGalaxy.Tests;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * Engenheiro de Software (v1495403) - Merchant'sGuideToTheGalaxy
 * @author Wu Liang Kuan
 */
public class UnitTestRunner {
  public static void main(String[] args) {
    Result result = JUnitCore.runClasses(UnitTest.class);
    if(result.wasSuccessful())System.out.println("All tests passed!");
    for (Failure failure : result.getFailures()) {
      System.out.println(failure.toString());
    }
  }
}