package com.vedayaj.parser.types;

import java.util.List;

import org.w3c.dom.Element;

import com.vedayaj.parser.AbstractParser;
import com.vedayaj.parser.ParseConstants;
import com.vedayaj.parser.ParseTree;
import com.vedayaj.parser.Parser;
import com.vedayaj.parser.ParserFactory;
import com.vedayaj.parser.buffer.ByteBufferWrapper;
import com.vedayaj.parser.buffer.exception.InvalidConfigurationException;
import com.vedayaj.parser.buffer.exception.ParseException;

public class ContentParser extends AbstractParser
{
	private String name;
	
	private String contentType;
	
	private boolean generate;
	
	private List<Transform> transformList;
	
	private String delimiter;
	
	private byte[] content;
	
	@Override
	public void initialize(ParseTree tree, Parser parent) throws InvalidConfigurationException
	{
		super.initialize(tree, parent);
		
		Element docElement = tree.getDocumentElement();
		
		contentType = docElement.getAttribute(ParseConstants.TYPE);
		
		name = docElement.getAttribute(ParseConstants.NAME);
		
		generate = docElement.getAttribute(ParseConstants.GENERATE).equalsIgnoreCase("TRUE");
	}

	@Override
	public void parse(ByteBufferWrapper buffer) throws ParseException
	{
		if(useLength)
			content = buffer.getBytesByLength(length);
		else
		{
			content = buffer.getBytesByTerminator(delimiter.getBytes());
			
			//have we found the last token?? then get the remaining.
			if(content == null)
				content = buffer.getRemaining();
		}
	}
	
	public void setDelimiter(String d)
	{
		this.delimiter = d;
	}
	
	protected void initializeChildren(ParseTree tree)
			throws InvalidConfigurationException
	{
		//These are Transforms. 
		List<ParseTree> children = tree.getChildren();
	
		for(ParseTree child : children)
		{
			if(this.contentType == "int")
			{
				Transform<Integer> t = new Transform<Integer>(tree);
				transformList.add(t);
			}
			else if(this.contentType == "string")
			{
				Transform<String> t = new Transform<String>(tree);
				transformList.add(t);
			}
		}
	}

	public byte[] getContent()
	{
		return content;
	}
	
	public String getName()
	{
		return name;
	}
}
