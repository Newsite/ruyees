package cn.xdf.me.otp.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.DocumentException;

public class HtmlToPdf {

	public static void saveHtmlToPdf(String url, String destPath)
			throws IOException, DocumentException {
		OutputStream os = new FileOutputStream(destPath);
		ITextRenderer render = new ITextRenderer();
		render.setDocument(url);
		render.layout();
		render.createPDF(os);
		os.close();
	}

}
