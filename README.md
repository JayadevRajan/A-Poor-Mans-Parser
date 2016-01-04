# A-Poor-Mans-Parser

An attempt to write a simple parser, yet powerful enough to extract data out of Unstructured Data. 

Lets go through an example to understand better. Consider parsing medical data like this
```OBX|1|ST|84295^Na||150|mmol/l|136-148|Above high normal|||Final results
OBX|2|ST|84132^K+||4.5|mmol/l|3.5-5|Normal|||Final results
...```

So we see a row pattern separated by lines and columns separated by delimiter '|'
So its parser equivalent is 

<pre>
&lt;Parser type="Simple"&gt;
	&lt;!-- Group that occurs one time. If this pattern repeats, wrap it under RepeatingGroup than Group.--&gt;
	&lt;!-- Delimited Repeating group of records. Should declare all fields irrespective of whether its taken. This will make the parser simple to understand--&gt;
	&lt;RepeatingGroup&gt;
		&lt;Record type="delimited" delimiter="|" terminator="crlf" name="tests"&gt;
			&lt;Content name="type" type="string" generate="false"&gt;
			&lt;/Content&gt;
			&lt;Content name="serialNo" type="string" generate="false"&gt;
			&lt;/Content&gt;
			&lt;Content name="testRef" type="string" generate="true"&gt;
			&lt;/Content&gt;
			&lt;Content name="test" type="string" generate="true"&gt;
      ...
    &lt;/Record&gt;
	&lt;/RepeatingGroup&gt;

&lt;/Parser&gt;
</pre>


What if there is no delimiter and is each token is identified by position. Consider this record. It has patient Id, name, sex etc. The second header in contrast is sperated by delimiter '|' 
```PATID1234 M11 Jones^William                      19610613 M
OBR||||80004^Electrolytes```

Parser equivalent is 
<pre>
&lt;Parser type="Simple"&gt;
	&lt;!-- Group that occurs one time. If this pattern repeats, wrap it under RepeatingGroup than Group.--&gt;
	&lt;Group&gt;
		&lt;!-- Positional Header text. Positional should ensure that it consumes the carriage return at the end of the line--&gt;
		&lt;!-- generate="true" means it will be part of output port.--&gt;
		&lt;Record type="positional" length="61" name="patientInfo"&gt;
			&lt;Content name="patientId" type="string" length="10" generate="true"&gt;
				&lt;Transform function="trim()"/&gt;
			&lt;/Content&gt;
			&lt;Content name="wardNo" type="string" length="4" generate="false"&gt;
			&lt;/Content&gt;
			&lt;Content name="patientName" type="string" length="35"&gt;
				&lt;Transform function="trim()"/&gt;
				&lt;Transform function="replaceChar('^',' ')"/&gt;
			&lt;/Content&gt;
			&lt;Content name="refNo" type="string" length="9" generate="false"&gt;
			&lt;/Content&gt;
			&lt;Content name="sex" type="string" length="1" generate="true"&gt;
			&lt;/Content&gt;
			&lt;Content name="carriagereturn" type="string" length="2" generate="false"&gt;
			&lt;/Content&gt;
		&lt;/Record&gt;
    &lt;!-- Delimited Header. --&gt;
		&lt;Record type="delimited" delimiter="|" terminator="crlf" name="additionalInfo"&gt;
			&lt;Content name="type" type="string" generate="false"&gt;
			&lt;/Content&gt;
			&lt;Content name="serialNo" type="string" generate="false"&gt;
			&lt;/Content&gt;
			&lt;Content name="testRef" type="string" generate="false"&gt;
			&lt;/Content&gt;
			&lt;Content name="testRef1" type="string" generate="false"&gt;
			&lt;/Content&gt;
			&lt;Content name="test" type="string" generate="true"&gt;
			&lt;/Content&gt;
		&lt;/Record&gt;
</pre>	
Notice the use of Group and Repeating Group. 
Group is for content that does not repeat. Repeating group is for one that repeats. 

Full Examples are found at ParseTreeTest.java & RootParserTest.java

