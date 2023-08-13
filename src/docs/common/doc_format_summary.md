# Venus Guidelines : Doc Format Summary 

[index](../../../README.md)

## Version : 1 (2023-08-13)

## Description

This page is an entry point for the Venus Document Format.

When writing a sample Venus Document, it is possible to draw from some online resources :
1. [Venus DOC XSD](https://www.fugerit.org/data/java/doc/xsd/doc-2-0.xsd)
2. [Venus DOC Reference](https://venusdocs.fugerit.org/fj-doc-base/src/main/docs/doc_xsd_config_ref.html) 
3. [The Venus Guides](https://venusguides.fugerit.org/) (this page of part of it)

These resources should help you in writing the xml source document which can be used as input to Fugerit Doc Venus to output various formats.

Here is a very simple example of a xml document compliant with the Venus DOC XSD : 

```
<?xml version="1.0" encoding="utf-8"?>
<doc
	xmlns="http://javacoredoc.fugerit.org"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://javacoredoc.fugerit.org https://www.fugerit.org/data/java/doc/xsd/doc-2-0.xsd" > 

  <metadata>
	<!-- Margin for document : left;right;top;bottom -->
	<info name="margins">10;10;10;30</info>  
	<!-- documenta meta information -->
	<info name="doc-title">Hello World</info>
	<info name="doc-author">fugerit79</info>
	<info name="doc-language">en</info>
  </metadata>
  <body>
	<para>Hello World!</para>
  </body>

</doc>
```
