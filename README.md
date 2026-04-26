# MicroJava Compiler

A compiler for the **MicroJava** programming language, implemented in Java as part of the *Compiler Construction 1* course at the School of Electrical Engineering, University of Belgrade, 2024/2025.

---

## Overview

MicroJava is a simplified, Java-like language. This compiler translates syntactically and semantically valid MicroJava source programs into bytecode for the **MicroJava Virtual Machine (MJVM)**. It is implemented at **Level C**, covering the full language specification including a complete object-oriented model.

---

## Features

- Program begins with the keyword `program` and has static fields, static methods, and inner classes that can be used as user-defined data types
- The main method is always called `main()` and is the program entry point
- Primitive types: `int`, `bool`, `char` (ASCII)
- Structured/reference types: one-dimensional arrays (as in Java), inner classes with fields and methods, and sets of integers exclusively
- Constants: integer, character, and boolean (`int`, `char`, `bool`)
- Variables: global (static), local, and class fields (instance variables)
- Variables of primitive types hold values directly
- Variables of reference types hold references (addresses that cannot be changed explicitly)
- Global static methods
- No garbage collector - allocated objects are deallocated only at program end
- Class inheritance and polymorphism
- Method overriding
- Inner class methods are bound to an instance and have an implicit `this` parameter (reference to the instance the method was called on)
- `this` is implicitly declared as the first formal parameter of instance methods, typed as a reference to the enclosing class
- Inside instance methods, a field name refers to the current object's field, unless shadowed by a method parameter - in that case it can be accessed via `this.fieldName`
- Predeclared methods: `ord`, `chr`, `len`, `add`
- `print` outputs values of all primitive types and sets
- Control flow: conditional branching (`if-else`) and loop (`do-while`)
- Sets contain unique integers - inserting a duplicate is ignored; a set is initialized with `new set[capacity]`
- Interfaces with optional default method implementations - a class implementing an interface must implement all methods for which no default implementation exists

---

## Language at a Glance

```
program p
const int tableSize = 10;
set s1;
class Table {
    int pos[], neg[], factor;
    {
		void setfactor(int factor) { this.factor = factor; }
        void putp(int a, int idx) { this.pos[idx] = a; }
		void putn(int a, int idx) { this.neg[idx] = a; }
        int getp(int idx) { return pos[idx]; }
		int getn(int idx) { return neg[idx]; }
    }
}
Table val;
int rows, columns;
{
	void f(char ch, int a, int arg)
		int x;
	{
		x = arg;
	}
	
    void main()
		int x, i;
		char c;
		int arr[];
	{
        val = new Table();
		val.setfactor(2);
		
		s1 = new set[5];
		s1.add(5); s1.add(10);
		print(s1);
		
		arr = new int[3];
		i = 0;
		do
			arr[i] = i;
		while (i < 3, i++);
		i = 0;
		do
			print(arr[i]);
		while (i < 3, i++);
		
        val.pos = new int[tableSize];
		val.neg = new int[tableSize];
		
        i = 0;
        do {
            val.putp(0, i);
			val.putn(0, i);
        } while (i < tableSize, i++);
		
        read(x);
		f(c, x, i);
		read(rows);
		x = rows;
		do {
			if (x <= 0) break;
			if (0 <= x && x < tableSize) {
				val.putp(val.getp(x) + 1);
			} else if (-tableSize < x && x < 0) {
				val.putn(val.getn(-x) + 1);
			}
			read(x);
		} while ();
    }
}
```

---

## Architecture

### Lexical Analysis
- Implemented via a **JFlex** `.flex` specification (`mjlexer.lex`)
- Recognizes identifiers, numeric/character/boolean constants, keywords, operators, and single-line comments (`//`)
- Skips whitespace (spaces, tabs, newlines, form feeds, backspace)
- Generates lexer class (`Yylex`)
- Reports lexical errors with the unrecognized token, line number, and column position

### Syntactic Analysis
- **LALR(1)** grammar implemented using **AST-CUP** (`mjparser.cup`)
- Generates parser class (`MJParser`)
- Builds an **Abstract Syntax Tree (AST)** automatically via the AST-CUP generator
- Error recovery for:
  - Global variable declarations (recover to `;` or `,`)
  - Assignment statements (recover to `;`)
  - Formal parameter declarations (recover to `,` or `)`)
  - `if` conditions (recover to `)`)
  - Inner class field declarations (recover to `;` or `{`)
  - Superclass extension declarations (recover to `{`)
- Prints the full AST on successful parse

### Semantic Analysis
- Implemented as an AST visitor (`SemanticAnalyzer`) extending `VisitorAdapter`
- Integrated with the provided **Symbol Table** library (`symboltable-1-1.jar`)
- Checks all contextual conditions from the MicroJava specification (`mikrojava_2024_2025_jan.pdf`)
- Detects use of symbolic constants, global/local variables, formal parameters, array elements, class fields, and method calls (both global and instance)
- Dumps the symbol table after the semantic pass

### Code Generation
- Implemented as an AST visitor (`CodeGenerator`) extending `VisitorAdapter`
- Targets the **MicroJava VM** bytecode format using the `mj-runtime.jar` library
- Generates a binary `.obj` file executable on the MJVM

---

## Project Structure

```
MJCompiler/
├── spec/
│   ├── mjlexer.lex                    # JFlex lexer specification
│   ├── mjparser.cup                   # AST-CUP parser specification
│   └── mjparser_astbuild.cup          # Generated AST-CUP AST specification
├── src/
│   └── rs/ac/bg/etf/pp1/
│       ├── Yylex.java                 # Generated lexer class
│       ├── sym.java                   # Generated token symbols
│       ├── MJParser.java              # Generated parser class
│       ├── MJSemanticAnalyzer.java    # Semantic analysis AST visitor
│       ├── MJCodeGenerator.java       # Code generation AST visitor
│       ├── ast/                       # Auto-generated AST node classes
│       └── util/
│           └── Log4JUtils.java        # Log4j utility class
├── test/
│   ├── rs/ac/bg/etf/pp1/
│   │   └── Compiler.java              # Main compiler class
│   ├── test301.mj                     # Tests Level A features
│   ├── test302.mj                     # Tests Level B features
│   ├── test303.mj                     # Tests Level C features
│   ├── program.mj                     # MicroJava source file to compile
│   ├── program.obj                    # Generated MJVM bytecode
│   ├── input.txt                      # Program standard input
│   ├── output.txt                     # Program standard output (if redirected)
│   └── error.txt                      # Program error output (if redirected)
├── config/
│   └── log4j.xml                      # Log4j configuration
├── lib/
│   ├── JFlex.jar                      # JFlex lexer generator
│   ├── cup_v10k.jar                   # AST-CUP parser and AST generator
│   ├── symboltable-1-1.jar            # Symbol table library
│   ├── mj-runtime-1.1.jar             # MJVM runtime
│   └── log4j-1.2.17.jar               # Log4j logging library
└── build.xml                          # Ant build script
```

---

## Build & Run

> Requires **JDK 17** and **Apache Ant**.

**1. Place MicroJava source file and program input:**
```
test/program.mj   ← MicroJava source to compile
test/input.txt    ← stdin for the compiled program
```

> All subsequent commands should be run from the `MJCompiler/` directory.

**2. Build:**
```bash
ant compile
```

This executes the full pipeline in order:

1. **`delete`** - cleans previously generated sources (preserving hand-written files)
2. **`lexerGen`** - runs JFlex on `spec/mjlexer.lex` to generate `Yylex.java`
3. **`parserGen`** - runs AST-CUP on `spec/mjparser.cup` to generate `MJParser.java`, `sym.java`, and all AST node classes
4. **`repackage`** - fixes package references in generated files
5. **`compile`** - compiles all 200+ Java source files

**3. Run the compiler to generate `test/program.obj`:**

Windows:
```bat
java -classpath "bin;lib/log4j-1.2.17.jar;lib/JFlex.jar;lib/cup_v10k.jar;lib/symboltable-1-1.jar;lib/mj-runtime-1.1.jar" rs.ac.bg.etf.pp1.Compiler
```

Linux/Mac:
```bash
java -classpath "bin:lib/log4j-1.2.17.jar:lib/JFlex.jar:lib/cup_v10k.jar:lib/symboltable-1-1.jar:lib/mj-runtime-1.1.jar" rs.ac.bg.etf.pp1.Compiler
```

**4. Run `test/program.obj` on the MJVM:**
```bash
ant runObj
```

---

## Compiler Output

On a successful compilation the compiler prints:

1. **Semantic pass log** - symbol lookups and any semantic errors
2. **AST dump** - full abstract syntax tree
3. **Symbol table dump** - all declared symbols and their types/addresses
4. **`program.obj` file** - binary bytecode ready for MJVM execution

---

## MicroJava VM

The generated `.obj` file follows the MJVM object file format:

| Field | Size | Description |
|---|---|---|
| Magic | 2 bytes | `MJ` |
| Code size | 4 bytes | Size of the code section in bytes |
| Global data words | 4 bytes | Number of words reserved for static data |
| `mainPC` | 4 bytes | Entry point (address of `main`) |
| Code | n bytes | Bytecode instructions |

The VM provides five memory regions: **Code**, **StaticData**, **Heap**, **ProcStack**, and **ExprStack**. All variables are word-sized (32 bits); `char` arrays use byte-level storage.

---

## MicroJava VM Output

After running `ant runObj`, two outputs are produced:

1. **Disassembly** - bytecode trace with instructions and expression stack state at each step
2. **Program output** - the actual output of the compiled program

---
 
## Dependencies
 
This project depends on the following third-party libraries, none of which are authored by the project author:
  - **[JFlex](https://jflex.de/)** - lexer generator
  - **[AST-CUP](http://ir4pp1.etf.rs/)** - LALR(1) parser and AST generator
  - **[symboltable-1-1.jar](http://ir4pp1.etf.rs/Domaci/symboltable-1-1.jar)** - symbol table library
  - **[mj-runtime-1.1.jar](http://ir4pp1.etf.rs/Domaci/mj-runtime-1.1.jar)** - MicroJava VM runtime
  - **[Log4j 1.2.17](https://logging.apache.org/log4j/)** - logging library