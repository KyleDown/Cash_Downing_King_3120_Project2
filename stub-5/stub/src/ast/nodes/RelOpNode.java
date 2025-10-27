/*
 *   Copyright (C) 2022 -- 2025  Zachary A. Kissel
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package ast.nodes;

import ast.EvaluationException;
import environment.Environment;
import lexer.TokenType;

/**
 * This node represents relational operations.
 * 
 * @author Zach Kissel
 */
public final class RelOpNode extends SyntaxNode
{
    private TokenType op;
    private SyntaxNode leftExpr;
    private SyntaxNode rightExpr;

    /**
     * Constructs a new binary operation syntax node.
     * 
     * @param lexpr the left operand.
     * @param op    the binary operation to perform.
     * @param rexpr the right operand.
     * @param line  the line of code the node is associated with.
     */
    public RelOpNode(SyntaxNode lexpr, TokenType op, SyntaxNode rexpr,
            long line)
    {
        super(line);
        this.op = op;
        this.leftExpr = lexpr;
        this.rightExpr = rexpr;
    }

    /**
     * Display a AST inferencertree with the indentation specified.
     * 
     * @param indentAmt the amout of indentation to perform.
     */
    public void displaySubtree(int indentAmt)
    {
        printIndented("RelOp[" + op + "](", indentAmt);
        leftExpr.displaySubtree(indentAmt + 2);
        rightExpr.displaySubtree(indentAmt + 2);
        printIndented(")", indentAmt);
    }

    /**
     * Evaluate the node.
     * 
     * @param env the executional environment we should evaluate the node under.
     * @return the object representing the result of the evaluation.
     * @throws EvaluationException if the evaluation fails.
     */
    @Override
    public Object evaluate(Environment env) throws EvaluationException {
        Object leftVal = leftExpr.evaluate(env);
        Object rightVal = rightExpr.evaluate(env);

        // Both operands must be numbers (Integer or Double)
        if ((leftVal instanceof Integer || leftVal instanceof Double) &&
            (rightVal instanceof Integer || rightVal instanceof Double)) {

            double l = (leftVal instanceof Integer) ? (Integer) leftVal : (Double) leftVal;
            double r = (rightVal instanceof Integer) ? (Integer) rightVal : (Double) rightVal;

            switch (op) {
                case LT:  return l < r;
                case LTE: return l <= r;
                case GT:  return l > r;
                case GTE: return l >= r;
                case EQ:  return l == r;
                case NEQ: return l != r;
                default:
                    logError("Unsupported relational operation: " + op);
                    throw new EvaluationException();
            }
        } else {
            logError("Type mismatch in relational operation: " + op);
            throw new EvaluationException();
        }
    }

    protected void logError(String message) {
        System.err.println("Evaluation error: " + message);
    }
}
