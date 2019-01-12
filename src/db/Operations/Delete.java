package db.Operations;

/**
 * Created by donezio on 1/11/19.
 */
public class Delete extends Operations {
    private static Delete delete;

    private Delete() {

    }

    public static Delete getInstance() {
        if (delete == null) {
            delete = new Delete();
        }

        return delete;
    }
}
