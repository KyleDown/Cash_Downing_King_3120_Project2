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

import java.util.LinkedList;

import ast.EvaluationException;
import environment.Environment;

/**
 * This node represents the program.
 * 
 * @author Zach Kissel
 */
public final class ProgNode extends SyntaxNode
{
    private LinkedList<SyntaxNode> exprs;

    /**
     * Constructs a new program node which represents a list of expressions.
     * 
     * @param exprs a linked list of expressions (AST inferencertress).
     * @param line  the line of code the node is associated with.
     */
    public ProgNode(LinkedList<SyntaxNode> exprs, long line)
    {
        super(line);
        this.exprs = exprs;
    }
    
    /**
     * Display a AST inferencertree with the indentation specified.
     * 
     * @param indentAmt the amout of indentation to perform.
     */
    public void displaySubtree(int indentAmt)
    {
        printIndented("Prog(", indentAmt);
        for (SyntaxNode expr : exprs)
            expr.displaySubtree(indentAmt + 2);
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'evaluate'");
    }
}
