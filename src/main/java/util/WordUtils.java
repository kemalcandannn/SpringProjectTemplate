package util;

import java.util.Locale;

import com.google.re2j.Matcher;
import com.google.re2j.Pattern;

import util.enums.BuyukKucukHarfEnum;

public class WordUtils {

	private static final Locale TURKISH = Locale.forLanguageTag("tr-TR");
	public static final int PAROLADAKI_MINIMUM_KARAKTER_SAYISI = 8;

	public static boolean equals(String str1, String str2) {
		if(str1 == null && str2 == null) {
			return true;
		}else if(str1 == null && str2 != null) {
			return false;
		}else if(str1 != null && str2 == null) {
			return false;
		}else {
			if(str1.toLowerCase(TURKISH).trim().equals(str2.toLowerCase(TURKISH).trim())) {
				return true;
			}else {
				return false;
			}
		}
	}
	
	public static String toEnglish(String text, BuyukKucukHarfEnum fontSize) {
		if (Util.empty(text))
			return text;

		text = fontSize.equals(BuyukKucukHarfEnum.KUCUK) ? (text.replaceAll("ö", "o")) : fontSize.equals(BuyukKucukHarfEnum.OLDUGU_GIBI) ? (text.replaceAll("ö", "o")) : fontSize.equals(BuyukKucukHarfEnum.BUYUK) ? (text.replaceAll("ö", "O")) : "o";
		text = fontSize.equals(BuyukKucukHarfEnum.KUCUK) ? (text.replaceAll("Ö", "o")) : fontSize.equals(BuyukKucukHarfEnum.OLDUGU_GIBI) ? (text.replaceAll("Ö", "O")) : fontSize.equals(BuyukKucukHarfEnum.BUYUK) ? (text.replaceAll("Ö", "O")) : "O";
		text = fontSize.equals(BuyukKucukHarfEnum.KUCUK) ? (text.replaceAll("ç", "c")) : fontSize.equals(BuyukKucukHarfEnum.OLDUGU_GIBI) ? (text.replaceAll("ç", "c")) : fontSize.equals(BuyukKucukHarfEnum.BUYUK) ? (text.replaceAll("ç", "C")) : "c";
		text = fontSize.equals(BuyukKucukHarfEnum.KUCUK) ? (text.replaceAll("Ç", "c")) : fontSize.equals(BuyukKucukHarfEnum.OLDUGU_GIBI) ? (text.replaceAll("Ç", "C")) : fontSize.equals(BuyukKucukHarfEnum.BUYUK) ? (text.replaceAll("Ç", "C")) : "C";
		text = fontSize.equals(BuyukKucukHarfEnum.KUCUK) ? (text.replaceAll("ş", "s")) : fontSize.equals(BuyukKucukHarfEnum.OLDUGU_GIBI) ? (text.replaceAll("ş", "s")) : fontSize.equals(BuyukKucukHarfEnum.BUYUK) ? (text.replaceAll("ş", "S")) : "s";
		text = fontSize.equals(BuyukKucukHarfEnum.KUCUK) ? (text.replaceAll("Ş", "s")) : fontSize.equals(BuyukKucukHarfEnum.OLDUGU_GIBI) ? (text.replaceAll("Ş", "S")) : fontSize.equals(BuyukKucukHarfEnum.BUYUK) ? (text.replaceAll("Ş", "S")) : "S";
		text = fontSize.equals(BuyukKucukHarfEnum.KUCUK) ? (text.replaceAll("ü", "u")) : fontSize.equals(BuyukKucukHarfEnum.OLDUGU_GIBI) ? (text.replaceAll("ü", "u")) : fontSize.equals(BuyukKucukHarfEnum.BUYUK) ? (text.replaceAll("ü", "U")) : "u";
		text = fontSize.equals(BuyukKucukHarfEnum.KUCUK) ? (text.replaceAll("Ü", "u")) : fontSize.equals(BuyukKucukHarfEnum.OLDUGU_GIBI) ? (text.replaceAll("Ü", "U")) : fontSize.equals(BuyukKucukHarfEnum.BUYUK) ? (text.replaceAll("Ü", "U")) : "U";
		text = fontSize.equals(BuyukKucukHarfEnum.KUCUK) ? (text.replaceAll("ğ", "g")) : fontSize.equals(BuyukKucukHarfEnum.OLDUGU_GIBI) ? (text.replaceAll("ğ", "g")) : fontSize.equals(BuyukKucukHarfEnum.BUYUK) ? (text.replaceAll("ğ", "G")) : "g";
		text = fontSize.equals(BuyukKucukHarfEnum.KUCUK) ? (text.replaceAll("Ğ", "g")) : fontSize.equals(BuyukKucukHarfEnum.OLDUGU_GIBI) ? (text.replaceAll("Ğ", "G")) : fontSize.equals(BuyukKucukHarfEnum.BUYUK) ? (text.replaceAll("Ğ", "G")) : "G";
		text = fontSize.equals(BuyukKucukHarfEnum.KUCUK) ? (text.replaceAll("ı", "i")) : fontSize.equals(BuyukKucukHarfEnum.OLDUGU_GIBI) ? (text.replaceAll("ı", "i")) : fontSize.equals(BuyukKucukHarfEnum.BUYUK) ? (text.replaceAll("ı", "I")) : "i";
		text = fontSize.equals(BuyukKucukHarfEnum.KUCUK) ? (text.replaceAll("İ", "i")) : fontSize.equals(BuyukKucukHarfEnum.OLDUGU_GIBI) ? (text.replaceAll("İ", "I")) : fontSize.equals(BuyukKucukHarfEnum.BUYUK) ? (text.replaceAll("İ", "I")) : "I";

		return text;
	}

	public static String whitespaceToUnderline(String text) {
		if (Util.empty(text)) {
			return text;
		}

		return text.replaceAll(" ", "_");
	}

	public static String capitalFirstLetter(String text) {
		if (Util.empty(text)) {
			return text;
		}

		String[] textArray = text.split(" ");
		String result = "";

		if (textArray != null && textArray.length > 0) {
			for (int i = 0; i < textArray.length; i++) {
				String tempText = textArray[i];
				result += tempText.substring(0, 1).toUpperCase() + 
						 tempText.substring(1).toLowerCase();

				if (i != textArray.length - 1) {
					result += " ";
				}
			}
		}
		return result;
	}

	public static boolean buyukHarfIceriyorMu(String yeniParola) {
		if(Util.empty(yeniParola))
			return false;
		return !yeniParola.equals(yeniParola.toLowerCase());
	}
	
	public static boolean sayisalDegerIceriyorMu(String yeniParola) {
		if(Util.empty(yeniParola))
			return false;
		Pattern numericPatter = Pattern.compile(".*\\d.*");
		Matcher m1 = numericPatter.matcher(yeniParola);
		return m1.matches();
	}
	
	public static boolean ozelKarakterIceriyorMu(String yeniParola) {
		if(Util.empty(yeniParola))
			return false;
		return !yeniParola.matches("[A-Za-z0-9 ]*");
	}
	
	public static boolean paroladakiMinimumKarakterSayisindanFazlaMi(String yeniParola) {
		if(Util.empty(yeniParola))
			return false;
		return yeniParola.length() >= PAROLADAKI_MINIMUM_KARAKTER_SAYISI;
	}

}
