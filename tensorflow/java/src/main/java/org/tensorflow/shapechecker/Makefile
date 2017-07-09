default: ShapeParser.java Syms.java Lexer.java

ShapeParser.java Syms.java: ShapeParser.cup
	cup -parser ShapeParser -symbols Syms ShapeParser.cup

Lexer.java: Lexer.flex
	java -jar $(HOME)/polyglot/lib/jflex.jar Lexer.flex
