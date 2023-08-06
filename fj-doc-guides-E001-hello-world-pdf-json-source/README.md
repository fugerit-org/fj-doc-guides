# Venus Guide E001 : Hello World PDF JSON Source 

[Index](../README.md)

*Abstract* :  
Simple guide to create a json source file to generate a PDF.

*Author* : Matteo Franci a.k.a. Fugerit (fugerit79)

*Version* : 001 (2023-08-06)

*Description* :  
While XML is the default document source type for Fugerit Doc Venus, it is possible to use a JSON o YAML too as a source document. 
This guide explains the basic of creating and using a JSON document as source for a Type Handler.

*Prerequisites* :
* [Venus Guide A001 : Hello World PDF](../fj-doc-guides-A001-hello-world-pdf/README.md)

*Quickstart* :  
Run the junit : [TestHelloWorldPdf.java](src/test/java/test/org/fugerit/java/doc/guides/helloworld/source/TestHelloWorldPdf.java)


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

And the support for json source and  module for rendering PDF through [FOP](https://github.com/fugerit-org/fj-doc/tree/main/fj-doc-bom-fop)

```
		<dependency>
			<groupId>org.fugerit.java</groupId>
			<artifactId>fj-doc-base-json</artifactId>
		</dependency>	
		
		<dependency>
			<groupId>org.fugerit.java</groupId>
			<artifactId>fj-doc-mod-fop</artifactId>
		</dependency>	
```

## 2. Create the doc xml source document

This will be the actual document content : [hello-world.json](src/main/resources/doc-json/hello-world.json)

```
{
	"xmlns" : "http://javacoredoc.fugerit.org",
	"xsi:schemaLocation" : "http://javacoredoc.fugerit.org https://www.fugerit.org/data/java/doc/xsd/doc-2-0.xsd",
	"xmlns:xsi" : "http://www.w3.org/2001/XMLSchema-instance",
	"_t" : "doc",
	"_e" : [ {
		"_t" : "metadata",
		"_e" : [ {
			"name" : "margins",
			"_t" : "info",
			"_v" : "10;10;10;30"
		}, {
			"name" : "doc-title",
			"_t" : "info",
			"_v" : "Hello World"
		}, {
			"name" : "doc-author",
			"_t" : "info",
			"_v" : "fugerit79"
		}, {
			"name" : "doc-language",
			"_t" : "info",
			"_v" : "en"
		} ]
	}, {
		"_t" : "body",
		"_e" : [ {
			"_t" : "para",
			"_v" : "Hello World!"
		} ]
	} ]
}
```

## 3. Create the java code for document generation

This simple [HelloWorldPdf](src/main/java/org/fugerit/java/doc/guides/helloworld/source/HelloWorldPdf.java) class actually creates the PDF based on the xml source content : 

```
	public void generateHelloWorld( OutputStream os ) throws Exception {
		// reader for the xml source docuement
		try ( Reader jsonReader = new InputStreamReader( ClassHelper.loadFromDefaultClassLoader("doc-json/hello-world.json") ) ) {
			DocTypeHandler handler = PdfFopTypeHandler.HANDLER;						// handler for pdf output
			DocInput docInput = DocInput.newInput( handler.getType(), jsonReader, DocFacadeSource.SOURCE_TYPE_JSON );	// handler input
			DocOutput docOutput = DocOutput.newOutput( os );						// handler output
			handler.handle(docInput, docOutput);									// generation
		} 
	}
```

## 4. Sample Junit to activate the code

This simple [TestHelloWorldPdf.java](src/test/java/test/org/fugerit/java/doc/guides/helloworld/source/TestHelloWorldPdf.java) Junit can be used to test the result : 

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
