package test;

import java.io.FileNotFoundException;

import scanner.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class TestScanner {
	
	private Exception exc;
	
	private Scanner scanEOF=null, scanID=null, scanKeywords=null, scanOperators=null,
			scanId=null, scanINT=null, scanFLOAT=null, scanErroriIdNumbers=null,
			scanGenerale=null;
	
	@BeforeEach
	public void setUpScanners() {
		Assertions.assertDoesNotThrow(() -> {
			scanEOF = new Scanner("src/test/data/testScanner/testEOF.txt");
			scanID = new Scanner("src/test/data/testScanner/testID.txt");
			scanKeywords = new Scanner("src/test/data/testScanner/testKeywords.txt");
			scanOperators = new Scanner("src/test/data/testScanner/testOperators.txt");
			scanId = new Scanner("src/test/data/testScanner/testScanId.txt");
			scanINT = new Scanner("src/test/data/testScanner/testINT.txt");
			scanFLOAT = new Scanner("src/test/data/testScanner/testFLOAT.txt");
			scanErroriIdNumbers = new Scanner("src/test/data/testScanner/erroriIdNumbers.txt");
			scanGenerale = new Scanner("src/test/data/testScanner/testGenerale.txt");
		});
	}

	@Test
	public void testFileNotFoundException() {
		String path = "src/test/data/test.txt";
		Assertions.assertThrows(FileNotFoundException.class, () -> new Scanner(path));
	}
	
	@Test
	public void testEOF() {
		Assertions.assertDoesNotThrow(() -> {
			Assertions.assertEquals("<EOF,r:3>", scanEOF.nextToken().toString());
		});
	}
	
	@Test
	public void testID() {
		Assertions.assertDoesNotThrow(() -> {
			Assertions.assertEquals("<ID,r:1,jskjdsfhkjdshkf>", scanID.nextToken().toString());
			Assertions.assertEquals("<ID,r:2,printl>", scanID.nextToken().toString());
			Assertions.assertEquals("<PRINT,r:3>", scanID.nextToken().toString());
			Assertions.assertEquals("<ID,r:4,hhhjj>", scanID.nextToken().toString());
			Assertions.assertEquals("<EOF,r:5>", scanID.nextToken().toString());
		});
		
	}
	
	@Test
	public void testKeywords() {
		Assertions.assertDoesNotThrow(() -> {
			Assertions.assertEquals("<PRINT,r:2>", scanKeywords.nextToken().toString());
			Assertions.assertEquals("<TYFLOAT,r:2>", scanKeywords.nextToken().toString());
			Assertions.assertEquals("<TYINT,r:5>", scanKeywords.nextToken().toString());
			Assertions.assertEquals("<EOF,r:5>", scanKeywords.nextToken().toString());
		});
	}

	@Test
	public void testOperators() {
		Assertions.assertDoesNotThrow(() -> {
			Assertions.assertEquals("<PLUS,r:1>", scanOperators.nextToken().toString());
			Assertions.assertEquals("<MINUS,r:2>", scanOperators.nextToken().toString());
			Assertions.assertEquals("<TIMES,r:2>", scanOperators.nextToken().toString());
			Assertions.assertEquals("<DIV,r:3>", scanOperators.nextToken().toString());
			Assertions.assertEquals("<ASSIGN,r:8>", scanOperators.nextToken().toString());
			Assertions.assertEquals("<SEMI,r:10>", scanOperators.nextToken().toString());
			Assertions.assertEquals("<EOF,r:10>", scanOperators.nextToken().toString());
		});
	}
	
	@Test
	public void testScanId() {
		Assertions.assertDoesNotThrow(() -> {
			Assertions.assertEquals("<TYINT,r:1>", scanId.nextToken().toString());
			Assertions.assertEquals("<TYFLOAT,r:2>", scanId.nextToken().toString());
			Assertions.assertEquals("<PRINT,r:3>", scanId.nextToken().toString());
			Assertions.assertEquals("<ID,r:4,nome>", scanId.nextToken().toString());
			Assertions.assertEquals("<ID,r:5,intnome>", scanId.nextToken().toString());
			Assertions.assertEquals("<TYINT,r:6>", scanId.nextToken().toString());
			Assertions.assertEquals("<ID,r:6,nome>", scanId.nextToken().toString());
			Assertions.assertEquals("<EOF,r:6>", scanId.nextToken().toString());
		});
	}
	
	@Test
	public void testINT() {
		Assertions.assertDoesNotThrow(() -> {
			Assertions.assertEquals("<INT,r:2,698>", scanINT.nextToken().toString());
			Assertions.assertEquals("<EOF,r:2>", scanINT.nextToken().toString());
		});
	}
	
	@Test
	public void testFLOAT() {
		Assertions.assertDoesNotThrow(() -> {
			Assertions.assertEquals("<FLOAT,r:1,098.895>", scanFLOAT.nextToken().toString());
			Assertions.assertEquals("<INT,r:2,98>", scanFLOAT.nextToken().toString());
		});
		exc = Assertions.assertThrows(LexicalException.class, () -> scanFLOAT.nextToken());
		Assertions.assertEquals("Rilevato numero float con parte decimale nulla alla riga 3", exc.getMessage());
		exc = Assertions.assertThrows(LexicalException.class, () -> scanFLOAT.nextToken());
		Assertions.assertEquals("Rilevato numero float con parte decimale troppo lunga alla riga 5", exc.getMessage());
		Assertions.assertDoesNotThrow(() -> Assertions.assertEquals("<EOF,r:5>", scanFLOAT.nextToken().toString()));
	}
	
	@Test
	public void testErroriIdNumbers() {
			exc = Assertions.assertThrows(LexicalException.class, () -> scanErroriIdNumbers.nextToken());
			Assertions.assertEquals("Rilevato numero float con parte decimale nulla alla riga 1", exc.getMessage());
			Assertions.assertDoesNotThrow(() -> Assertions.assertEquals("<ID,r:1,asd>", scanErroriIdNumbers.nextToken().toString()));
			exc = Assertions.assertThrows(LexicalException.class, () -> scanErroriIdNumbers.nextToken());
			Assertions.assertEquals("E' stato rilevato il carattere illegale '.' alla riga 2", exc.getMessage());
			Assertions.assertDoesNotThrow(() -> {
				Assertions.assertEquals("<INT,r:2,123>", scanErroriIdNumbers.nextToken().toString());
				Assertions.assertEquals("<ID,r:3,asd>", scanErroriIdNumbers.nextToken().toString());
			});
			exc = Assertions.assertThrows(LexicalException.class, () -> scanErroriIdNumbers.nextToken());
			Assertions.assertEquals("E' stato rilevato il carattere illegale '.' alla riga 3", exc.getMessage());
			Assertions.assertDoesNotThrow(() -> {
				Assertions.assertEquals("<INT,r:3,123>", scanErroriIdNumbers.nextToken().toString());
				Assertions.assertEquals("<TYINT,r:4>", scanErroriIdNumbers.nextToken().toString());
			});
			exc = Assertions.assertThrows(LexicalException.class, () -> scanErroriIdNumbers.nextToken());
			Assertions.assertEquals("E' stato rilevato il carattere illegale '.' alla riga 4", exc.getMessage());
			exc = Assertions.assertThrows(LexicalException.class, () -> scanErroriIdNumbers.nextToken());
			Assertions.assertEquals("Rilevato numero float con parte decimale nulla alla riga 4", exc.getMessage());
			Assertions.assertDoesNotThrow(() -> {
				Assertions.assertEquals("<INT,r:5,123>", scanErroriIdNumbers.nextToken().toString());
				Assertions.assertEquals("<ID,r:5,asd>", scanErroriIdNumbers.nextToken().toString());
				Assertions.assertEquals("<ID,r:6,abcd>", scanErroriIdNumbers.nextToken().toString());
				Assertions.assertEquals("<INT,r:6,6>", scanErroriIdNumbers.nextToken().toString());
			});
	}
	
	@Test
	public void testGenerale() {
		Assertions.assertDoesNotThrow(() -> {
			Assertions.assertEquals("<TYINT,r:1>", scanGenerale.nextToken().toString());
			Assertions.assertEquals("<ID,r:1,temp>", scanGenerale.nextToken().toString());
			Assertions.assertEquals("<SEMI,r:1>", scanGenerale.nextToken().toString());
			Assertions.assertEquals("<ID,r:2,temp>", scanGenerale.nextToken().toString());
			Assertions.assertEquals("<ASSIGN,r:2>", scanGenerale.nextToken().toString());
		});
		exc = Assertions.assertThrows(LexicalException.class, () -> scanGenerale.nextToken());
		Assertions.assertEquals("Rilevato numero float con parte decimale nulla alla riga 2", exc.getMessage());
		Assertions.assertDoesNotThrow(() -> {
			Assertions.assertEquals("<SEMI,r:2>", scanGenerale.nextToken().toString());
			Assertions.assertEquals("<TYFLOAT,r:4>", scanGenerale.nextToken().toString());
			Assertions.assertEquals("<ID,r:4,b>", scanGenerale.nextToken().toString());
			Assertions.assertEquals("<SEMI,r:4>", scanGenerale.nextToken().toString());
			Assertions.assertEquals("<ID,r:5,b>", scanGenerale.nextToken().toString());
			Assertions.assertEquals("<ASSIGN,r:5>", scanGenerale.nextToken().toString());
			Assertions.assertEquals("<ID,r:5,temp>", scanGenerale.nextToken().toString());
			Assertions.assertEquals("<PLUS,r:5>", scanGenerale.nextToken().toString());
			Assertions.assertEquals("<FLOAT,r:5,3.2>", scanGenerale.nextToken().toString());
			Assertions.assertEquals("<SEMI,r:5>", scanGenerale.nextToken().toString());
			Assertions.assertEquals("<PRINT,r:6>", scanGenerale.nextToken().toString());
			Assertions.assertEquals("<ID,r:6,b>", scanGenerale.nextToken().toString());
			Assertions.assertEquals("<SEMI,r:6>", scanGenerale.nextToken().toString());
			Assertions.assertEquals("<EOF,r:7>", scanGenerale.nextToken().toString());
		});	
	}

}
