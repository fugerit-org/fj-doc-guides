<#ftl output_format="XML"><#-- Set the output format for XML, not mandatory but reccomended, otherwise escaping should be handled in a cusomt way, for example through CDATA sections -->
<?xml version="1.0" encoding="utf-8"?>
<doc
	xmlns="http://javacoredoc.fugerit.org"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://javacoredoc.fugerit.org https://www.fugerit.org/data/java/doc/xsd/doc-2-1.xsd" > 

  <!--
  	This is a Venus Fugerit Doc (https://github.com/fugerit-org/fj-doc) XML Source Document.
  	For documentation on how to write a valid Venus Doc XML Meta Model refer to : 
  	https://venusguides.fugerit.org/src/docs/common/doc_format_summary.html
  -->

  <metadata>
	<!-- Margin for document : left;right;top;bottom -->
	<info name="margins">10;10;10;30</info>  
	<!-- documenta meta information -->
	<info name="doc-title">${docTitle}</info>
	<info name="doc-author">fugerit79</info>
	<info name="doc-language">en</info>
  </metadata>
  
  <body>
	<h head-level="1" id="top" size="16">${docTitle}</h>
  </body>

</doc>
