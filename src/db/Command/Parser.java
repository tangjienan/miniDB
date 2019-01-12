package db.Command;

/**
 * Created by donezio on 1/10/19.
 */
public interface Parser {

    public DBStatus readLine(String line);

    public DBStatus metaData();
}
