# Venus Guide A001 : Hello World PDF 

[Index](../README.md)

*Abstract* :  
Simple guide to create a xml source file to generate a PDF.

*Author* : Matteo Franci a.k.a. Fugerit (fugerit79)

*Version* : 001 (2023-07-31)

*Quickstart* :  
Run the junit : [src/test/java/test/org/fugerit/java/doc/guides/helloworld/pdf/TestHelloWorldPdf.java](src/test/java/test/org/fugerit/java/doc/guides/helloworld/pdf/TestHelloWorldPdf.java)


*Guide* :  

## 1. pom setup

For dependency handling we set the parent pom to [fj-doc](https://github.com/fugerit-org/fj-doc)

```
	<parent>
		<groupId>org.fugerit.java</groupId>
		<artifactId>fj-doc</artifactId>
		<version>${fj-doc-version}</version>
		<relativePath></relativePath>
	</parent> 
```

Minimum java 11 version (most modules works with java 8 too)

```
	<properties>
		<maven.compiler.release>11</maven.compiler.release>
		<fj-doc-version>${project.parent.version}</fj-doc-version>
	</properties>
```

And the module for rendering PDF through [FOP](https://github.com/fugerit-org/fj-doc/tree/main/fj-doc-bom-fop)

```
		<dependency>
			<groupId>org.fugerit.java</groupId>
			<artifactId>fj-doc-mod-fop</artifactId>
		</dependency>	
```

## 2. Create the doc xml source document

This will be the actual document content : [hello-world.xml](src/main/resources/doc-xml/hello-world.xml)

```
<?xml version="1.0" encoding="utf-8"?>
<doc
	xmlns="http://javacoredoc.fugerit.org"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://javacoredoc.fugerit.org https://www.fugerit.org/data/java/doc/xsd/doc-2-0.xsd" > 

  <!--
  	This is a Venus Fugerit Doc (https://github.com/fugerit-org/fj-doc) XML Source Document.
  	For documentation on how to write a valid Venus Doc XML Meta Model refer to : 
  	https://venusguides.fugerit.org/src/docs/common/doc_format_summary.html
  -->

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

The document should adhere to the [doc xsd](https://www.fugerit.org/data/java/doc/xsd/doc-2-0.xsd)

## 3. Create the java cod for document generation

This simple [HelloWorldPdf](src/main/java/org/fugerit/java/doc/guides/helloworld/pdf/HelloWorldPdf.java) class actually creates the PDF based on the xml source content : 

```
	public void generateHelloWorld( OutputStream os ) throws Exception {
		// reader for the xml source docuement
		try ( Reader xmlReader = new InputStreamReader( ClassHelper.loadFromDefaultClassLoader( "doc-xml/hello-world.xml" ) ) ) {
			DocTypeHandler handler = PdfFopTypeHandler.HANDLER; // handler for pdf output
			DocInput docInput = DocInput.newInput( handler.getType(), xmlReader );	 // handler input
			DocOutput docOutput = DocOutput.newOutput( os );	 // handler output
			handler.handle(docInput, docOutput); // generation
		} 
	}
```

## 4. Sample Junit to activate the code

This simple [TestHelloWorldPdf](src/test/java/test/org/fugerit/java/doc/guides/helloworld/pdf/TestHelloWorldPdf.java) Junit can be used to test the result : 

```
	@Test
	public void testHelloWorldPdf() {
		File outputFile = new File(  "target", "hello-world.pdf" ); // output file
		try ( OutputStream os = new FileOutputStream( outputFile ) ) {
			HelloWorldPdf helloWorldPdf = new HelloWorldPdf(); // the facade to generate the document
			helloWorldPdf.generateHelloWorld(os); // actual document generation
			log.info( "Generated hello world pdf file in path : {}", outputFile.getCanonicalPath() );
		} catch (Exception e) {
			String message = "Error : "+e;
			log.error( message , e );
			fail( message );
		}
	}
```

If everything worked fine, in the path *target/hello-world.pdf* it will be possible to find the generated document.

If you find any problem, you can submit an issue on the on this project github repository [https://github.com/fugerit-org/fj-doc-guides](https://github.com/fugerit-org/fj-doc-guides)
