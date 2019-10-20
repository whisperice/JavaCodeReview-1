package com.whisperice.code.review.service;

import com.whisperice.code.review.entity.Media;
import com.whisperice.code.review.service.MediaBundleReader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class MediaBundleReaderTest {

    @Before
    public void before() {

    }

    @Test
    public void testOneLineRead() throws Exception {
        // Acquire private method object via the reflection
        Class<?> classType = Class.forName("com.whisperice.code.review.service.MediaBundleReader");
        Method method = classType.getDeclaredMethod("addMediaToList", String.class);

        // Suppress IllegalAccessException
        method.setAccessible(true);
        method.invoke(null, "Image\t            IMG\t            5@$450.00 10@$800");
        method.setAccessible(false);

        List<Media> mediaList = MediaBundleReader.getMediaList();
        System.out.println(mediaList);

        Media media = MediaBundleReader.getMediaList().get(0);
        assertEquals("IMAGE", media.getFormat());
        assertEquals("IMG", media.getFormatCode());
    }

    @Test
    public void testBundleFileRead() throws Exception {
        MediaBundleReader.load("MediaBundles.txt");
        MediaBundleReader.load("MediaBundles.txt");
        MediaBundleReader.getMediaList().forEach(System.out::println);
    }

    @Test(expected = Exception.class)
    public void testBundleFileReadWithWrongFileName() throws Exception {
        MediaBundleReader.load("ediaBundles.txt");
        MediaBundleReader.getMediaList().forEach(System.out::println);
    }

    @After
    public void after() {

    }
}
