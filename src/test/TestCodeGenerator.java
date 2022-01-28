package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ast.NodeProgram;

import parser.Parser;
import scanner.Scanner;
import symbolTable.SymbolTable;
import visitor.*;

class TestCodeGenerator {

	public static NodeProgram runParser(String filePath ) {
		return Assertions.assertDoesNotThrow(() -> new Parser(new Scanner(filePath)).parse());
    }
	
	@Test
	public void testCodeGenerator() { 
		SymbolTable.init();
		String path = "src/test/data/testCodeGenerator/Programma.txt";
		String pathCorretto = "src/test/data/testCodeGenerator/Programma_corretto.txt";
		NodeProgram nodeProgram = runParser(path);
		TypeCheckingVisitor typeChecking = new TypeCheckingVisitor();
		CodeGeneratorVisitor codeGenerator = new CodeGeneratorVisitor();
		typeChecking.visit(nodeProgram);

		assertTrue(typeChecking.getLog().toString().isEmpty());
		codeGenerator.visit(nodeProgram);
		File output = new File (pathCorretto);
		FileWriter fw = Assertions.assertDoesNotThrow(() -> new FileWriter(output, false));
		
		Assertions.assertDoesNotThrow(() -> {
			fw.write(codeGenerator.getCodice().toString());
			fw.flush();
			fw.close();	
		});
		assertEquals("1.0 6 5 k / sB 0 k lB p P 1 6 / sA 0 k lA p P ", codeGenerator.getCodice().toString());
	}

}
