package com.coratch.pdf.textreplacer;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "textreplacer")
public class TextReplacerApplicationProperties {

	/**
	 * The path of pdf file which should be replaced.
	 */
	@NotNull
	private String pdf;
	
	/**
	 * The path to the image of which should be placed in the PDF.
	 */
	@NotNull
	private String image;
	
	/**
	 * The width of the image how it should be placed (resized) in the PDF
	 */
	@NotNull
	private Integer imageWidth;
	
	/**
	 * The height of the image how it should be placed (resized) in the PDF
	 */
	@NotNull
	private Integer imageHeight;

	/**
	 * The path to the image of which should be created in the PDF.
	 */
	@NotNull
	private String targetImagePath;

	/**
	 * The x multiple offset of the image in the PDF.
	 */
	private int xOffsetMultiple = 0;

	/**
	 * The width multiple offset of the image in the PDF.
	 */
	@Min(1)
	private int widthOffsetMultiple = 1;
	

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getPdf() {
		return pdf;
	}

	public void setPdf(String pdf) {
		this.pdf = pdf;
	}

	public Integer getImageWidth() {
		return imageWidth;
	}

	public void setImageWidth(Integer imageWidth) {
		this.imageWidth = imageWidth;
	}

	public Integer getImageHeight() {
		return imageHeight;
	}

	public void setImageHeight(Integer imageHeight) {
		this.imageHeight = imageHeight;
	}

	public String getTargetImagePath() {
		return targetImagePath;
	}

	public void setTargetImagePath(String targetImagePath) {
		this.targetImagePath = targetImagePath;
	}

	public int getxOffsetMultiple() {
		return xOffsetMultiple;
	}

	public void setxOffsetMultiple(int xOffsetMultiple) {
		this.xOffsetMultiple = xOffsetMultiple;
	}

	public int getWidthOffsetMultiple() {
		return widthOffsetMultiple;
	}

	public void setWidthOffsetMultiple(int widthOffsetMultiple) {
		this.widthOffsetMultiple = widthOffsetMultiple;
	}
}
