package test;

import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import scanner.*;
import parser.*;

class TestParser {

	@Test
	void testDec() throws IOException {
		String path = "src/test/data/testParser/testDec.txt";
		Scanner scanner = new Scanner(path);
		Parser parser = new Parser(scanner);
		Assertions.assertDoesNotThrow(() -> parser.parse());
	}
	
	@Test
	void testDSsDclStm() throws IOException {
		String path = "src/test/data/testParser/testDSsDclStm.txt";
		Scanner scanner = new Scanner(path);
		Parser parser = new Parser(scanner);
		SintaxException exc = Assertions.assertThrows(SintaxException.class, () -> parser.parse());
		Assertions.assertEquals("Errore sintattico alla riga 5: aspettavo ID, invece ho ricevuto TYFLOAT", exc.getMessage());	
	}
	
	@Test
	void testParserWrong1() throws IOException {
		String path = "src/test/data/testParser/fileParserWrong1.txt";
		Scanner scanner = new Scanner(path);
		Parser parser = new Parser(scanner);
		SintaxException exc = Assertions.assertThrows(SintaxException.class, () -> parser.parse());
		Assertions.assertEquals("Errore sintattico alla riga 2: aspettavo ID, invece ho ricevuto TYFLOAT", exc.getMessage());
	}
	
	@Test
	void testParserWrong2() throws IOException {
		String path = "src/test/data/testParser/fileParserWrong2.txt";
		Scanner scanner = new Scanner(path);
		Parser parser = new Parser(scanner);
		SintaxException exc = Assertions.assertThrows(SintaxException.class, () -> parser.parse());
		Assertions.assertEquals("Errore sintattico alla riga 2: aspettavo ID, invece ho ricevuto TYFLOAT", exc.getMessage());
	}
	
	@Test
	void testScannerCorrect1() throws IOException {
		String path = "src/test/data/testParser/fileScannerCorrect1.txt";
		Scanner scanner = new Scanner(path);
		Assertions.assertEquals("<TYINT,r:1>", scanner.nextToken().toString());
		Assertions.assertEquals("<ID,r:1,num>", scanner.nextToken().toString());
		Assertions.assertEquals("<ASSIGN,r:1>", scanner.nextToken().toString());
		Assertions.assertEquals("<INT,r:1,5>", scanner.nextToken().toString());
		Assertions.assertEquals("<SEMI,r:1>", scanner.nextToken().toString());
		Assertions.assertEquals("<INT,r:2,5>", scanner.nextToken().toString());
		Assertions.assertEquals("<PLUS,r:2>", scanner.nextToken().toString());
		Assertions.assertEquals("<FLOAT,r:2,13.0>", scanner.nextToken().toString());
		Assertions.assertEquals("<SEMI,r:2>", scanner.nextToken().toString());
		Assertions.assertEquals("<EOF,r:3>", scanner.nextToken().toString());
	}
	
	@Test
	void testParserCorrect2() throws IOException {
		String path = "src/test/data/testParser/fileParserCorrect2.txt";
		Scanner scanner = new Scanner(path);
		Parser parser = new Parser(scanner);
		Assertions.assertDoesNotThrow(() -> parser.parse());
	}
	
	@Test
	void testParserCorrect3() throws IOException {
		String path = "src/test/data/testParser/fileParserCorrect3.txt";
		Scanner scanner = new Scanner(path);
		Parser parser = new Parser(scanner);
		Assertions.assertDoesNotThrow(() -> parser.parse());
	}


}
