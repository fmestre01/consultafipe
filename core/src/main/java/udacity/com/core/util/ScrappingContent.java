package udacity.com.core.util;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ScrappingContent {

    public static String testJsoup(String source) throws JSONException {
        Document doc = Jsoup.parse(source);
        JSONObject jsonParentObject = new JSONObject();

        Elements table = doc.select("table");
        Elements span = table.select("span");
        for (Element src : span) {
            if (src.tagName().equals("span")) {
                JSONObject jsonObject = new JSONObject();
                String texto = table.text();
                jsonObject.put("texto", src.attr("class"));
                jsonParentObject.put(texto, jsonObject);
            }
        }

        return jsonParentObject.toString();
    }
}
