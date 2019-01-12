package db.Operations;

/**
 * Created by donezio on 1/11/19.
 */
public class Update extends Operations {
    private static Update update;

    private Update() {

    }

    public static Update getInstance() {
        if (update == null) {
            update = new Update();
        }

        return update;
    }
}
