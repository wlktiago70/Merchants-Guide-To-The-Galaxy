# Solution Specification & Documentation
# Merchant's Guide To The Galaxy

## Content
1. [Revision history](#revisionhistory)
2. [Introduction](#introduction)
2. 1. [Background](#background)
2. 2. [User Stories](#userstories)
2. 3. [Prerequisites](#prerequisites)
2. 4. [Restrictions and Comments](#restrictions)
3. [Solution overview](#solution)
3. 1. [Architecture overview](#arch)
3. 2. [Compilation and Execution](#comprun)
4. [General references](#generalref)

## 1. <a id="revisionhistory"> Revision history</a> 

You decided to give up on earth after the latest financial collapse left 99.99% of the earth's population with 0.01% of the wealth. Luckily, with the scant sum of money that is left in your account, you are able to afford to rent a spaceship, leave earth, and fly all over the galaxy to sell common metals and dirt (which apparently is worth a lot).

## 2. <a id="introduction"> Introduction</a> 

Buying and selling over the galaxy requires you to convert numbers and units, the numbers used for intergalactic transactions follows similar convention to the roman numerals and you have painstakingly collected the appropriate translation between them.

### 2. 1. <a id="background"> Background</a> 

Roman numerals are based on seven symbols:

|Symbol|Value|
|--|--|
|I|1|
|V|5|
|X|10|
|L|50|
|C|100|
|D|500|
|M|1,000|

Numbers are formed by combining symbols together and adding the values. For example, MMVI is 1000 + 1000 + 5 + 1 = 2006. Generally, symbols are placed in order of value, starting with the largest values. When smaller values precede larger values, the smaller values are subtracted from the larger values, and the result is added to the total. For example MCMXLIV = 1000 + (1000 − 100) + (50 − 10) + (5 − 1) = 1944.

The symbols "I", "X", "C", and "M" can be repeated three times in succession, but no more. (They may appear four times if the third and fourth are separated by a smaller value, such as XXXIX.)

"D", "L", and "V" can never be repeated.

"I" can be subtracted from "V" and "X" only. "X" can be subtracted from "L" and "C" only. "C" can be subtracted from "D" and "M" only. "V", "L", and "D" can never be subtracted.
Only one small-value symbol may be subtracted from any large-value symbol.

A number written in [16]Arabic numerals can be broken into digits. For example, 1903 is composed of 1, 9, 0, and 3. To write the Roman numeral, each of the non-zero digits should be treated separately. Inthe above example, 1,000 = M, 900 = CM, and 3 = III. Therefore, 1903 = MCMIII.

### 2. 2. <a id="userstories"> User Stories</a> 

- Abstract: Roman numeral conversion
> - - Description: As a system user, I want to convert roman numeral value to respective decimal value.
> - - Acceptance Criteria: The program must provide a method that receive a string as argument and return an integer value. 
> - - Definition of Done: The program has to be able to convert a given string representation of roman numeral value into decimal value properly. It has also to be able to process strings with whitespaces and be case insesitive.
> - - Hints: The string validation can be done by regular expression.

- Abstract: Sentence intepretation
> - - Description: As a system user, I want to input lines of text detailing my notes on conversion between intergalactic units and roman numerals.
> - - Acceptance Criteria: The program must provide lexical, syntactic and semantic analyis on a given sentence, and then provide a proper answer for each sentence. The program must receive the inputs from a file located by a given path.
> - - Definition of Done: The program has to be able to to handle invalid queries appropriately and provide proper answer for each sentence. The unit tests must run with no errors.
> - - Hints: The interpretation can vary depending on context, hence it has to be considered.

### 2. 3. <a id="prerequisites"> Prerequisites</a> 

The program requires a correct path to the input text file as argument. When there is no specified path, the program will get the input from system command-line.

The program must be salved on a path that JVM is able to read the required binary.

### 2. 4. <a id="restrictions"> Restrictions and Comments</a> 

The solution described in this document consider only the syntax exemplified by following test and some of its variation:

Test input:
> - glob is I
> - prok is V
> - pish is X
> - tegj is L
> - glob glob Silver is 34 Credits
> - glob prok Gold is 57800 Credits
> - pish pish Iron is 3910 Credits
> - how much is pish tegj glob glob ?
> - how many Credits is glob prok Silver ?
> - how many Credits is glob prok Gold ?
> - how many Credits is glob prok Iron ?
> - how much wood could a woodchuck chuck if a woodchuck could chuck wood ?

Test Output:
> - pish tegj glob glob is 42
> - glob prok Silver is 68 Credits
> - glob prok Gold is 57800 Credits
> - glob prok Iron is 782 Credits
> - I have no idea what you are talking about

## 3. <a id="solution"> Solution overview</a> 

The solution was implemented using Java language and the unit tests were created using JUnit library.

### 3. 1. <a id="arch"> Architecture overview</a> 

For this specification three principal classes were created:
 - Context
 - Interpreter
 - RomanNumeral

Since a interpreter can have different interpretation in different contexts, i.e. it has a dependency with the context, the Context class was created to encapsulate the context relevant attributes, such as word definitions, values exchange informations, necessary keywords to establish the communication and context information from user point of view.

The Interpreter class is responsible by sentence understanding, this process occurrs in three stages: first, the program will try to find all lexemes of the sentence, identify some of them by using token values and remove the unnecessary characters for next stage of the process; in the second stage, the program checks the syntax of the sentence (for this solution the grammar uses only terminal symbols) and identifies the action required for next stage; finally, the program performs semantic analysis on the sentence based on actions identified in previous stage. If the analysis fails in any stage of this process, a null value will be returned to a method responsible for the communication and a default message will be shown. In addition, two enum class were created in Interpreter class, Flag and Key, the first one is used on member methods communication and the second one is used to check reserved words.

The RomanNumeral is the class that provide all necessary method for validation and conversion of string representation of roman numeral. These method are called by semantic analyser method in Interpreter class.

The App class is an interface that has a main method for program execution. This class provide the communication between user input and the Interpreter class. The input source can be a file or user inputs through command-line.  

### 3. 2. <a id="comprun"> Compilation and Execution</a> 

To compile the java source code you can run follwing commands:
> - javac br\com\vagas\desafios\MerchantsGuideToTheGalaxy\App.java
> - javac br\com\vagas\desafios\MerchantsGuideToTheGalaxy\Interpreter.java
> - javac br\com\vagas\desafios\MerchantsGuideToTheGalaxy\Context.java
> - javac br\com\vagas\desafios\MerchantsGuideToTheGalaxy\RomanNumeral.java
> - javac br\com\vagas\desafios\MerchantsGuideToTheGalaxy\Tests\UnitTest.java
> - javac br\com\vagas\desafios\MerchantsGuideToTheGalaxy\Tests\UnitTestRunner.java

To run the binary file you can run the following commands, "[...].App" to execute the main aplication and "[...].UnitTestRunner" to execute the unit tests:
> - java br.com.vagas.desafios.MerchantsGuideToTheGalaxy.App input.txt
> - java br.com.vagas.desafios.MerchantsGuideToTheGalaxy.Tests.UnitTestRunner

## 4. <a id="generalref"> General references</a> 
Vagas - Merchant's Guide To The Galax[Y] challenge documentation, available in: https://site.vagas.com.br/Download/vagas/ruby/desafioVAGAS1MerchantsGuideToTheGalaxyVagadeDesenvolvedorRuby.pdf
