package com.vedayaj.parser.tests;
import java.io.File;
import java.io.FileInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import com.vedayaj.parser.ParseTree;
import com.vedayaj.parser.buffer.ByteBufferWrapper;
import com.vedayaj.parser.extractor.RecordExtractor;
import com.vedayaj.parser.types.RootParser;


public class RootParserTest
{

	public static void main(String[] args) throws Exception
	{

		DocumentBuilderFactory fac = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = fac.newDocumentBuilder();
		Document document = db.parse(RootParserTest.class.getClassLoader().getResourceAsStream("PrototypeParse.xml"));
		
		ParseTree root = ParseTree.loadParseTree(document);
		
		RootParser rootParser = new RootParser();
		rootParser.initialize(root, null);
		rootParser.doPostInitialize();
		
		File inputFile = new File(RootParserTest.class.getClassLoader().getResource("SampleFile.txt").getPath());
		
		FileInputStream fis = new FileInputStream(inputFile);
		
		ByteBufferWrapper wrap = new ByteBufferWrapper( fis, (int)inputFile.length());
		
		rootParser.parse(wrap);
		
		RecordExtractor.getRecordExtractor().print();
	}

}
