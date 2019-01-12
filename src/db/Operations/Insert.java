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



        return DBStatus.Fail;
    }

}
