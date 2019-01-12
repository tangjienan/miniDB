import db.Command.Command;
import db.MetaData;

/**
 * Created by donezio on 1/10/19.
 */
public class Main {
    public static void main(String[] args) {

        MetaData metaData = MetaData.getInstance();

        if (metaData.dbDirectory() != null) {
            System.out.println(metaData.dbDirectory());
        }


        Command command = Command.getInstance();
        command.run();

    }
}
