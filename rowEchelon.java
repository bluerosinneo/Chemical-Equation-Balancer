/*
Class to solve for Row Echelon form
Input should be an array of arrays of integers
Assumption is that every row has the same number of elements
Everything should be kept in INTEGER!
*/
import java.util.ArrayList;
import java.lang.StringBuilder;

class rowEchelon
{
	public static void main(String args[])
	{
		int[][] A = {
			{1,0,0,-2,0,0,-5},
			{0,1,0,-3,0,0,-7},
			{0,0,1,-1,0,0,-3},
			{0,0,0,0,1,0,-4},
			{0,0,0,0,0,1,-2}
		};
		
		System.out.println(showArray(A));
		
		/*
		int[][] A = {
			{2,0,0,-3},
			{0,5,0,-7},
			{0,0,7,-1}
		};
		*/
		
		/*
		int[][] A = {
			{0,0,0,0},
			{0,0,2,1},
			{0,1,3,2},
			{3,4,5,6}
		};
		*/
		
		/*		
		int[][] A = {
			{1,2,1,10},
			{2,4,0,8}
		};
		*/
		
		/*
		int[][] A = {
			{1,0,-2,0},
			{1,0,0,-1},
			{0,2,0,-1},
			{0,1,-1,0}
		};
		*/
		
		/*
		int[][] A = {
			{1,2,3,},
			{4,5,6},
			{7,8,9}
		};
		*/
		
		//testing if pass by value or refference (it worked)
		/*
		System.out.println("This is a before testPassByValueRef.");
		showArray(a);
		testPassByValueRef(a);
		System.out.println("This is a after testPassByValueRef.");
		showArray(a);
		*/

		//test swapping rows (it worked)
		/*
		System.out.println("before swap rows.");
		showArray(a);
		swapRows(a,0,1);
		System.out.println("After swap rows.");
		showArray(a);
		*/
		
		//beggining to work on row echelon
		//test crissCross (it worked)
		/*
		System.out.println("before crissCross.");
		showArray(A);
		crissCross(A,0,1,0,1);
		System.out.println("After crissCross.");
		showArray(A);
		*/
		//test doRowEchelon
		/*
		System.out.println("before doRowEchelon.");
		showArray(A);
		doRowEchelon(A);
		System.out.println("After doRowEchelon.");
		showArray(A);
		*/
		
		// testing GCD *don't think further testing is needed*
		/*
		System.out.println(gcd(462,1071));
		int[] aBar = {6,18,342};
		System.out.println(gcd(aBar));
		*/
		int[] solutionOutput;
		solutionOutput = solveHomogeneous(A);
		
		
		System.out.println(showArray(solutionOutput));
		
		
		System.out.println("yep I worked");
	}
	
	static String showArray(int[][] A)
	{
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i<A.length; i++)
		{
			for(int j = 0; j<A[i].length; j++)
			{
				builder.append(A[i][j] + " ");
			}
			if(i!=(A.length-1))
			{
				builder.append("\n");
			}
		}
		
		String result = builder.toString();
		
		return result;
	}
	
	static String showArray(int[] A)
	{
		StringBuilder builder = new StringBuilder();
		for(int i = 0;i<A.length;i++)
		{
			builder.append(A[i] + " ");
		}
		String result = builder.toString();
		return result;
	}
	
	// arrays are pass by "refference"
	static void testPassByValueRef(int[][] A)
	{
		A[0][0]=9;
		//System.out.print("Inside testPassByValueRef.");
		//showArray(a);
		//return a;
	}
	
	static void swapRows(int[][] A, int i1, int i2)
	{
		int[] b = A[i1].clone();
		A[i1]=A[i2];
		A[i2]=b;
	}
	
	static void crissCross(int[][] A, int i1, int i2, int j1, int j2)
	{
		A[i2][j2]=((A[i1][j1]*A[i2][j2])-(A[i1][j2]*A[i2][j1]));
	}
	
	static int gcd(int a, int b)
	{
		if(b>a)
		{
			int temp = a;
			a = b;
			b = temp;
		}
		while (b > 0)
		{
			int temp = b;
			b = a%b;
			a = temp;
		}
		return a;
	}
	
	
	static int gcd(int[] aBar)
	{
		int result = 0;
		if(aBar.length>0)
		{
			result = aBar[0];
			for(int i = 1;i<aBar.length;i++)
			{
				result = gcd(result,aBar[i]);
			}
			
		}
		return result;
	}
	
	
	static void doRowEchelon(int[][] A)
	{
		boolean atEndOfMatrix = false;
		//first loob goes through each column
		for(int d=0; d<A[0].length; d++)
		{
			if(atEndOfMatrix == true)
			{
				break;
			}
			
			int iBlast=-1;
			int jBlast=-1;
			boolean foundBlastPoint = false;
			find:
			for(int jFind=d; jFind<A[0].length; jFind++)
			{
				for(int iFind=d; iFind<A.length; iFind++)
				{
					if(A[iFind][jFind]!=0)
					{
						//System.out.println("hopefully only see one of therese!!!");
						if(iFind!=d)
						{
							//System.out.println("what about this");
							swapRows(A,d,iFind);
						}
						//showArray(A);
						iBlast=d;
						jBlast=jFind;
						//jBlast=j;
						foundBlastPoint = true;
						break find;
					}
				}
			}
			
			if(atEndOfMatrix == true)
			{
				break;
			}
			
			//hold off on this part for now while i work on getting swaps and such working
			
			//blast everything into dust after iFind1 j1
			if(foundBlastPoint==true)
			{
				// this is what ib was before change to 0 "(iBlast+1)"

				for(int ib=0; ib<A.length; ib++)
				{
					if(A[ib][jBlast]!=0)
					{
						if(ib!=iBlast)
						{
							//replacing jb = jBlast with jb = 0
							for(int jb=0; jb<A[0].length; jb++)
							{
								if(jb==jBlast)
								{
									//can't do this before everything is blasted
									//A[ib][jb]=0;
								}
								else
								{
									//System.out.println("iBlast:" + iBlast + " ib:" + ib + " jBlast:" + jBlast + " jb:" + jb);
									crissCross(A,iBlast,ib,jBlast,jb);
								}
							}

						}
					}
				}
				//want to put here where we can simplify rows of the Matrix (it worked)
				/*
				System.out.println("after each blast");
				System.out.println(showArray(A));
				*/
				simplifyMatrix(A);
				/*
				System.out.println("this is after simplify");
				System.out.println(showArray(A));
				*/

				
				// this is what ib was before change to 0 "(iBlast+1)"
				for(int ib=0; ib<A.length; ib++)
				{
					if(ib!=iBlast)
					{
						A[ib][jBlast]=0;
					}
				}
				//System.out.println("--");
				//showArray(A);
				//System.out.println("--");
			}
		}
		
		//put simplify matrix formula here
		simplifyMatrix(A);
	}
	
	static void simplifyMatrix(int[][] A)
	{
		for(int i=0;i<A.length;i++)
		{
			boolean doReduce = false;
			for(int j=0;j<A[i].length;j++)
			{
				if(A[i][j]>0)
				{
					doReduce = true;
					break;
				}
				else if(A[i][j]<0)
				{
					//function to make leading non zero positive (or loop)
					for(int jInner=j;jInner<A[i].length;jInner++)
					{
						A[i][jInner] = A[i][jInner]*(-1);
					}
					doReduce = true;
					break;
				}
			}
			
			ArrayList<Integer> numsToReduce = new ArrayList<Integer>();
			for(int j=0;j<A[i].length;j++)
			{
				if(A[i][j]!=0)
				{
					if(A[i][j]<0)
					{
						numsToReduce.add((A[i][j])*(-1));
					}
					else
					{
						numsToReduce.add(A[i][j]);
					}
						
				}
			}
			int[] numsToReduceArr = new int[numsToReduce.size()];
			for(int k = 0 ; k<numsToReduce.size();k++)
			{
				numsToReduceArr[k]=numsToReduce.get(k).intValue();
			}
			int reduceBy = gcd(numsToReduceArr);
			for(int j = 0 ; j<A[i].length;j++)
			{
				if(A[i][j]!=0)
				{
					A[i][j]=A[i][j]/reduceBy;
				}
			}
		}
	}
	
	
	
	
	
	
	static int[] solveHomogeneous(int A[][])
	{
		doRowEchelon(A);
		//System.out.println(showArray(A));
		//System.out.println("this is after doRowEchelon");
		//showArray(A);
		
		int dimOfVariables = A[0].length;
		int numOfRows = A.length;
		
		//System.out.println("dimOfVariables:"+dimOfVariables);
		//System.out.println("numOfRows")
		
		//creating equation coefficients
		int[] baseDegreeFreedom=new int[dimOfVariables];
		int[] solution=new int[dimOfVariables];
		//move over colums first "dimOfVariables"
		for(int d=0;d<dimOfVariables;d++)
		{
			boolean foundDegreeOfFreedom = false;
			find: // go down the column
			for(int row=0;row<numOfRows;row++)
			{
				// is this location nonzero
				if(A[row][d]!=0)
				{
					//System.out.println("find a non zero");
					//look for leading nonzero before the nonzero
					//so start at the beggining of the row and move just before the nonzero to find leading nonzero
					for(int j=0;j<d;j++)
					{
						if(A[row][j]!=0)
						{
							// check if this column was already determined to be a degree of freedom
							if(baseDegreeFreedom[j]==0)
							{
								foundDegreeOfFreedom=true;
								baseDegreeFreedom[d]=1;
								break find;
							}
						}
					}
				}
			}
			
			if(foundDegreeOfFreedom=true)
			{
				findLeading: // looking for leading nonzero before the degree of freedom *move over rows first in the DF column*
				for(int i=0;i<numOfRows;i++)
				{
					//is the ith location of the degree of freedom column nonZero
					if(A[i][d]!=0)
					{
						// move over the beggining of the row to find the leading nonzero *the first non zero should not have anything
						// compeeting for it
						for(int jLeading=0;jLeading<d;jLeading++)
						{
							if(A[i][jLeading]!=0)
							{
								if(baseDegreeFreedom[d]<A[i][jLeading])
								{
									baseDegreeFreedom[d]=baseDegreeFreedom[d]*A[i][jLeading];
								}
								else if((baseDegreeFreedom[d]%A[i][jLeading])!=0)
								{
									baseDegreeFreedom[d]=baseDegreeFreedom[d]*A[i][jLeading];
								}
								break;
							}
						}
					}
				}
			}
		}
		
		//System.out.println("This should be baseDegreeFreedom");
		//showArray(baseDegreeFreedom);
		
		// fill in the final coefficients
		int iiIncrease = 0;
		for(int d=0;(d+iiIncrease)<dimOfVariables;d++)
		{

			if(baseDegreeFreedom[d+iiIncrease]!=0&&solution[d+iiIncrease]==0)
			{
				solution[d+iiIncrease]=baseDegreeFreedom[d+iiIncrease];
				iiIncrease++;
				d--;
			}
			else
			{
				//System.out.println((d+iiIncrease));
				if(((d+iiIncrease)<dimOfVariables)&&(d<numOfRows))
				{
					if(A[d][d+iiIncrease]!=0)
					{
						//System.out.println("solution");
						int divisor=A[d][d+iiIncrease];
						//iterate over row to create final 
						for(int jInner=(d+iiIncrease+1);jInner<dimOfVariables;jInner++)
						{
							if(A[d][jInner]!=0)
							{
								//System.out.println
								solution[d+iiIncrease]=solution[d+iiIncrease]-(A[d][jInner]*baseDegreeFreedom[jInner])/divisor;
							}
						}
					}
					else
					{
						//System.out.println("!!! THIS SHOULD NOT HAPPEN OMG OMG !!!");
					}
				}
			}
			//iiIncrease++;
		}
		
		return solution;
	}
}






















