package db.Operations;

import db.Command.DBStatus;
import db.MetaData;
import db.Utils.Utils;

import java.util.Map;

/**
 * Created by donezio on 1/11/19.
 */
public class Insert extends Operations {
    private static Insert insert;

    private Insert() {

    }

    public static Insert getInstance() {
        if (insert == null) {
            insert = new Insert();
        }

        return insert;
    }



    public DBStatus insertTo(String tableName, Map<String, String> columnToValue, String pk) {

        Utils.saveMapToLocation(columnToValue, MetaData.dbDirectory() + "/" + tableName + "/" + pk);
        OpUtils.getInstance().metaTableUpdate(OpsType.Insert, tableName, pk);
        if (MetaData.test().equals("TRUE")) {
            System.out.println("These are the inserted data");
            Map<String, String> map = Utils.createMapFromFile(MetaData.dbDirectory() + "/" + tableName + "/" + pk);
            Utils.printHashMap(map);
            System.out.println("=============================");
            System.out.println("These are the meta data for table");
            map = Utils.createMapFromFile(MetaData.dbDirectory() + "/" + tableName + "/" + "tableMetaData");
            Utils.printHashMap(map);
            System.out.println("=============================");
        }
        return DBStatus.Success;
    }

}
