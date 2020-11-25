package service;

import java.io.File;

public class CheckConversionSuccesfulness{

    public static String isWritingSuccesfully(String path) {
        File destinationFolder = new File(path);
        File[] filesInDestinationFolder = destinationFolder.listFiles();
        return filesInDestinationFolder.length != 0 ? "Converted" : "Conversion problem";
    }
}
