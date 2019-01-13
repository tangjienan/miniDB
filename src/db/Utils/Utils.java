package db.Utils;

import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * Created by donezio on 1/11/19.
 */


public class Utils {

    public static <K,Y> void saveMapToLocation(Map<K,Y> map, String location) {
        try {
            // overwrite files
            FileOutputStream fos = new FileOutputStream(location, false);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(map);
            oos.close();

        } catch (FileNotFoundException e) {
            System.out.println("Failed creating file from hashmap");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Failed writing to file");
            e.printStackTrace();
        }
    }


    public static <K,Y> Map<K,Y> createMapFromFile(String location)  {
        FileInputStream fis = null;
        Map<K,Y> res = null;
        try {
            fis = new FileInputStream(location);
            ObjectInputStream ois = new ObjectInputStream(fis);
            res = (Map<K,Y>) ois.readObject();
            ois.close();
            return res;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    // V should be string or List<String>
    public static <V>  void printHashMap(Map<String,V> map) {
        for (Map.Entry<String, V> e : map.entrySet()) {
            String str = null;
            List<String> list = null;
            System.out.print("  " + e.getKey() + ":   ");
            // Check if a String
            try {
                str = (String) e.getValue();
                System.out.print(str + " ");
            } catch (Exception exception) {}

            // Check if a List
            try {
                list = (List<String>) e.getValue();
                for(String s: list) {
                    System.out.print("  " + s + " "  );
                }
            } catch (Exception exception) {}
        }
        System.out.println();
    }
}
