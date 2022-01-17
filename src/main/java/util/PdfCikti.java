package util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.layout.font.FontProvider;
//import com.itextpdf.licensing.base.LicenseKey;
//import com.itextpdf.styledxmlparser.css.media.MediaDeviceDescription;
//import com.itextpdf.styledxmlparser.css.media.MediaType;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.openhtmltopdf.bidi.support.ICUBidiReorderer;
import com.openhtmltopdf.bidi.support.ICUBidiSplitter;
import com.openhtmltopdf.outputdevice.helper.BaseRendererBuilder.TextDirection;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

import tr.bays.common.base.BaseCrudController;

public class PdfCikti {
	private static Log LOGGER = LogFactory.getLog(BaseCrudController.class);
	public static final String FONT_PATH_ARAPCA = "src/main/resources/NotoNaskhArabic-Regular.ttf";
	public static final String FONT_PATH = "src/main/resources/ARIALUNI.TTF";
	public static final String FONT_PATH_TIMES = "src/main/resources/TIMES.TTF";
	public static final String FONT_PATH_TIMES_BOLD = "src/main/resources/TIMESBOLD.TTF";
	
	public static String htmlToPdfOnceki(String html) {
		KutukIslemleri ki = new KutukIslemleri();
		String filePath = ki.getDosyaYolu()+ki.randomName()+".pdf";
		try {
//			HtmlConverter.convertToPdf(html, new FileOutputStream(filePath));
			
//			BelgeCikti cikti = new BelgeCikti();
//			cikti.olusturForm(html, filePath);
			
		} catch (Exception e) {
			LOGGER.error("Error", e);
		}
		return filePath;
	}
	
	public static String htmlToPdf(String html, String klasor) {
		KutukIslemleri ki = new KutukIslemleri();
		String klasorStr = (Util.notEmpty(klasor))?(klasor+"/"):("");
		String filePath = ki.getDosyaYolu() + klasorStr + ki.randomName() + ".pdf";
//		String filePath = ki.getDosyaYolu()+ki.randomName()+".pdf";
//		LicenseKey.loadLicenseFile(new File("src/main/resources/b782840d7998218c2a4e4f06730522005ba3a8fadc8859b4f258867c4422eebd.json"));
		try {
			//-------------------
			ConverterProperties converterProperties = new ConverterProperties();
			FontProvider fontProvider = new DefaultFontProvider(false, false, false);
			FontProgram fontProgram = FontProgramFactory.createFont(FONT_PATH_TIMES);
			fontProvider.addFont(fontProgram);
			fontProgram = FontProgramFactory.createFont(FONT_PATH_TIMES_BOLD);
			fontProvider.addFont(fontProgram);
//			fontProgram = FontProgramFactory.createFont(FONT_PATH_ARAPCA);
//			fontProvider.addFont(fontProgram);
			fontProgram = FontProgramFactory.createFont(FONT_PATH);
			fontProvider.addFont(fontProgram);
			converterProperties.setFontProvider(fontProvider);
 		    //----------------------------
//			converterProperties.setTagWorkerFactory(new CustomTagWorkerFactory());
//			converterProperties.setMediaDeviceDescription(new MediaDeviceDescription(MediaType.PRINT));
 			
			//dil arapca ise tersine cevriliyor
			if(html != null && html.contains("<rtl>"))
				html = rtlCevir(html);
			
			if(html != null && html.contains("NotoNaskhArabic"))
				generateOpenPDF(html, filePath);
			else
				HtmlConverter.convertToPdf(html, new FileOutputStream(filePath), converterProperties);
			
			float marginDeger = 60;
			File file = new File(filePath);
			byte[] output = ki.toByteArray(file);
			PdfReader pdfReader = new PdfReader(output);

			PdfStamper pdfStamper = new PdfStamper(pdfReader, new FileOutputStream(filePath));
			PdfContentByte over = pdfStamper.getOverContent(1);
			
			Image img = Image.getInstance("src/main/resources/META-INF/resources/public/img/logo/TC_Adalet_Bakanligi_logo.png");
			img.scaleAbsolute(89, 89);
			img.setAbsolutePosition(marginDeger, PageSize.A4.getHeight() - img.getScaledHeight() - marginDeger + 30);
			over.addImage(img);
			pdfStamper.close();
			
		} catch (Exception e) {
			LOGGER.error("Error", e);
		}
		return filePath;
	}
	
	public static String htmlToPdfArapcaBirlesmeyen(String html) {
		KutukIslemleri ki = new KutukIslemleri();
		String filePath = ki.getDosyaYolu()+ki.randomName()+".pdf";
		try {
			//-------------------
			ConverterProperties converterProperties = new ConverterProperties();
			FontProvider fontProvider = new DefaultFontProvider(false, false, false);
			FontProgram fontProgram = FontProgramFactory.createFont(FONT_PATH_TIMES);
			fontProvider.addFont(fontProgram);
			fontProgram = FontProgramFactory.createFont(FONT_PATH_TIMES_BOLD);
			fontProvider.addFont(fontProgram);
//			fontProgram = FontProgramFactory.createFont(FONT_PATH_ARAPCA);
//			fontProvider.addFont(fontProgram);
			fontProgram = FontProgramFactory.createFont(FONT_PATH);
			fontProvider.addFont(fontProgram);
			converterProperties.setFontProvider(fontProvider);
 		    //----------------------------
//			converterProperties.setTagWorkerFactory(new CustomTagWorkerFactory());
 			
			//dil arapca ise tersine cevriliyor
//			if(html != null && html.contains("<rtl>"))
//				html = rtlCevir(html);
			
			HtmlConverter.convertToPdf(html, new FileOutputStream(filePath), converterProperties);
			
			float marginDeger = 60;
			File file = new File(filePath);
			byte[] output = ki.toByteArray(file);
			PdfReader pdfReader = new PdfReader(output);

			PdfStamper pdfStamper = new PdfStamper(pdfReader, new FileOutputStream(filePath));
			PdfContentByte over = pdfStamper.getOverContent(1);
			
			Image img = Image.getInstance("src/main/resources/META-INF/resources/public/img/logo/TC_Adalet_Bakanligi_logo.png");
			img.scaleAbsolute(89, 89);
			img.setAbsolutePosition(marginDeger, PageSize.A4.getHeight() - img.getScaledHeight() - marginDeger + 30);
			over.addImage(img);
			pdfStamper.close();
			
		} catch (Exception e) {
			LOGGER.error("Error", e);
		}
		return filePath;
	}
	
	/**
	 * html icinde <change></change> arasinda bulunan ifadelerin harflerini tersine cevirir
	 * */
	public static String rtlCevir(String html) {
		StringBuffer sb = new StringBuffer();

		Pattern p = Pattern.compile("<rtl>(.*?)</rtl>",Pattern.DOTALL);
		Matcher m = p.matcher(html);

		while(m.find()){
		    String valueFromTags = m.group(1);
		    m.appendReplacement(sb, new StringBuilder(valueFromTags).reverse().toString());
		}
		m.appendTail(sb);

		String result = sb.toString();
		return result;
	}
	
//	public static String manipulatePdf() throws Exception {
//		KutukIslemleri ki = new KutukIslemleri();
//		String filePath = ki.getDosyaYolu()+ ki.randomName() + ".pdf";
//		String ARABIC = "\u0627\u0644\u0633\u0639\u0631 \u0627\u0644\u0627\u062c\u0645\u0627\u0644\u064a";
//		
//        com.itextpdf.kernel.pdf.PdfDocument pdfDoc = new com.itextpdf.kernel.pdf.PdfDocument(new PdfWriter(filePath));
//        Document doc = new Document(pdfDoc);
//        PdfFont f = PdfFontFactory.createFont(FONT_PATH_ARAPCA, PdfEncodings.IDENTITY_H);
//
//        // It is required to add iText typography dependency to handle correctly arabic text
//        Paragraph p = new Paragraph("This is auto detection: ");
////        p.add(new Text("الجمهورية").setFont(f));
//        p.add(new Text(ARABIC).setFont(f));
//        p.add(new Text("الجمهورية").setFont(f));
//        doc.add(p);
//        doc.close();
//        return filePath;
//    }
	
	public static void generateOpenPDF(String htmlContent, String path) throws Exception {
		KutukIslemleri ki = new KutukIslemleri();

	   ByteArrayOutputStream os = new ByteArrayOutputStream();
	   PdfRendererBuilder builder = new PdfRendererBuilder();
	   builder.useUnicodeBidiSplitter(new ICUBidiSplitter.ICUBidiSplitterFactory());
	   builder.useUnicodeBidiReorderer(new ICUBidiReorderer());
	   builder.useFont(new File(FONT_PATH_ARAPCA), "noto");
	   builder.useFont(new File(FONT_PATH_TIMES), "times");
	   builder.defaultTextDirection(TextDirection.RTL); // OR RTL

	   htmlContent = htmlContent.replaceAll("<br>", "<br />");
	   
	   builder.withHtmlContent(htmlContent, "file:///file:///"+ki.getDosyaYolu());
	        
	   builder.toStream(os);
	   builder.run();
	   byte[] pdfAsBytes = os.toByteArray();
	   os.close();
	   String filePath = path;
	   Path file = Paths.get(filePath);
	   Files.write(file, pdfAsBytes);
	   System.out.println(filePath);
	}
	
	public static String htmlToPdf(String path, String html, String klasor) {
		KutukIslemleri ki = new KutukIslemleri();
		String klasorStr = (Util.notEmpty(klasor))?(klasor+"/"):("");
		String filePath = path + klasorStr + ki.randomName() + ".pdf";
		try {
			HtmlConverter.convertToPdf(html, new FileOutputStream(filePath));
		} catch (Exception e) {
			LOGGER.error("Error", e);
		}
		return filePath;
	}
	public static String[] htmlToPdfWithName(String path, String html) {
		KutukIslemleri ki = new KutukIslemleri();
		String dosyaAdi = ki.randomName();
		String filePath = path+dosyaAdi+".pdf";
		try {
			HtmlConverter.convertToPdf(html, new FileOutputStream(filePath));
		} catch (Exception e) {
			LOGGER.error("Error", e);
		}
		String[] array = {filePath,dosyaAdi};
		return array;
	}
}
