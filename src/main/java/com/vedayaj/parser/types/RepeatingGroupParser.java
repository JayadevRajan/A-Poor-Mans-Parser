package com.vedayaj.parser.types;

import com.vedayaj.parser.AbstractParser;
import com.vedayaj.parser.ParseTree;
import com.vedayaj.parser.Parser;
import com.vedayaj.parser.buffer.ByteBufferWrapper;
import com.vedayaj.parser.buffer.exception.InvalidConfigurationException;
import com.vedayaj.parser.buffer.exception.ParseException;

public class RepeatingGroupParser extends AbstractParser
{

	@Override
	public void initialize(ParseTree tree, Parser parent) throws InvalidConfigurationException
	{
		super.initialize(tree, parent);
	}

	@Override
	public void parse(ByteBufferWrapper buffer) throws ParseException
	{
		while(buffer.hasRemaining())
		{
			for(Parser child : subParsers)
			{
				child.parse(buffer);
			}
		}
	}

}
