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

public class RelationalTests
{
    /*
     * Tests for =.
     */
    @Test
    public void simpleIntEq()
    {
        runEvalTest("simpleIntEq", "5 = 5;", "true");
    }

    @Test
    public void simpleRealEq()
    {
        runEvalTest("simpleRealEq", "5.3 = 5.3;", "true");
    }

    /*
     * Test for !=
     */
    @Test
    public void simpleIntNotEq()
    {
        runEvalTest("simpleIntNotEqual", "5 != 3;", "true");
    }

    @Test
    public void simpleRealNotEq()
    {
        runEvalTest("simpleRealNotEqual", "5.02 != 3.145;", "true");
    }

    /*
     * Tests for >.
     */
    @Test
    public void simpleIntGT()
    {
        runEvalTest("simpleIntGT", "5 > 3;", "true");
    }

    @Test
    public void simpleRealGT()
    {
        runEvalTest("simpleRealGT", "5.3 > 2.1;", "true");
    }

    @Test
    public void simpleIntNotGT()
    {
        runEvalTest("simpleIntNotGT", "5 > 5;", "false");
    }

    @Test
    public void simpleRealNotGT()
    {
        runEvalTest("simpleRealNotGT", "3.145 > 5.0;", "false");
    }

    /*
     * Tests for >=.
     */
    @Test
    public void simpleIntGTE()
    {
        runEvalTest("simpleIntGTE", "5 >= 5;", "true");
    }

    @Test
    public void simpleRealGTE()
    {
        runEvalTest("simpleRealGTE", "5.3 >= 2.1;", "true");
    }

    @Test
    public void simpleIntNotGTE()
    {
        runEvalTest("simpleIntNotGTE", "5 >= 6;", "false");
    }

    @Test
    public void simpleRealNotGTE()
    {
        runEvalTest("simpleRealNotGTE", "3.131592 >= 5.0;", "false");
    }

    /*
     * Tests for <.
     */
    @Test
    public void simpleIntLT()
    {
        runEvalTest("simpleIntLT", "3 < 5;", "true");
    }

    @Test
    public void simpleRealLT()
    {
        runEvalTest("simpleRealLT", "2.1 < 5.3;", "true");
    }

    @Test
    public void simpleIntNotLT()
    {
        runEvalTest("simpleIntNotLT", "5 < 5;", "false");
    }

    @Test
    public void simpleRealNotLT()
    {
        runEvalTest("simpleRealNotLT", "5.0 < 3.1415;", "false");
    }


    /*
     * Tests for <=.
     */
    @Test
    public void simpleIntLTE()
    {
        runEvalTest("simpleIntLTE", "3 <= 5;", "true");
    }

    @Test
    public void simpleRealLTE()
    {
        runEvalTest("simpleRealLTE", "2.1 <= 2.1;", "true");
    }

    @Test
    public void simpleIntNotLTE()
    {
        runEvalTest("simpleIntNotLTE", "5 <= 6;", "true");
    }

    @Test
    public void simpleRealNotLTE()
    {
        runEvalTest("simpleRealNotLTE", "3.1415 <= 3.0;", "false");
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
