# Venus Guide A003 : Apache FreeMarker Full document

[Index](../README.md)

*Abstract* :  
Simple guide to a use [Venus XML Doc Format](../src/docs/common/doc_format_summary.md) together with [Apache FreeMarker](../src/docs/common/doc_format_freemarker.md)

*Author* : Matteo Franci a.k.a. Fugerit (fugerit79)

*Version* : 001 (2023-10-14)

*Quickstart* :  
Run the junit : [src/test/java/test/org/fugerit/java/doc/guides/fulldocument/fm/facade/TestFullDocumentFmFacade.java](src/test/java/test/org/fugerit/java/doc/guides/fulldocument/fm/facade/TestFullDocumentFmFacade.java)


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

This will be the template [full-document.ftl](src/main/resources/fj-freemarker-sample-template/full-document.ftl)

It will generate a XML document which adheres to [doc xsd](https://www.fugerit.org/data/java/doc/xsd/doc-2-1.xsd)

## 3. Create the java cod for document generation

This is a simple facade [FullDocumentFmFacade](src/main/java/org/fugerit/java/doc/guides/fulldocument/fm/facade/FullDocumentFmFacade.java) which will just : 

- create a simple FreemarkerDocProcessConfig (more complex examples will be available)
- add some ismple date
- implement the generateFullDocument() method which will do the job

```
	private static final FreemarkerDocProcessConfig SIMPLE_CONFIG = 
			// SafeFunction.get() will execute the configuration code and re-throw a ConfigRuntimeException if any exception is thrown
			SafeFunction.get( () -> FreemarkerDocProcessConfigFacade.newSimpleConfig( "A003-sample-config" , "/fj-freemarker-sample-template/" ) );
	
	private static final List<MapEntry<String, String>> LIST_TESTS = Arrays.asList(
			new MapEntry<>( "ul", "default unordered list"),
			new MapEntry<>( "uld", "dotted unordered list"),
			new MapEntry<>( "ulm", "square unordered list"),
			new MapEntry<>( "ol", "default ordered list"),
			new MapEntry<>( "oll", "letter ordered list"),
			new MapEntry<>( "oln", "numbered ordered list")
		);
	
	public void generateFullDocument( OutputStream os, DocTypeHandler handler, List<UserData> listUsers ) throws DocException {
		// wraps any exception as a DocException
		DocException.apply( () -> {
			DocProcessContext context = DocProcessContext.newContext( "listUsers", listUsers );
			context.setAttribute( "listTests" , LIST_TESTS );
			SIMPLE_CONFIG.fullProcess( "full-document", context, handler, DocOutput.newOutput(os) );
		} );
	}
```

## 4. Sample Junit to activate the code

This is a simple JUnit to activate the code [TestFullDocumentFmFacade](src/test/java/test/org/fugerit/java/doc/guides/fulldocument/fm/facade/TestFullDocumentFmFacade.java) , it will : 

- define the handlers for rendering the desired output formats
- add some test data
- iterate all the handler and output them in `target/` directory

```
	// three formats will be rendered in this example
	private static final DocTypeHandler[] HANDLERS =  { 
			PdfFopTypeHandler.HANDLER, 					// PDF through Apache FOP
			FreeMarkerFopTypeHandler.HANDLER_UTF8,			// XLS-FO format
			FreeMarkerHtmlTypeHandlerEscapeUTF8.HANDLER,	// HTML through Apache FreeMarker
			DocTypeHandlerXMLUTF8.HANDLER };				// XML as source fugerit doc format XML
	
	@Test
	public void testFullDocument() throws IOException, DocException {
		// custom data to be added to the template via Apache FreeMarker
		List<UserData> listUsers = Arrays.asList( 
				new UserData( "Marie" , "Curie", ">> Checmist"),
				new UserData( "Alan" , "Turing", "<IT/> Specialist")
			);
		for ( DocTypeHandler handler : HANDLERS ) {
			File outputFile = new File(  "target", "full-document."+handler.getType() ); // output file
			log.info( "outputFile : {}", outputFile );
			try ( OutputStream os = new FileOutputStream( outputFile ) ) {
				FullDocumentFmFacade facade = new FullDocumentFmFacade(); // the facade to generate the document
				facade.generateFullDocument(os, handler, listUsers); // actual document generation
				log.info( "Generated full documenti pdf file in path : {}", outputFile.getCanonicalPath() );
				Assert.assertTrue( outputFile.exists() );
			}	
		}
	}
```

If everything worked fine, in the path *target/full-document.pdf* it will be possible to find the generated document. (together with html, fo and xml format).

**Note about DocTypeHandlerXMLUTF8 handler** : this can be used to inspect the Venus XML Doc format after Apache FreeMarker substitution.

If you find any problem, you can submit an issue on the on this project github repository [https://github.com/fugerit-org/fj-doc-guides](https://github.com/fugerit-org/fj-doc-guides)
