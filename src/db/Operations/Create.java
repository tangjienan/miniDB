package db.Operations;

import db.Command.*;
import db.MetaData;

import java.io.File;

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


    public DBStatus createTable(String tableName) {
        System.out.println("createing table");
        File tablePath = new File(MetaData.dbDirectory() + "/" + tableName);
        if (tablePath.mkdir()) {
            return DBStatus.Success;
        } else {
            System.out.println("Fail in creating table");
            return DBStatus.Fail;
        }
    }
}
