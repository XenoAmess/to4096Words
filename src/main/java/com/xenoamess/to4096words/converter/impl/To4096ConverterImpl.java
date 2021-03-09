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
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;

public class To4096ConverterImpl implements To4096Converter {

    @Override
    public void convert(@NotNull File inputFile, @NotNull File outputFolder) throws IOException {
        String fileName = inputFile.getName();

        if (!outputFolder.isDirectory()) {
            throw new IllegalArgumentException("!outputFolder.isDirectory()");
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ForcastingRangeEncodingEncoder encoder = new ForcastingRangeEncodingEncoder(inputFile.length(), outputStream);
        encoder.encodeAll(FileUtils.openInputStream(inputFile));
        outputStream.close();
        byte[] rawData = outputStream.toByteArray();
        List<SingleTimeDto> singleTimeDtos = new ArrayList<>();
        byte[] res = Base64.getEncoder().encode(rawData);
        String content = new String(res, StandardCharsets.UTF_8);
        int totalLength = content.length();
        int times = totalLength / 4000;
        if (totalLength % 4000 != 0) {
            times++;
        }
        for (int i = 0; i * 4000 < totalLength; i++) {
            SingleTimeDto singleTimeDto = new SingleTimeDto();
            singleTimeDto.setI(i);
            singleTimeDto.setS(times);
            singleTimeDto.setH(fileName);
            singleTimeDto.setValue(content.substring(i * 4000, Math.min(totalLength, (i + 1) * 4000)));
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
