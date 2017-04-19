package core;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created on 17-4-18.
 * 一次只能由一个出租车输出信息到文件
 */
class SafeFile implements GlobalConstant{
    private Path outFile = Paths.get(PASSENGER_OUT);
    private BufferedWriter bw;
    private HashMap<String,String> allInfoSets = new HashMap<>(100);
    SafeFile(){
        try {
            bw = Files.newBufferedWriter(outFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    synchronized void writetoFile(String hashString,String info){
        String temp = allInfoSets.get(hashString);
        if(temp!=null)
            allInfoSets.put(hashString,temp+info+"\n");
        else
            allInfoSets.put(hashString,info);
    }
    void outPutToFile(){
        Iterator iter = allInfoSets.entrySet().iterator();
        while(iter.hasNext()){
            Map.Entry entry = (Map.Entry) iter.next();
            try {
                bw.write("请求********"+entry.getKey()+"***************\n");
                bw.write((String) entry.getValue());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}