package Command;

/**
 * Created by donezio on 1/10/19.
 */
public class ParserImp implements Parser{

    private static ParserImp parserImp;

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
            case "CREATE":
                return create();
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
                return Status.Exit;
        }
    }

    // CREATE (tableName) (colum1) (colum2) ..... primary_key (column*)
    public Status create() {
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



}
