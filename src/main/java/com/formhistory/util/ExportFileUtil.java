package com.formhistory.util;

import java.nio.channels.FileChannel;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public final class ExportFileUtil {
    private ExportFileUtil() {
    }

    public static boolean fileExistAndNotEmpty(String path) {
        try {
            Path profilePath = FileSystems.getDefault().getPath(path);
            if (Files.exists(profilePath) && Files.isReadable(profilePath)) {
                FileChannel fc = FileChannel.open(profilePath);
                long size = fc.size();
                fc.close();
                return size > 0;
            }
        } catch(Exception e) {
            return false;
        }
        return false;
    }

    public static boolean checkProfileDirectory(String path) {
        System.out.print("checking profile directory: ");
        boolean pathOkay = false;
        try {
            Path profilePath = FileSystems.getDefault().getPath(path);
            if (Files.exists(profilePath) && Files.isDirectory(profilePath) && Files.isReadable(profilePath)) {
                pathOkay = true;
            }
        } catch(Exception e) {
            pathOkay = false;
        }

        if (pathOkay) {
            System.out.println("OK");
        } else {
            System.out.println("FAILED profilepath directory is not accessible");
        }

        return pathOkay;
    }

    public static String getOutputFilepath(String path, String defaultFilename) {
        String outputFilepath = null;
        System.out.print("checking outputfile:        ");
        try {
            Path outfilePath = FileSystems.getDefault().getPath(path);
            if (outfilePath.getFileName().toString().isEmpty()) {
                outfilePath = FileSystems.getDefault().getPath(path, defaultFilename);
            }

            // get directory and check
            Path directory = outfilePath.getParent();
            if (directory == null || (Files.exists(directory) && Files.isDirectory(directory))) {

                // check if file is writable
                if (Files.exists(outfilePath) && !Files.isWritable(outfilePath)) {
                    System.out.println("FAILED file exists but not writable");
                } else {
                    outputFilepath = outfilePath.toString();
                    System.out.println("OK");
                }

            } else {
                System.out.println("FAILED directory is not accessible");
            }

        } catch(Exception e) {
            System.out.println("FAILED " + e.getMessage());
        }

        return outputFilepath;
    }

}
