/*
 * Merchant's Guide To The Galaxy
 */
package br.com.vagas.desafios.MerchantsGuideToTheGalaxy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Engenheiro de Software (v1495403) - Merchant'sGuideToTheGalaxy
 * @author Wu Liang Kuan
 */
public class App {

    private static BufferedReader notes;
    private static BufferedWriter answers;
    private static String sentence;
    private static Interpreter interpreter;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        interpreter = new Interpreter(new Context());
        if(args.length <= 0){
            Scanner inputs = new Scanner(System.in);
            System.out.println("So tell me what you've heard.");
            while(inputs.hasNext()){
                sentence = inputs.nextLine();
                if(sentence.isEmpty())
                    continue;
                if(sentence.toLowerCase().matches("end|finish|exit"))
                    break;
                System.out.println(interpreter.getAnswer(sentence));
                System.out.println("Anything else?");
            }
        }
        else {
            String outputline;
            try {
                notes = new BufferedReader(new InputStreamReader(new FileInputStream(args[0])));
                answers = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("output.txt")));
                sentence = notes.readLine();
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, "This program expects unicode encoding file.", ex);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, "There is no file with this name.", ex);
            } catch (IOException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, "An error occurrs when reading your file.", ex);
            } 
            
            while(sentence != null){
                try {
                    if(sentence.isEmpty())
                        continue;
                    outputline = interpreter.getAnswer(sentence);
                    if(outputline.length() != 0){
                        answers.append(outputline.subSequence(0, outputline.length()));
                        answers.newLine();
                    }
                    System.out.println(outputline);
                    sentence = notes.readLine();
                } catch (IOException ex) {
                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, "An error occurrs when reading your file.", ex);
                }
            }
            
            try {
                notes.close();
                answers.close();
            } catch (IOException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, "An error occurrs when closing your file.", ex);
            }
            
        }
    }
    
}
