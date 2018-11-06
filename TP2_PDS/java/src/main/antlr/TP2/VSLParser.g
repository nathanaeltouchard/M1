parser grammar VSLParser;

options {
  language = Java;
  tokenVocab = VSLLexer;
}

@header {
  package TP2;

  import java.util.stream.Collectors;
  import java.util.Arrays;
}


// TODO : other rules

program returns [ASD.Program out]
    : e=instruction EOF { $out = new ASD.Program($e.out); } // TODO : change when you extend the language
    ;

expression returns [ASD.Expression out]
    : l=factor PLUS r=expression  { $out = new ASD.AddExpression($l.out, $r.out); }
    | f=factor { $out = $f.out; }
    // TODO : that's all?
    ;

factor returns [ASD.Expression out]
    : p=primary { $out = $p.out; }
    // TODO : that's all?
    ;

primary returns [ASD.Expression out]
    : INTEGER { $out = new ASD.IntegerExpression($INTEGER.int); }
    | IDENT { $out = new ASD.VariableExpression($IDENT.text); }
    // TODO : that's all?
    ;
    
instruction returns [ASD.Instruction out]
	: IDENT AFFECT e=expression { $out = new ASD.AffectInstruction($IDENT.text, $e.out); };

	
declaration returns [ASD.Declaration out]
	: INT (IDENT {$out = new ASD.IntegerVariable($IDENT.text)); })*