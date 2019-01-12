package db.Operations;

/**
 * Created by donezio on 1/11/19.
 */
public class DBOps {

    private static DBOps dbOps;


    Create create;

    Delete delete;

    Update update;

    Select select;

    OpUtils opUtls;

    Insert insert;

    private DBOps(Create create, Delete delete, Update update, Select select, OpUtils utls, Insert insert) {
        this.create = create;
        this.select = select;
        this.delete = delete;
        this.update = update;
        this.opUtls   = utls;
        this.insert = insert;

    }

    public static DBOps getInstance() {
        if (dbOps == null) {
            dbOps = new DBOps(Create.getInstance(), Delete.getInstance(), Update.getInstance(), Select.getInstance(), OpUtils.getInstance(), Insert.getInstance());
        }
        return dbOps;
    }

    public static void getAllOperations() {

    }

    public Create getCreate() {
        return create;
    }

    public Delete getDelete() {
        return delete;
    }

    public Update getUpdate() {
        return update;
    }

    public Select getSelect() {
        return select;
    }

    public OpUtils getUtls() {
        return opUtls;
    }

    public Insert getInsert() {
        return insert;
    }
}
