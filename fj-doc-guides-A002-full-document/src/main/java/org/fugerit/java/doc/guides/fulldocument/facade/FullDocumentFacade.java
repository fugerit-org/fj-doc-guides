package org.fugerit.java.doc.guides.fulldocument.facade;

import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;

import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.fugerit.java.doc.base.config.DocException;
import org.fugerit.java.doc.base.config.DocInput;
import org.fugerit.java.doc.base.config.DocOutput;
import org.fugerit.java.doc.base.config.DocTypeHandler;

public class FullDocumentFacade {

	public void generateFullDocument( OutputStream os, DocTypeHandler handler ) throws DocException {
		// wraps any exception as a DocException
		DocException.apply( () -> {
			// reader for the xml source docuement
			try ( Reader xmlReader = new InputStreamReader( ClassHelper.loadFromDefaultClassLoader( "doc-xml/full-document-100.xml" ) ) ) {
				DocInput docInput = DocInput.newInput( handler.getType(), xmlReader );	// handler input
				DocOutput docOutput = DocOutput.newOutput( os );						// handler output
				handler.handle(docInput, docOutput);									// generation
			} 			
		} );
	}
	
}
