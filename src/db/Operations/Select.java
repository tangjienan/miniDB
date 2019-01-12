package db.Operations;

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
}
