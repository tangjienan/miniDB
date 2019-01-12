package db.Operations;

/**
 * Created by donezio on 1/11/19.
 */
public class Create extends Operations {
    private static Create create;

    private Create() {

    }

    public Create getInstance() {
        if (create == null) {
            create = new Create();
        }

        return create;
    }
}
