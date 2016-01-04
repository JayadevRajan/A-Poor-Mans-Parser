package com.vedayaj.parser;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ParseTree
{
	private List<ParseTree> children = new ArrayList<>();
	
	private String parserType;
	
	private Element element;
	
	private ParseTree parent;
	
	public ParseTree(Element element, ParseTree parent)
	{
		this.element = element;
		
		this.parserType = element.getTagName();
	
		this.parent = parent;
		
		parse(element);
	}
	
	public static ParseTree loadParseTree(Document document)
	{
		Element rootElement = document.getDocumentElement();
		
		ParseTree root = new ParseTree(rootElement, null);
		
		return root;
	}
	
	private void parse(Element element)
	{
		NodeList childNodes = element.getChildNodes();
		
		for(int i=0; i<childNodes.getLength(); i++)
		{
			Node node = childNodes.item(i);
			switch(node.getNodeType())
			{
			case Node.ELEMENT_NODE:
				ParseTree childNode = new ParseTree((Element)node, this);
				this.children.add(childNode);
				break;
			}
		}
	}
	
	public Element getDocumentElement()
	{
		return this.element;
	}
	
	public List<ParseTree> getChildren()
	{
		return children;
	}
	
	public String getParserType()
	{
		return parserType;
	}
}
