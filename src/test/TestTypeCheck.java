package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ast.LangType;
import ast.NodeProgram;
import parser.Parser;
import scanner.Scanner;
import symbolTable.SymbolTable;
import visitor.TypeCheckingVisitor;

class TestTypeCheck {

	@Test
	void testDeclRepeat() {
		SymbolTable.init();
		Scanner scanner = Assertions.assertDoesNotThrow(() -> new Scanner("src/test/data/testTypeCheck/1_dicRipetute.txt"));
		Parser parser = new Parser(scanner);
		NodeProgram nodeProgram = Assertions.assertDoesNotThrow(() -> parser.parse());
		TypeCheckingVisitor visitor = new TypeCheckingVisitor();
		visitor.visit(nodeProgram);
		Assertions.assertEquals("Variabile 'a' gia' definita\n", visitor.getLog().toString());
	}
	
	@Test
	void testIdUndefined() {
		SymbolTable.init();
		Scanner scanner = Assertions.assertDoesNotThrow(() -> new Scanner("src/test/data/testTypeCheck/3_IdNotDeclare.txt"));
		Parser parser = new Parser(scanner);
		NodeProgram nodeProgram = Assertions.assertDoesNotThrow(() -> parser.parse());
		TypeCheckingVisitor visitor = new TypeCheckingVisitor();
		visitor.visit(nodeProgram);
		Assertions.assertEquals("Variabile 'b' non definita\n"+"Variabile 'c' non definita\n", visitor.getLog().toString());
	}
	
	@Test
	void testCorrect1() {
		SymbolTable.init();
		Scanner scanner = Assertions.assertDoesNotThrow(() -> new Scanner("src/test/data/testTypeCheck/2_fileCorrect.txt"));
		Parser parser = new Parser(scanner);
		NodeProgram nodeProgram = Assertions.assertDoesNotThrow(() -> parser.parse());
		TypeCheckingVisitor visitor = new TypeCheckingVisitor();
		visitor.visit(nodeProgram);
		Assertions.assertEquals(SymbolTable.lookup("a").getType(), LangType.INT);
		Assertions.assertTrue(visitor.getLog().toString().isEmpty());
	}
	
	@Test
	void testErrorAssignConvert() {
		SymbolTable.init();
		Scanner scanner = Assertions.assertDoesNotThrow(() -> new Scanner("src/test/data/testTypeCheck/errorAssignConvert.txt"));
		Parser parser = new Parser(scanner);
		NodeProgram nodeProgram = Assertions.assertDoesNotThrow(() -> parser.parse());
		TypeCheckingVisitor visitor = new TypeCheckingVisitor();
		visitor.visit(nodeProgram);
		Assertions.assertEquals("Variabile 'a' non definita\n"+"Espressione: (Deref:(Id:b)) non compatibile con Id: a\n", visitor.getLog().toString());
	}
	
	@Test
	void testErrorOp() {
		SymbolTable.init();
		Scanner scanner = Assertions.assertDoesNotThrow(() -> new Scanner("src/test/data/testTypeCheck/errorOp.txt"));
		Parser parser = new Parser(scanner);
		NodeProgram nodeProgram = Assertions.assertDoesNotThrow(() -> parser.parse());
		TypeCheckingVisitor visitor = new TypeCheckingVisitor();
		visitor.visit(nodeProgram);
		Assertions.assertEquals("Espressione: (BinOp:TIMES,(Convert:(Deref:(Id:b))),(Cost:0.1,FLOAT)) non compatibile con Id: b\n"+"Variabile 'c' non definita\n"+"Variabile 'd' non definita\n", visitor.getLog().toString());
	}
	
	@Test
	void testCorrect2() {
		SymbolTable.init();
		Scanner scanner = Assertions.assertDoesNotThrow(() -> new Scanner("src/test/data/testTypeCheck/fileCorrect2.txt"));
		Parser parser = new Parser(scanner);
		NodeProgram nodeProgram = Assertions.assertDoesNotThrow(() -> parser.parse());
		TypeCheckingVisitor visitor = new TypeCheckingVisitor();
		visitor.visit(nodeProgram);
		Assertions.assertEquals(SymbolTable.lookup("a").getType(), LangType.FLOAT);
		Assertions.assertEquals(SymbolTable.lookup("b").getType(), LangType.INT);
		Assertions.assertTrue(visitor.getLog().toString().isEmpty());
	}
	
	@Test
	void testGenerale() {
		SymbolTable.init();
		Scanner scanner = Assertions.assertDoesNotThrow(() -> new Scanner("src/test/data/testTypeCheck/testGenerale.txt"));
		Parser parser = new Parser(scanner);
		NodeProgram nodeProgram = Assertions.assertDoesNotThrow(() -> parser.parse());
		TypeCheckingVisitor visitor = new TypeCheckingVisitor();
		visitor.visit(nodeProgram);
		Assertions.assertEquals(SymbolTable.lookup("a").getType(), LangType.INT);
		Assertions.assertEquals(SymbolTable.lookup("b").getType(), LangType.FLOAT);
		Assertions.assertTrue(visitor.getLog().toString().isEmpty());
	}
	
	@Test
	void testGenerale2() {
		SymbolTable.init();
		Scanner scanner = Assertions.assertDoesNotThrow(() -> new Scanner("src/test/data/testTypeCheck/testGenerale2.txt"));
		Parser parser = new Parser(scanner);
		NodeProgram nodeProgram = Assertions.assertDoesNotThrow(() -> parser.parse());
		TypeCheckingVisitor visitor = new TypeCheckingVisitor();
		visitor.visit(nodeProgram);
		Assertions.assertEquals(SymbolTable.lookup("a").getType(), LangType.INT);
		Assertions.assertEquals(SymbolTable.lookup("b").getType(), LangType.FLOAT);
		Assertions.assertTrue(visitor.getLog().toString().isEmpty());
	}

}
