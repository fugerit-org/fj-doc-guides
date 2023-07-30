package test.org.fugerit.java.doc.guides.helloworld.pdf;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.fugerit.java.doc.guides.helloworld.pdf.HelloWorldPdf;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestHelloWorldPdf {

	private static final Logger log = LoggerFactory.getLogger( TestHelloWorldPdf.class );
	
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
	
}
