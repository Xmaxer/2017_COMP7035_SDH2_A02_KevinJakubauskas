package hint2;
/**
 * The class contains the Divide and Conquer-based Algorithms we are using. 
 */
public class DivideAndConquerAlgorithms {

	//----------------------------------------------
	// Class constructor
	//----------------------------------------------	
	/**
	 * Constructor of the class. Do not edit it.
	 */
	public DivideAndConquerAlgorithms(){}

	//-------------------------------------------------------------------
	// 0. iterativeDisplayElements --> Displays all elements of a MyList 
	//-------------------------------------------------------------------	
	/**
	 * Given a concrete MyList, this iterative algorithm displays its elements by screen (if any).
	 * @param m: The MyList we want to display its elements.	  
	 */	
	public void iterativeDisplayElements(MyList<Integer> m){
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
			System.out.println("MyList Contains the following " + size + " items: ");

			//2. We traverse the items
			for (int i = 0; i < size; i++)
				System.out.println("Item " + i + ": " + m.getElement(i));

			break;

		}

	}

	//-------------------------------------------------------------------
	// 1. recursiveDisplayElements --> Displays all elements of a MyList  
	//-------------------------------------------------------------------	
	/**
	 * Given a concrete MyList, this recursive algorithm displays its elements by screen (if any).
	 * @param m: The MyList we want to display its elements.	  
	 */	
	public void recursiveDisplayElements(MyList<Integer> m){

		if(m.length() > 0)
		{
			Integer num = m.getElement((m.length() - 1));
			
			if(m.length() == 1)
			{
				System.out.println(num);
				return;
			}

			m.removeElement((m.length() - 1));
			recursiveDisplayElements(m);

			System.out.println(num);

			m.addElement(m.length(), num);
		}
		else
		{
			System.out.println("No elements to print.");
		}
	}

	//-------------------------------------------------------------------
	// 2. smallerMyList --> Filters all elements in MyList smaller than e
	//-------------------------------------------------------------------	
	/**
	 * The function filters all elements of MyList being smaller than 'e'  
	 * @param m: The MyList we want to check.
	 * @param e: The number 'e' we want to compare each element of MyList to.
	 * @return: The new MyList containing just the elements being smaller than 'e'  
	 */	
	public MyList<Integer> smallerMyList(MyList<Integer> m, int e){

		MyList<Integer> updatedList = new MyDynamicList<Integer>();

		if(m.length() > 0)
		{
			Integer num = m.getElement(0);

			m.removeElement(0);

			updatedList = smallerMyList(m, e);

			if(e > num)
			{
				updatedList.addElement(0, num);
			}

			m.addElement(0, num);
		}

		return updatedList;
	}

	//-------------------------------------------------------------------
	// 3. biggerMyList --> Filters all elements in MyList bigger than e
	//-------------------------------------------------------------------	
	/**
	 * The function filters all elements of MyList being bigger than 'e'  
	 * @param m: The MyList we want to check.
	 * @param e: The number 'e' we want to compare each element of MyList to.
	 * @return: The new MyList containing just the elements being bigger or equal than 'e'  
	 */	
	public MyList<Integer> biggerEqualMyList(MyList<Integer> m, int e){

		MyList<Integer> updatedList = new MyDynamicList<Integer>();

		if(m.length() > 0)
		{
			Integer num = m.getElement(0);

			m.removeElement(0);

			updatedList = biggerEqualMyList(m, e);

			if(e <= num)
			{
				updatedList.addElement(0, num);
			}

			m.addElement(0, num);
		}

		return updatedList;
	}

	//-------------------------------------------------------------------
	// 3. concatenate --> It concatenates 2 MyList   
	//-------------------------------------------------------------------	
	/**
	 * The function concatenates the content of 2 MyList.   
	 * @param m1: The first MyList.
	 * @param m2: The second MyList.
	 * @return: The new MyList resulting of concatenate the other 2 MyList
	 */	
	public MyList<Integer> concatenate(MyList<Integer> m1, MyList<Integer> m2){

		MyList<Integer> res = new MyDynamicList<Integer>();

		if(m1.length() > 0)
		{
			Integer num = m1.getElement(0);
			m1.removeElement(0);

			res = concatenate(m1, m2);
			res.addElement(0, num);
			
			m1.addElement(0, num);

		}
		else if(m2.length() > 0)
		{
			Integer num = m2.getElement(0);
			m2.removeElement(0);

			res = concatenate(m1, m2);
			res.addElement(0, num);
			
			m2.addElement(0, num);
		}

		return res;
	}

	//-------------------------------------------------------------------
	// 4. quickSort --> Sort a MyList using the method quick sort
	//-------------------------------------------------------------------	
	/**
	 * Given a concrete MyList, it computes a new sorted list using the method Quick Sort.
	 * @param m: The MyList we want to sort.
	 * @return: The new MyList being sorted.	  	  
	 */	  
	public MyList<Integer> quickSort(MyList<Integer> m){

		MyList<Integer> res = new MyDynamicList<Integer>();

		if(m.length() == 1)
		{
			return m;
		}
		else if(m.length() > 1)
		{
			Integer num = m.getElement(0);
			
			m.removeElement(0);
			
			MyList<Integer> bigger = quickSort(biggerEqualMyList(m, num));
			
			bigger.addElement(0, num);
			
			res = concatenate(quickSort(smallerMyList(m, num)), bigger);
			
			m.addElement(0, num);
		}
		return res;		
	}		

}
