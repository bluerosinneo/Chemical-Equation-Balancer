class unitElement
{
	private String elementSym;
	private int numberS;
	
	unitElement(String name, int howMany)
	{
		elementSym = name;
		numberS = howMany;
	}
	
	String getElementSym()
	{
		return elementSym;
	}
	
	int getNumberS()
	{
		return numberS;
	}
	
	void setNumberS(int subScript)
	{
		numberS = subScript;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (o == this)
		{
			return true;
		}
		
		if(!(o instanceof unitElement))
		{
			return false;
		}
		
		unitElement u = (unitElement) o;
		
		return elementSym.equals(u.elementSym);
	}
}