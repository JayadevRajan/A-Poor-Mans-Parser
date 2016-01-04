package com.vedayaj.parser.tests;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import com.vedayaj.parser.ParseTree;


public class ParseTreeTest
{

	public static void main(String[] args) throws Exception
	{

		DocumentBuilderFactory fac = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = fac.newDocumentBuilder();
		Document document = db.parse(ParseTreeTest.class.getClassLoader().getResourceAsStream("PrototypeParse.xml"));
		
		ParseTree root = ParseTree.loadParseTree(document);
		
	}

}
