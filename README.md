# library_s
Some (maybe) useful libraries 

***Sorry for the misspells and syntax errors - I'm not English. Hope that makes sense***

*NAME         --   Added on(dd/MM/yyyy)*

DataExporter  --   11/12/2019 :

    Can write custom data-saving files, just for saving basic variables and objects (Strings,Integer,Date,...),
    this files support costumization for structure's patterns and some characters.
    It can read files only if known the exact structure pattern of your file and each separator and parentheses: it's supposed that the user set the file's pattern.
		
		Every block starts with the char SOH (HEX 1) and ends with the char EOT (HEX 4). The open char is followed by two characters that 
		identifies the type of block (it represent usually the abbreviation of the java class name of the block)  and they're followed by the #
		which introduce the block user-defined name enclosed in '"'. Every variable is itroduced by a set of chars (between HEX 0 to HEX 1F) 
		of a maximum of 4 in row (and if below the last char is a NUL char (HEX 0)). 
		
		Example 1:
		
		DH#"VarName":(testo 1; 2143.56; 3575; 45.0f; true;-11072;300)
		
		> "DH" stands for DataHolder which is the java class name that read,parse and write this string
		> "VarName" (always between '"') is the name representating the following variables
		> ":" user definable character which means that data is now defining 
		
		If there are the parentheses the DataHolder object knows that it contains one or more variable otherwise only one; the variables are
		separated by ";" (in this case but is a user definable character).
		Its pattern (Java regex) is:
		
		[^]{2}#"([^]*)":\(([^]*)\)
			
		
		Example 2:
		
		CG#"GroupName"[DH#"VarNameX":(text 3)DH#"VarNameY"§(text 5)]
		
		> "CG" stands for ContainerGroup which is the java class name that read,parse and write this string
		> "GroupName" (always between '"') is the name representating the following variables sub-objects
		> "[" and "]" are the open and close characters that enclose sub-objects and they're both user definable characters
		
		In this example the group contains two sub-objects: two DataHolders, but it can contains other group which contains on their own 
		other data.
		Its pattern (Java regex) is:
		
		[^]{2}#"([^]*)"\[[^]{2}#"([^]*)":\(([^]*)\)[^]{2}#"([^]*)"§\(([^]*)\)\]
