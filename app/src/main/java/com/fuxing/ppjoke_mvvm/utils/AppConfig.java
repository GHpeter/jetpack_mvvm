package com.fuxing.ppjoke_mvvm.utils;

import android.content.res.AssetManager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.fuxing.ppjoke_mvvm.model.Destination;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * @author ：Create by lichunfu
 * @Date : 2020-04-07
 * Description:
 **/
public class AppConfig {
    private static HashMap<String, Destination> sDestConfig;

    private static String FILENAME = "destination.json";

    public static HashMap<String, Destination> getDestConfig() {
        if (sDestConfig == null) {
            String content = parseFile(FILENAME);
            sDestConfig = JSON.parseObject(content, new TypeReference<HashMap<String, Destination>>() {
            }.getType());

        }
        return sDestConfig;
    }

    private static String parseFile(String fileName) {
        AssetManager assets = AppGloabls.getApplication().getResources().getAssets();
        InputStream stream = null;
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();
        try {
            stream = assets.open(fileName);
            reader = new BufferedReader(new InputStreamReader(stream));
            String line = null;
            while ((line = reader.readLine()) != null) {
                builder.append(line);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
                if (reader != null) {
                    reader.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return builder.toString();

    }

}
