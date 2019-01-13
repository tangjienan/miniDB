package db.Operations;

import db.Command.DBStatus;
import db.MetaData;
import db.Utils.Utils;

import java.io.File;
import java.util.*;

/**
 * Created by donezio on 1/11/19.
 */
public class Create extends Operations {
    private static Create create;

    private Create() {

    }

    public static Create getInstance() {
        if (create == null) {
            create = new Create();
        }

        return create;
    }


    public DBStatus createTable(String tableName, Set<String> columnName, Set<String> autoIncrement, String pk) {
        System.out.println("createing table");
        File tablePath = new File(MetaData.dbDirectory() + "/" + tableName);

        Map<String, List<String>> map = new HashMap<>();
        Map<String, String> index = new HashMap<>();
        // insert place holder
        index.put("PRIMARY_KEY", "PRIMARY_KEY");

        List<String> count = new ArrayList<>();

        for (String str: autoIncrement) {
            count.add("0");
        }

        List<String> tmp = new ArrayList<>();
        tmp.addAll(count);
        map.put("AI_COUNT", tmp);

        tmp = new ArrayList<>();
        tmp.add("0");
        map.put("COUNT", tmp);

        tmp = new ArrayList<>();
        tmp.add(tableName);
        map.put("TABLE", tmp);

        tmp = new ArrayList<>();
        tmp.add(pk);
        map.put("PK", tmp);

        tmp = new ArrayList<>();
        tmp.addAll(autoIncrement);
        map.put("AI", tmp);

        tmp = new ArrayList<>();
        tmp.addAll(columnName);
        map.put("COLUMN", tmp);


        if (tablePath.mkdir()) {
            Utils.saveMapToLocation(map,MetaData.dbDirectory() + "/" + tableName + "/tableMetaData");
            Utils.saveMapToLocation(index,MetaData.dbDirectory() + "/" + tableName + "/tableIndex");
            if (MetaData.test().equals("TRUE")) {
                System.out.println("Meta data stored");
                Map<String, List<String>> m = Utils.createMapFromFile(MetaData.dbDirectory() + "/" + tableName + "/tableMetaData");
                Utils.printHashMap(m);
                System.out.println("=================================");
            }
            return DBStatus.Success;
        } else {
            System.out.println("Fail in creating table");
            return DBStatus.Fail;
        }
    }


}
