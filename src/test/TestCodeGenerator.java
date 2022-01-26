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
import visitor.CodeGeneratorVisitor;
import visitor.TypeCheckingVisitor;

class TestCodeGenerator {

	public static NodeProgram runParser(String filePath ) {
		return Assertions.assertDoesNotThrow(() -> new Parser(new Scanner(filePath)).parse());
    }
	
	@Test
	public void testGenerazioneCodice() { 
		SymbolTable.init();
		String path = "src/test/data/testCodeGenerator/Programma_da_analizzare.txt";
		String pathCorretto = "src/test/data/testCodeGenerator/Programma_corretto.txt";
		NodeProgram prg = runParser(path);
		TypeCheckingVisitor tcv = new TypeCheckingVisitor();
		CodeGeneratorVisitor cgv = new CodeGeneratorVisitor();
		tcv.visit(prg);
		/*
		if(!tcv.getLog().isEmpty())
			fail("Errori durante l'esecuzione dell'analisi semantica.");
		*/
		assertTrue(tcv.getLog().toString().isEmpty());
		cgv.visit(prg);
		File output = new File (pathCorretto);
		FileWriter fw = Assertions.assertDoesNotThrow(() -> new FileWriter(output, false));
		/*
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(cgv.getCodice());
		bw.flush();
		bw.close();
		*/
		Assertions.assertDoesNotThrow(() -> {
			fw.write(cgv.getCodice().toString());
			fw.flush();
			fw.close();
		});
		assertEquals(" lA sB 0 k 2 7.5 5 k + sB 0 k lA p P lB p P", cgv.getCodice().toString());
	}
	
	@Test
	public void testGenerazioneCodiceProf() { 
		SymbolTable.init();
		String path = "src/test/data/testCodeGenerator/Programma_prof.txt";
		String pathCorretto = "src/test/data/testCodeGenerator/Programma_corretto_prof.txt";
		NodeProgram prg = runParser(path);
		TypeCheckingVisitor tcv = new TypeCheckingVisitor();
		CodeGeneratorVisitor cgv = new CodeGeneratorVisitor();
		tcv.visit(prg);

		assertTrue(tcv.getLog().toString().isEmpty());
		cgv.visit(prg);
		File output = new File (pathCorretto);
		FileWriter fw = Assertions.assertDoesNotThrow(() -> new FileWriter(output, false));
		
		Assertions.assertDoesNotThrow(() -> {
			fw.write(cgv.getCodice().toString());
			fw.flush();
			fw.close();	
		});
		assertEquals(" 1.0 6 5 k / sB 0 k lB p P 1 6 / sA 0 k lA p P", cgv.getCodice().toString());
	}
	
	@Test
	public void testGenerazioneCodiceSbagliato() { 
		SymbolTable.init();
		String path = "src/test/data/testCodeGenerator/Programma_da_analizzare_sbagliato.txt";
		// String pathCorretto = "src/test/codeGeneratorVisitor/Programma_sbagliato.txt";
		NodeProgram prg = runParser(path);
		TypeCheckingVisitor tcv = new TypeCheckingVisitor();
		// CodeGeneratorVisitor cgv = new CodeGeneratorVisitor();
		tcv.visit(prg);

		assertFalse(tcv.getLog().toString().isEmpty());
		assertEquals("Variabile 'c' non definita\n", tcv.getLog().toString());
	}
	
	@Test
	public void testProgrammaDaTestare() { 
		SymbolTable.init();
		String path = "src/test/data/testCodeGenerator/Programma_da_testare_output.txt";
		String pathCorretto = "src/test/data/testCodeGenerator/Programma_testato_corretto.txt";
		NodeProgram prg = runParser(path);
		TypeCheckingVisitor tcv = new TypeCheckingVisitor();
		CodeGeneratorVisitor cgv = new CodeGeneratorVisitor();
		tcv.visit(prg);

		assertTrue(tcv.getLog().toString().isEmpty());				
		
		cgv.visit(prg);
		File output = new File (pathCorretto);
		FileWriter fw = Assertions.assertDoesNotThrow(() -> new FileWriter(output, false));		
		
		Assertions.assertDoesNotThrow(() -> {
			fw.write(cgv.getCodice().toString());
			fw.flush();
			fw.close();
		});	
		assertEquals(" 2 5 * sA 0 k lA p P lA 2.0 5 k + sB 0 k lB p P", cgv.getCodice().toString());
	}

}
