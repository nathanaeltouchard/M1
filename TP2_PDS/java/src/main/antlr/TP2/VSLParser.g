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
    : e=bloc EOF { $out = new ASD.Program($e.out); }
     // TODO : change when you extend the language
    ;

bloc returns [ASD.Bloc out]
	: d=declaration i=instruction {$out = new ASD.Bloc($i.out,$d.out);};

expression returns [ASD.Expression out]
    : l=factor PLUS r=expression  { $out = new ASD.AddExpression($l.out, $r.out); }
    | l=factor MINUS r=expression { $out = new ASD.MinusExpression($l.out, $r.out);}
	| f=factor { $out = $f.out; }
    ;

factor returns [ASD.Expression out]
    : p=primary { $out = $p.out; }
   	| l=factor MULT r=expression { $out = new ASD.MultExpression($l.out, $r.out);}
    | l=factor DIV r=expression { $out = new ASD.DivExpression($l.out, $r.out);}
    ;

primary returns [ASD.Expression out]
    : INTEGER { $out = new ASD.IntegerExpression($INTEGER.int); }
    | IDENT { $out = new ASD.VariableExpression($IDENT.text); }
    // TODO : that's all?
    ;
    
instruction returns [List<ASD.Instruction> out]
	: IDENT AFFECT e=expression { $out.add(new ASD.AffectInstruction($IDENT.text, $e.out)); };

	
declaration returns [List<ASD.Declaration> out]
	: INT (IDENT {$out.add(new ASD.IntegerVariable($IDENT.text));});