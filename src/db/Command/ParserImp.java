package db.Command;


import db.MetaData;
import db.Operations.DBOps;
import java.util.HashSet;
import java.util.Set;

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
        }
    };;
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
            case "INFO":
                //TODO
                return DBStatus.Exit;
            case "CREATE":
                return create(args);
            case "DROP":
                return drop();
            case "UPDATE":
                return update();
            case "SELECT":
                return select();
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

    public DBStatus select() {
        return DBStatus.Success;
    }

    @Override
    public DBStatus metaData() {
        return DBStatus.Success;
    }
}
