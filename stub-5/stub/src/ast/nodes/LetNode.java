package ast.nodes;

import ast.EvaluationException;
import environment.Environment;
import lexer.Token;

/**
 * This node represents a let expression which defines a local scope.
 */
public class LetNode extends SyntaxNode {
    private Token varName;
    private SyntaxNode varExpression;
    private SyntaxNode body;

    /**
     * Constructs a new LetNode.
     * 
     * @param varName       the token containing the identifier name.
     * @param varExpression the syntax node representing the value to be bound.
     * @param body          the syntax node representing the body of the let.
     * @param line          the line of code the node is associated with.
     */
    public LetNode(Token varName, SyntaxNode varExpression, SyntaxNode body, long line) {
        super(line);
        this.varName = varName;
        this.varExpression = varExpression;
        this.body = body;
    }

    /**
     * Display a AST subtree with the indentation specified.
     * 
     * @param indentAmt the amout of indentation to perform.
     */
    public void displaySubtree(int indentAmt) {
        printIndented("Let[ " + varName.getValue() + " ](", indentAmt);
        varExpression.displaySubtree(indentAmt + 2);
        printIndented(") In (", indentAmt);
        body.displaySubtree(indentAmt + 2);
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

    // evaluate the bound expression
    Object value = varExpression.evaluate(env);

    // create a local copy of the environment
    Environment localEnv = env.copy();

    // bind variable in the local environment
    localEnv.updateEnvironment(varName, value);

    // evaluate body expression using the local environment
    Object result = body.evaluate(localEnv);

    // return result
    return result;
    }
        
}
