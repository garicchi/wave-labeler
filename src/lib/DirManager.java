package lib;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by garicchi on 16/05/15.
 */
public class DirManager {
    private static void readFolder( File dir,String keyword,List<String> result) {

        File[] files = dir.listFiles();
        if (files == null)
            return;
        for (File file : files) {
            if (!file.exists())
                continue;
            else if (file.isDirectory())
                readFolder(file, keyword,result);
            else if (file.isFile())
                if (file.getName().contains(keyword)) {
                    result.add(file.getPath());
                }
        }
    }
    public static List<String> list_files(String root,String keyword){
        List<String> files = new ArrayList<String>();
        readFolder(new File(root),keyword,files);
        return files;
    }

    public static String getExt(String fileName) {
        String newName;

        int lastPosition = fileName.lastIndexOf('.');
        if (lastPosition > 0) {
            newName = fileName.substring(lastPosition,fileName.length());
        } else {
            newName = fileName;
        }

        return newName;
    }

    public static String getNoExt(String fileName) {
        String newName;
        int lastPosition = fileName.lastIndexOf('.');
        if (lastPosition > 0) {
            newName = fileName.substring(0, lastPosition);
        } else {
            newName = fileName;
        }

        return newName;
    }
}
