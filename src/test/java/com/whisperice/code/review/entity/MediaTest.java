package com.whisperice.code.review.entity;

import com.whisperice.code.review.service.MediaBundleReader;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

import static junit.framework.TestCase.*;

public class MediaTest {
    private static MediaBundleReader mediaBundleReader;

    @BeforeClass
    public static void beforeClass() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-context.xml");
        mediaBundleReader = (MediaBundleReader) applicationContext.getBean("mediaBundleReader");
    }

    @Test
    public void testSortBundles() throws Exception {
        mediaBundleReader.load("MediaBundles.txt");
        List<Media> mediaList = MediaBundleReader.getMediaList();
        mediaList.forEach(Media::sortBundles);

        assertEquals(new Bundle(10, 800.0), mediaList.get(0).getBundles().get(0));
        assertEquals(new Bundle(9, 1147.50), mediaList.get(1).getBundles().get(0));
        assertEquals(new Bundle(9, 1530), mediaList.get(2).getBundles().get(0));
    }
}
