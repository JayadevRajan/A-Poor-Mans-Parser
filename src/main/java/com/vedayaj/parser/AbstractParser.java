package com.vedayaj.parser;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;

import com.vedayaj.parser.buffer.ByteBufferWrapper;
import com.vedayaj.parser.buffer.exception.InvalidConfigurationException;

public abstract class AbstractParser implements Parser
{
	
	protected Parser parentParser;
	
	protected boolean useLength;
	protected boolean useTerminator;
	
	protected int length; 
	protected String terminator;
	
	protected List<Parser> subParsers = new ArrayList<>();
	
	@Override
	public void initialize(ParseTree tree, Parser parent) throws InvalidConfigurationException
	{
		Element element = tree.getDocumentElement();
		
		String textLen = element.getAttribute(ParseConstants.LENGTH);
		if(textLen != null && !textLen.equals(""))
		{
			length = Integer.parseInt(textLen);
			useLength = true;
		}
		
		String term = element.getAttribute(ParseConstants.TERMINATOR);
		if(term != null && !term.equals(""))
		{
			terminator = normalizeTerminator(term);
			useTerminator = true;
		}
		
		if(useLength && useTerminator)
			throw new InvalidConfigurationException();
		
		initializeChildren(tree);
		
	}

	public void setParent(Parser p)
	{
		this.parentParser = p;
	}
	
	protected void initializeChildren(ParseTree tree)
			throws InvalidConfigurationException
	{
		List<ParseTree> children = tree.getChildren();
	
		for(ParseTree child : children)
		{
			Parser childParser = ParserFactory.getParser(child.getParserType());
			childParser.initialize(child, this);
			
			subParsers.add(childParser);
		}
	}
	
	@Override
	public void doPostInitialize()
	{
		for(Parser subParser : subParsers)
		{
			subParser.doPostInitialize();
		}
	}
	
	private String normalizeTerminator(String term)
	{
		if(term.equals(ParseConstants.CRLF))
			return "\r\n";
		else if (term.equals(ParseConstants.LF))
			return "\n";
		else if(term.equals(ParseConstants.CR))
			return "\r";
		else 
			return term;
	}
}
