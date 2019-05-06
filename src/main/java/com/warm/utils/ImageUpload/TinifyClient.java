package com.warm.utils.ImageUpload;


import com.tinify.Tinify;

import java.io.IOException;

public class TinifyClient {

    private static final String key = "EEqvAJV9ZtqVqkHmfvdafqa2llwxoR5y";
   public static byte[] getbyteImageBybyte(byte[] by){
       Tinify.setKey(key);
       byte[] bytes = null;
       try {
           System.err.println("-------------------压缩-------------------------");
           bytes = Tinify.fromBuffer(by).toBuffer();
       } catch (IOException e) {
           e.printStackTrace();
       }
       System.err.println("------------------------结束-------------------------");
       return bytes;
   }
}
