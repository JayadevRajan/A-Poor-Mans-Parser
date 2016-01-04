package com.vedayaj.parser.extractor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecordExtractor
{
	private Map<String, List<Record>> recordsByType;
	
	private static RecordExtractor INSTANCE;
	private RecordExtractor()
	{
		recordsByType = new HashMap<>();
	}
	
	public static RecordExtractor getRecordExtractor()
	{
		if(INSTANCE == null)
			INSTANCE = new RecordExtractor();
		return INSTANCE;
	}
	
	public void addRecord(String name, Record record)
	{
		List<Record> recordList = recordsByType.get(name);
		if(recordList == null)
		{
			recordList = new ArrayList<>();
			recordsByType.put(name, recordList);
		}
		recordList.add(record);
	}
	
	public void print()
	{
		for(String recordName : recordsByType.keySet())
		{
			List<Record> recordList = recordsByType.get(recordName);
			System.out.println("****************"+ recordName +"*****************");
			for(Record record : recordList)
			{
				record.print();
			}
			System.out.println("***********************************************");
		}
	}
}
