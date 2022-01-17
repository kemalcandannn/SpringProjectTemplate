package util;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.re2j.Matcher;
import com.google.re2j.Pattern;
import com.querydsl.core.types.dsl.BooleanExpression;

import util.PasswordGenerator.PasswordGeneratorBuilder;

public class Util{
	
	public static final int LIMIT = 10;

	public static <E> List<E> toList(Iterable<E> iterable) {
		if (iterable instanceof List) {
			return (List<E>) iterable;
		}
		ArrayList<E> list = new ArrayList<E>();
		if (iterable != null) {
			for (E e : iterable) {
				list.add(e);
			}
		}
		return list;
	}

	public static boolean empty(Object obj) {
		if(obj instanceof String) {
			return ((String) obj) == null || ((String) obj).trim().isEmpty();
		}else if(obj instanceof Long) {
			return ((Long) obj) == null || ((Long) obj) == 0L;
		}

		return obj == null;
	}
	
	public static boolean notEmpty(Object obj) {
		if(obj instanceof String) {
			return ((String) obj) != null && ((String) obj).trim().isEmpty() == false;
		}else if(obj instanceof Long) {
			return ((Long) obj) != null && ((Long) obj) != 0L;
		}

		return obj != null;
	}

	public static BooleanExpression and(BooleanExpression booleanExpression, BooleanExpression booleanExpression2) {
		return booleanExpression == null ? booleanExpression2 : booleanExpression.and(booleanExpression2);
	}

	public static BooleanExpression or(BooleanExpression booleanExpression, BooleanExpression booleanExpression2) {
		return booleanExpression == null ? booleanExpression2 : booleanExpression.or(booleanExpression2);
	}
	
	public static boolean isValidIPAddress(String ip) {
		
		String zeroTo255 = "(\\d{1,2}|(0|1)\\" + "d{2}|2[0-4]\\d|25[0-5])"; 
        String regex = zeroTo255 + "\\." + zeroTo255 + "\\." + zeroTo255 + "\\." + zeroTo255; 
        Pattern p = Pattern.compile(regex); 
  
        if (ip == null) { 
            return false; 
        } 
  
        Matcher m = p.matcher(ip); 
  
        return m.matches();
	}
	
	public static String getKullaniciAdi() {
		String username;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
		  username = ((UserDetails)principal).getUsername();
		} else {
		  username = principal.toString();
		}

		return username;
	}

	public static String getRemoteAddress() {
		HttpServletRequest request = null;
		if(FacesContext.getCurrentInstance() != null) {
			request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		}
		else if(RequestContextHolder.currentRequestAttributes() != null) {
			request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		}
		
		if(request == null)
			return "";
//		String ipAddress = request.getHeader("X-FORWARDED-FOR");
//		if (ipAddress != null) {
//			//liste var ise ilk ip
//			ipAddress = ipAddress.replaceFirst(",.*", "");
//		} else {
//			ipAddress = request.getRemoteAddr();
//		}

		return request.getRemoteAddr();
	}

	public static String randomName(int uzunluk) {
		PasswordGenerator pg = new PasswordGenerator(new PasswordGeneratorBuilder().
				useDigits(true).
				useUpper(true).
				useLower(false).
				usePunctuation(false));

		return pg.generate(uzunluk);
	}
	
	/**
	 * tckn dogru ise true doner
	 * */
	public static boolean tcknDogruMu(String tckn) {
		//11 hane olmali
		if(tckn.length() != 11)
			return false;
		
		//son hane cift olmali
		if(Integer.parseInt(tckn.charAt(10)+"") % 2 == 1)
			return false;
		
		int tekHaneToplam = Integer.parseInt(tckn.charAt(0)+"") + Integer.parseInt(tckn.charAt(2)+"") + Integer.parseInt(tckn.charAt(4)+"") + Integer.parseInt(tckn.charAt(6)+"") + Integer.parseInt(tckn.charAt(8)+"");
		int ciftHaneToplam = Integer.parseInt(tckn.charAt(1)+"") + Integer.parseInt(tckn.charAt(3)+"") + Integer.parseInt(tckn.charAt(5)+"") + Integer.parseInt(tckn.charAt(7)+"");
		int carpim = tekHaneToplam * 7;
		int fark = carpim - ciftHaneToplam;
		int hane10 = fark % 10;
		int toplamGenel = tekHaneToplam + ciftHaneToplam + hane10;
		int hane11 = toplamGenel % 10;
		
		//son 2 hane algoritmaya uymali
		if(Integer.parseInt(tckn.charAt(9)+"") != hane10 || Integer.parseInt(tckn.charAt(10)+"") != hane11)
			return false;
		
		return true;
	}

	public static String masksizCepTelefonuAl(String cep_tel) {
		String olmasi_gereken_cep_telefonu = null;
		try {
			olmasi_gereken_cep_telefonu = cep_tel.substring(0,1) + 
										  cep_tel.substring(3,6) + 
										  cep_tel.substring(8,11) + 
										  cep_tel.substring(12,14) + 
										  cep_tel.substring(15,17);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(Util.notEmpty(olmasi_gereken_cep_telefonu)) {
			return olmasi_gereken_cep_telefonu;
		}else {
			try {
				olmasi_gereken_cep_telefonu = cep_tel.replaceAll("[\\s\\-()]", "");
			} catch (Exception e) {
				e.printStackTrace();
			}

			return Util.notEmpty(olmasi_gereken_cep_telefonu) ? olmasi_gereken_cep_telefonu : "";
		}
		
	}

}