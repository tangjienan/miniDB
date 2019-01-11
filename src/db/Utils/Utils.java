package db.Utils;

import db.MetaData;

import java.io.File;

/**
 * Created by donezio on 1/11/19.
 */
public class Utils {


    public static boolean containsTable(String tableName) throws Exception {
        String dbDirectory = MetaData.dbDirectory();
        File dir = new File(dbDirectory);
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                if (child.getName().equals(tableName)) {
                    System.out.println("Duplicate table name");
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

}
