package TP2;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ASD {
	static public class Program {
		Expression e; // What a program contains. TODO : change when you extend the language

		public Program(Expression e) {
			this.e = e;
		}

		// Pretty-printer
		public String pp() {
			return e.pp();
		}

		// IR generation
		public Llvm.IR toIR() throws TypeException {
			// TODO : change when you extend the language
			SymbolTable symbT = new SymbolTable();
			
			// computes the IR of the expression
			Expression.RetExpression retExpr = e.toIR(symbT);
			// add a return instruction
			Llvm.Instruction ret = new Llvm.Return(retExpr.type.toLlvmType(), retExpr.result);
			retExpr.ir.appendCode(ret);

			return retExpr.ir;
		}
	}

	// All toIR methods returns the IR, plus extra information (synthesized attributes)
	// They can take extra arguments (inherited attributes)

	static public abstract class Expression {
		public abstract String pp();
		public abstract RetExpression toIR(SymbolTable symbT) throws TypeException;

		// Object returned by toIR on expressions, with IR + synthesized attributes
		static public class RetExpression {
			// The LLVM IR:
			public Llvm.IR ir;
			// And additional stuff:
			public Type type; // The type of the expression
			public String result; // The name containing the expression's result
			// (either an identifier, or an immediate value)

			public RetExpression(Llvm.IR ir, Type type, String result) {
				this.ir = ir;
				this.type = type;
				this.result = result;
			}
		}
	}


	// Concrete class for Expression: add case
	static public class AddExpression extends Expression {
		Expression left;
		Expression right;

		public AddExpression(Expression left, Expression right) {
			this.left = left;
			this.right = right;
		}

		// Pretty-printer
		public String pp() {
			return "(" + left.pp() + " + " + right.pp() + ")";
		}

		// IR generation
		public RetExpression toIR(SymbolTable symbT) throws TypeException {
			RetExpression leftRet = left.toIR(symbT);
			RetExpression rightRet = right.toIR(symbT);

			// We check if the types mismatches
			if(!leftRet.type.equals(rightRet.type)) {
				throw new TypeException("type mismatch: have " + leftRet.type + " and " + rightRet.type);
			}

			// We base our build on the left generated IR:
			// append right code
			leftRet.ir.append(rightRet.ir);

			// allocate a new identifier for the result
			String result = Utils.newtmp();

			// new add instruction result = left + right
			Llvm.Instruction add = new Llvm.Add(leftRet.type.toLlvmType(), leftRet.result, rightRet.result, result);

			// append this instruction
			leftRet.ir.appendCode(add);

			// return the generated IR, plus the type of this expression
			// and where to find its result
			return new RetExpression(leftRet.ir, leftRet.type, result);
		}
	}

	// Concrete class for Expression: constant (integer) case
	static public class IntegerExpression extends Expression {
		int value;
		public IntegerExpression(int value) {
			this.value = value;
		}

		public String pp() {
			return "" + value;
		}

		public RetExpression toIR(SymbolTable symbT) {
			// Here we simply return an empty IR
			// the `result' of this expression is the integer itself (as string)
			return new RetExpression(new Llvm.IR(Llvm.empty(), Llvm.empty()), new Int(), "" + value);
		}
	}

	static public abstract class Instruction {
		public abstract String pp();
		public abstract RetInstruction toIR(SymbolTable symbT) throws TypeException;

		// Object returned by toIR on expressions, with IR + synthesized attributes
		static public class RetInstruction {
			// The LLVM IR:
			public Llvm.IR ir;
			// Resul of instruction
			public String result; // The name containing the expression's result
			// (either an identifier, or an immediate value)

			public RetInstruction(Llvm.IR ir, String result) {
				this.ir = ir;
				this.result = result;
			}
		}
	}

	static public class AffectExpression extends Instruction {
		String ident;

		Expression right;

		public AffectExpression(String id, Expression right) {
			this.ident = id;
			this.right = right;
		}

		public String pp() {
			return this.ident + ":= " + this.right.pp();
		}

		public RetInstruction toIR(SymbolTable symbT) throws TypeException{
			String ident = "";
			SymbolTable.VariableSymbol identSymb = (SymbolTable.VariableSymbol) symbT.lookup(this.ident);

			if(identSymb == null) {
				System.err.println("Error, '"+ this.ident+"' doesn't exist in symbol table");
				System.exit(0);
			}
			else {
				ident = "%" + identSymb.ident;
				ident += "1"; 
			}

			Expression.RetExpression retExpr = this.right.toIR(symbT);

			if(!identSymb.type.equals(retExpr.type)) {
				throw new TypeException("type mismatch: have " + identSymb.type + " and " + retExpr.type);
			}

			RetInstruction ret = new RetInstruction(new Llvm.IR(Llvm.empty(), Llvm.empty()), ident);

			ret.ir.append(retExpr.ir);

			Llvm.Instruction affect = new Llvm.Affect(retExpr.type.toLlvmType(), retExpr.result, identSymb.type.toLlvmType(), ident);

			ret.ir.appendCode(affect);

			return ret;
		}
	}

	// Warning: this is the type from VSL+, not the LLVM types!
	static public abstract class Type {
		public abstract String pp();
		public abstract Llvm.Type toLlvmType();
	}

	static class Int extends Type {
		public String pp() {
			return "INT";
		}

		@Override public boolean equals(Object obj) {
			return obj instanceof Int;
		}

		public Llvm.Type toLlvmType() {
			return new Llvm.Int();
		}
	}
}
