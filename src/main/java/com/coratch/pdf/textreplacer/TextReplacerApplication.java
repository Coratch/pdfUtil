package com.coratch.pdf.textreplacer;

import java.io.*;
import java.util.List;

import com.coratch.pdf.textreplacer.exceptions.MissingPDFException;
import com.coratch.pdf.textreplacer.pdfutils.ImageUtil;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.text.TextPosition;
import org.apache.pdfbox.util.Matrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.coratch.pdf.textreplacer.pdfutils.TextPositionFinder;

@SpringBootApplication
@EnableConfigurationProperties(TextReplacerApplicationProperties.class)
public class TextReplacerApplication implements CommandLineRunner {

	@Autowired
	private TextReplacerApplicationProperties properties;

	public static void main(String[] args) {
		try {
			SpringApplication.run(TextReplacerApplication.class, args);
		}catch (Exception ex){
			System.out.println(ex.getMessage());
		}
	}

	@Override
	public void run(String... arg0) throws Exception {
		int replaceCount = Integer.MAX_VALUE;
		for (int i = 0; i < Integer.MAX_VALUE; i++) {
			if (i >= replaceCount){
				break;
			}
			File pdfFile = new File(properties.getPdf());
			if (!pdfFile.exists()) {
				throw new MissingPDFException(properties.getPdf());
			}
			try (PDDocument document = PDDocument.load(pdfFile)) {
				replaceCount = document.getNumberOfPages();
				//text to be replaced
				String text = "xxx";
				PDPage page = document.getPage(i);
				TextPositionFinder textPositionFinder = new TextPositionFinder(text);
				textPositionFinder.setSortByPosition(true);
				textPositionFinder.setStartPage(i+1);
				textPositionFinder.setEndPage(i+1);
				textPositionFinder.writeText(document, new StringWriter());
				//check whether the text to be replaced exists
				if (!textPositionFinder.textExists()) {
					continue;
				}
				List<TextPosition> textPositions = textPositionFinder.getTextPosition();
				for (TextPosition textPosition : textPositions) {
					try (PDPageContentStream contentStream = new PDPageContentStream(document, page, AppendMode.APPEND,
							true, true)) {
						PDImageXObject pdImage = PDImageXObject.createFromFile(properties.getImage(), document);
						Matrix textMatrix = textPosition.getTextMatrix();
						//offset
						float xOffset = textPosition.getWidth() * properties.getxOffsetMultiple();
						//width offset
						float widthOffset = text.length() * textPosition.getWidth();
						//draw text
						contentStream.drawImage(pdImage, textMatrix.getTranslateX() + xOffset, textMatrix.getTranslateY() - properties.getImageHeight() + (textPosition.getHeight() *2), widthOffset, properties.getImageHeight());
					}
				}
				document.save(properties.getPdf());
			}
		}

		FileInputStream inputStream = new FileInputStream(properties.getPdf());
		byte[] bytes = ImageUtil.transfer2Image(inputStream);
		byte[] bytes1 = ImageUtil.imageToPdf(bytes);
		PDDocument.load(bytes1).save(properties.getTargetImagePath());
		inputStream.close();

	}

}
