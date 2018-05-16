package com.github.dimzak.neo4jslicer.helpers;

import com.github.dimzak.neo4jslicer.constants.FileSize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Helper class regarding file handling
 */
public class FileHelpers {

    private static final Logger log = LoggerFactory.getLogger(FileHelpers.class);

    public static boolean fileExists(String path) {
        return new File(path).exists();
    }

    public static long getFileSize(String path, FileSize fileSize) {
        long size = 0;
        switch (fileSize) {
            case BYTES:
                size = new File(path).length();
                break;
            case KILOBYTES:
                size = new File(path).length() / 1024;
            case MEGABYTES:
                size = new File(path).length() / (1024 ^ 2);
                break;
            case GIGABYTES:
                size = new File(path).length() / (1024 ^ 3);
                break;
        }
        return size;
    }

    public static long getFileLineCount(String path) {
        long lines =0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            while (reader.readLine() != null) lines++;
            reader.close();
        } catch (Exception e ) {
            log.error("An exception occurred while getting line count from " + path);
        }
        return lines;
    }
}
