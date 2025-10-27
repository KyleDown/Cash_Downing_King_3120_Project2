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
 * This node represents the unary op node.
 * 
 * @author Zach Kissel
 */
public final class UnaryOpNode extends SyntaxNode
{
    private TokenType op;
    private SyntaxNode expr;

    /**
     * Constructs a new binary operation syntax node.
     * 
     * @param expr the operand.
     * @param op   the binary operation to perform.
     * @param line the line of code the node is associated with.
     */
    public UnaryOpNode(SyntaxNode expr, TokenType op, long line)
    {
        super(line);
        this.op = op;
        this.expr = expr;
    }

    /**
     * Display a AST inferencertree with the indentation specified.
     * 
     * @param indentAmt the amout of indentation to perform.
     */
    public void displaySubtree(int indentAmt)
    {
        printIndented("UnaryOp[" + op + "](", indentAmt);
        expr.displaySubtree(indentAmt + 2);
        printIndented(")", indentAmt);
    }

    /**
     * Evaluate the node.
     * 
     * <p>
     * This method evaluates the operand and applies the unary operator {@code op}.
     * Currently, the only supported unary operation is:
     * <ul>
     *   <li>{@code NOT}: logical negation on a Boolean value</li>
     * </ul>
     * </p>
     * 
     * <p>
     * If the operand is not a Boolean or if an unsupported operator is specified,
     * an {@link EvaluationException} is thrown and the error is logged.
     * </p>
     * @param env the executional environment we should evaluate the node under.
     * @return the object representing the result of the evaluation.
     * @throws EvaluationException if the evaluation fails.
     */
    @Override
    public Object evaluate(Environment env) throws EvaluationException {
    // Evaluate the operand
    Object value = expr.evaluate(env);

    // Handle the unary operation
    if (op == TokenType.NOT) {
        if (value instanceof Boolean) {
            return !(Boolean) value;
        } else {
            logError("Unary NOT operator applied to non-boolean value: " + value);
            throw new EvaluationException();
        }
    } else {
        logError("Unsupported unary operator: " + op);
        throw new EvaluationException();
    }
}
}
