
/*
 *   Copyright (C) 2022 -- 2023  Zachary A. Kissel
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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import parser.MFLParser;
import parser.ParseException;
import ast.EvaluationException;
import ast.SyntaxTree;

public class CompoundTests
{
    @Test
    public void equalNotEqual()
    {
        runEvalTest("equalNotEqual", "(5 != 3) or (2 = 2);", "true");
    }

    @Test
    public void falseAndExpr()
    {
        runEvalTest("falseAndExpr", "2 > 3 and 2 = 2;", "false");
    }

    @Test
    public void trueAndExpr()
    {
        runEvalTest("trueAndExpr", "2 < 3 and 2 = 2;", "true");

    }

    @Test
    public void falseOrExpr()
    {
        runEvalTest("falseOrExpr", "2 > 3 or 2 = 2;", "true");
    }

    @Test
    public void trueOrExpr()
    {
        runEvalTest("trueOrExpr", "2 < 3 or 6 > 4;", "true");

    }

    @Test
    public void lessAndMore()
    {
        runEvalTest("lessAndMore", "(5 < 7) and (7 > 3);", "true");
    }

    @Test
    public void multiStatement()
    {
        runEvalTest("multiStatement",
                "val x := 3 + 5 * 2;\nval y := 4 + x;\nx > y;", "false");
    }

    /**
     * Evaluate the expression and return the result as a string or throw and exception 
     * if it does not succeed.
     * @param name the name of the test.
     * @param statement the statement to evaluate. 
     * @param expected the expected output of the test.
     */
    public void runEvalTest(String name, String statement, String expected)
    {
        MFLParser p = new MFLParser(statement);
        
        Object res = null;
        
        try
        {
            SyntaxTree ast = p.parse();
            res = ast.evaluate();
        }
        catch (EvaluationException ex)
        {
            assertFalse(name + ": Unexpected Exception.", true);
            System.out.println(ex);
        } catch (ParseException e) {
            assertFalse(name + ": Unexpeced Exception.", true);
            System.out.println(e);
        }

        assertEquals(name + ":", expected, res.toString());
    }
}
