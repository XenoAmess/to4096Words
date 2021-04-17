package com.xenoamess.to4096words.converter.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author XenoAmess
 */
public class FromAndTo4096ConverterImplTest {

    private static final String FILE_NAME = "ACM.png";

    @Test
    public void test1() throws IOException {
        To4096ConverterImpl to4096ConverterImpl = new To4096ConverterImpl();
        to4096ConverterImpl.convert(
                new File("src/test/resources/" + FILE_NAME),
                new File("target")
        );

    }

    @Test
    public void test2() {
        From4096ConverterImpl from4096Converter = new From4096ConverterImpl();
        String nowHash = FILE_NAME;
        for (int i = 0; i < 1000; i++) {
            try {
                from4096Converter.add(
                        FileUtils.readFileToString(
                                new File("target/" + nowHash + "_" + i + ".x8l"),
                                StandardCharsets.UTF_8
                        )
                );
            } catch (Exception ignored) {
//                ignored.printStackTrace();
            }
        }
        from4096Converter.convert(nowHash);
    }

    @Test
    public void test3() throws IOException {
        try (
                InputStream outputInputStream =
                        new ByteArrayInputStream(FileUtils.readFileToByteArray(new File(FILE_NAME)));
                InputStream originalInputStream =
                        this.getClass().getClassLoader().getResourceAsStream(FILE_NAME)
        ) {
            assertTrue(IOUtils.contentEquals(originalInputStream, outputInputStream));
        }
    }
}
