package com.zzq.util;

import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImageUtil {

    public static void main(String[] args) throws Exception {
        String [] arr = new String[]{"slideInLeft","bounceInDown","flipInX","zoomInUp","rotateIn"};
        ArrayList<String> code = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(  new File("D:/p.txt")));
        String p = "";
        while ( (p=br.readLine())!=null ){
            if( p.indexOf("、")!=-1 ){
                code.add( p.substring(p.indexOf("、")+1 ) );
            }
        }

        File file = new File("E:\\mm");
        int i = 1;
        String html = "";
        for (File f: file.listFiles()) {
             html += "<div class=\"swiper-slide slide-"+ ((int)(Math.random()*5) + 1 ) +"\">\n" +
                    "            <div style=\"background-image: url('mm/"+ f.getName() +"');\"  class=\"item-image animated\" data-ani-name=\""+ arr[(int)(Math.random()*arr.length)] +"\" data-ani-duration=\"1s\"\n" +
                    "                 data-ani-delay=\"0s\"></div>\n" +
                    "            <p class=\"item-text animated\" data-ani-name=\""+arr[(int)(Math.random()*arr.length)]+"\" data-ani-duration=\"1s\" data-ani-delay=\"0.3s\">\n" +
                    "                "+ code.get( (int)(Math.random()*code.size()) )+"\n" +
                    "            </p>\n" +
                    "        </div>";

            i++;
        }
        System.out.println( html );
    }

}

class ImageCompree2 implements Runnable{

    private File infile ;
    private File tofile ;

    public ImageCompree2(File infile, File tofile) {
        this.infile = infile;
        this.tofile = tofile;
    }

    @Override
    public void run() {
        try{
            Thumbnails
                    .of( infile )
                    .scale(0.5f)
                    .outputQuality(0.5f)
                    .toFile( tofile );
        }catch (Exception e){

        }
    }
}


class ImageCompress implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(ImageCompress.class);

    private byte[] inFileByte;

    private byte[] toFileByte;

    public ImageCompress(byte[] inFileByte, byte[] toFileByte) {
        this.inFileByte = inFileByte;
        this.toFileByte = toFileByte;
    }

    /**
     * 字节数组转 文件，临时文件
     * @return
     */
    private File byteToFile(){
        try {
            File temp = File.createTempFile(UUID.randomUUID().toString(), ".temp");
            BufferedOutputStream bos = new BufferedOutputStream(  new FileOutputStream(temp));
            bos.write(inFileByte);

            return temp;
        } catch (IOException e) {
            e.printStackTrace();
            logger.debug("字节转图片失败！");
            return null;
        }
    }

    @Override
    public void run() {
        try {
            File inFile = byteToFile();
            File toFile = File.createTempFile(UUID.randomUUID().toString(), ".temp");
            Thumbnails
                    .of( inFile )
                    .scale(1f)
                    .outputQuality(0.1f)
                    .toFile( toFile );
            // 此处就可以file 转byte 了
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
