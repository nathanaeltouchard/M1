(* TODO : extend when you extend the language *)

(* This file contains a simple LLVM IR representation *)
(* and methods to generate its string representation  *)

open List

type llvm_type =
  | LLVM_Type_Int


(* Warning: because of type inference, we can not
 * have the same field name in two record type
 * (actually this is possible but cumbersome)
 *)

(* fields in each instruction *)
type llvm_binop = {
  lvalue_name: string;
  lvalue_type: llvm_type;
  op: string;
  left: string;
  right: string;
}
and llvm_return = {
  ret_type: llvm_type;
  ret_value: string; (* we only return identifier, or integers as int *)
}

(* instructions sum type *)
and llvm_instr =
  | Binop of llvm_binop
  | Return of llvm_return


(* Note: instructions in list are taken in reverse order in
 * string_of_ir in order to permit easy list construction !!
 *)
and llvm_ir = {
  header: llvm_instr list; (* to be placed before all code (global definitions) *)
  code: llvm_instr list;
}

(* handy *)
let empty_ir = {
  header = [];
  code = [];
}

(* actual IR generation *)
let rec string_of_llvm_type = function
  | LLVM_Type_Int -> "i32"

and string_of_ir ir =
  (string_of_instr_list ir.header) ^ "\n\n" ^ (string_of_instr_list ir.code)

and string_of_instr_list l =
  String.concat "" (rev_map string_of_instr l)

and string_of_instr = function
  | Binop v -> v.lvalue_name ^ " = " ^ v.op ^ " " ^ (string_of_llvm_type v.lvalue_type) ^ " " ^ v.left ^ ", " ^ v.right ^ "\n"
  | Return v ->
      "ret " ^ (string_of_llvm_type v.ret_type) ^ " " ^ v.ret_value ^ "\n"
