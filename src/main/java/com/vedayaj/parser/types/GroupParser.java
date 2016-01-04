package com.vedayaj.parser.types;

import com.vedayaj.parser.AbstractParser;
import com.vedayaj.parser.ParseTree;
import com.vedayaj.parser.Parser;
import com.vedayaj.parser.buffer.ByteBufferWrapper;
import com.vedayaj.parser.buffer.exception.InvalidConfigurationException;
import com.vedayaj.parser.buffer.exception.ParseException;

public class GroupParser extends AbstractParser
{
	@Override
	public void initialize(ParseTree tree, Parser parent) throws InvalidConfigurationException
	{
		//nothing to do here. Group can have a name?
		super.initialize(tree, parent);
	}

	@Override
	public void parse(ByteBufferWrapper buffer) throws ParseException
	{
		for(Parser subParser : subParsers)
		{
			subParser.parse(buffer);
		}
	}

}
