package com.formhistory.app;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class CmdLine {

    private String[] args = null;
    private Options options = new Options();
    private CommandLine commandLine = null;
    private String defaultOutputFilename;

    public CmdLine(final String defaultOutputFilename, String... args) {
        this.args = args;

        options.addOption("h", "help", false, "show help.");
        options.addOption("p", "profile-dir", true, "required, the Firefox profile directory.");
        options.addOption("o", "outputfile", true, "optional, the path of the outputfile (default: " + defaultOutputFilename + " in current directory)");

        this.defaultOutputFilename = defaultOutputFilename;
    }

    public void parse() {
        CommandLineParser parser = new DefaultParser();
        try {
            commandLine = parser.parse(options, args);

            if (commandLine.hasOption("h")) {
                help();
            }

            if (!commandLine.hasOption("p")) {
                System.out.println("profile-dir parameter is missing, please provide the directory of the Firefox profile dir.");
                help();
            }

        } catch (ParseException e) {
            System.out.println("Failed to parse command line properties");
            help();
        }
    }

    public String getProfiledir() {
        return commandLine.getOptionValue("p");
    }

    public String getOutputfile() {
        return commandLine.getOptionValue("o", defaultOutputFilename);
    }

    private void help() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("Form History Control exporter", options);
        System.exit(0);
    }

}
