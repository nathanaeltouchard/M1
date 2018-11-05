open Lexer
open Parser
open ASD
open Codegen
open Prettyprinter

let lexbuf = Lexing.from_channel stdin
in try
  let token_stream = (Stream.of_list (Lexer.tokenize lexbuf))
  in let ast = Parser.program token_stream

  (* Activate one of these output: pretty-print or LLVM IR *)

  (* Pretty-print input *)
  (* in print_endline (prettyprint ast) *)

  (* Print LLVM IR *)
  in let document = ir_of_ast ast
  in print_endline document

with
  Lexer.Unexpected_character e ->
  begin
    Printf.printf "Unexpected character: `%c' at position '%d' on line '%d'\n"
      e.character e.pos e.line;
    exit 1
  end


