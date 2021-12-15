package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ast.*;
import parser.*;
import scanner.*;

class TestAST {

	/*
	@Test
	void test() {
		Scanner scanner = Assertions.assertDoesNotThrow(() -> new Scanner("src/test/data/testAST/testDec.txt"));
		Parser parser = new Parser(scanner);
		NodeProgram nodeProgram = Assertions.assertDoesNotThrow(() -> parser.parse());
	}
	*/
	
	@Test
	void testDec() {
		Scanner scanner = Assertions.assertDoesNotThrow(() -> new Scanner("src/test/data/testParser/testDec.txt"));
		Parser parser = new Parser(scanner);
		NodeProgram nodeProgram = Assertions.assertDoesNotThrow(() -> parser.parse());
		//Assertions.assertEquals("Decl: <id="+this.id+",type="+this.type+">", actual);
		System.out.println(nodeProgram.toString());
	}
	
	/*
	@Test
	void testDSsDclStm() {
		Parser parser = new Parser(scanDSsDclStm);
		Assertions.assertThrows(SyntaxException.class, () -> parser.parse(), "Errore sintattico alla riga 5: aspettavo ID, invece ho ricevuto TYFLOAT");	
	}
	*/

}
