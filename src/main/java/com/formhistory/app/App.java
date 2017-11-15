package com.formhistory.app;

import com.formhistory.model.Formhistory;
import com.formhistory.util.Database;
import com.formhistory.util.ExportFileUtil;
import com.formhistory.util.XmlPrettyPrintWriter;
import com.formhistory.util.Utils;
import com.thoughtworks.xstream.XStream;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Export Form History from a Firefox/Mozilla sqlite database.
 *
 */
public class App {
    private static final String DEFAULT_OUTPUT_FILE = "formhistory.xml";

    private static final int EXIT_STATUS_FAIL = -1;

    public static void main(String[] args) {
        CmdLine cli = new CmdLine(DEFAULT_OUTPUT_FILE, args);
        cli.parse();

        System.out.println("Start formhistory export\n" +
                           "- profile directory: " + cli.getProfiledir() + "\n"+
                           "- destination file : " + cli.getOutputfile() + "\n");

        // check output file
        String outputFile = ExportFileUtil.getOutputFilepath(cli.getOutputfile(), DEFAULT_OUTPUT_FILE);
        if (outputFile == null) {
            System.exit(EXIT_STATUS_FAIL);
        }

        // check profile directory
        if (!ExportFileUtil.checkProfileDirectory(cli.getProfiledir())) {
            System.exit(EXIT_STATUS_FAIL);
        }

        // initialize the SQLite database driver
        if (!Database.initSqliteDriver()) {
            System.exit(EXIT_STATUS_FAIL);
        }

        // create object tree to hold the retrieved data
        Formhistory formhistory = new Formhistory();
        formhistory.getHeader().setExportDate(Utils.getNowString());

        // retrieve data from the database
        Database.retrieveInputFields(cli.getProfiledir(), formhistory.getFields());
        Database.retrieveMultilineFields(cli.getProfiledir(), formhistory.getEditorFields());

        // output object tree to xml
        XStream xstream = Database.getXstream();
        Writer outWriter = new StringWriter();
        xstream.marshal(formhistory, new XmlPrettyPrintWriter(outWriter));

        System.out.print("writing data:               ");
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputFile))) {
            writer.write(Utils.selfCloseTags(outWriter.toString()));
            System.out.println("OK");
        } catch (IOException e) {
            System.err.println("FAILED write error: " + e.getMessage());
            System.exit(EXIT_STATUS_FAIL);
        }
    }
    
}
