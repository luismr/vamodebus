package br.com.vamodebus.crawler;

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

/**
 * Created by Eduardo Silva Rosa on 31/05/2013.
 * mail to: edus.silva.rosa@gmail.com
 */
public class ParserHtml {

	//FIXME arrumar isto urgente!
	public static String url;

	public ParserHtml(String url) {
		this.url = url;
	}
	
	public static HashMap<String, String> mapOptions() {
		HashMap<String, String> optionsMaped = new HashMap<String, String>();
		try {
			
			InputStream in = getInputStreamFromWeb();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in,"ISO-8859-1"));
			StringBuilder strBuilder = new StringBuilder();
			String line;
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

    public String getHtml(){
        StringBuilder strBuilder = new StringBuilder();
        try{
            InputStream in = getInputStreamFromWeb();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in,"ISO-8859-1"));
            String line;
            while((line = reader.readLine()) != null)
            {
                strBuilder.append(line);
                //ISO-8859-1
            }
                in.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        return strBuilder.toString();

    }
	
	public static List<Pair<String, Boolean>> mapTable(String strBuilder) {
		List<Pair<String, Boolean>> listPair = new ArrayList<Pair<String,Boolean>>();

			Document document = Jsoup.parse(strBuilder.toString(),"ISO-8859-1");
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


		return listPair;
	}
	
	public static InputStream getInputStreamFromWeb() throws IOException{
		URL url1 = new URL(url);
		URLConnection conexao = url1.openConnection();
		conexao.setConnectTimeout(5 * 1000);
		conexao.connect();
		InputStream in = url1.openStream();
		return in;
		
	}
	
	public static String convertTagsHTMLCaracteres(String htmlString) {

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
