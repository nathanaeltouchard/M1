open ASD
open Llvm
open Utils
open List
open SymbolTable

(* main function. return only a string: the generated code *)
let rec ir_of_ast p =
  (* this header describe to LLVM the target
   * and declare the external function printf
   *)
  let head = "; Target\n" ^
  "target triple = \"x86_64-unknown-linux-gnu\"\n" ^
  "; External declaration of the printf function\n" ^
  "declare i32 @printf(i8* noalias nocapture, ...)\n" ^
  "\n; Actual code begins\n\n"

  (* TODO : change when you extend the language *)
  in let ((ir, _), (ret, _)) = ir_of_expression (empty_ir, []) p
  (* adds the return instruction *)
  in let new_ir = {
    header = ir.header;
    code = Return {
      ret_type = LLVM_Type_Int;
      ret_value = ret;
    } :: ir.code
  }
  
  (* generates the final string *)
  in head ^

  (* We create the function main *)
  (* TODO : remove this when you extend the language *)
  "define i32 @main() {\n" ^

  (string_of_ir new_ir) ^

  (* TODO : remove this when you extend the language *)
  (* TODO : remove this when you extend the language *)
  "}\n"

and llvm_type_of_asd_typ = function
  | Type_Int -> LLVM_Type_Int

(* All main code generation functions take the current IR and a scope,
 * append header and/or code to the IR, and/or change the scope
 * They return the new pair (ir, scope)
 * This is convenient with List.fold_left
 *
 * They can return other stuff (synthesized attributes)
 * They can take extra arguments (inherited attributes)
 *)


(* returns the regular pair, plus the pair of the name of the result and its type *)
and ir_of_expression (ir, scope) =
  (* function to generate all binop operations *)
  let aux op t (l, r) =
    (* generation of left operand computation. We give directly (ir, scope) *)
    let ll, (lresult_name, lresult_type) = ir_of_expression (ir, scope) l
    (* generation of right operand computation. We give directly (ir, scope) from the left computation *)
    (* it appends code to the previous code generated *)
    in let rr, (rresult_name, rresult_type) = ir_of_expression ll r

    (* allocate a new unique locale identifier *)
    and result = newtmp () 

    (* type checking *)
    in let _ = if lresult_type <> rresult_type || t <> rresult_type then failwith "Type error"

    (* new instruction *)
    in let code = Binop {
      lvalue_name = result;
      lvalue_type = t;
      op = op;
      left = lresult_name;
      right = rresult_name;
    }

    (* Returns : *)
    in (({
      header = (fst rr).header;
      code = code :: (fst rr).code;
    }, scope), (result, t))

  in function
    | AddExpression (l, r)  -> aux "add" LLVM_Type_Int (l, r) (* For now, all binop are integer *)
    | IntegerExpression i    -> ((ir, scope), (string_of_int i, LLVM_Type_Int))
