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
 * This node represents a binary operation.
 * 
 * @author Zach Kissel
 */
public final class BinOpNode extends SyntaxNode
{
    private TokenType op;
    private SyntaxNode leftTerm;
    private SyntaxNode rightTerm;

    /**
     * Constructs a new binary operation syntax node.
     * 
     * @param lterm the left operand.
     * @param op    the binary operation to perform.
     * @param rterm the right operand.
     * @param line  the line of code the node is associated with.
     */
    public BinOpNode(SyntaxNode lterm, TokenType op, SyntaxNode rterm,
            long line)
    {
        super(line);
        this.op = op;
        this.leftTerm = lterm;
        this.rightTerm = rterm;
    }

    /**
     * Display a AST inferencertree with the indentation specified.
     * 
     * @param indentAmt the amout of indentation to perform.
     */
    public void displaySubtree(int indentAmt)
    {
        printIndented("BinOp[" + op + "](", indentAmt);
        leftTerm.displaySubtree(indentAmt + 2);
        rightTerm.displaySubtree(indentAmt + 2);
        printIndented(")", indentAmt);
    }

    /**
     * Evaluate the node.
     * <p>
     * This method evaluates the left and right operands and then performs
     * depends on the types of the operands:
     * <ul>
     *   <li>Integer operands: ADD, SUB, MULT, DIV, MOD</li>
     *   <li>Double operands: ADD, SUB, MULT, DIV (MOD not supported for doubles)</li>
     *   <li>Boolean operands: AND, OR</li>
     * </ul>
     * Type mismatches or unsupported operations will result in an 
     * {@link EvaluationException}.
     * </p>
     * 
     * <p>
     * Division by zero is also caught and triggers an {@link EvaluationException}.
     * </p>
     * @param env the executional environment we should evaluate the node under.
     * @return the object representing the result of the evaluation.
     * @throws EvaluationException if the evaluation fails.
     */
    @Override
    public Object evaluate(Environment env) throws EvaluationException {
        Object leftVal = leftTerm.evaluate(env);
        Object rightVal = rightTerm.evaluate(env);

        if (leftVal instanceof Integer && rightVal instanceof Integer) {
        int l = (Integer) leftVal;
        int r = (Integer) rightVal;

        switch (op) {
            case ADD: return l + r;
            case SUB: return l - r;
            case MULT: return l * r;
            case DIV:
                if (r == 0) {
                    logError("Division by zero");
                    throw new EvaluationException();
                }
                return l / r;
            case MOD: return l % r;
            default:
                logError("Unsupported binary integer operation: " + op);
                throw new EvaluationException();
        }
    }

    // Doubles
    else if (leftVal instanceof Double && rightVal instanceof Double) {
        double l = (Double) leftVal;
        double r = (Double) rightVal;

        switch (op) {
            case ADD: return l + r;
            case SUB: return l - r;
            case MULT: return l * r;
            case DIV:
                if (r == 0.0) {
                    logError("Division by zero");
                    throw new EvaluationException();
                }
                return l / r;
            default:
                logError("Unsupported binary real operation: " + op);
                throw new EvaluationException();
        }
    }

    // Booleans
    else if (leftVal instanceof Boolean && rightVal instanceof Boolean) {
        boolean l = (Boolean) leftVal;
        boolean r = (Boolean) rightVal;

        switch (op) {
            case AND: return l && r;
            case OR:  return l || r;
            default:
                logError("Unsupported binary boolean operation: " + op);
                throw new EvaluationException();
        }
    }

    // Type mismatch
    else {
        logError("Type mismatch for binary operation: " + op);
        throw new EvaluationException();
    }

}}