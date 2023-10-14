# Venus Guide A004 : Apache FreeMarker Hello World

[Index](../README.md)

*Abstract* :  
Simple guide to a use [Venus XML Doc Format](../src/docs/common/doc_format_summary.md) together with [Apache FreeMarker](../src/docs/common/doc_format_freemarker.md)

*Author* : Matteo Franci a.k.a. Fugerit (fugerit79)

*Version* : 001 (2023-10-14)

*Quickstart* :  
Run the junit : [src/test/java/test/org/fugerit/java/doc/guides/helloworld/fm/facade/TestHelloWorldFreeMarkerFacade.java](src/test/java/test/org/fugerit/java/doc/guides/helloworld/fm/facade/TestHelloWorldFreeMarkerFacade.java)


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

And the module for rendering PDF through [FOP](https://github.com/fugerit-org/fj-doc/tree/main/fj-doc-mod-fop)
(It will all other modules as transient dependencies)

```
		<dependency>
			<groupId>org.fugerit.java</groupId>
			<artifactId>fj-doc-mod-fop</artifactId>
		</dependency>	
```

## 2. Create the ApacheFreemarker template

This will be the template [hello-world.ftl](src/main/resources/fj-freemarker-sample-template/hello-world.ftl)

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


It will generate a XML document which adheres to [doc xsd](https://www.fugerit.org/data/java/doc/xsd/doc-2-1.xsd)

## 3. Create the java cod for document generation

This is a simple facade [HelloWorldFreeMarkerFacade](src/main/java/org/fugerit/java/doc/guides/helloworld/fm/facade/HelloWorldFreeMarkerFacade.java) which will just : 

- create a simple FreemarkerDocProcessConfig (more complex examples will be available)
- add some ismple date
- implement the generateFullDocument() method which will do the job

```
public class HelloWorldFreeMarkerFacade {

	// in the simple config, there is a default method mapping any chainId to a template with the path ${chainId}.ftl
	private static final FreemarkerDocProcessConfig SIMPLE_CONFIG = 
			// SafeFunction.get() will execute the configuration code and re-throw a ConfigRuntimeException if any exception is thrown
			SafeFunction.get( () -> FreemarkerDocProcessConfigFacade.newSimpleConfig( "A004-sample-config" , "/fj-freemarker-sample-template/" ) );
	
	private static final String CHAIN_ID = "hello-world";	// it will map to /fj-freemarker-sample-template/hello-world.ftl
	
	public void generateDocument( OutputStream os, DocTypeHandler handler, String docTitle ) throws DocException {
		// wraps any exception as a DocException
		DocException.apply( () -> {
			DocProcessContext context = DocProcessContext.newContext( "docTitle", docTitle );
			SIMPLE_CONFIG.fullProcess( CHAIN_ID, context, handler, DocOutput.newOutput(os) );
		} );
	}
	
}
```

## 4. Sample Junit to activate the code

This is a simple JUnit to activate the code [TestHelloWorldFreeMarkerFacade](src/test/java/test/org/fugerit/java/doc/guides/helloworld/fm/facade/TestHelloWorldFreeMarkerFacade.java) , it will : 

- define the handlers for rendering the desired output formats
- add some test data
- iterate all the handler and output them in `target/` directory

```
	@Test
	public void testGenerateDocument() throws IOException, DocException {
		DocTypeHandler handler = PdfFopTypeHandler.HANDLER;	// the handler for desired output, in this case PDF
		String docTitle = "Hello World with Apache FreeMarker Template!";
		File outputFile = new File(  "target", "hello-world."+handler.getType() ); // output file
		log.info( "outputFile : {}", outputFile );
		try ( OutputStream os = new FileOutputStream( outputFile ) ) {
			HelloWorldFreeMarkerFacade facade = new HelloWorldFreeMarkerFacade(); // the facade to generate the document
			facade.generateDocument(os, handler, docTitle); // actual document generation
			log.info( "Generated full documenti pdf file in path : {}", outputFile.getCanonicalPath() );
			Assert.assertTrue( outputFile.exists() );
		}
	}
```

If everything worked fine, in the path *target/hello-world.pdf* it will be possible to find the generated document.

If you find any problem, you can submit an issue on the on this project github repository [https://github.com/fugerit-org/fj-doc-guides](https://github.com/fugerit-org/fj-doc-guides)
