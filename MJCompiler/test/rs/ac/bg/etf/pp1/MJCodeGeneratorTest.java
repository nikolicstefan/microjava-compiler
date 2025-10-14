package rs.ac.bg.etf.pp1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import java_cup.runtime.Symbol;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import rs.ac.bg.etf.pp1.ast.*;
import rs.ac.bg.etf.pp1.util.Log4JUtils;
import rs.etf.pp1.mj.runtime.*;
import rs.etf.pp1.symboltable.*;

public class MJCodeGeneratorTest {

	static {
		DOMConfigurator.configure(Log4JUtils.instance().findLoggerConfigFile());
		Log4JUtils.instance().prepareLogFile(Logger.getRootLogger());
	}

	public static void main(String[] args) throws Exception {
		Logger log = Logger.getLogger(MJParserTest.class);
		Reader br = null;

		try {
			File sourceCode = new File("test/program.mj");
			log.info("Compiling source file: " + sourceCode.getAbsolutePath());

			// generate lexer
			br = new BufferedReader(new FileReader(sourceCode));
			Yylex lexer = new Yylex(br);

			// generate parser
			MJParser parser = new MJParser(lexer);

			// build AST
			Symbol token = parser.parse();
			Program program = (Program) (token.value);

			// print AST
			log.info(program.toString(""));

			if (parser.isErrorDetected()) {
				log.error("Parsing was NOT completed successfully.");
			}

			// semantic analysis
			MJSemanticAnalyzer semanticAnalyzer = new MJSemanticAnalyzer();
			program.traverseBottomUp(semanticAnalyzer);

			// print symbol table
			Tab.dump();

			// delete previous object file
			File objFile = new File("test/program.obj");
			if (objFile.exists()) {
				objFile.delete();
			}

			if (semanticAnalyzer.isErrorDetected()) {
				log.error("Semantic analysis was NOT completed successfully.");
				return;
			}

			// generate code
			MJCodeGenerator codeGenerator = new MJCodeGenerator(semanticAnalyzer.getGlobalVarsCnt());
			program.traverseBottomUp(codeGenerator);

			// generate new object file
			Code.dataSize = codeGenerator.getGlobalVarsCnt();
			Code.mainPc = codeGenerator.getMainPc();
			Code.write(new FileOutputStream(objFile));

			log.info("Code generation completed successfully.");
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e1) {
					log.error(e1.getMessage(), e1);
				}
			}
		}
	}

}
