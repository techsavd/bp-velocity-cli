package application;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Cli {
 private static final Logger log = Logger.getLogger(Cli.class.getName());
 private String[] args = null;
 private Options options = new Options();

 public Cli(String[] args) {

  this.args = args;

  options.addOption("h", "help", false, "show help.");
  options.addOption("g", "var", true, "Here you can set parameter .");

 }

 public void parse() {
  CommandLineParser parser = new BasicParser();

  CommandLine cmd = null;
  try {
   cmd = parser.parse(options, args);

   if (cmd.hasOption("h"))
    help();

   if (cmd.hasOption("g")) {
    log.log(Level.INFO, "Using cli argument -v=" + cmd.getOptionValue("g"));
    try{
        FileGenerator.generateFile(cmd.getOptionValue("g"));
    } catch(IOException io) {
        log.log(Level.SEVERE, "Exception while generating file" + io);
    }

    // Whatever you want to do with the setting goes here
   } else {
    log.log(Level.SEVERE, "MIssing v option");
    help();
   }

  } catch (ParseException e) {
   log.log(Level.SEVERE, "Failed to parse comand line properties", e);
   help();
  }
 }

 private void help() {
  // This prints out some help
  HelpFormatter formater = new HelpFormatter();

  formater.printHelp("Main", options);
  System.exit(0);
 }
}