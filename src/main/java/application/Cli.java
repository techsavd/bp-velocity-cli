package application;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.cli.*;

public class Cli {
 private static final Logger log = Logger.getLogger(Cli.class.getName());
 private String[] args = null;
 private Options options = new Options();

 public Cli(String[] args) {

  this.args = args;
  Option help = getHelpOption();
  Option generate = getGenerateOption();

  options.addOption(help);
  options.addOption(generate);
 }

    public Option getGenerateOption() {
        return Option.builder("g")
                      .longOpt("generate")
                      .desc("generate code from tempalate")
                      .hasArgs()
                      .build();
    }

    public Option getHelpOption() {
        return Option.builder("h")
                      .longOpt("help")
                      .desc("show help.")
                      .build();
    }

    public void parse() {
      CommandLineParser parser = new DefaultParser();

      CommandLine cmd = null;
      try {
       cmd = parser.parse(options, args);


       if (cmd.hasOption("h"))
        OptionHelper.help(options);

       if (cmd.hasOption("g")) {
        log.log(Level.INFO, "Using cli argument -v=" + cmd.getOptionValue("g"));
        try{
            OptionHelper.generate(cmd);
        } catch(IOException io) {
            log.log(Level.SEVERE, "Exception while generating file" + io);
        }

        // Whatever you want to do with the setting goes here
       } else {
        log.log(Level.SEVERE, "MIssing v option");
        OptionHelper.help(options);
       }

      } catch (ParseException e) {
       log.log(Level.SEVERE, "Failed to parse comand line properties", e);
       OptionHelper.help(options);
      }
    }
}