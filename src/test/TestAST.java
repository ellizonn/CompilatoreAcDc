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
		Assertions.assertEquals("Prg: [(Decl:(Id:a),FLOAT)]", nodeProgram.toString());
	}
	
	@Test
	void test2() {
		Scanner scanner = Assertions.assertDoesNotThrow(() -> new Scanner("src/test/data/testAST/test2.txt"));
		Parser parser = new Parser(scanner);
		NodeProgram nodeProgram = Assertions.assertDoesNotThrow(() -> parser.parse());
		Assertions.assertEquals("Prg: [(Decl:(Id:a),INT), (Decl:(Id:b),FLOAT), (Print:(Id:a))]", nodeProgram.toString());
	}
	
	@Test
	void test3() {
		Scanner scanner = Assertions.assertDoesNotThrow(() -> new Scanner("src/test/data/testAST/test3.txt"));
		Parser parser = new Parser(scanner);
		NodeProgram nodeProgram = Assertions.assertDoesNotThrow(() -> parser.parse());
		Assertions.assertEquals("Prg: [(Print:(Id:stampa)), (Decl:(Id:numberfloat),FLOAT), (Decl:(Id:ciao),INT)]", nodeProgram.toString());
	}
	
	@Test
	void test4() {
		Scanner scanner = Assertions.assertDoesNotThrow(() -> new Scanner("src/test/data/testParser/fileParserCorrect2.txt"));
		Parser parser = new Parser(scanner);
		NodeProgram nodeProgram = Assertions.assertDoesNotThrow(() -> parser.parse());
		Assertions.assertEquals("Prg: [(Print:(Id:stampa)), (Decl:(Id:numberfloat),FLOAT), (Decl:(Id:floati),INT), (Assign:(Id:a),(BinOp:PLUS,(Cost:5,INT),(Cost:3,INT))), (Assign:(Id:b),(BinOp:PLUS,(Deref:(Id:a)),(Cost:5,INT)))]", nodeProgram.toString());
	}
	
	@Test
	void test5() {
		Scanner scanner = Assertions.assertDoesNotThrow(() -> new Scanner("src/test/data/testParser/fileParserCorrect3.txt"));
		Parser parser = new Parser(scanner);
		NodeProgram nodeProgram = Assertions.assertDoesNotThrow(() -> parser.parse());
		Assertions.assertEquals("Prg: [(Decl:(Id:num),INT), (Assign:(Id:num),(Cost:5,INT)), (Assign:(Id:num),(Deref:(Id:id))), (Assign:(Id:num),(BinOp:PLUS,(Deref:(Id:id)),(Cost:5.0,FLOAT))), (Assign:(Id:num),(BinOp:TIMES,(Deref:(Id:id)),(Cost:5,INT))), (Assign:(Id:num),(BinOp:TIMES,(Deref:(Id:id)),(Deref:(Id:id)))), (Assign:(Id:num),(BinOp:MINUS,(BinOp:PLUS,(Deref:(Id:id)),(Cost:5,INT)),(BinOp:DIV,(BinOp:TIMES,(Cost:8,INT),(Cost:6.0,FLOAT)),(Cost:2,INT)))), (Assign:(Id:num),(BinOp:PLUS,(BinOp:MINUS,(BinOp:TIMES,(Deref:(Id:id)),(Cost:5,INT)),(BinOp:TIMES,(Cost:8.0,FLOAT),(Cost:6,INT))),(Cost:2,INT))), (Print:(Id:ok))]", nodeProgram.toString());
	}
	
}
