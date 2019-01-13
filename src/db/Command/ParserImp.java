package db.Command;


import db.MetaData;
import db.Operations.DBOps;
import db.Utils.Utils;

import java.util.*;

/**
 * Created by donezio on 1/10/19.
 */
public class ParserImp implements Parser{

    private static ParserImp parserImp;

    private static Set<String> keyWords = new HashSet<String>() {
        private static final long serialVersionUID = 1031377713931961017L;
        {
            add("TABLE");
            add("CREATE");
            add("*");
            add("PRIMARY_KEY");
            add("AUTO_INCREMENT");
            add("INSERT");
            add("DROP");
            add("NULL");
            add("VALUES");
            add("FROM");
        }
    };
    private final DBOps dbOps;

    ParserImp(DBOps dbOps) {
        this.dbOps = dbOps;
    }

    public static ParserImp getInstance() {
        if (parserImp == null)  {
            parserImp = new ParserImp(DBOps.getInstance());
        }
        return parserImp;
    }

    @Override
    public DBStatus readLine(String line) {
        line = line.toUpperCase();
        String[] args = line.split("\\s+");
        String command = args[0];
        switch (command) {
            case "INSERT":
                //break;
                return insert(args);
            case "INFO":
                //TODO
                return DBStatus.Exit;
            case "CREATE":
                System.out.println("Hey this is create");
                //break;
                return create(args);
            case "DROP":
                return drop();
            case "UPDATE":
                return update();
            case "SELECT":
                return select(args);
            case "EXIT":
                return DBStatus.Exit;
            default:
                System.out.println("wrong inpit...");
                return DBStatus.Fail;
        }
    }

    // Datatype not supported, all store as String.
    // CREATE Table tableName (colum1 column2 column3..... primary_key (column*))
    public DBStatus create(String[] args) {
        System.out.println("CREATE IS CALLED");
        if (args.length < 8) {
            System.out.println("Arguments too short, check input");
            return DBStatus.Fail;
        }
        if (!args[1].equals("TABLE")) {
            System.out.println("Incorrect command" + args[1]);
            return DBStatus.Fail;
        }

        String tableName = args[2];

        if (keyWords.contains(tableName)) {
            return DBStatus.Exit;
        }
        // Check if tableName is duplicate
        try {
            if ( dbOps.getUtls().containsTable(tableName)) {
                return DBStatus.Fail;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Get Collumn Name and check field
        Set<String> columnNameSet = new HashSet<>();
        Set<String> autoIncreColumn = new HashSet<>();

        StringBuilder sb = new StringBuilder();
        for (int i = 3; i < args.length; i++) {
            sb.append(' ').append(args[i]);
        }

        String columnPart = sb.toString().substring(1);

        String columns = columnPart;
        if (columns.charAt(0) != '(' && columns.charAt(columns.length() - 1) != ')') {
            System.out.println("Incorrect command. " + columns);
            return DBStatus.Fail;
        }
        columns = columns.substring(1, columns.length() - 1);

        String[] columnsNames = columns.split("\\s+");

        String primaryKey = null;

        for (int i = 0; i < columnsNames.length; i++) {
            String tmp = columnsNames[i];
            // Get Primary Key
            if (i == columnsNames.length - 3) {
                if (tmp.equals("PRIMARY") && columnsNames[i + 1].equals("KEY")) {
                    primaryKey = columnsNames[i + 2];
                    if (keyWords.contains(primaryKey)) {
                        System.out.println("Wrong primary key. Primary Key can not be a key word");
                        return DBStatus.Fail;
                    }
                    if (!columnNameSet.contains(primaryKey)) {
                        System.out.println("Wrong primary key. Primary key should be one of the columns");
                        return DBStatus.Fail;
                    }
                }
                break;
            }

            if (keyWords.contains(tmp)) {
                System.out.println("Incorrect command.");
                return DBStatus.Fail;
            }
            else if (!columnNameSet.add(tmp)) {
                System.out.println("Duplicate table name. " + tmp);
                return DBStatus.Fail;
            }
            if (i != columnsNames.length - 1 && columnsNames[i + 1].equals("AUTO_INCREMENT")) {
                autoIncreColumn.add(tmp);
                i++;
            }
        }

        if (primaryKey == null) {
            System.out.println("Primary Key Not Exist.");
            return DBStatus.Fail;
        }

        if (MetaData.test().equals("TRUE")) {

            // testing
            System.out.println("These are the column Name");
            for (String str : columnNameSet) {
                System.out.println(str);
            }
            System.out.println("--------------------");
            System.out.println("These are auto increment column");
            for (String str : autoIncreColumn) {
                System.out.println(str);
            }
            System.out.println("--------------------");
            System.out.println("This is the primary key");
            System.out.println(primaryKey);
        }
        // Create Table

        //DBStatus status = DBStatus.Exit;
        DBStatus status = dbOps.getCreate().createTable(tableName, columnNameSet, autoIncreColumn, primaryKey);
        return status;
    }



    public DBStatus drop(){
        return DBStatus.Success;
    }

    public DBStatus update() {
        return DBStatus.Success;
    }


    //SELECT * FROM table_name;
    //SELECT colimn1, column2 FROM table_name

    public DBStatus select(String[] args) {

        Set<String> whereOps = new HashSet<>();
        whereOps.add("=");
        whereOps.add("<");
        whereOps.add(">");

        int fromIndex = -1;
        int tableIndex = -1;
        int whereIndex = -1;
        int whereColumn = -1;
        int whereOp  = -1;
        int whereVal = -1;


        for (int i = 1; i < args.length; i++) {
            if (args[i].equals("FROM")) {
                if (fromIndex != - 1) {
                    System.out.println("duplicate from keyword");
                    return DBStatus.Fail;
                }
                fromIndex = i;
                if ( i != args.length - 1) {
                    tableIndex = i + 1;
                } else {
                    System.out.println("No table name found");
                    return DBStatus.Fail;
                }
            }

            if (args[i].equals("WHERE")) {
                // after process where i should == args.length - 1, otherwise error
                if (whereIndex != -1) {
                    System.out.println("Too many where keyword");
                    return DBStatus.Fail;
                }
                if (i + 3 != args.length - 1) {
                    System.out.println("Wrong where keyword");
                    return DBStatus.Fail;
                }
                whereIndex = i;
                whereColumn = i + 1;
                whereOp = i + 2;
                whereVal = i + 3;
                break;
            }

        }

        if (fromIndex == -1) {
            System.out.println("keyword FROM missing");
            return DBStatus.Fail;
        }

        // check table

        // check column

        // check where


        // display


        return DBStatus.Success;
    }



//    INSERT INTO table_name (column1, column2, column3, ...)
//    VALUES (value1, value2, value3, ...);  automatically overwrite any values pass to the auto_increment columns

    public DBStatus insert(String[] args) {

        if (args.length < 6) {
            System.out.println("Arguments too short, check input");
            return DBStatus.Fail;
        }
        if (!args[1].equals("INTO")) {
            System.out.println("Incorrect command " + args[1]);
            return DBStatus.Fail;
        }

        String tableName = args[2];


        // TODO: use index to look for key later (HashMap<String, String>)
        try {
            if (!dbOps.getUtls().containsTable(tableName)) {
                System.out.println("Table not exist");
                return DBStatus.Exit;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return DBStatus.Fail;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 3; i < args.length; i++) {
            sb.append(' ').append(args[i]);
        }

        String columnValues = sb.toString().substring(1);

        String[] columnValuesArr = columnValues.split("VALUES");

        if (columnValuesArr.length != 2) {
            System.out.println("Incorrect command, VALUES not found or too many VALUES");
            return DBStatus.Fail;
        }

        if  (columnValuesArr[0].charAt(0) != '(' ) {
            System.out.println(columnValuesArr[0]);
            System.out.println("Incorrect command, columns in correct");
            return DBStatus.Fail;
        }

        if  (columnValuesArr[1].charAt(columnValuesArr[1].length() - 1) != ')' ) {
            System.out.println("Incorrect command, values in correct");
            return DBStatus.Fail;
        }

        String columnsTmp = columnValuesArr[0].substring(1);
        String valuesTmp = columnValuesArr[1].substring(1, columnValuesArr[1].length() - 1);


        String[] columns = columnsTmp.split("\\s+");
        String[] values  = valuesTmp.split("\\s+");


        if (values.length != columns.length) {
            System.out.println("Incorrect command, column number and values number don't match");
            return DBStatus.Fail;
        }

        Map<String, List<String>> tableMetaData = Utils.createMapFromFile(MetaData.dbDirectory() + "/" + tableName + "/tableMetaData");
        if (tableMetaData ==  null) {
            System.out.println("Fail to conver table metadata file to hashmap");
            return DBStatus.Fail;
        }

        String pk = tableMetaData.get("PK").get(0);
        String pkVal = "";
        Map<String, String> columnToValue = new HashMap<>();
        List<String> columnName = tableMetaData.get("COLUMN");
        List<String> AI = tableMetaData.get("AI");
        List<String> AICount = tableMetaData.get("AI_COUNT");

        for (int i = 0; i < columns.length; i++ ) {
            String col = columns[i];
            String val = values[i];
            if (!columnName.contains(col)) {
                System.out.println("Column name not exist");
                return DBStatus.Fail;
            }

            if (col.equals(pk)) {
                if ( dbOps.getUtls().containsKey(tableName,val)) {
                    System.out.println("Keys with value " + val + " already exist");
                    return DBStatus.Fail;
                }
                pkVal = val;
            }

            if (AI.indexOf(col) != -1) {
                columnToValue.put(col, (Integer.parseInt(AICount.get(AI.indexOf(col))) + 1) + "" );
            } else {
                columnToValue.put(col, val);
            }
        }

        return  dbOps.getInsert().insertTo(tableName, columnToValue, pkVal);
    }

    @Override
    public DBStatus metaData() {
        return DBStatus.Success;
    }
}
