package db.Operations;

import db.MetaData;
import db.Utils.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by donezio on 1/11/19.
 */
public class Select extends Operations {
    private static Select select;

    private Select() {

    }

    public static Select getInstance() {
        if (select == null) {
            select = new Select();
        }

        return select;
    }

    /**
     *
     * @param tableName
     * @param columns
     * @param conditions  [0] column [1] ops [2] value
     */
    public void selectTableColumn(String tableName, List<String> columns, String[] conditions) throws Exception{

        //ops type: "=" 0, "<" -1, ">" 1

        List<List<String>> values = new ArrayList<>();
        String tableDirectory = MetaData.dbDirectory() + "/" + tableName;
        File dir = new File(tableDirectory);
        File[] directoryListing = dir.listFiles();

        if (directoryListing != null) {

            // iterate through each row
            for (File child : directoryListing) {
                if (child.getName().equals("tableIndex") || child.getName().equals("tableMetaData")) continue;
                List<String> row = new ArrayList<>();
                String path = child.getAbsolutePath();
                Map<String, String> map = Utils.createMapFromFile(path);
                //Utils.printHashMap(map);
                // process each column
                boolean canAdd = true;
                for (String column : columns) {
                    if (canAdd == true && conditions[0] != null && conditions[0].length() != 0) {
                        int opsVal = Integer.valueOf(conditions[1]);
                        String tmp = map.get(column);
                        int compareVal = tmp.compareTo(conditions[2]);

                        if (compareVal > 1) compareVal = 1;
                        if (compareVal < 0) compareVal = -1;


                        if (column.equals(conditions[0]) &&  compareVal == opsVal) {
                            canAdd = true;
                        }
                        else if (column.equals(conditions[0]) && compareVal != opsVal){
                            canAdd = false;
                        }
                    }
                    row.add(map.get(column));
                }
                if (canAdd) {
                    values.add(row);
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

        OpUtils.getInstance().displayTable(columns, values, tableName);
    }

}
