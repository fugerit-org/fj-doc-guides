package org.fugerit.java.doc.guides.helloworld.jaon.source;

import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;

import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.fugerit.java.doc.base.config.DocException;
import org.fugerit.java.doc.base.config.DocInput;
import org.fugerit.java.doc.base.config.DocOutput;
import org.fugerit.java.doc.base.config.DocTypeHandler;
import org.fugerit.java.doc.base.facade.DocFacadeSource;
import org.fugerit.java.doc.mod.fop.PdfFopTypeHandler;

public class HelloWorldPdfJsonSource {

	public void generateHelloWorld( OutputStream os ) throws DocException {
		// wraps any exception as a DocException
		DocException.apply( () -> {
			// reader for the json source docuement
			try ( Reader jsonReader = new InputStreamReader( ClassHelper.loadFromDefaultClassLoader("doc-json/hello-world.json") ) ) {
				DocTypeHandler handler = PdfFopTypeHandler.HANDLER;						// handler for pdf output
				DocInput docInput = DocInput.newInput( handler.getType(), jsonReader, DocFacadeSource.SOURCE_TYPE_JSON );	// handler input
				DocOutput docOutput = DocOutput.newOutput( os );						// handler output
				handler.handle(docInput, docOutput);									// generation
			} 			
		} );
	}
	
}
