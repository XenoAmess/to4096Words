package com.xenoamess.to4096words.converter.impl;

import com.xenoamess.cyan_potion.cyan_zip.forecastingRangeEncoding.ForcastingRangeEncodingEncoder;
import com.xenoamess.to4096words.converter.To4096Converter;
import com.xenoamess.to4096words.dto.SingleTimeDto;
import com.xenoamess.x8l.ContentNode;
import com.xenoamess.x8l.TextNode;
import com.xenoamess.x8l.X8lTree;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;

/**
 * @author XenoAmess
 */
public class To4096ConverterImpl implements To4096Converter {

    public static final int ALLOW_LENGTH_LIMIT = 4000;

    @Override
    public void convert(@NotNull File inputFile, @NotNull File outputFolder) throws IOException {
        String fileName = inputFile.getName();

        if (!outputFolder.isDirectory()) {
            throw new IllegalArgumentException("!outputFolder.isDirectory()");
        }

        byte[] rawData;
        ForcastingRangeEncodingEncoder encoder;
        try(ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            encoder = new ForcastingRangeEncodingEncoder(inputFile.length(), outputStream);
            outputStream.close();
            rawData = outputStream.toByteArray();
        }
        try(
            InputStream inputStream = FileUtils.openInputStream(inputFile)
        ){
            encoder.encodeAll(inputStream);
        }

        List<SingleTimeDto> singleTimeDtos = new ArrayList<>();
        byte[] res = Base64.getEncoder().encode(rawData);
        String content = new String(res, StandardCharsets.UTF_8);
        int totalLength = content.length();
        int times = totalLength / ALLOW_LENGTH_LIMIT;
        if (totalLength % ALLOW_LENGTH_LIMIT != 0) {
            times++;
        }
        for (int i = 0; i * ALLOW_LENGTH_LIMIT < totalLength; i++) {
            SingleTimeDto singleTimeDto = new SingleTimeDto();
            singleTimeDto.setI(i);
            singleTimeDto.setS(times);
            singleTimeDto.setH(fileName);
            singleTimeDto.setValue(content.substring(i * ALLOW_LENGTH_LIMIT, Math.min(totalLength, (i + 1) * ALLOW_LENGTH_LIMIT)));
            singleTimeDtos.add(singleTimeDto);
        }

        String outputFolderString = outputFolder.getAbsolutePath();
        //output
        for (SingleTimeDto singleTimeDto : singleTimeDtos) {
            X8lTree x8lTree = new X8lTree();
            ContentNode contentNode = new ContentNode(x8lTree.getRoot());
            new TextNode(contentNode, singleTimeDto.getValue());
            contentNode.addAttribute("i", singleTimeDto.getI().toString());
            contentNode.addAttribute("s", singleTimeDto.getS().toString());
            contentNode.addAttribute("h", singleTimeDto.getH());
            X8lTree.save(
                    new File(outputFolderString + "/" + fileName + "_" + singleTimeDto.getI() + ".x8l"),
                    x8lTree
            );
        }
    }
}
