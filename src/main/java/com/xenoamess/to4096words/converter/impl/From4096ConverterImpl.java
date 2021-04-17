package com.xenoamess.to4096words.converter.impl;

import com.xenoamess.cyan_potion.cyan_zip.forecastingRangeEncoding.ForcastingRangeEncodingDecoder;
import com.xenoamess.to4096words.converter.From4096Converter;
import com.xenoamess.to4096words.dto.SingleTimeDto;
import com.xenoamess.x8l.ContentNode;
import com.xenoamess.x8l.X8lTree;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.concurrent.ConcurrentHashMap;
import org.jetbrains.annotations.NotNull;

public class From4096ConverterImpl implements From4096Converter {

    ConcurrentHashMap<String, ConcurrentHashMap<Integer, String>> pool = new ConcurrentHashMap<>();
    ConcurrentHashMap<String, Integer> sMap = new ConcurrentHashMap<>();

    @Override
    public void convert(@NotNull String outputFolder, @NotNull String hash) {
        Integer sum = sMap.get(hash);
        if (sum == null) {
            throw new RuntimeException("not ready.");
        }
        ConcurrentHashMap<Integer, String> subPool = pool.get(hash);
        if (subPool == null) {
            throw new RuntimeException("not ready.");
        }
        if (subPool.size() != sum) {
            throw new RuntimeException("not ready.");
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < sum; i++) {
            stringBuilder.append(subPool.get(i));
        }
        String encodedContent = stringBuilder.toString();
        byte[] bytes = Base64.getDecoder().decode(encodedContent);
        try (InputStream inputStream = new ByteArrayInputStream(bytes)) {
            File outputFolderFile = new File(outputFolder);
            if (!outputFolderFile.exists()) {
                outputFolderFile.mkdirs();
            }
            FileOutputStream outputStream = new FileOutputStream(new File(outputFolderFile, hash));
            ForcastingRangeEncodingDecoder decoder = new ForcastingRangeEncodingDecoder(inputStream);
            decoder.decodeAll(outputStream);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void convert(@NotNull String hash) {
        this.convert("./", hash);
    }

    @Override
    public void add(@NotNull String inputString) {
        X8lTree x8lTree = X8lTree.load(inputString);
        for (ContentNode contentNode : x8lTree.getRoot().getContentNodesFromChildren()) {
            SingleTimeDto singleTimeDto = new SingleTimeDto();
            singleTimeDto.setS(Integer.parseInt(contentNode.getAttributes().get("s")));
            singleTimeDto.setI(Integer.parseInt(contentNode.getAttributes().get("i")));
            singleTimeDto.setH(contentNode.getAttributes().get("h"));
            singleTimeDto.setValue(contentNode.getTextNodesFromChildren(1).get(0).getTextContent());
            ConcurrentHashMap<Integer, String> subPool = pool.get(singleTimeDto.getH());
            if (subPool == null) {
                subPool = new ConcurrentHashMap<>();
                pool.put(singleTimeDto.getH(), subPool);
            }
            subPool.put(singleTimeDto.getI(), singleTimeDto.getValue());
            sMap.put(singleTimeDto.getH(), singleTimeDto.getS());
        }
    }
}
