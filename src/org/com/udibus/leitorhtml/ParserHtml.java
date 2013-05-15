package org.com.udibus.leitorhtml;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.util.Pair;

public class ParserHtml {

	public String url;

	public ParserHtml(String url) {
		this.url = url;
	}
	
	public HashMap<String, String> mapOptions() {
		HashMap<String, String> optionsMaped = new HashMap<String, String>();
		try {
			
			InputStream in = getInputStreamFromWeb();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder strBuilder = new StringBuilder();
			String line = new String();
			while((line = reader.readLine()) != null)
			{
				strBuilder.append(line);
				//ISO-8859-1
			}
			
			Document document = Jsoup.parse(strBuilder.toString(),"ISO-8859-1");
			
			
			Elements options = document.getElementsByTag("option");
			for(Element str : options){
				if(!str.val().equals("0"))
				optionsMaped.put(str.val(), convertTagsHTMLCaracteres(str.html()));
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return optionsMaped;
	}
	
	public List<Pair<String, Boolean>> mapTable() {
		List<Pair<String, Boolean>> listPair = new ArrayList<Pair<String,Boolean>>();
		try {
			InputStream in = getInputStreamFromWeb();
			Document document = Jsoup.parse(in,"ISO-8859-1","");
			Elements table = document.select("table[ID=table_synoptic]");
			Elements trs = table.select("tr");
			
			for (Element elemens : trs) {
				Elements tdDesc = elemens.select("td[class=td_desc]");
				Elements tdCodBus = elemens.select("td[class=td_cod_bus]");
				for (Element td : tdDesc) {
					Pair<String, Boolean> pair;
					if(tdCodBus.isEmpty()){
						pair = new Pair<String, Boolean>(convertTagsHTMLCaracteres(td.select("a").html()), Boolean.FALSE);
					}else{
						pair = new Pair<String, Boolean>(convertTagsHTMLCaracteres(td.select("a").html()), Boolean.TRUE);
					}
					listPair.add(pair);
						
				}
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return listPair;
	}
	
	public InputStream getInputStreamFromWeb() throws IOException{
		URL url = new URL(this.url);
		URLConnection conexao = url.openConnection();
		conexao.setConnectTimeout(5 * 1000);
		conexao.connect();
		InputStream in = url.openStream();
		return in;
		
	}
	
	public String convertTagsHTMLCaracteres(String htmlString) {

		try {

			htmlString = htmlString.replace("&Agrave;", "À")

			.replace("&Aacute;", "Á")

			.replace("&Acirc;", "Â")

			.replace("&Atilde;", "Ã")

			.replace("&agrave;", "à")

			.replace("&aacute;", "á")

			.replace("&acirc;", "â")

			.replace("&atilde;", "ã")

			.replace("&Ograve;", "Ò")

			.replace("&Oacute;", "Ó")

			.replace("&Ocirc;", "Ô")

			.replace("&Otilde;", "Õ")

			.replace("&ograve;", "ò")

			.replace("&oacute;", "ó")

			.replace("&ocirc;", "ô")

			.replace("&otilde;", "õ")

			.replace("&Egrave;", "È")

			.replace("&Eacute;", "É")

			.replace("&Ecirc;", "Ê")

			.replace("&egrave;", "è")

			.replace("&eacute;", "é")

			.replace("&ecirc;", "ê")

			.replace("&Igrave;", "Ì")

			.replace("&Iacute;", "Í")

			.replace("&igrave;", "ì")

			.replace("&iacute;", "í")

			.replace("&Ugrave;", "Ù")

			.replace("&Uacute;", "Ú")

			.replace("&ugrave;", "ù")

			.replace("&uacute;", "ú")

			.replace("&Ccedil;", "Ç")

			.replace("&ccedil;", "ç")

			.replace("&circ;", "^")

			.replace("&tilde;", "~")

			.replace("&167;", "º")

			.replace("&166;", "ª");

		} catch (Exception e) {

			e.printStackTrace();

		}

		return htmlString;

	}
	
}
