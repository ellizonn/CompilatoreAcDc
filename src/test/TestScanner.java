package test;

import java.io.FileNotFoundException;
import java.io.IOException;

import scanner.Scanner;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class TestScanner {

	@Test
	public void FileNotFoundException() throws FileNotFoundException {
		String path = "src/test/data/test.txt";
		Assertions.assertThrows(FileNotFoundException.class, () -> new Scanner(path));
	}
	
	@Test
	public void testEOF() throws IOException {
		String path = "src/test/data/testScanner/testEOF.txt";
		Scanner scanner = new Scanner(path);
		Assertions.assertEquals("<EOF,r:3>", scanner.nextToken().toString());
	}
	
	@Test
	public void testID() throws IOException {
		String path = "src/test/data/testScanner/testID.txt";
		Scanner scanner = new Scanner(path);
		Assertions.assertEquals("<ID,r:1,jskjdsfhkjdshkf>", scanner.nextToken().toString());
		Assertions.assertEquals("<ID,r:2,printl>", scanner.nextToken().toString());
		Assertions.assertEquals("<PRINT,r:3>", scanner.nextToken().toString());
		Assertions.assertEquals("<ID,r:4,hhhjj>", scanner.nextToken().toString());
		Assertions.assertEquals("<EOF,r:5>", scanner.nextToken().toString());
	}
	
	@Test
	public void testKeywords() throws IOException {
		String path = "src/test/data/testScanner/testKeywords.txt";
		Scanner scanner = new Scanner(path);
		Assertions.assertEquals("<PRINT,r:2>", scanner.nextToken().toString());
		Assertions.assertEquals("<TYFLOAT,r:2>", scanner.nextToken().toString());
		Assertions.assertEquals("<TYINT,r:5>", scanner.nextToken().toString());
		Assertions.assertEquals("<EOF,r:5>", scanner.nextToken().toString());
	}

	@Test
	public void testOperators() throws IOException {
		String path = "src/test/data/testScanner/testOperators.txt";
		Scanner scanner = new Scanner(path);
		Assertions.assertEquals("<PLUS,r:1>", scanner.nextToken().toString());
		Assertions.assertEquals("<MINUS,r:2>", scanner.nextToken().toString());
		Assertions.assertEquals("<TIMES,r:2>", scanner.nextToken().toString());
		Assertions.assertEquals("<DIV,r:3>", scanner.nextToken().toString());
		Assertions.assertEquals("<ASSIGN,r:8>", scanner.nextToken().toString());
		Assertions.assertEquals("<SEMI,r:10>", scanner.nextToken().toString());
		Assertions.assertEquals("<EOF,r:10>", scanner.nextToken().toString());
	}
	
	@Test
	public void testScanId() throws IOException {
		String path = "src/test/data/testScanner/testScanId.txt";
		Scanner scanner = new Scanner(path);
		Assertions.assertEquals("<TYINT,r:1>", scanner.nextToken().toString());
		Assertions.assertEquals("<TYFLOAT,r:2>", scanner.nextToken().toString());
		Assertions.assertEquals("<PRINT,r:3>", scanner.nextToken().toString());
		Assertions.assertEquals("<ID,r:4,nome>", scanner.nextToken().toString());
		Assertions.assertEquals("<ID,r:5,intnome>", scanner.nextToken().toString());
		Assertions.assertEquals("<TYINT,r:6>", scanner.nextToken().toString());
		Assertions.assertEquals("<ID,r:6,nome>", scanner.nextToken().toString());
		Assertions.assertEquals("<EOF,r:6>", scanner.nextToken().toString());
	}
	
	@Test
	public void testINT() throws IOException {
		String path = "src/test/data/testScanner/testINT.txt";
		Scanner scanner = new Scanner(path);
		Assertions.assertEquals("<INT,r:2,698>", scanner.nextToken().toString());
		Assertions.assertEquals("<EOF,r:2>", scanner.nextToken().toString());
	}
	
	@Test
	public void testFLOAT() throws IOException {
		String path = "src/test/data/testScanner/testFLOAT.txt";
		Scanner scanner = new Scanner(path);
		Assertions.assertEquals("<FLOAT,r:1,098.895>", scanner.nextToken().toString());
		Assertions.assertEquals("<INT,r:2,98>", scanner.nextToken().toString());
		Assertions.assertThrows(NumberFormatException.class, () -> scanner.nextToken());
		Assertions.assertThrows(NumberFormatException.class, () -> scanner.nextToken());
		Assertions.assertEquals("<EOF,r:5>", scanner.nextToken().toString());
	}
	
	@Test
	public void testErroriIdNumbers() throws IOException {
		String path = "src/test/data/testScanner/erroriIdNumbers.txt";
		Scanner scanner = new Scanner(path);
		Assertions.assertThrows(NumberFormatException.class, () -> scanner.nextToken());
		Assertions.assertEquals("<ID,r:1,asd>", scanner.nextToken().toString());
		Assertions.assertThrows(IllegalArgumentException.class, () -> scanner.nextToken());
		Assertions.assertEquals("<INT,r:2,123>", scanner.nextToken().toString());
		Assertions.assertEquals("<ID,r:3,asd>", scanner.nextToken().toString());
		Assertions.assertThrows(IllegalArgumentException.class, () -> scanner.nextToken());
		Assertions.assertEquals("<INT,r:3,123>", scanner.nextToken().toString());
		Assertions.assertEquals("<TYINT,r:4>", scanner.nextToken().toString());
		Assertions.assertThrows(IllegalArgumentException.class, () -> scanner.nextToken());
		Assertions.assertThrows(NumberFormatException.class, () -> scanner.nextToken());
		Assertions.assertEquals("<INT,r:5,123>", scanner.nextToken().toString());
		Assertions.assertEquals("<ID,r:5,asd>", scanner.nextToken().toString());
		Assertions.assertEquals("<ID,r:6,abcd>", scanner.nextToken().toString());
		Assertions.assertEquals("<INT,r:6,6>", scanner.nextToken().toString());
	}
	
	@Test
	public void testGenerale() throws IOException {
		String path = "src/test/data/testScanner/testGenerale.txt";
		Scanner scanner = new Scanner(path);
		Assertions.assertEquals("<TYINT,r:1>", scanner.nextToken().toString());
		Assertions.assertEquals("<ID,r:1,temp>", scanner.nextToken().toString());
		Assertions.assertEquals("<SEMI,r:1>", scanner.nextToken().toString());
		Assertions.assertEquals("<ID,r:2,temp>", scanner.nextToken().toString());
		Assertions.assertEquals("<ASSIGN,r:2>", scanner.nextToken().toString());
		Assertions.assertThrows(NumberFormatException.class, () -> scanner.nextToken());
		Assertions.assertEquals("<SEMI,r:2>", scanner.nextToken().toString());
		Assertions.assertEquals("<TYFLOAT,r:4>", scanner.nextToken().toString());
		Assertions.assertEquals("<ID,r:4,b>", scanner.nextToken().toString());
		Assertions.assertEquals("<SEMI,r:4>", scanner.nextToken().toString());
		Assertions.assertEquals("<ID,r:5,b>", scanner.nextToken().toString());
		Assertions.assertEquals("<ASSIGN,r:5>", scanner.nextToken().toString());
		Assertions.assertEquals("<ID,r:5,temp>", scanner.nextToken().toString());
		Assertions.assertEquals("<PLUS,r:5>", scanner.nextToken().toString());
		Assertions.assertEquals("<FLOAT,r:5,3.2>", scanner.nextToken().toString());
		Assertions.assertEquals("<SEMI,r:5>", scanner.nextToken().toString());
		Assertions.assertEquals("<PRINT,r:6>", scanner.nextToken().toString());
		Assertions.assertEquals("<ID,r:6,b>", scanner.nextToken().toString());
		Assertions.assertEquals("<SEMI,r:6>", scanner.nextToken().toString());
		Assertions.assertEquals("<EOF,r:7>", scanner.nextToken().toString());
	}

}
