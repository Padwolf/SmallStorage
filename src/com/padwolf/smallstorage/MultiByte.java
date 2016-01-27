package com.padwolf.smallstorage;

import java.util.Arrays;

/**
 * Created by 970098955 on 1/10/2016.
 */
public class MultiByte {

    public static final char[] INTS = {'2', '3', '4', '5', '6', '7'};

    private Object[] cnt;
    private String fmt;

    public MultiByte(Object[] content, String format){
        char[] tmp = format.toCharArray();
        for (char c : tmp){
            if (!(c == ' ' || c == 'b' || isIn(INTS, c)))
                throw new IllegalArgumentException("The character '" + c
                    + "' is not a valid format character");
        }

        cnt = content;
        fmt = format;
    }

    private boolean isIn(char[] cs, char val){
        boolean temp = false;
        for (char tmp : cs){
            if (val == tmp){
                temp = true;
                break;
            }
        }
        return temp;
    }

    public Object[] getContents(){
        return cnt;
    }

    public String getFormat(){
        return fmt;
    }

    public String[] getLongFormat(){
        String[] output = new String[fmt.toCharArray().length];

        for (int i = 0; i < fmt.toCharArray().length; i++){
            if (fmt.toCharArray()[i] == ' '){
                output[i] = "null";
            } else if (fmt.toCharArray()[i] == 'b'){
                output[i] = "boolean";
            } else if (Arrays.asList(INTS).contains(fmt.toCharArray()[i])){
                output[i] = "integer";
            }
        }

        return output;
    }

    public void setFormat(String format){
        fmt = format;
    }

    public void setContent(Object[] content){
        cnt = content;
    }

    public void setContentAt(int position, Object[] object){
        cnt[position] = object;
    }

}
