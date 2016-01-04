package com.vedayaj.parser;

import com.vedayaj.parser.types.ContentParser;
import com.vedayaj.parser.types.GroupParser;
import com.vedayaj.parser.types.RecordParser;
import com.vedayaj.parser.types.RepeatingGroupParser;

public class ParserFactory
{
	private static final String GROUP = "Group";
	private static final String RECORD = "Record";
	private static final String CONTENT = "Content";
	private static final String TRANSFORM = "Transform";
	private static final String REPEATING_GROUP = "RepeatingGroup";
	
	public static Parser getParser(String parserType)
	{
		switch(parserType)
		{
		case GROUP:
			return new GroupParser();
		case RECORD:
			return new RecordParser();
		case CONTENT:
			return new ContentParser();
		case REPEATING_GROUP:
			return new RepeatingGroupParser();
		default:
			return null;
		}
	}
}
