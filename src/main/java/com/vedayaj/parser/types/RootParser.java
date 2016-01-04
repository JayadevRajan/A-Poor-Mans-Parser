package com.vedayaj.parser.types;

import com.vedayaj.parser.AbstractParser;
import com.vedayaj.parser.Parser;
import com.vedayaj.parser.buffer.ByteBufferWrapper;
import com.vedayaj.parser.buffer.exception.ParseException;

public class RootParser extends AbstractParser
{
	@Override
	public void parse(ByteBufferWrapper buffer) throws ParseException
	{
		for(Parser subParser : subParsers)
		{
			subParser.parse(buffer);
		}
	}
}
