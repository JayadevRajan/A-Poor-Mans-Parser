# A-Poor-Mans-Parser

An attempt to write a simple parser, yet powerful enough to extract data out of Unstructured Data. 

Lets go through an example to understand better. Consider parsing medical data like this
OBX|1|ST|84295^Na||150|mmol/l|136-148|Above high normal|||Final results
OBX|2|ST|84132^K+||4.5|mmol/l|3.5-5|Normal|||Final results
...

So we see a row pattern separated by lines and columns separated by delimiter '|'
So its parser equivalent is 

<Parser type="Simple">
	<!-- Group that occurs one time. If this pattern repeats, wrap it under RepeatingGroup than Group.-->
	<!-- Delimited Repeating group of records. Should declare all fields irrespective of whether its taken. This will make the parser simple to understand-->
	<RepeatingGroup>
		<Record type="delimited" delimiter="|" terminator="crlf" name="tests">
			<Content name="type" type="string" generate="false">
			</Content>
			<Content name="serialNo" type="string" generate="false">
			</Content>
			<Content name="testRef" type="string" generate="true">
			</Content>
			<Content name="test" type="string" generate="true">
      ...
    </Record>
	</RepeatingGroup>

</Parser>
...

What if there is no delimiter and is each token is identified by position. Consider this record. It has patient Id, name, sex etc. The second header in contrast is sperated by delimiter '|' 
PATID1234 M11 Jones^William                      19610613 M
OBR||||80004^Electrolytes

Parser equivalent is 
<Parser type="Simple">
	<!-- Group that occurs one time. If this pattern repeats, wrap it under RepeatingGroup than Group.-->
	<Group>
		<!-- Positional Header text. Positional should ensure that it consumes the carriage return at the end of the line-->
		<!-- generate="true" means it will be part of output port.-->
		<Record type="positional" length="61" name="patientInfo">
			<Content name="patientId" type="string" length="10" generate="true">
				<Transform function="trim()"/>
			</Content>
			<Content name="wardNo" type="string" length="4" generate="false">
			</Content>
			<Content name="patientName" type="string" length="35">
				<Transform function="trim()"/>
				<Transform function="replaceChar('^',' ')"/>
			</Content>
			<Content name="refNo" type="string" length="9" generate="false">
			</Content>
			<Content name="sex" type="string" length="1" generate="true">
			</Content>
			<Content name="carriagereturn" type="string" length="2" generate="false">
			</Content>
		</Record>
    <!-- Delimited Header. -->
		<Record type="delimited" delimiter="|" terminator="crlf" name="additionalInfo">
			<Content name="type" type="string" generate="false">
			</Content>
			<Content name="serialNo" type="string" generate="false">
			</Content>
			<Content name="testRef" type="string" generate="false">
			</Content>
			<Content name="testRef1" type="string" generate="false">
			</Content>
			<Content name="test" type="string" generate="true">
			</Content>
		</Record>
		
Notice the use of Group and Repeating Group. 
Group is for content that does not repeat. Repeating group is for one that repeats. 

Full Examples are found at ParseTreeTest.java & RootParserTest.java

