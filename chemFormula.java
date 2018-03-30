/*
This is a class to read a chemical formula from text
*/
import java.util.ArrayList;
import java.lang.StringBuilder;

class chemFormula
{

	private ArrayList<unitElement> elementArrayList;
	private int formulaCoef;
	private String origFormula;
	private String elementStringOfFormula;
	public static void main(String args[])
	{
		
		chemFormula myChemFormula = new chemFormula();
		String formulaText = "Co3(Fe(CN)6)2";
		//System.out.println(formulaText);
		myChemFormula.readTextToFormula(formulaText);
		
		myChemFormula.printFormula();
		

	}
	
	chemFormula()
	{
		//want to somehow store original formula text but need to think about how i'm going to do it
		//String originalFormulaTest = "";
		elementArrayList = new ArrayList<unitElement>();
		formulaCoef = 1;
		origFormula = "";
		elementStringOfFormula = "";
	}
	
	void readTextToFormula(String formulaText)
	{
		this.readTextToFormula(formulaText, 1);
	}
	
	void readTextToFormula(String formulaText, int formulaSubscript)
	{
		// a hard coded one worked :=-)
		/*
		//start with just addin one with only 1 character
		elementArrayList.add(new unitElement(formulaText,1) );
		*/
		
		// this part was a bit more simpler than i thought
		/*
		//next lets work on on multiple single elements all 
		char[] formulaCharList= formulaText.toCharArray();
		for(int i=0;i<formulaCharList.length;i++)
		{
			if(Character.isLetter(formulaCharList[i]))
			{
			elementArrayList.add(new unitElement(String.valueOf(formulaCharList[i]),1));
			}
		}
		*/
		
		
		// now lets work on multiple elements but they are of the form Aa
		//works
		/*
		char[] formulaCharList= formulaText.toCharArray();
		for(int i=0;i<formulaCharList.length;i++)
		{
			char iChar=formulaCharList[i];
			if(Character.isLetter(iChar)&Character.isUpperCase(iChar))
			{
				if((i+1)<formulaCharList.length)
				{
					char iCharPlus = formulaCharList[i+1];
					if(Character.isLetter(iCharPlus)&Character.isLowerCase(iCharPlus))
					{
					elementArrayList.add(new unitElement(String.valueOf(iChar)+String.valueOf(iCharPlus),1));
					i++;
					}
					else
					{
					elementArrayList.add(new unitElement(String.valueOf(iChar),1));
					}
				}
				else
				{
					elementArrayList.add(new unitElement(String.valueOf(iChar),1));
				}
				
				
			}
		}
		*/
		
		// now adding numbers which will be somewhat simmilar to the change of adding Aa
		/*
		char[] formulaCharList= formulaText.toCharArray();
		for(int i=0;i<formulaCharList.length;i++)
		{
			String elementSym="";
			int numberS=1;
			char iChar=formulaCharList[i];
			if(Character.isLetter(iChar)&Character.isUpperCase(iChar))
			{
				if((i+1)<formulaCharList.length)
				{
					char iCharPlus = formulaCharList[i+1];
					if(Character.isLetter(iCharPlus)&Character.isLowerCase(iCharPlus))
					{
					elementSym = String.valueOf(iChar)+String.valueOf(iCharPlus);
					//elementArrayList.add(new unitElement(String.valueOf(iChar)+String.valueOf(iCharPlus),1));
					i++;
					}
					else
					{
					elementSym = String.valueOf(iChar);
					//elementArrayList.add(new unitElement(String.valueOf(iChar),1));
					}
				}
				else
				{
					elementSym = String.valueOf(iChar);
					//elementArrayList.add(new unitElement(String.valueOf(iChar),1));
				}
				System.out.println(i);
				if((i+1)<formulaCharList.length)
				{
					System.out.println("Hey Listen!");
					char iCharNumberCand = formulaCharList[i+1];
					System.out.print("this might be a digit: ");
					System.out.println(iCharNumberCand);
					if(Character.isDigit(iCharNumberCand))
					{
						System.out.println("Its an INT");
						numberS = Character.getNumericValue(iCharNumberCand);
						i++;
					}
				}
				// elementSym //numberS
				elementArrayList.add(new unitElement(elementSym,numberS));
			}
		}
		
	}
	*/
	
	
		// testing double entries of elements in formula
		if(origFormula.equals(""))
		{
			origFormula = formulaText;
		}
		
		while(formulaText.indexOf('.')>=0)
		{
			//numberS to be replaced with numberCrystal
			int numberCrystal = 1;
			int iStart = formulaText.indexOf('.');
			int dotLocation = iStart;
			
			boolean foundSubscript = false;
			String numberSString = "";
			while((iStart+1)<formulaText.length())
			{
				char iCharNumberCand = formulaText.charAt(iStart+1);
				if(Character.isDigit(iCharNumberCand))
				{
					foundSubscript = true;
					numberSString = numberSString + String.valueOf(iCharNumberCand);
					iStart++;
				}
				else
				{
					break;
				}
			}
			if (foundSubscript)
			{
				numberCrystal = Integer.parseInt(numberSString);
			}
			
			
			String crystalFormula = formulaText.substring((dotLocation+1),formulaText.length());
			formulaText = formulaText.substring(0,(dotLocation));
			
			
			this.readTextToFormula(crystalFormula,(numberCrystal*formulaSubscript));
		}
		
		while((formulaText.indexOf('(')>=0)&(formulaText.indexOf(')')>=0))
		{
			
			int iStart = formulaText.indexOf('(');
			int iEnd = 0;
			
			int openI = 0;
			int closeI = 0;
			for(int i = (iStart+1);i<formulaText.length();i++)
			{
				if(formulaText.charAt(i)==')'&(openI==closeI))
				{
					iEnd = i;
					break;
				}
				if(formulaText.charAt(i)==')')
					closeI++;
				if(formulaText.charAt(i)=='(')
					openI++;
			}
			
			int i = iEnd;
			boolean foundSubscript = false;
			String numberSString = "";
			int subFormulaNum = 1;
			char[] subCharList= formulaText.toCharArray();
			while((i+1)<subCharList.length)
			{
				char iCharNumberCand = subCharList[i+1];
				if(Character.isDigit(iCharNumberCand))
				{
					foundSubscript = true;
					numberSString = numberSString + String.valueOf(iCharNumberCand);
					i++;
				}
				else
				{
					break;
				}
			}
			if (foundSubscript)
			{
				subFormulaNum = Integer.parseInt(numberSString);
			}
			
			String beg = formulaText.substring(0,(iStart));
			String mid = formulaText.substring((iStart+1),iEnd);
			String end = formulaText.substring((i+1),(formulaText.length()));
			
			this.readTextToFormula(mid,(subFormulaNum*formulaSubscript));
			
			formulaText=beg+end;
		}
		
		char[] formulaCharList= formulaText.toCharArray();
		
		
		for(int i=0;i<formulaCharList.length;i++)
		{
			String elementSym="";
			int numberS=1;
			char iChar=formulaCharList[i];
			if(Character.isLetter(iChar)&Character.isUpperCase(iChar))
			{
				if((i+1)<formulaCharList.length)
				{
					char iCharPlus = formulaCharList[i+1];
					if(Character.isLetter(iCharPlus)&Character.isLowerCase(iCharPlus))
					{
					elementSym = String.valueOf(iChar)+String.valueOf(iCharPlus);
					i++;
					}
					else
					{
					elementSym = String.valueOf(iChar);
					}
				}
				else
				{
					elementSym = String.valueOf(iChar);
				}
				
				boolean foundSubscript = false;
				String numberSString = "";
				while((i+1)<formulaCharList.length)
				{
					char iCharNumberCand = formulaCharList[i+1];
					if(Character.isDigit(iCharNumberCand))
					{
						foundSubscript = true;
						numberSString = numberSString + String.valueOf(iCharNumberCand);
						//numberS = Character.getNumericValue(iCharNumberCand);
						i++;
					}
					else
					{
						break;
					}
				}
				if (foundSubscript)
				{
					numberS = Integer.parseInt(numberSString);
				}

				unitElement u = new unitElement(elementSym,(numberS*formulaSubscript));
				this.addToFormula(u);
			}
		}
		
	}
	
	void printFormula()
	{
		
		int listSize=elementArrayList.size();
		
		StringBuilder builder = new StringBuilder();
		
		for(int i=0;i<listSize;i++)
		{
			builder.append(elementArrayList.get(i).getElementSym());
			if(elementArrayList.get(i).getNumberS()!=1)
			{
				builder.append(elementArrayList.get(i).getNumberS());
			}
			if(i!=(listSize-1))
			{
				builder.append("|");
			}
		}
		String result = builder.toString();
		elementStringOfFormula = result;
	}
	
	
	void addToFormula(unitElement elementToAdd)
	{
		if(elementArrayList.contains(elementToAdd))
		{
			String elementSym = elementToAdd.getElementSym();
			int numberS = elementToAdd.getNumberS();
			int elementLocation = elementArrayList.indexOf(elementToAdd);
			numberS = elementArrayList.get(elementLocation).getNumberS() + numberS;
			elementArrayList.set(elementLocation ,new unitElement(elementSym,numberS));
		}
		else
		{
			elementArrayList.add(elementToAdd);
		}
	}
	
	void setFormulaCoef(int n)
	{
		formulaCoef=n;
	}
	
	int getFormulaCoef()
	{
		return formulaCoef;
	}
	
	String getOrigFormula()
	{
		return origFormula;
	}
	
	int getSize()
	{
		int i = elementArrayList.size();
		return i;
	}
	
	unitElement getUnitElement(int i)
	{
		return elementArrayList.get(i);
	}
	
	boolean formulaContains(unitElement elementToCheck)
	{
		return elementArrayList.contains(elementToCheck);
	}
	
	int indexOfElement(unitElement elementToFind)
	{
		return elementArrayList.indexOf(elementToFind);
	}
	
	int getElementNumberS(int index)
	{
		return elementArrayList.get(index).getNumberS();
	}
	
	String getElementStringOfFormula()
	{
		return elementStringOfFormula;
	}
}