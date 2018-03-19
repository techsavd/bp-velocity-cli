package application;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import java.io.IOException;

public class OptionHelper {
    public static void help(Options options) {
        // This prints out some help
        HelpFormatter formater = new HelpFormatter();

        formater.printHelp("Main", options);
        System.exit(0);
    }
    public static void generate(CommandLine cmd) throws IOException {
        FileGenerator.generateFile(cmd.getOptionValue("g"));
    }
}

