
import java.util.ArrayList;
import java.lang.StringBuilder;

class chemEquation
{
	private String origEquation;
	private ArrayList<chemFormula> reactantFormulas;
	private ArrayList<chemFormula> productFormulas;
	private chemFormula masterElementList;
	
	public static void main(String args[])
	{
		String chemEquationString = "K4Fe(CN)6 + KMnO4 + H2SO4->KHSO4+Fe2(SO4)3+MnSO4+HNO3+CO2+H2O";
		
		chemEquation myChemEquation = new chemEquation();
		myChemEquation.readEquation(chemEquationString);
		myChemEquation.buildMasterList();
		//masterElementList.printFormula();
		//System.out.println(this.checkMasterList());
		myChemEquation.balanceEquation();
		System.out.println(myChemEquation.getReadEquation());
		System.out.println(myChemEquation.getBalancedEquation());
		System.out.println(myChemEquation.checkIfBalanced());

		//printFormula
	}
	
	chemEquation()
	{
		origEquation = "";
		reactantFormulas = new ArrayList<chemFormula>();
		productFormulas = new ArrayList<chemFormula>();
		masterElementList = new chemFormula();
	}
	
	
	
	public void readEquation(String chemEquationString)
	{
		origEquation = chemEquationString;
		int iStart = chemEquationString.indexOf('-');
		int iEnd = chemEquationString.indexOf('>');
		
		String reactants = chemEquationString.substring(0,(iStart));
		String products = chemEquationString.substring((iEnd+1),(chemEquationString.length()));
		reactants = reactants.trim();
		products = products.trim();
		
		
		//System.out.println(reactants);
		//System.out.println(products);
		
		
		
		while((reactants.indexOf('+')>=0))
		{
			int plusLocation = reactants.indexOf('+');
			String nextFormula = reactants.substring(0,(plusLocation));
			nextFormula = nextFormula.trim();
			reactants = reactants.substring((plusLocation+1),(reactants.length()));
			
			chemFormula myChemFormula = new chemFormula();

			myChemFormula.readTextToFormula(nextFormula);
			myChemFormula.printFormula();
			reactantFormulas.add(myChemFormula);
		}
		
		{
			reactants = reactants.trim();
			chemFormula myChemFormula = new chemFormula();

			myChemFormula.readTextToFormula(reactants);
			myChemFormula.printFormula();
			reactantFormulas.add(myChemFormula);
		}
		
		while((products.indexOf('+')>=0))
		{
			int plusLocation = products.indexOf('+');
			String nextFormula = products.substring(0,(plusLocation));
			nextFormula = nextFormula.trim();
			products = products.substring((plusLocation+1),(products.length()));
			
			chemFormula myChemFormula = new chemFormula();

			myChemFormula.readTextToFormula(nextFormula);
			myChemFormula.printFormula();
			productFormulas.add(myChemFormula);
		}
		
		{
			products = products.trim();
			chemFormula myChemFormula = new chemFormula();
			
			myChemFormula.readTextToFormula(products);
			myChemFormula.printFormula();
			productFormulas.add(myChemFormula);
		}
		

	}
	
	private void buildMasterList()
	{
		for(int i = 0; i<reactantFormulas.size(); i++)
		{
			for(int j = 0; j<reactantFormulas.get(i).getSize(); j++)
			{
				String currElementSym = reactantFormulas.get(i).getUnitElement(j).getElementSym();
				unitElement u = new unitElement(currElementSym,1);
				masterElementList.addToFormula(u);
				//System.out.println(currElementSym);
			}
		}
		
		for(int i = 0; i<productFormulas.size(); i++)
		{
			for(int j = 0; j<productFormulas.get(i).getSize(); j++)
			{
				String currElementSym = productFormulas.get(i).getUnitElement(j).getElementSym();
				unitElement u = new unitElement(currElementSym,1);
				masterElementList.addToFormula(u);
				//System.out.println(currElementSym);
			}
		}
	}
	
	boolean checkMasterList()
	{	
		for(int iMaster=0; iMaster<masterElementList.getSize(); iMaster++)
		{
			unitElement masterElement = masterElementList.getUnitElement(iMaster);
			
			boolean reactantYesNo = false;
			for(int i = 0; i<reactantFormulas.size(); i++)
			{
				if(reactantFormulas.get(i).formulaContains(masterElement))
				{
					reactantYesNo = true;
					break;
				}
			}
			
			boolean productYesNo = false;
			for(int i = 0; i<productFormulas.size(); i++)
			{
				if(productFormulas.get(i).formulaContains(masterElement))
				{
					productYesNo = true;
					break;
				}
			}
			
			if((reactantYesNo == false)||(productYesNo == false))
			{
				//System.out.println("WutOh!!!!!!!!");
				return false;
			}
		}
		
		return true;
	}
	
	boolean checkIfBalanced()
	{
		for(int iMaster=0; iMaster<masterElementList.getSize(); iMaster++)
		{
			unitElement masterElement = masterElementList.getUnitElement(iMaster);
			int reactantNumber = 0;
			int productNumber = 0;
			
			for(int i = 0; i<reactantFormulas.size(); i++)
			{
				if(reactantFormulas.get(i).formulaContains(masterElement))
				{
					int elementLocation = reactantFormulas.get(i).indexOfElement(masterElement);
					int numberOfElements = reactantFormulas.get(i).getElementNumberS(elementLocation);
					reactantNumber=reactantNumber+numberOfElements*reactantFormulas.get(i).getFormulaCoef();
				}
			}
			
			
			for(int i = 0; i<productFormulas.size(); i++)
			{
				if(productFormulas.get(i).formulaContains(masterElement))
				{
					int elementLocation = productFormulas.get(i).indexOfElement(masterElement);
					int numberOfElements = productFormulas.get(i).getElementNumberS(elementLocation);
					productNumber=productNumber+numberOfElements*productFormulas.get(i).getFormulaCoef();
				}
			}
			//System.out.print(masterElement.getElementSym()+ " ");
			//System.out.print(reactantNumber + " ");
			//System.out.println(productNumber);
			if(reactantNumber!=productNumber)
			{
				//System.out.println("WutOh!!!!!!!!");
				return false;
			}
		}
		
		return true;
	}
	
	void balanceEquation()
	{
		int dimOfVariables = reactantFormulas.size() + productFormulas.size();
		int numOfRows = masterElementList.getSize();
		int[][] A;
		A = new int[numOfRows][dimOfVariables];
		//indexOfElement
		for(int iMaster=0; iMaster<masterElementList.getSize(); iMaster++)
		{
			unitElement masterElement = masterElementList.getUnitElement(iMaster);
			
			for(int i = 0; i<reactantFormulas.size(); i++)
			{
				if(reactantFormulas.get(i).formulaContains(masterElement))
				{
					int elementLocation = reactantFormulas.get(i).indexOfElement(masterElement);
					int numberOfElements = reactantFormulas.get(i).getElementNumberS(elementLocation);
					A[iMaster][i] = numberOfElements;
				}
			}
			

			for(int i = 0; i<productFormulas.size(); i++)
			{
				if(productFormulas.get(i).formulaContains(masterElement))
				{
					int elementLocation = productFormulas.get(i).indexOfElement(masterElement);
					int numberOfElements = productFormulas.get(i).getElementNumberS(elementLocation);
					A[iMaster][reactantFormulas.size()+i] = (-1)*numberOfElements;
				}
			}
			

		}
		rowEchelon hahaha = new rowEchelon();
		//hahaha.showArray(A);
		//System.out.println();
		//hahaha.doRowEchelon(A);
		//hahaha.showArray(A);
		
		int[] solutionOutput;
		solutionOutput = hahaha.solveHomogeneous(A);
		//hahaha.showArray(solutionOutput);
		
		//Set formula Coefficients "reactantFormulas" "productFormulas"

		int d=0;
		for(int i=0;i<reactantFormulas.size();i++)
		{
			reactantFormulas.get(i).setFormulaCoef(solutionOutput[d]);

			d++;
		}
		for(int i=0;i<productFormulas.size();i++)
		{
			productFormulas.get(i).setFormulaCoef(solutionOutput[d]);
			d++;
		}
		
	}
	
	String getBalancedEquation()
	{
		//replace equationB with StringBuilder builder
		StringBuilder builder = new StringBuilder();
		
		for(int i=0;i<reactantFormulas.size();i++)
		{
			if(i!=0)
			{
				builder.append("+");
			}
			if(reactantFormulas.get(i).getFormulaCoef()!=1)
			{
				builder.append(reactantFormulas.get(i).getFormulaCoef());
			}
			builder.append(reactantFormulas.get(i).getOrigFormula());
		}
		builder.append("->");
		for(int i=0;i<productFormulas.size();i++)
		{
			if(i!=0)
			{
				builder.append("+");
			}
			if(productFormulas.get(i).getFormulaCoef()!=1)
			{
				builder.append(productFormulas.get(i).getFormulaCoef());
			}
			builder.append(productFormulas.get(i).getOrigFormula());
		}
		String result = builder.toString();
		return result;
	}
	
	String getReadEquation()
	{
		//replace equationB with StringBuilder builder
		StringBuilder builder = new StringBuilder();
		for(int i=0;i<reactantFormulas.size();i++)
		{
			if(i!=0)
			{
				builder.append("+");
			}
			builder.append(reactantFormulas.get(i).getElementStringOfFormula());
		}
		builder.append("->");
		for(int i=0;i<productFormulas.size();i++)
		{
			if(i!=0)
			{
				builder.append("+");
			}
			builder.append(productFormulas.get(i).getElementStringOfFormula());
		}
		String result = builder.toString();
		return result;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}