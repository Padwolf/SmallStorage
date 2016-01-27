package com.padwolf.smallstorage;

import java.util.Arrays;

/**
 * Created by 970098955 on 1/8/2016.
 */

public class SmallStorage {

    public static void main(String[] args){
//        for (char temp : MultiByte.INTS){
//            System.out.println(temp);
//        }

//        System.out.println(MultiByte.INTS[0] == '2');
//        System.out.println(Arrays.asList(MultiByte.INTS).contains('2'));

        Object[] stuff = {2, false, null, -5};
        MultiByte mb = new MultiByte(stuff, "2b 4");

        //"10, 0, 0/1, "

        //System.out.println(Byte.toBinaryString(encodeToByte(mb)));
        System.out.println(encodeToByte(mb));
        System.out.println();
        System.out.println();

        MultiByte nmb = retrieveFromByte(encodeToByte(mb), "2b 4");

        for (Object tmp : nmb.getContents()){
            System.out.println(tmp);
        }

    }

    public static MultiByte retrieveFromByte(byte bte, String format){

        int pos = 0;

        Object[] output = new Object[format.toCharArray().length];

        for (int i = 0; i < format.toCharArray().length; i++){


            if(format.toCharArray()[i] == ' '){
                output[i] = null;
                pos++;
            } else if (format.toCharArray()[i] == 'b'){
                output[i] = !((bte & ((byte)(0b10000000 >> pos))) == 0);

                pos++;
            } else if (isIn(MultiByte.INTS, format.toCharArray()[i])){

                output[i] = (((byte)(bte << pos)) >> 8-Integer.decode(Character.toString(format.toCharArray()[i])));
                pos+=Integer.decode(Character.toString(format.toCharArray()[i]));
            } else{
                throw new IllegalArgumentException("The character '" + format.toCharArray()[i] + "' in position " + Integer.toString(i)
                        + " (byte position " + Integer.toString(pos + 1) + ") is not a valid format");
            }
        }
        return new MultiByte(output, format);
    }

    public static byte encodeToByte(MultiByte multiByte){

        int pos = 0;

        byte output = (byte)0;

        for (int i = 0; i < multiByte.getFormat().toCharArray().length; i++){
            if(multiByte.getFormat().toCharArray()[i] == ' '){
                pos++;
            } else if (multiByte.getFormat().toCharArray()[i] == 'b'){
                if ((boolean)multiByte.getContents()[i]){
                    output = (byte)(output | (0b10000000 >> pos));
                }
                pos++;
            } else if(isIn(MultiByte.INTS, multiByte.getFormat().toCharArray()[i])){

//                if (Integer.decode(Character.toString(multiByte.getFormat().toCharArray()[i])) < 0){
//                    throw new IllegalArgumentException("Numerical values may not be negative");
//                }

                output = (byte)(output | (((byte)((int)multiByte.getContents()[i])) <<
                        (byte)((8-Integer.decode(Character.toString(multiByte.getFormat().toCharArray()[i]))) - pos)));
                        //(byte)((Integer.decode(Character.toString(multiByte.getFormat().toCharArray()[i])))-pos)));

                pos+=Integer.decode(Character.toString(multiByte.getFormat().toCharArray()[i]));

            } else{
                throw new IllegalArgumentException("The character '" + multiByte.getFormat().toCharArray()[i]
                        + "' in position " + Integer.toString(i) + " (byte position "
                        + Integer.toString(pos + 1) + ") is not a valid format");
            }
        }

        return output;
    }

    public static byte encodeToByte(Object[] input, String format){

        int pos = 0;

        byte output = (byte)0;

        for (int i = 0; i < format.toCharArray().length; i++){
            if(format.toCharArray()[i] == ' '){
                pos++;
            } else if (format.toCharArray()[i] == 'b'){
                if ((boolean)input[i]){
                    output = (byte)(output | (0b10000000 >> pos));
                }
                pos++;
            }else if(isIn(MultiByte.INTS, format.toCharArray()[i])){
                output = (byte)(output | (((byte)((int)input[i])) <<
                        ((8-(Integer.decode(Character.toString(format.toCharArray()[i]))))-pos)));
                pos+=Integer.decode(Character.toString(format.toCharArray()[i]));
            } else{
                throw new IllegalArgumentException("The character '" + format.toCharArray()[i] + "' in position " + Integer.toString(i)
                        + " (byte position " + Integer.toString(pos + 1) + ") is not a valid format");
            }
        }

        return output;
    }

    private static boolean isIn(char[] cs, char val){
        boolean temp = false;
        for (char tmp : cs){
            if (val == tmp){
                temp = true;
                break;
            }
        }
        return temp;
    }

}
