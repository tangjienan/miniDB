package db;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by donezio on 1/11/19.
 */
public class MetaData {

    private static Set<String> keyWords = new HashSet<String>() {
        private static final long serialVersionUID = 1031377763931961017L;
        {
            add("dbDirectory");
            add("TEST");
        }
    };;


    private static HashMap<String, String> metaDataMap;

    private static MetaData metaData;

    private MetaData() {
        metaDataMap = new HashMap<>();
        parseProperties();
    }

    private void parseProperties() {
        try {
            File file = new File("Properties");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] args = line.split(":");
                if (keyWords.contains(args[0])){
                    switch (args[0]) {
                        case "dbDirectory":
                            metaDataMap.put("dbDirectory", args[1]);
                            break;
                        case "TEST":
                            metaDataMap.put("TEST", args[1]);
                            break;
                    }
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error reading property file");
            e.printStackTrace();
        }
    }

    public static MetaData getInstance() {
        if (metaData == null) {
            metaData = new MetaData();
        }
        return metaData;
    }


    public static String dbDirectory() {
        if (metaDataMap.containsKey("dbDirectory")) {
            return  metaDataMap.get("dbDirectory");
        }
        else {
            return null;
        }
    }

    public static String test() {
        if (metaDataMap.containsKey("TEST")) {
            return  metaDataMap.get("TEST");
        }
        else {
            return null;
        }
    }


}
