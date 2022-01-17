package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import util.PasswordGenerator.PasswordGeneratorBuilder;

public class KutukIslemleri {
	
	public String getContextPath() {
		String path = new FileSystemResource("").getFile().getAbsolutePath();
		return path+"/";
	}
	public String getDosyaYolu() {
		String path = getContextPath();
		
		
		File theDir = new File(path + "src/main/webapp/tmpDosya");
		// if the directory does not exist, create it
		if (!theDir.exists()) {
		    try{
		        theDir.mkdir();
				path += "src/main/webapp/tmpDosya/";
		    } 
		    catch(SecurityException se){
		        //handle it
		    }        

		}else {
			path += "src/main/webapp/tmpDosya/";
		}
		
		return path;
	}
	
	public String getTempFolder() {
		File file = new File("/tmp/");
		if(file.exists())
			return "/tmp/";//linux icin
		return getDosyaYolu()+"tmp/";//windows icin
	}

	public String randomName() {
		PasswordGenerator pg = new PasswordGenerator(new PasswordGeneratorBuilder().useDigits(true).useUpper(true).useLower(false).usePunctuation(false));
		return pg.generate(20);
//		return (new SecureRandom()).nextInt(65543) + "";
	}
	
	public URL getResource(ResourceLoader resourceLoader, String filePathName) {
		Resource resource=resourceLoader.getResource("classpath:"+filePathName);
		try {
			return resource.getURL();
		} catch (Exception e) {
			return null;
		}
	}
	
	public String encodeBase64(File file) throws IOException {
		byte[] fileBytes = loadFile(file);
		return Base64.encodeBase64String(fileBytes);
	}
	
	//TODO asagidaki fonksiyonun ismi decodeBase64 olmali
	public File encodeBase64(String fileString, String uzanti) throws IOException {
		byte[] fileBytes = Base64.decodeBase64(fileString);
		File file = new File(getTempFolder() + randomName()+randomName()+"."+uzanti);
		FileUtils.writeByteArrayToFile(file, fileBytes);
		return file;
	}
	
	public byte[] decodeBase64(String icerik) {
		byte[] fileBytes = Base64.decodeBase64(icerik);
		return fileBytes;
	}
	
	public byte[] toByteArray(String url) throws IOException {
		return toByteArray(new File(url));
	}
	
	public byte[] toByteArray(File file) throws IOException {
		return loadFile(file);
	}
	
	private static byte[] loadFile(File file) throws IOException {
	    InputStream is = new FileInputStream(file);

	    long length = file.length();
	    if (length > Integer.MAX_VALUE) {
	        // File is too large
	    }
	    byte[] bytes = new byte[(int)length];
	    
	    int offset = 0;
	    int numRead = 0;
	    while (offset < bytes.length
	           && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
	        offset += numRead;
	    }

	    if (offset < bytes.length) {
	    	is.close();
	        throw new IOException("Could not completely read file "+file.getName());
	    }

	    is.close();
	    return bytes;
	}
	
	public File getFileFromURL(String url) {
		File ret = new File(getTempFolder() + randomName() + randomName() + randomName());
		try {
			FileUtils.copyURLToFile(new URL(url), ret);
			if(ret == null || ret.exists() == false)
				return null;
			return ret;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public boolean writeFile(String url, byte[] fileBytes) {
		try {
			FileUtils.writeByteArrayToFile(new File(url), fileBytes);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static double getFileSizeMegaBytes(File file) {
		if(file == null) {
			return 0;
		}

		return ((double) file.length() / (1024 * 1024));
	}

	public String readLines(File file) {
		String sonuc = "";
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = "";
			while((line = reader.readLine()) != null) {
				sonuc += line + "\n";
			}
			reader.close();
		} catch (Exception e) {
			return null;
		}
		return sonuc;
	}
	
}
