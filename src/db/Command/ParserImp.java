package db.Command;


import db.MetaData;
import db.Utils.Utils;

import java.io.File;
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
            add("CREATE");
            add("*");
            add("PRIMARY_KEY");
        }
    };;

    ParserImp() {

    }

    public static ParserImp getInstance() {
        if (parserImp == null)  {
            parserImp = new ParserImp();
        }
        return parserImp;
    }

    @Override
    public Status readLine(String line) {
        String[] args = line.split("\\s+");
        String command = args[0];
        command = command.toUpperCase();
        switch (command) {
            case "INFO":
                //TODO
                return Status.Exit;
            case "CREATE":
                return create(args);
            case "DROP":
                return drop();
            case "UPDATE":
                return update();
            case "SELECT":
                return select();
            case "EXIT":
                return Status.Exit;
            default:
                System.out.print("wrong inpit...");
                return Status.Fail;
        }
    }

    // CREATE (tableName) (colum1) (colum2) ..... primary_key (column*)
    public Status create(String[] args) {
        String tableName = args[1];

        if (keyWords.contains(tableName)) {
            return Status.Exit;
        }

        try {
            if (Utils.containsTable(tableName)) {
                return Status.Fail;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("createing table");
        File tablePath = new File(MetaData.dbDirectory() + "/" + tableName);
        tablePath.mkdir();
        return Status.Success;
    }



    public Status drop(){
        return Status.Success;
    }

    public Status update() {
        return Status.Success;
    }

    public Status select() {
        return Status.Success;
    }

    @Override
    public Status metaData() {
        return Status.Success;
    }
}
