package db.Command;

import java.util.Scanner;

/**
 * Created by donezio on 1/10/19.
 */
public class Command {

    private static Command command;
    private ParserImp parserImp;

    private Command() {
        parserImp = ParserImp.getInstance();
    }

    public static Command getInstance() {
        if (command == null)  {
            command = new Command();
        }
        return command;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Database is starting...");
        boolean run = true;
        while ( run == true ) {
            String line = scanner.nextLine();
            if (line.length() != 0) {
                DBStatus dBStatus = parserImp.readLine(line);
                if (dBStatus == DBStatus.Exit) {
                    System.out.println("Bye~");
                    run = false;
                } else if (dBStatus == DBStatus.Fail) {
                    System.out.println("Command error, enter it again");
                }
            }
        }
    }

    private Command build() {
        return this;
    }
}
