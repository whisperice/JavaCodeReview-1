package com.whisperice.code.review.service;

import com.whisperice.code.review.entity.Bundle;
import com.whisperice.code.review.entity.Media;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MediaBundleReader {
    private static final String MEDIA_FILE_HEADER = "SUBMISSION";
    private static final String BUNDLE_SEPARATOR = "@$";
    private static final String EMPTY_STRING = "";
    private static final int MEDIA_FORMAT_INDEX = 0;
    private static final int MEDIA_FORMAT_CODE_INDEX = 1;
    private static final int MEDIA_BUNDLES_INDEX = 2;

    private static final List<Media> mediaList = new ArrayList<>();

    public static List<Media> getMediaList() {
        return Collections.unmodifiableList(mediaList);
    }

    // Can ensure the bundles in the increasing order from lowest average price to highest average price
    // todo: may extract the file loading strategy
    public static void load(String fileName) throws Exception {
        try (InputStream inputStream = MediaBundleReader.class.getClassLoader().getResourceAsStream(fileName);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                addMediaToList(line);
            }

            mediaList.forEach(Media::sortBundles);
        } catch (NullPointerException | IOException e) {
            System.out.println("Load MediaBundle File Failed.");
            e.printStackTrace();
            throw new Exception("Load MediaBundle File Failed.", e);
        }
    }

    private static void addMediaToList(String line) {
        String[] strings = StringUtils.split(StringUtils.trimToEmpty(line.toUpperCase()));

        // Drop the first line of MediaBundles File, since it is the header
        if (!StringUtils.equals(EMPTY_STRING, strings[MEDIA_FORMAT_INDEX]) &&
            !StringUtils.equals(MEDIA_FILE_HEADER, strings[MEDIA_FORMAT_INDEX])) {

            Media media = new Media(strings[MEDIA_FORMAT_INDEX], strings[MEDIA_FORMAT_CODE_INDEX]);

            for (int i = MEDIA_BUNDLES_INDEX; i < strings.length; i++) {
                String[] bundle = StringUtils.split(strings[i], BUNDLE_SEPARATOR);
                media.addBundle(new Bundle(Integer.parseInt(bundle[0]), Double.parseDouble(bundle[1])));
            }

            // Merge bundles when format and format code are the same
            for (Media mediaInList : mediaList) {
                if (StringUtils.equals(mediaInList.getFormat(), media.getFormat()) &&
                    StringUtils.equals(mediaInList.getFormatCode(), media.getFormatCode())) {
                    media.getBundles().forEach(mediaInList::addBundle);
                    return;
                }
            }

            mediaList.add(media);
        }

    }
}

