package db.Operations;

import db.Command.DBStatus;
import db.MetaData;
import db.Utils.Utils;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by donezio on 1/11/19.
 */
public class OpUtils extends Operations {
    private static OpUtils utils;

    private OpUtils() {

    }

    public static OpUtils getInstance() {
        if (utils == null) {
            utils = new OpUtils();
        }
        return utils;
    }

    /**
     *
     * @param tableName
     * @return true if the table is already created, otherwise false.
     * @throws Exception database directory error, can't open or not exist.
     */
    public boolean containsTable(String tableName) throws Exception {
        String dbDirectory = MetaData.dbDirectory();
        File dir = new File(dbDirectory);
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                if (child.getName().equals(tableName)) {
                    return true;
                }
            }
        } else {
            // Handle the case where dir is not really a directory.
            // Checking dir.isDirectory() above would not be sufficient
            // to avoid race conditions with another process that deletes
            // directories.
            //TODO: Throws exceptions here
            //System.out.println("Wrong database directory");
            throw new Exception("Wrong database directory");
        }
        return false;
    }

    public boolean containsKey(String tableName, String value) {
        Map<String, String> index = Utils.createMapFromFile(MetaData.dbDirectory() + "/" + tableName + "/tableIndex");
        return index.containsKey(value);
    }

    public DBStatus metaTableUpdate(OpsType t, String tableName, String pk) {
        switch (t) {
            case Insert:
                insert(tableName,pk);
                break;
            case Update:
                break;
            case Delete:
                break;
            default:
                break;
        }
        return DBStatus.Fail;
    }


    private DBStatus insert(String tableName, String pk) {
        // update index map
        Map<String, String> index = Utils.createMapFromFile(MetaData.dbDirectory() + "/" + tableName + "/tableIndex");
        index.put(pk, "pk");
        // update meta data
        Map<String, List<String>> tableMetaData = Utils.createMapFromFile(MetaData.dbDirectory() + "/" + tableName + "/tableMetaData");
        // update Coubt
        List<String> count = tableMetaData.get("COUNT");
        count.set(0, (Integer.parseInt(count.get(0)) + 1) + "");
        // update auto_increment count
        List<String> aiCount = tableMetaData.get("AI_COUNT");
        for (int i = 0; i < aiCount.size(); i++) {
            count.set(0, (Integer.parseInt(aiCount.get(i)) + 1) + "");
        }
        tableMetaData.put("COUNT", count);
        tableMetaData.put("AI_COUNT", aiCount);

        Utils.saveMapToLocation(tableMetaData, MetaData.dbDirectory() + "/" + tableName + "/tableMetaData");
        Utils.saveMapToLocation(index, MetaData.dbDirectory() + "/" + tableName + "/tableIndex");
        return DBStatus.Success;
    }


}
