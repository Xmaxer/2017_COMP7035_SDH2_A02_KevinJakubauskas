package hint3;
/**
* Classical Change making problem with an unlimited amount of coins of each type. <br> 
* Version 2: Selection function with more elaborated policy: First biggest-coin.<br> 
* Depending on the type of coins, it can lead to an optimal solution.<br>
* The class encapsulates all the functions of the Greedy schema<br>
*/

public class ChangeMaking_2 {

	//---------------------------------------
	//	Constructor
	//---------------------------------------
	/**
	 * Constructor of the class. Do not edit it.
	 */
	public ChangeMaking_2(){}

	//-------------------------------------------------------------------
	// 0. displayElements --> Displays all elements of a MyList 
	//-------------------------------------------------------------------	
	/**
	 * Given a concrete MyList, this function displays its elements by screen (if any).
	 * @param m: The MyList we want to display its elements.	  
	 */	
	public void displayElements(MyList<Integer> m){
		//-----------------------------
		//SET OF OPS
		//-----------------------------

		//-----------------------------
		// I. SCENARIO IDENTIFICATION
		//-----------------------------
		int scenario = 0; 
		
		//Rule 1. MyList is empty
		if (m.length() == 0) 
			scenario = 1;
		//Rule 2. MyList is non-empty
		else
			scenario = 2;

		//-----------------------------
		// II. SCENARIO IMPLEMENTATION 
		//-----------------------------
		switch(scenario){	
				
		//Rule 1. MyList is empty
		case 1: 
			//1. We print the empty message
			System.out.println("Empty MyList");
			
			break;
			
		//Rule 2. MyList is non-empty
		case 2: 
			//1. We print the initial message
			int size = m.length();
			System.out.print("MyList has " + size + " items: [");
			
			//2. We traverse the items
			for (int i = 0; i < size - 1; i++)
				System.out.print(m.getElement(i) + ", ");
			System.out.println(m.getElement(size - 1) + "]");
			
			break;
	
		}
		
	}
		
	//-------------------------------------------------------------------
	// 1. selectionFunction --> It selects the next candidate to be considered.  
	//-------------------------------------------------------------------	
	/**
	 * Given a current solution that is not a final solution, this function selects the new candidate to be added to it.<br> 
	 * The policy followed is more elaborated: Pick the biggest non-discarded type of coin.
	 * @param changeGenerated: The quantity of change we have generated so far. 
	 * @param discarded: The MyList stating whether a candidate has been discarded so far or not.
	 * @param coinValues: A MyList containing the value of each type of coin supported. 
	 * @return: The index of candidate to be selected.
	 */	
	public int selectionFunction(int changeGenerated, 
								 MyList<Integer> discarded, 
								 MyList<Integer> coinValues){
		
		int biggestCoin = 0;
		int selectedIndex = -1;
		
		for(int i = 0, length = coinValues.length(); i < length; i++)
		{
			if(discarded.getElement(i) == 0)
			{
				int coinValue = coinValues.getElement(i);
				if(coinValue > biggestCoin)
				{
					biggestCoin = coinValue;
					selectedIndex = i;
				}
			}
		}
		return selectedIndex;		
	}
	
	//-------------------------------------------------------------------
	// 2. feasibilityTest --> It selects if a candidate can be added to the solution.  
	//-------------------------------------------------------------------	
	/**
	 * Given a current solution and a selected candidate, this function 
	 * states whether the candidate must be added to the solution or discarded.<br> 
	 * @param coinValues: A MyList containing the value of each type of coin supported. 
	 * @param amount: The amount of change we want to generate.
	 * @param changeGenerated: The quantity of change we have generated so far. 
	 * @param itemSelected: The index of the candidate selected.
	 * @return: Whether the candidate fits or not into the solution.
	 */	

	public boolean feasibilityTest(MyList<Integer> coinValues,
								   int amount,
								   int changeGenerated,
								   int itemSelected){
		
		return (coinValues.getElement(itemSelected) + changeGenerated <= amount);	
	}
	
	//-------------------------------------------------------------------
	// 3. solutionTest --> It selects if the current solution is the final solution  
	//-------------------------------------------------------------------	
	/**
	 * Given a current solution, this function states whether it is a final solution or it can still be improved.<br> 
	 * To determine it, it checks whether there is (at least) one more coin that can be used as part of the change.
	 * @param changeGenerated: The change generated by the current solution. 
	 * @param discarded: The MyList stating whether a candidate has been discarded so far or not.
	 * @param coinValues: The MyList containing the set of coins supported. 
	 * @param amount: The amount of change we want to generate.
	 * @return: Whether the current solution is the final solution.
	 */	
	public boolean solutionTest(int changeGenerated,
								MyList<Integer> discarded,
								MyList<Integer> coinValues, 
							    int amount){
		
		for(int i = 0, length = coinValues.length(); i < length; i++)
		{
			if(discarded.getElement(i) == 0 && feasibilityTest(coinValues, amount, changeGenerated, i))
				return false;
		}
		return true;		
	}

	//-------------------------------------------------------------------
	// 4. objectiveFunction --> This function computes the value of the final solution.  
	//-------------------------------------------------------------------	
	/**
	 * Given the final solution to the problem, this function 
	 * computes its value according to:<br>
	 * How many coins are used in the solution.<br>
	 * How accurate it is the change.<br> 
	 * @param sol: The MyList containing the solution to the problem.
	 * @param changeGenerated: The change generated by the current solution. 
	 * @param amount: The amount of change we want to generate. 
	 * @return: The value of such solution.
	 */	
	public MyList<Integer> objectiveFunction(MyList<Integer> sol, 
											 int changeGenerated, 
											 int amount){
		
		int counter = 0;
		int accuracy = (int) (((double) changeGenerated / (double) amount)*100);
		
		for(int i = 0, length = sol.length(); i < length; i++)
		{
			if(sol.getElement(0) != 0)
				counter++;
				
		}
		MyList<Integer> res = new MyDynamicList<Integer>();
		res.addElement(0, counter);
		res.addElement(1, accuracy);
		
		return res;		
	}
	
	//-------------------------------------------------------------------
	// 5. solve --> This function solves the problem using a greedy algorithm.  
	//-------------------------------------------------------------------	
	/**
	 * Given an instance of the GP1 problem, this function solves it using 
	 * a greedy algorithm.<br> 
	 * @param coinValues: A MyList containing the value of each type of coin supported. 
	 * @param amount: The amount of change we want to generate.
	 * @return: A MyList containing the amount of coins of each type being selected.
	 */	
	public MyList<Integer> solve(MyList<Integer> coinValues, int amount){

		MyList<Integer> res = new MyDynamicList<Integer>();
		MyList<Integer> discarded = new MyDynamicList<Integer>();
		MyList<Integer> solutionValue = null;
		int changeGenerated = 0;
		
		for(int i = 0, length = coinValues.length(); i < length; i++)
		{
			res.addElement(0, 0);
			discarded.addElement(0, 0);
		}
		
		while(!solutionTest(changeGenerated, discarded, coinValues, amount))
		{
			int item = selectionFunction(changeGenerated, discarded, coinValues);
			
			if(feasibilityTest(coinValues, amount, changeGenerated, item))
			{
				res.removeElement(item);
				res.addElement(item, 1);
				
				changeGenerated += coinValues.getElement(item);
			}
			
			discarded.removeElement(item);
			discarded.addElement(item, 1);
		}

		displayElements(res);
		
		solutionValue = objectiveFunction(res, changeGenerated, amount);
		System.out.println("Number of coins used: " + solutionValue.getElement(0) + "\nAccuracy: " + solutionValue.getElement(1) + "%");
		return res;		
	}
	
}
