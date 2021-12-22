package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ast.*;
import parser.*;
import scanner.*;

class TestAST {

	@Test
	void test1() {
		Scanner scanner = Assertions.assertDoesNotThrow(() -> new Scanner("src/test/data/testAST/test1.txt"));
		Parser parser = new Parser(scanner);
		NodeProgram nodeProgram = Assertions.assertDoesNotThrow(() -> parser.parse());
		Assertions.assertEquals("Prg: [Decl: <Id: a, FLOAT>]", nodeProgram.toString());
	}
	
	@Test
	void test2() {
		Scanner scanner = Assertions.assertDoesNotThrow(() -> new Scanner("src/test/data/testAST/test2.txt"));
		Parser parser = new Parser(scanner);
		NodeProgram nodeProgram = Assertions.assertDoesNotThrow(() -> parser.parse());
		Assertions.assertEquals("Prg: [Decl: <Id: a, INT>, Decl: <Id: b, FLOAT>, Print: <Id: a>]", nodeProgram.toString());
	}
	
	@Test
	void test3() {
		Scanner scanner = Assertions.assertDoesNotThrow(() -> new Scanner("src/test/data/testAST/test3.txt"));
		Parser parser = new Parser(scanner);
		NodeProgram nodeProgram = Assertions.assertDoesNotThrow(() -> parser.parse());
		Assertions.assertEquals("Prg: [Print: <Id: stampa>, Decl: <Id: numberfloat, FLOAT>, Decl: <Id: ciao, INT>]", nodeProgram.toString());
	}
	
	/*
	@Test
	void test4() {
		Scanner scanner = Assertions.assertDoesNotThrow(() -> new Scanner("src/test/data/testParser/fileParserCorrect2.txt"));
		Parser parser = new Parser(scanner);
		NodeProgram nodeProgram = Assertions.assertDoesNotThrow(() -> parser.parse());
		System.out.println(nodeProgram);
	}
	*/

	
}
