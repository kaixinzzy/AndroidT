package com.zzy.jsoup;

import com.orhanobut.logger.Logger;
import com.zzy.event.ac.BuildConfig;
import com.zzy.jsoup.data.Comic;
import com.zzy.jsoup.data.LargeHomeItem;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TencentComicAnalysis {
    private static final String TAG = "TencentComicAnalysis";

    /**
     * banner的获取
     * @param doc
     * @return
     */
    public static List<Comic> TransToBanner(Document doc){
        List<Comic> mdats = new ArrayList<>();
        Element detail = doc.getElementsByAttributeValue("class","banner-list").get(0);
        List<Element> infos = detail.getElementsByTag("a");
        for(int i=0;i<infos.size();i++){
            Comic comic = new Comic();
            comic.setTitle(infos.get(i).select("a").attr("title"));
            comic.setCover(infos.get(i).select("img").attr("src"));
            try{
                comic.setId(Long.parseLong(getID(infos.get(i).select("a").attr("href"))));
                mdats.add(comic);
            }catch (Exception e){
            }
        }
        if (BuildConfig.DEBUG) Logger.d("banner的获取\n" + mdats);

        return mdats;
    }

    /**
     * 热门连载
     * @param doc
     * @return
     */
    public static List<LargeHomeItem> TransToNewComic(Document doc){
        List<LargeHomeItem> mdats = new ArrayList<LargeHomeItem>();
        Random random =new Random();
        int result = random.nextInt(7);
        Elements elements = doc.getElementsByAttributeValue("class","in-anishe-list clearfix in-anishe-ul");

        System.out.println("elements size " + elements.size());
        Element detail = elements.get(result);
        List<Element> hots = detail.getElementsByTag("li");
        for(int i=0;i<4;i++){
            LargeHomeItem comic = new LargeHomeItem();
            comic.setTitle(hots.get(i).select("img").attr("alt"));
            comic.setCover(hots.get(i).select("img").attr("data-original"));
            Element ElementDescribe = hots.get(i).getElementsByAttributeValue("class","mod-cover-list-intro").get(0);
            comic.setDescribe(ElementDescribe.select("p").text());
            comic.setId(Long.parseLong(getID(hots.get(i).select("a").attr("href"))));
            mdats.add(comic);
        }
        if (BuildConfig.DEBUG) Logger.d("热门连载\n" + mdats);

        return mdats;
    }

    /**
     * 获取漫画ID
     * @param splitID
     * @return
     */
    public static String getID(String splitID){
        String[] ids = splitID.split("/");
        return ids[ids.length-1];
    }

}
