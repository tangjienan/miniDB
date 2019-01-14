package db.Utils;

import java.io.*;
import java.util.ArrayList;
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


    // left adjust
    public static List<String> fullJustify(List<String> list, int maxWidth) {
        // 1 greedily pick words
        // 2 normal line
        // 3 last line
        // 4 padd zero behind



        String[] words = new String[list.size()];
        int index = 0;
        for (String str : list) {
            words[index++] = str;
        }

        List<String> res = new ArrayList<>();
        int curLen = 0;
        List<String> cur = new ArrayList<>();
        for (int i = 0; i < words.length; i++) {
            if (curLen + words[i].length() > maxWidth) {
                String str = "";
                if (cur.size() != 1) {
                    str = normalLine(cur, maxWidth, curLen - 1);
                }
                else {
                    str = lastLine(cur, maxWidth);
                }
                res.add(str);
                cur.clear();
                curLen = 0;
            }
            curLen += words[i].length() + 1;
            cur.add(words[i]);
        }
        String lst = lastLine(cur, maxWidth);
        res.add(lst);
        return res;
    }


    private static  String normalLine(List<String> strings, int maxWidth, int curLen) {
        int totalSpace = maxWidth - curLen + strings.size() - 1;
        int avgSpace = totalSpace / (strings.size() - 1);
        int remainder = totalSpace % (strings.size() - 1);
        StringBuilder sb = new StringBuilder();
        for (int index = 0; index < strings.size(); index++) {
            String str = strings.get(index);
            sb.append(str);
            if (index != strings.size() - 1) {
                for (int i = 0; i < avgSpace; i++) {
                    sb.append(" ");
                }
            }
            if (remainder != 0) {
                sb.append(" ");
                remainder -= 1;
            }
        }
        return sb.toString();
    }

    private static String lastLine(List<String> strings, int maxWidth) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < strings.size(); i++) {
            String str = strings.get(i);
            sb.append(str);
            if (i < strings.size() - 1) {
                sb.append(" ");
            }
        }

        while (sb.length() < maxWidth) {
            //System.out.println("dsadas");
            sb.append(" ");
        }
        return sb.toString();
    }

    private static String paddZero(String str, int maxWidth) {
        return "";
    }
}
