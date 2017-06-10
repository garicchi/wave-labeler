package lib;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Created by garicchi on 2015/11/07.
 */
public class CsvManager {
    public static List<String[]> Read(String filePath,String splitStr,String encode,boolean isHeaderRead,BiConsumer<String[],Integer> callBack) throws IOException {
        List<String[]> dataList = new ArrayList<String[]>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filePath)),encode));
        String line = "";
        if(isHeaderRead) {
            reader.readLine();
        }
        int index = 0;
        while((line = reader.readLine())!=null){
            String[] cols = line.split(splitStr);
            callBack.accept(cols,index);
            dataList.add(cols);
            index++;
        }
        reader.close();
        return dataList;
    }

    public static void Write(String filePath,String encode,String header,int dataSize,Function<Integer,String> callBack) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(filePath)),encode));
        if(!header.equals("")){
            writer.write(header);
            writer.newLine();
        }
        for(int i=0;i<dataSize;i++){
            String line = callBack.apply(i);
            if(line != null){
                writer.write(line);
                writer.newLine();
            }
        }
        writer.close();
    }
}
