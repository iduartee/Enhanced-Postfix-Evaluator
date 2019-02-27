import java.util.Stack;
import java.util.Scanner;

public class PostfixEvaluator {
    private final static char ADD      = '+';
    private final static char SUBTRACT = '-';
    private final static char MULTIPLY = '*';
    private final static char DIVIDE   = '/';
    private final static char POWER = '^';
    private final static char MODULUS = '%';
    private final static char FACTORIAL = '!';
    private final static char NEGATE = '~';
    private final static char GREATER = '>';
    private final static char LESSER = '<';
    private final static char EQUAL = '=';
    private final static char AND = '&';
    private final static char OR = '|';
    private final static char TERN = '?';

    private static Stack<Integer> stack;

    public PostfixEvaluator() {
        stack = new Stack<Integer>();
    }

	public int evaluate(String expr) throws Exception {
        int op1, op2, op3, result = 0;
        String token;
        Scanner parser = new Scanner(expr);

        while (parser.hasNext()) {
            token = parser.next();
            
            if (isOperator(token))
            {	
            	
                op2 = (stack.pop()).intValue();
                op1 = (stack.pop()).intValue();
                result = evaluateSingleOperator(token.charAt(0), op1, op2);
                stack.push(result);
            }
            else if (isFactorial(token))
            {
            	op1 = (stack.pop()).intValue();
            	result = evaluateFactorial(token.charAt(0), op1);
            	stack.push(result);
            }
            else if (isNegative(token))
            {
            	op1 = (stack.pop()).intValue();
            	result = evaluateNegative(token.charAt(0), op1);
            	stack.push(result);
            }
            else if (isInequality(token))
            {
            	op2 = (stack.pop()).intValue();
            	op1 = (stack.pop()).intValue();
            	result = evaluateInequality(token.charAt(0), op1, op2);
            	stack.push(result);
            			
            }
            else if (isBoolean(token))
            {
            	op2 = (stack.pop()).intValue();
            	op1  = (stack.pop()).intValue();
            	result = evaluateBoolean(token.charAt(0), op1, op2);
            	stack.push(result);
            }
            else if (isTernary(token))
            {
            	op3 = (stack.pop()).intValue();
            	op2 = (stack.pop()).intValue();
            	op1 = (stack.pop()).intValue();
            	result = evaluateTernary(token.charAt(0), op1, op2, op3);
            	stack.push(result);
            }
            else
            {
            	stack.push(Integer.parseInt(token)); 
            }
        }
        
        if(stack.size() > 1)
        {
        	parser.close();
        	throw new Exception();
        }
        else
        {
        	parser.close();
        	return(stack.pop());
        }
    }

    private boolean isOperator(String token) {
        return ( token.equals("+") || token.equals("-") ||  // OR return ("+-*/".indexOf(token) >= 0);
                 token.equals("*") || token.equals("/") || token.equals("^") || token.equals("%"));
    }
    
    private boolean isFactorial(String token)
    {
    	return token.equals("!");
    }
    
    private boolean isNegative(String token)
    {
    	return token.equals("~");
    }
    
    private boolean isInequality(String token)
    {
    	return (token.equals(">") || token.equals("<") || token.equals("="));
    }
    
    private boolean isBoolean(String token)
    {
    	return (token.equals("&") || token.equals(("|")));
    }
    
    private boolean isTernary(String token)
    {
    	return (token.equals("?"));
    }
    private int fact(int z)
    {
		int factResult;
		if (z == 0 || z == 1)
		{
			factResult = 1;
		}
		else 
		{
			factResult = fact(z-1) * z;
		}
		return factResult;
    	
    }

    private int evaluateSingleOperator(char operation, int op1, int op2) {
        int result = 0;

        switch (operation) {
            case ADD:
                result = (op1 + op2);
                break;
                
            case SUBTRACT:
                result = (op1 - op2);
                break;
                
            case MULTIPLY:
                result = (op1 * op2);
                break;
                
            case DIVIDE:
            	result = (op1 / op2);
            	break;
                
            case POWER:
            	result = (int) (Math.pow(op1, op2));
            	break;
            	
            case MODULUS:
            	result = (op1 % op2);
            	break;

        }
        return result;
    }
    
    private int evaluateFactorial(char operation, int op1)
    {
    	int result = 0;
    	
    	switch(operation)
    	{
    	case FACTORIAL:
    		result = (fact(op1));
    		break;
    	}
    	
		return result;
    	
    }
    
   private int evaluateNegative(char operation, int op1)
   {
    	int result = 0;
    	
    	switch(operation)
    	{
    	case NEGATE:
    		result = -op1;
    		break;
    	}
    	return result;
    }
   
   private int evaluateInequality(char operation, int op1, int op2)
   {
	  int result = 0;
	  
	 switch(operation)
	 {
	 	case GREATER:
	 		if (op1 > op2)
	 		{
	 			result = 1;
	 		}
	 		else
	 		{
	 			result = 0;
	 		}
	 		break;
	 		
	 	case LESSER:
	 		if(op1 < op2)
	 		{
	 			result = 1;
	 		}
	 		else
	 		{
	 			result = 0;
	 		}
				break;
	 		
	 	case EQUAL:
	 		if (op1 == op2)
	 		{
	 			result = 1;
	 		}
	 		else
	 		{
	 			result = 0;
	 		}
	 		break;
	 }
	   return result;
	   
   }
   
   private int evaluateBoolean(char operation, int op1, int op2)
   {
	   int result = 0;
	   
	   switch(operation)
	   {
	   		case AND:
	   			if(op1 != 0 && op2 != 0)
	   			{
	   				result = 1;
	   			}
	   			else
	   			{
	   				result = 0;
	   			}
	   			break;
	   			
	   		case OR:
	   			if (op1 !=0 && op2 != 0)
	   			{
	   				result = 1;
	   			}
	   			else if ((op2 > 0) || op1 > 0)
	   			{
	   				result = 1;
	   			}

	   			else if ((op2 == 0) && (op1 == 0))
	   			{
	   				result = 0;
	   			}
	   			break;
	   }
	   return result;
   }
   
   private int evaluateTernary(char operation, int op1, int op2, int op3)
   {
	   int result = 0;
	   switch(operation) 
	   {
	   		case TERN:
	   		{
	   			if (op1 >= 1 || op1 < 0)
	   			{
	   				result = op2;
	   			}
	   			else
	   			{
	   				result = op3;
	   			}
	   		}
	   }
	   return result;
   }
}
