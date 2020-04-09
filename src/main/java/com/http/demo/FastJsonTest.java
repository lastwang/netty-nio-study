package com.http.demo;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class FastJsonTest {

    public static void main(String[] args) throws IOException {
        String s = FileUtils.readFileToString(new File("C:\\Users\\wang\\Downloads\\twitter"), Charset.defaultCharset());
        Document doc = Jsoup.parse(JSONObject.parseObject(s).getString("items_html"));

        parse(doc);

    }

    private static void parse(Document doc) {

        Map<String, Map<String, Object>> map = new HashMap<String, Map<String, Object>>();
//      JSONObject map = new JSONObject();

        Elements lis = doc.select("li.js-stream-item");
        for (Element element : lis) {
            try {
                String tid = element.attr("data-item-id");
                String content = element.select("p.js-tweet-text").text();
                String time = Long.parseLong(element.select("a.tweet-timestamp span").attr("data-time")) * 1000 + "";
//              Map<String,Object> j = new HashMap<String, Object>();
                JSONObject j = new JSONObject();
                j.put("id", tid);
                j.put("content", content);
                j.put("time", time);
                map.put(tid, j);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.println(map);

    }

}
