package com.vedayaj.parser.types;

import org.w3c.dom.Element;

import com.vedayaj.parser.ParseConstants;
import com.vedayaj.parser.ParseTree;

public class Transform<T>
{
	private String functionSignature;
	
	T value;
	
	public Transform(ParseTree tree)
	{
		Element element = tree.getDocumentElement();
		functionSignature = element.getAttribute(ParseConstants.FUNCTION);
	}
	
	public void setValue(T val)
	{
		value = val;
	}
}
