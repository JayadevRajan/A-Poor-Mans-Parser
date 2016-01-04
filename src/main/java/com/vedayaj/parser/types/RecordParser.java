package com.vedayaj.parser.types;

import org.w3c.dom.Element;

import com.vedayaj.parser.AbstractParser;
import com.vedayaj.parser.ParseConstants;
import com.vedayaj.parser.ParseTree;
import com.vedayaj.parser.Parser;
import com.vedayaj.parser.buffer.ByteBufferWrapper;
import com.vedayaj.parser.buffer.exception.InvalidConfigurationException;
import com.vedayaj.parser.buffer.exception.ParseException;
import com.vedayaj.parser.extractor.Record;
import com.vedayaj.parser.extractor.RecordExtractor;

public class RecordParser extends AbstractParser
{
	private String recordtype;
	
	private String delimiter;
	
	private String recordName;
	
	@Override
	public void initialize(ParseTree tree, Parser parent) throws InvalidConfigurationException
	{
		super.initialize(tree, parent);
		
		Element docElement = tree.getDocumentElement();
		recordtype = docElement.getAttribute(ParseConstants.TYPE);
		
		//Positional length is taken care of in Abstract impl. 
		//here handle the delimiter
		if(recordtype.equals(ParseConstants.DELIMITED))
		{
			delimiter = docElement.getAttribute(ParseConstants.DELIMITER);
		}
		
		recordName = docElement.getAttribute(ParseConstants.NAME);
	}

	@Override
	public void doPostInitialize()
	{	
		super.doPostInitialize();
		for(Parser child : subParsers)
		{
			if(child instanceof ContentParser)
			{
				((ContentParser) child).setDelimiter(delimiter);
			}
		}
	}

	@Override
	public void parse(ByteBufferWrapper buffer) throws ParseException
	{
		byte[] thisRecord = null;
		if(useTerminator)
		{
			thisRecord = buffer.getBytesByTerminator(terminator.getBytes());
			if(thisRecord == null)
			{
				thisRecord = buffer.getRemaining();
			}
		}
		else if(useLength)
			thisRecord = buffer.getBytesByLength(length);
		
		//Copy buffer and pass it to children
		ByteBufferWrapper subWrapper = ByteBufferWrapper.makeCopy(thisRecord);
		
		for(Parser parser : subParsers)
		{
			parser.parse(subWrapper);
		}
		
		extractToRecord();
	}
	
	public void extractToRecord()
	{
		Record record = new Record();
		
		for(Parser child : subParsers)
		{
			if(child instanceof ContentParser)
			{
				byte[] content = ((ContentParser) child).getContent();
				record.add(((ContentParser) child).getName(), new String(content));
			}
		}
		
		RecordExtractor.getRecordExtractor().addRecord(recordName, record);
	}
}
