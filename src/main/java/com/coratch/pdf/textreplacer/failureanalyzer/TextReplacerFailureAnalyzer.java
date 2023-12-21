package com.coratch.pdf.textreplacer.failureanalyzer;

import com.coratch.pdf.textreplacer.exceptions.GeneralException;
import com.coratch.pdf.textreplacer.exceptions.MissingPDFException;
import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;


public class TextReplacerFailureAnalyzer extends AbstractFailureAnalyzer<GeneralException> {

	@Override
	protected FailureAnalysis analyze(Throwable rootFailure, GeneralException cause) {
		String descriptionMessage = cause.getMessage();
		String actionMessage = cause.getMessage();

		if(cause instanceof MissingPDFException) {
			MissingPDFException e = (MissingPDFException)cause;
			descriptionMessage = "The PDF file doesn't exists " + e.getFlePath();
			actionMessage = "Please provide a valid file with the property textreplacer.pdf";
		}
		
		return new FailureAnalysis(descriptionMessage, actionMessage, cause);
	}

}
