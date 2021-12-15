package test;

import scanner.*;
import parser.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestParser {
	
	private Scanner scanDec=null, scanDSsDclStm=null, scanParserWrong1=null,
			scanParserWrong2=null, scanCorrect1=null, scanParserCorrect2=null,
			scanParserCorrect3=null;
	
	@BeforeEach
	public void setUpScanners() {
		Assertions.assertDoesNotThrow(() -> {
			scanDec = new Scanner("src/test/data/testParser/testDec.txt");
			scanDSsDclStm = new Scanner("src/test/data/testParser/testDSsDclStm.txt");
			scanParserWrong1 = new Scanner("src/test/data/testParser/fileParserWrong1.txt");
			scanParserWrong2 = new Scanner("src/test/data/testParser/fileParserWrong2.txt");
			scanCorrect1 = new Scanner("src/test/data/testParser/fileScannerCorrect1.txt");
			scanParserCorrect2 = new Scanner("src/test/data/testParser/fileParserCorrect2.txt");
			scanParserCorrect3 = new Scanner("src/test/data/testParser/fileParserCorrect3.txt");
		});
	}

	@Test
	void testDec() {
		Parser parser = new Parser(scanDec);
		Assertions.assertDoesNotThrow(() -> parser.parse());
	}
	
	@Test
	void testDSsDclStm() {
		Parser parser = new Parser(scanDSsDclStm);
		Assertions.assertThrows(SyntaxException.class, () -> parser.parse(), "Errore sintattico alla riga 5: aspettavo ID, invece ho ricevuto TYFLOAT");	
	}
	
	@Test
	void testParserWrong1() {
		Parser parser = new Parser(scanParserWrong1);
		Assertions.assertThrows(SyntaxException.class, () -> parser.parse(), "Errore sintattico alla riga 2: aspettavo ID, invece ho ricevuto TYFLOAT");
	}
	
	@Test
	void testParserWrong2() {
		Parser parser = new Parser(scanParserWrong2);
		Assertions.assertThrows(SyntaxException.class, () -> parser.parse(), "Errore sintattico alla riga 2: aspettavo ID, invece ho ricevuto TYFLOAT");
	}
	
	@Test
	void testScannerCorrect1() {
		Assertions.assertDoesNotThrow(() -> {
			Assertions.assertEquals("<TYINT,r:1>", scanCorrect1.nextToken().toString());
			Assertions.assertEquals("<ID,r:1,num>", scanCorrect1.nextToken().toString());
			Assertions.assertEquals("<ASSIGN,r:1>", scanCorrect1.nextToken().toString());
			Assertions.assertEquals("<INT,r:1,5>", scanCorrect1.nextToken().toString());
			Assertions.assertEquals("<SEMI,r:1>", scanCorrect1.nextToken().toString());
			Assertions.assertEquals("<INT,r:2,5>", scanCorrect1.nextToken().toString());
			Assertions.assertEquals("<PLUS,r:2>", scanCorrect1.nextToken().toString());
			Assertions.assertEquals("<FLOAT,r:2,13.0>", scanCorrect1.nextToken().toString());
			Assertions.assertEquals("<SEMI,r:2>", scanCorrect1.nextToken().toString());
			Assertions.assertEquals("<EOF,r:3>", scanCorrect1.nextToken().toString());
		});
	}
	
	@Test
	void testParserCorrect2() {
		Parser parser = new Parser(scanParserCorrect2);
		Assertions.assertDoesNotThrow(() -> parser.parse());
	}
	
	@Test
	void testParserCorrect3() {
		Parser parser = new Parser(scanParserCorrect3);
		Assertions.assertDoesNotThrow(() -> parser.parse());
	}


}
