# Venus Guidelines : Doc Format and Apache FreeMarker guidelines

## Deprecation notice start

This guide has been deprecated and will not be maintained, now you can refer to the new official guide 
(built it in the main [Fugerit Venus Doc repository](https://github.com/fugerit-org/fj-doc)) :

[![HTML - Guide](https://img.shields.io/badge/HTML-Guide-blue?style=for-the-badge)](https://venusdocs.fugerit.org/guide/ "Go to project HTML documentation")
[![PDF - Guide](https://img.shields.io/badge/PDF-Guide-red?style=for-the-badge)](https://venusdocs.fugerit.org/guide/fj-doc-guide.pdf "Go to project PDF documentation")

Especially the [Dynamic Data : FreeMarker](https://venusdocs.fugerit.org/guide/#doc-freemarker-entry-point).

## Deprecation notice end

[Venus Guides index](../../../README.md)

## Version : 1 (2023-10-14)

## Introduction

This page contains guidelines for using the Venus DOC XML format with [Apache FreeMarker](https://freemarker.apache.org/).

Some examples are available, for instance : 

- [Venus Guide A004 : Apache FreeMarker Hello World](../../../fj-doc-guides-A004-freemarker-hello-world/README.md)
- [Venus Guide A003 : Apache FreeMarker Full document](../../../fj-doc-guides-A003-full-document-freemarker/README.md)

The step for generating documents with Fugerit Venus Doc Format and Apache FreeMarker are : 

## Usage sample

### 1 - create a FreemarkerDocProcessConfig configuration

For instance the most simple configuration is : 

```
	// in the simple config, there is a default method mapping any chainId to a template with the path ${chainId}.ftl (and will map all doc context attributes to FreeMarker)
	private static final FreemarkerDocProcessConfig SIMPLE_CONFIG = 
			// SafeFunction.get() will execute the configuration code and re-throw a ConfigRuntimeException if any exception is thrown
			SafeFunction.get( () -> FreemarkerDocProcessConfigFacade.newSimpleConfig( "A004-sample-config" , "/fj-freemarker-sample-template/" ) );
			
	private static final String CHAIN_ID = "hello-world";	// it will map to /fj-freemarker-sample-template/hello-world.ftl
			
```

### 2 - create a Apache FreeMarker template

For instance the most simple configuration is could be a file in /fj-freemarker-sample-template/hello-world.ftl

```
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
```

Notes : 

- Use `<#ftl output_format="XML">` to handle escape of XML format (see [FreeMarker output_format directive](https://freemarker.apache.org/docs/ref_directive_outputformat.html) for further info))
- Use .ftl extension for Apache FreeMarker templates (which is the [official extension](https://freemarker.apache.org/docs/versions_2_1_3.html))
- Refer to [Apache FreeMarker manual](https://freemarker.apache.org/docs/index.html) for information on what it is possible in the ftl template.

### 3 - generate the document with a type handler

```	
	DocTypeHandler handler = PdfFopTypeHandler.HANDLER;	// the handler for desired output, in this case PDF
	String docTitle = "Hello World with Apache FreeMarker Template!";
	File outputFile = new File(  "target", "full-document."+handler.getType() ); // output file
	log.info( "outputFile : {}", outputFile );
	try ( OutputStream os = new FileOutputStream( outputFile ) ) {
		DocProcessContext context = DocProcessContext.newContext( "docTitle", docTitle );
		SIMPLE_CONFIG.fullProcess( CHAIN_ID, context, handler, DocOutput.newOutput(os) );
	}
	
```

Notes : 

- Use attributes like `docTitle` to pass information to the template


## Best practices on Apache FreeMarker usage with Venus DOC Format

### 1 - Escaping XML

As noted above, when using Apache FreeMarker to render Venus DOC Format XML, xml characters should be escaped.

The recommended way to do so is through the [FreeMarker directive](https://freemarker.apache.org/docs/ref_directive_outputformat.html) : `<#ftl output_format="XML">`  
For auto escape behavior see [Auto-escaping and output formats](https://freemarker.apache.org/docs/dgui_misc_autoescaping.html)

Albeit not recommended, it is possible to use a CDATA section : `<![CDATA[${docTitle}]]>`

Or a custom macro : `<@escapeXml text=docTitle/>`

### 2 - Add functions for more customized code

If the normal features ([build in](https://freemarker.apache.org/docs/ref_builtins.html), [macros](https://freemarker.apache.org/docs/ref_directive_macro.html)) in FreeMarker are not enough, it is possible to add [custom functions](https://freemarker.apache.org/docs/pgui_datamodel_method.html) to your template.

Look for instance at [Fugerit Venus ImageBase64CLFun](https://github.com/fugerit-org/fj-doc/blob/main/fj-doc-freemarker/src/main/java/org/fugerit/java/doc/freemarker/fun/ImageBase64CLFun.java)

