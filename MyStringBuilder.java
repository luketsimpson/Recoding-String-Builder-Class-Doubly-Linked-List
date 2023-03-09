// CS 0445 Spring 2023
// Read this class and its comments very carefully to make sure you implement
// the class properly.  Note the items that are required and that cannot be
// altered!  Generally speaking you will implement your MyStringBuilder using
// a circular, doubly linked list of nodes.  See more comments below on the
// specific requirements for the class.

// You should use this class as the starting point for your implementation. 
// Note that all of the methods are listed -- you need to fill in the method
// bodies.  Note that you may want to add one or more private methods to help
// with your implementation -- that is fine.

// For more details on the general functionality of most of these methods, 
// see the specifications of the similar method in the StringBuilder class.  
public class MyStringBuilder
{
	// These are the only two instance variables you are allowed to have.
	// See details of CNode class below.  In other words, you MAY NOT add
	// any additional instance variables to this class.  However, you may
	// use any method variables that you need within individual methods.
	// But remember that you may NOT use any variables of any other
	// linked list class or of the predefined StringBuilder or 
	// StringBuffer class in any place in your code.  You may only use the
	// String class where it is an argument or return type in a method.
	private CNode firstC;	// reference to front of list.  This reference is necessary
							// to keep track of the list
	private int length;  	// number of characters in the list

	// You may also add any additional private methods that you need to
	// help with your implementation of the public methods.

	// Create a new MyStringBuilder initialized with the chars in String s
	// Note: This method is implemented for you.  See code below.  Also read
	// the comments.  The code here may be helpful for some of your other
	// methods.
	public MyStringBuilder(String s)
	{
		if (s == null || s.length() == 0)  // special case for empty String
		{
			firstC = null;
			length = 0;
		}
		else
		{
			firstC = new CNode(s.charAt(0));  // create first node
			length = 1;
			CNode currNode = firstC;
			// Iterate through remainder of the String, creating a new
			// node at the end of the list for each character.  Note
			// how the nodes are being linked and the current reference
			// being moved down the list.
			for (int i = 1; i < s.length(); i++)
			{
				CNode newNode = new CNode(s.charAt(i));  // create Node
				currNode.next = newNode;  	// link new node after current
				newNode.prev = currNode;	// line current before new node
				currNode = newNode;			// move down the list
				length++;
			}
			// After all nodes are created, connect end back to front to make
			// list circular
			currNode.next = firstC;
			firstC.prev = currNode;
		}
	}

	// Return the entire contents of the current MyStringBuilder as a String
	// For this method you should do the following:
	// 1) Create a character array of the appropriate length
	// 2) Fill the array with the proper characters from your MyStringBuilder
	// 3) Return a new String using the array as an argument, or
	//    return new String(charArray);
	// Note: This method is implemented for you.  See code below.
	public String toString()
	{
		char [] c = new char[length];
		int i = 0;
		CNode currNode = firstC;
		
		// Since list is circular, we cannot look for null in our loop.
		// Instead we count within our while loop to access each node.
		// Note that in this code we don't even access the prev references
		// since we are simply moving from front to back in the list.
		while (i < length)
		{
			c[i] = currNode.data;
			i++;
			currNode = currNode.next;
		}
		return new String(c);
	}

	// Create a new MyStringBuilder initialized with the chars in array s. 
	// You may NOT create a String from the parameter and call the first
	// constructor above.  Rather, you must build your MyStringBuilder by
	// accessing the characters in the char array directly.  However, you
	// can approach this in a way similar to the other constructor.
	public MyStringBuilder(char [] s)
	{	
		// special case for empty String
		if (s == null || s.length == 0) 
		{
			firstC = null;
			length = 0;
		}
		else
		{
			//Creates the first node and initializes the length
			firstC = new CNode(s[0]);
			length = 1;
			CNode currNode = firstC;
			
			// Iterate through remainder of the String, creating a new node at the end of the list for each character. 
			for (int i = 1; i < s.length; i++)
			{
				CNode newNode = new CNode(s[i]);  // create Node
				currNode.next = newNode;  	// link new node after current
				newNode.prev = currNode;	// line current before new node
				currNode = newNode;			// move down the list
				length++;
			}
			
			// After all nodes are created, connect end back to front to make list circular
			currNode.next = firstC;
			firstC.prev = currNode;
		}
	}
	
	// Copy constructor -- make a new MyStringBuilder from an old one.  Be sure
	// that you make new nodes for the copy (traversing the nodes in the original
	// MyStringBuilder as you do)
	public MyStringBuilder(MyStringBuilder old)
	{
		// special case for empty String
		if (old == null || old.length == 0)  
		{
			firstC = null;
			length = 0;
		}
		else
		{
			firstC = new CNode(old.firstC.data);  // create first node
			length = 1;
			CNode currNode = firstC;
			
			//Holds the old current node
			CNode oldCurrNode = old.firstC.next;
			
			// Iterate through remainder of the String, creating a new node at the end of the list for each character.  
			for (int i = 1; i < old.length; i++)
			{
				CNode newNode = new CNode(oldCurrNode.data); // create Node
				currNode.next = newNode; // link new node after current
				newNode.prev = currNode; // line current before new node
				currNode = newNode;	// move down the list
				
				length++;
				
				//Increments the old string builder
				if(oldCurrNode.next != old.firstC)
					oldCurrNode = oldCurrNode.next;
			}
			// After all nodes are created, connect end back to front to make
			// list circular
			currNode.next = firstC;
			firstC.prev = currNode;
		}
	}
	
	// Create a new empty MyStringBuilder
	public MyStringBuilder()
	{
		firstC = null;
		length = 0;
	}
	
	// Append MyStringBuilder b to the end of the current MyStringBuilder, and
	// return the current MyStringBuilder.  Be careful for special cases!  Note
	// that you cannot simply link the two MyStringBuilders together -- that is
	// very simple but it will intermingle the two objects, which you do not want.
	// Thus, you should copy the data in argument b to the end of the current
	// MyStringBuilder.
	public MyStringBuilder append(MyStringBuilder b)
	{
		//Checks for an empty string
		if (b != null && b.length != 0)  
		{
			CNode lastNode = null;
			//Checks if there are contents in the list
			if(length!=0)
			{
				lastNode = firstC.prev;
				firstC.prev = null; //Breaks the circular link to add new items
				lastNode.next = null;
			}
			
			//Keeps track of the current node to add
			CNode currNode = b.firstC;
				
			//Runs through the length of the string builder to add
			for(int i=0; i<b.length; i++)
			{
				//Checks if the current class' string is empty 
				if(length==0)
				{
					add(new CNode(currNode.data), null);
					lastNode = firstC;
				}
				else
				{
					add(new CNode(currNode.data), lastNode);
					lastNode = lastNode.next;
				}
				
				//gets the next node in b
				if(currNode.next != b.firstC)
					currNode = currNode.next;
					
			}
			
			// After all nodes are created, connect end back to front to make list circular
			lastNode.next = firstC;
			firstC.prev = lastNode;
		}	
		
		return this;
	}

	// Append String s to the end of the current MyStringBuilder, and return
	// the current MyStringBuilder.  Be careful for special cases!
	public MyStringBuilder append(String s)
	{
		//Checks for an empty string
		if (s != null && s.length() != 0)  
		{
			CNode lastNode = null;
		
			//Checks if there are contents in the list
			if(length!=0)
			{
				lastNode = firstC.prev;
				firstC.prev = null; //Breaks the circular link to add new items
				lastNode.next = null;
			}
				
			//Runs through the length of the string builder to add
			for(int i=0; i<s.length(); i++)
			{
				//Checks if the current class' string is empty 
				if(length==0)
				{
					add(new CNode(s.charAt(i)), null);
					lastNode = firstC;
				}
				else
				{
					add(new CNode(s.charAt(i)), lastNode);
					lastNode = lastNode.next;
				}
					
			}
			
			// After all nodes are created, connect end back to front to make list circular
			lastNode.next = firstC;
			firstC.prev = lastNode;
		}	
		
		return this;
	}

	// Append char array c to the end of the current MyStringBuilder, and
	// return the current MyStringBuilder.  Be careful for special cases!
	public MyStringBuilder append(char [] c)
	{
		//Checks for an empty string
		if (c != null && c.length != 0)  
		{
			CNode lastNode = null;
			//Checks if there are contents in the list
			if(length!=0)
			{
				lastNode = firstC.prev;
				firstC.prev = null; //Breaks the circular link to add new items
				lastNode.next = null;
			}
				
			//Runs through the length of the string builder to add
			for(int i=0; i<c.length; i++)
			{
				//Checks if the current class' string is empty 
				if(length==0)
				{
					add(new CNode(c[i]), null);
					lastNode = firstC;
				}
				else
				{
					add(new CNode(c[i]), lastNode);
					lastNode = lastNode.next;
				}
					
			}
			
			// After all nodes are created, connect end back to front to make list circular
			lastNode.next = firstC;
			firstC.prev = lastNode;
		}	
		
		return this;
	}

	// Append char c to the end of the current MyStringBuilder, and
	// return the current MyStringBuilder.  Be careful for special cases!
	public MyStringBuilder append(char c)
	{
		//Checks for an empty character
		if (c != '\u0000') 
		{			
			//Checks if the current class' string is empty 
			if(length==0)
			{
				firstC = new CNode(c); //Creates the first node in the list
				length = 1;
			}
			else
			{
				CNode newNode = new CNode(c);
				CNode currNode = firstC.prev;
				
				currNode.next = newNode;
				newNode.prev = currNode;
				newNode.next = firstC;
				firstC.prev = newNode;
				
				length++;
			}
		}	
		
		return this;
	}
	
	//This method adds a node to the end of the string builder
	private void add(CNode add, CNode last)
	{
		//Checks if the first node is being added
		if(last == null)
		{
			firstC = new CNode(add.data); //Creates the first node in the list
		}
		else
		{
			last.next = add; //Links the last node to the new node
			add.prev = last; //Links the new node to the last node
		}
		
		length++; //Increases the length
	}

	// Return the character at location "index" in the current MyStringBuilder.
	// If index is invalid, throw an IndexOutOfBoundsException.
	public char charAt(int index)
	{
		if(index<0||index>=length)
			throw new IndexOutOfBoundsException("Illegal Index " +index);
		
		//checks to see if the index is closer to the front or back
		if(index<(length/2))
		{
			//creates a variable to store the character
			CNode currNode = firstC;
			
			//Runs a for loop to traverse to the character
			for(int i=0; i<index; i++)
				currNode = currNode.next;
			
			//returns the node that the traversal stopped at
			return currNode.data;
		}
		else
		{
			//creates a variable to store the character
			CNode currNode = firstC.prev;
			
			//Runs a for loop to traverse to the character
			for(int i=length-1; i>index; i--)
				currNode = currNode.prev;
			
			//returns the node that the traversal stopped at
			return currNode.data;			
		}
	}

	// Delete the characters from index "start" to index "end" - 1 in the
	// current MyStringBuilder, and return the current MyStringBuilder.
	// If "start" is invalid or "end" <= "start" do nothing (just return the
	// MyStringBuilder as is).  If "end" is past the end of the MyStringBuilder, 
	// only remove up until the end of the MyStringBuilder. Be careful for 
	// special cases!
	public MyStringBuilder delete(int start, int end)
	{
		int startLength = length; //keeps track of the starting length for a comparison later
		
		if(end<=start || start<0 || end<0) // checks to make sure the start and end are valid 
			return this; //returns the original string builder if it is not valid 
		else
		{
			//sets the end variable to the logical end if it is past
			if(end>length)
				end = length;
			
			//Checks if all the elements are getting removed
			if(start ==0 && end == length)
			{
				firstC = null;
				firstC.prev = null;
				firstC.next = null;
				length=0;
			}	
			
			//checks if the string builder is empty
			if(length == 0)
				return this;
			
			//Runs a for loop to traverse to the element to remove
			CNode removeNode = firstC;
			for(int i=0; i<start; i++)
				removeNode = removeNode.next;
				
			//Runs a for loop to remove the elements
			for(int i=0; i<end-start; i++)
			{
				if(start == 0) //checks if the nodes are being removed from the front
				{
					CNode lastNode = firstC.prev; //stores the last node to make the connection 
					
					firstC = removeNode.next; //sets th new first C to frist C
					firstC.prev = lastNode; //links the new firstC to the last node
				}
				else
				{
					if(end == startLength && i==end-start-2)//Checks if the last node is being removed
					{
						CNode lastNode = removeNode.prev; // stores the last node
						
						firstC.prev = lastNode; //connects the firstC to the new last node
						lastNode.next = firstC; //connects the new last node to the first node
					}
					else
					{
						removeNode.prev.next = removeNode.next; //connects the node to remove -1 to the node to remove + 1
						removeNode.next.prev = removeNode.prev; //connects the node to remove +1 to the node to remove - 1
					}
				}
				
				removeNode = removeNode.next; //increments the node to remove
				length--; //decreases the length
			}
			
			//checks if the length is now 1
			if(length==1)
			{
				firstC.prev = null;
				firstC.next = null;
			}
			
			return this;
				
		}
			
			
	}

	// Delete the character at location "index" from the current
	// MyStringBuilder and return the current MyStringBuilder.  If "index" is
	// invalid, do nothing (just return the MyStringBuilder as is).  If "index"
	// is the last character in the MyStringBuilder, go backward in the list in
	// order to make this operation faster (since the last character is simply
	// the previous of the first character)
	// Be careful for special cases!
	public MyStringBuilder deleteCharAt(int index)
	{
		if(index < 0 || index >length)
			return this;
		else
		{
			//Checks if the last node is being removed
			if(index == length-1)
			{
				firstC.prev = firstC.prev.prev;
				firstC.prev.next = firstC;
			}
			else if(index == 0) //checks if the first node is bring removed
			{
				firstC.prev.next = firstC.next;
				firstC = firstC.next;
	
			}
			else //removes a node from the middle
			{
				//runs a for loop to get the node to be removed
				CNode removeNode = firstC;
				for(int i=0; i<index; i++)
					removeNode = removeNode.next;
				
				removeNode.prev.next = removeNode.next;
				removeNode.next.prev = removeNode.prev;
			}
			
			length--;
			
			return this;
		}			
			
	}

	// Find and return the index within the current MyStringBuilder where
	// String str first matches a sequence of characters within the current
	// MyStringBuilder.  If str does not match any sequence of characters
	// within the current MyStringBuilder, return -1.  Think carefully about
	// what you need to do for this method before implementing it.
	public int indexOf(String str)
	{
		//keeps track of the index to return 
		int index = -1;
		
		//keeps track of the current Node
		CNode currNode = firstC;
		
		//Runs through the string builder
		for(int i=0; i<length; i++)
		{
			//resets the variables that keep track of the subString position and the length of the string to check
			int strLength = 0;
			int subPosition = 0;
			
			//checks if the first character in str is found in the String builder
			if(currNode.data == str.charAt(subPosition))
			{
				index = i; //Stores the index in a variable before performing operations
				
				CNode check = currNode;//creates a new node to increment through to preserve the current node
				
				boolean valid = true; //keeps track if the string is still valid to be checked
				while (valid && strLength<str.length())
				{
					if(check.data == str.charAt(subPosition))
					{
						subPosition++; //gets the next position in str to check
						strLength++; //increments the string length
						
						//checks if the whole subString was found
						if(strLength == str.length())
							return index;
					}
					else
					{
						valid = false; //sets valid to false to break the while loop
						
						//resets all the variables that keep track of the information
						strLength = 0;
						subPosition = 0;
						index = -1;
					}
					
					check= check.next;	//gets the next node to check
				}
			}
			
			currNode = currNode.next; //gets the next node in the list
		}
		
		return -1; //returns negative one if the substring was not found
	}

	// Insert String str into the current MyStringBuilder starting at index
	// "offset" and return the current MyStringBuilder.  if "offset" == 
	// length, this is the same as append.  If "offset" is invalid
	// do nothing.
	public MyStringBuilder insert(int offset, String str)
	{
		//checks if the offset value is valid
		if(offset<0 || offset> length)
			return this;
		
		if(offset == 0) // inserts at the front of the string
		{
			//runs until the whole string was added
			for(int i=str.length()-1; i>=0; i--)//adds the characters in reverse order
			{
				//creates the new node to add
				CNode newNode = new CNode(str.charAt(i));
				
				//checks if the string builder is empty 
				if(firstC == null)
				{
					firstC = newNode;
					firstC.next = newNode;
					firstC.prev = newNode;
					length++;
				}
				else
				{				
					//Makes the connections
					firstC.prev.next = newNode; //Connects the last node to the new first node
					newNode.prev = firstC.prev; //connects the new first node to the last node
					firstC.prev = newNode; //connects old first node to the new first node
					newNode.next = firstC; //connects the new first node to the old first Node
				
					firstC = newNode; //sets the new node to the first node 
				
					length++; //increments the length
				}
			}
		}
		else if(offset == length) //inserts at the back of the string
		{
			append(str); //calls the append method to add to the end
		}
		else //inserts in the middle of the string 
		{
			//runs a for loop to get the offset node
			CNode offsetNode = firstC;
			for(int i=0; i<offset; i++)
				offsetNode = offsetNode.next;
			
			//runs until the whole string was added
			for(int i=0; i<str.length(); i++)
			{
				CNode newNode = new CNode(str.charAt(i));
				
				//Makes the connections
				offsetNode.prev.next = newNode; //connects the offset-1 node to the new node
				newNode.prev = offsetNode.prev; //connects the new node to the offset-1 node
				newNode.next = offsetNode; //connects the new node to the offset node
				offsetNode.prev = newNode; //connects the offset node to the new node
				
				length++; //increments the length
			}
		}
		
		//returns the new string builder
		return this;
	}

	// Insert character c into the current MyStringBuilder at index
	// "offset" and return the current MyStringBuilder.  If "offset" ==
	// length, this is the same as append.  If "offset" is invalid, 
	// do nothing.
	public MyStringBuilder insert(int offset, char c)
	{
		//checks if the offset value is valid
		if(offset<0 || offset> length)
			return this;
		
		if(offset == 0) // inserts at the front of the string
		{
			//creates the new node to add
			CNode newNode = new CNode(c);
				
			//Makes the connections
			firstC.prev.next = newNode; //Connects the last node to the new first node
			newNode.prev = firstC.prev; //connects the new first node to the last node
			firstC.prev = newNode; //connects old first node to the new first node
			newNode.next = firstC; //connects the new first node to the old first Node
			
			firstC = newNode; //sets the new node to the first node 
				
			length++; //increments the length
			
		}
		else if(offset == length) //inserts at the back of the string
		{
			append(c); //calls the append method to add to the end
		}
		else //inserts in the middle of the string 
		{
			//runs a for loop to get the offset node
			CNode offsetNode = firstC;
			for(int i=0; i<offset; i++)
				offsetNode = offsetNode.next;
			
			CNode newNode = new CNode(c);
				
			//Makes the connections
			offsetNode.prev.next = newNode; //connects the offset-1 node to the new node
			newNode.prev = offsetNode.prev; //connects the new node to the offset-1 node
			newNode.next = offsetNode; //connects the new node to the offset node
			offsetNode.prev = newNode; //connects the offset node to the new node
				
			length++; //increments the length
	
		}
		
		//returns the new string builder
		return this;
	}

	// Return the length of the current MyStringBuilder
	public int length()
	{
		return length;
	}

	// Delete the substring from "start" to "end" - 1 in the current
	// MyStringBuilder, then insert String "str" into the current
	// MyStringBuilder starting at index "start", then return the current
	// MyStringBuilder.  If "start" is invalid or "end" <= "start", do nothing.
	// If "end" is past the end of the MyStringBuilder, only delete until the
	// end of the MyStringBuilder, then insert.  This method should be done
	// as efficiently as possible.  In particular, you may NOT simply call
	// the delete() method followed by the insert() method, since that will
	// require an extra traversal of the linked list.
	public MyStringBuilder replace(int start, int end, String str)
	{
		int startLength = length; //keeps track of the starting length for a comparison later
		
		//checks if the start and end are valid
		if(start<0 || end<=start || end<0)
			return this;
		
		//sets the end to the length if it is too large
		if(end>length)
			end=length;
		
		if(end-start>str.length()) //runs if there is more to remove than replace
		{
			//runs a for loop to get the node to replace
			CNode replace = firstC;
			for(int i=0; i<start; i++)
				replace = replace.next;
			
			for(int i=0;i<str.length();i++) //runs for the length of the string 
			{
				replace.data = str.charAt(i); //replaces the characters
				replace = replace.next; //increments the replace node
			}
			
			for(int i=0; i<(end-start)-str.length(); i++) //removes the items that were not replaced but need to be removed
			{
				//checks if the end is being deleted
				if(end == startLength && i==(end-start)-str.length()-2)
				{
					replace.prev.next = firstC; // Makes a connection between the second to last node and the first
					firstC.prev = replace.prev;  // Makes a connection between the first node and the new last ndoe
					
					length--; //decrements the length
				}
				else
				{
					replace.prev.next = replace.next; //sets the node to remove's previous node to the node after the node to remove
					replace.next.prev = replace.prev; //sets the node to remove's next node to the node before the node to remove
					
					length--; //decrements the length
				}
					
			}
		}
		else if(end-start<str.length()) //runs if there is less to remove than replace
		{
			//runs a for loop to get the node to replace
			CNode replace = firstC;
			for(int i=0; i<start; i++)
				replace = replace.next;
			
			for(int i=0;i<end-start;i++) //runs for the length of the string 
			{
				replace.data = str.charAt(i); //replaces the characters
				replace = replace.next; //increments the replace node
			}
			
			//appends the rest of the string to replace
			for(int i=0; i<str.length()-(end-start); i++)
			{
				//checks if the last node is being appended to the end
				if(end==length)
				{
					CNode newNode = new CNode(str.charAt((end-start)+i)); //creates the new node to add
					
					firstC.prev.next = newNode; //sets the second to last node's next value to the new node
					newNode.prev = firstC.prev; //sets the new node's previous value to the first node
					firstC.prev = newNode; //sets the first node's previous value to the new node
					newNode.next = firstC; //sets the new node's next value to the first node
					
					length++; //increments the length
				}
				else
				{
					CNode newNode = new CNode(str.charAt((end-start)+i)); //creates the new node to add
					
					replace.prev.next = newNode; //sets the node before the node to replace's value of next to the new node
					newNode.prev = replace.prev; //sets the new node's previous value to the node before the node to add
					newNode.next = replace; //sets the new node's next value to the node to add
					replace.prev = newNode; //sets the node to add's previosu node to the new node
					
					length++; //increments the length
					replace = newNode.next; //gets the new node to replce
				}
			}
		}
		else //runs if there is an equal amount to remove and replace
		{
			//runs a for loop to get the node to replace
			CNode replace = firstC;
			for(int i=0; i<start; i++)
				replace = replace.next;
			
			for(int i=0;i<str.length();i++) //runs for the length of the string 
			{
				replace.data = str.charAt(i); //replaces the characters
				replace = replace.next; //increments the replace node
			}
		}
		
		return this;
	}

	// Return as a String the substring of characters from index "start" to
	// index "end" - 1 within the current MyStringBuilder.  For this method
	// you should do the following:
	// 1) Create a character array of the appropriate length
	// 2) Fill the array with the proper characters from your MyStringBuilder
	// 3) Return a new String using the array as an argument, or
	//    return new String(charArray);
	public String substring(int start, int end)
	{
		//checks if the start and end are valid
		if(start<0 || end<=start || end<0)
			return null;
		
		//sets the end to the length if it is too large
		if(end>length)
			end=length;
		
		//runs a for loop to get the node to start with
		CNode startNode = firstC;
		for(int i=0; i<start; i++)
			startNode = startNode.next;
		
		//creates the character array to hold the values 
		char [] c = new char[end-start];
		int i = 0;
				
		//creates a while loop to run through the nodes
		while (i < c.length)
		{
			c[i] = startNode.data;
			i++;
			
			startNode = startNode.next;
		}
		return new String(c);
	}

	// Return as a String the reverse of the contents of the MyStringBuilder.  Note
	// that this does NOT change the MyStringBuilder in any way.  See substring()
	// above for the basic approach.
	public String revString()
	{
		char [] c = new char[length];
		int i = 0;
		CNode currNode = firstC.prev;
		
		// Since list is circular, we cannot look for null in our loop.
		// Instead we count within our while loop to access each node.
		// Note that in this code we don't even access the prev references
		// since we are simply moving from front to back in the list.
		while (i < length)
		{
			c[i] = currNode.data;
			i++;
			currNode = currNode.prev;
		}
		return new String(c);
	}
	
	// You must use this inner class exactly as specified below.  Note that
	// since it is an inner class, the MyStringBuilder class MAY access the
	// data, next and prev fields directly.
	private class CNode
	{
		private char data;
		private CNode next, prev;

		public CNode(char c)
		{
			data = c;
			next = null;
			prev = null;
		}

		public CNode(char c, CNode n, CNode p)
		{
			data = c;
			next = n;
			prev = p;
		}
	}
}
