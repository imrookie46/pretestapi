package id.hci.api.helper;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Tools {

    public static String getMd5(String input)
    {
        try {

            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // digest() method is called to calculate message digest
            //  of an input digest() return array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getInitialName(String name) {
        if ( name == null) {
            return "00";
        }
        name = name.replaceAll("[^a-zA-Z0-9 ]", "");
        // replace multiple space to be 1
        name = name.replaceAll("( +)", " ");
        System.out.println("----------> name:" + name);
        String result = "";
        String tmp[] = name.split(" ");
        if ( tmp.length >= 2) {
            result = tmp[0].toUpperCase().substring(0,1);
            result += tmp[1].toUpperCase().substring(0,1);

        } else if ( tmp.length == 1) {
            if (tmp[0].length() >= 2) {
                result = tmp[0].toUpperCase().substring(0,2);
            } else {
                result = "0"+tmp[0].toUpperCase();
            }
        }
        return result;
    }

    public static String getYYMMDD() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        return sdf.format(new Date());
    }

}
