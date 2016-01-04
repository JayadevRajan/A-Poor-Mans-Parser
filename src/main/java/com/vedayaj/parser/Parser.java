package com.vedayaj.parser;

import com.vedayaj.parser.buffer.ByteBufferWrapper;
import com.vedayaj.parser.buffer.exception.InvalidConfigurationException;
import com.vedayaj.parser.buffer.exception.ParseException;

public interface Parser
{
	public void initialize(ParseTree tree, Parser parent) throws InvalidConfigurationException; 
	
	public void parse(ByteBufferWrapper buffer) throws ParseException;
	
	public void doPostInitialize();
}
