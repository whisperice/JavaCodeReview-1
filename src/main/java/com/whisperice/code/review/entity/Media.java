package com.whisperice.code.review.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Media {
    private String format;
    private String formatCode;
    private List<Bundle> bundles = new ArrayList<>();

    public Media(String format, String formatCode) {
        this.format = format;
        this.formatCode = formatCode;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getFormatCode() {
        return formatCode;
    }

    public void setFormatCode(String formatCode) {
        this.formatCode = formatCode;
    }

    public List<Bundle> getBundles() {
        return Collections.unmodifiableList(bundles);
    }

    public void addBundle(Bundle bundle) {
        if (bundles == null) {
            bundles = new ArrayList<>();
        }

        // if price and number of bundles are the same, skip to add.
        for (Bundle bundleInList : bundles) {
            if (bundleInList.equals(bundle)) {
                return;
            }
        }

        bundles.add(bundle);
    }

    // ensure the bundles in the increasing order from lowest average price to highest average price
    public void sortBundles() {
        bundles = bundles.stream().sorted(Comparator.comparingDouble(Bundle::getAveragePrice)).collect(
                Collectors.toList());
    }

    @Override
    public String toString() {
        return "Media{" +
               "format='" + format + '\'' +
               ", formatCode='" + formatCode + '\'' +
               ", bundles=" + bundles +
               '}';
    }
}
