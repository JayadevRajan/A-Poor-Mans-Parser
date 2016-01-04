package com.vedayaj.parser.extractor;

import java.util.HashMap;
import java.util.Map;

public class Record
{
	private Map<String, String> recordValue = new HashMap<String, String>();
	
	public void add(String name, String val)
	{
		recordValue.put(name, val);
	}
	
	public void print()
	{
		System.out.println("{");
		for(String recName : recordValue.keySet())
		{
			System.out.print("\t");
			System.out.print(recName);
			System.out.print(" : ");
			System.out.print(recordValue.get(recName));
			System.out.println(",");
		}
		System.out.println("}");
	}
}
