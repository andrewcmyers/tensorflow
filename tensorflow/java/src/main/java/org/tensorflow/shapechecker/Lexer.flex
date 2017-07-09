package org.tensorflow.shapechecker;
import java_cup.runtime.Symbol;

%%

%public
%class Lexer
%type Symbol
%function nextToken
%line
%column

%{
    void print() {
//	System.err.println("Saw token: " + yytext());
    }

    static class IntLiteral extends Symbol implements DimOrId {
        int val;
        IntLiteral(int i) {
            super(Syms.NUMBER);
            val = i;
            value = this;
        }
        public String toString() {
            return Integer.toString(val);
        }
    }

    static class Id extends Symbol implements DimOrId {
        String val;
        Id(String s) {
            super(Syms.ID);
            val = s;
            value = this;
        }
        public String toString() {
            return "\"" + val + "\"";
        }
    }
%}

%eofval{
  return new Symbol(Syms.EOF);
%eofval}

Whitespace = [ \t\f\r\n]
Integer = (-)?[1-9][0-9]*
Id = [a-zA-Z_][a-zA-Z0-9_]*

%%

{Whitespace}  { /* ignore */ }
{Integer}     { print(); return new IntLiteral(Integer.parseInt(yytext())); }
"scalar"      { print(); return new Symbol(Syms.SCALAR); }
{Id}          { print(); return new Id(yytext()); }
","           { print(); return new Symbol(Syms.COMMA); }
[^]           { print(); System.err.println("Lexical error at line " + yyline); return new Symbol(Syms.error); }
