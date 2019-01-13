package db.Operations;

import java.util.List;

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
    public void display(String tableName, List<String> columns, String[] conditions) {

    }

}
