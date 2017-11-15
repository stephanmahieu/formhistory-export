package com.formhistory.util;

import com.formhistory.model.EditorField;
import com.formhistory.model.Field;
import com.formhistory.model.Formhistory;
import com.thoughtworks.xstream.XStream;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;


/**
 * Structure Firefox formhistory.sqlite database
 *   id         TEXT
 *   fieldname  TEXT
 *   value      TEXT
 *   timesUsed  INTEGER
 *   firstUsed  INTEGER
 *   lastUsed   INTEGER
 *
 * Structure Firefox formhistory.sqlite database
 *   id          TEXT
 *   name        TEXT
 *   type        TEXT
 *   formid      TEXT
 *   content     TEXT
 *   host        TEXT
 *   url         TEXT
 *   firstsaved  INTEGER
 *   lastsaved   INTEGER
 *
 */
public final class Database {

    private static final String FIREFOX_DATABASE = "formhistory.sqlite";
    private static final String FHC_DATABASE = "cleanup.sqlite";

    private static final String FIREFOX_SELECT = "SELECT id, fieldname, value, timesUsed, firstUsed, lastUsed FROM moz_formhistory";
    private static final String FHC_SELECT = "SELECT id, name, type, formid, content, host, url, firstsaved, lastsaved FROM multiline";

    private Database() {
    }

    public static boolean initSqliteDriver() {
        boolean driverLoaded = false;
        try {
            // load the sqlite-JDBC driver using the current class loader
            Class.forName("org.sqlite.JDBC");
            driverLoaded = true;
        } catch (ClassNotFoundException e) {
            System.err.println("Unable to load the sqlite JDBC driver. " + e.getMessage());
        }
        return driverLoaded;
    }

    public static XStream getXstream() {
        XStream xstream = new XStream();
        xstream.processAnnotations(Formhistory.class);
        xstream.processAnnotations(Field.class);
        xstream.processAnnotations(EditorField.class);
        return xstream;
    }

    public static void retrieveInputFields(String profilePath, List<Field> fields) {
        System.out.print("reading inputfields:       ");

        final String dbPath = profilePath + File.separator + FIREFOX_DATABASE;
        if (!ExportFileUtil.fileExistAndNotEmpty(dbPath)) {
            System.out.println(" skipped, non existing or empty db (" + FIREFOX_DATABASE + ")");
            return;
        }

        long count = 0;
        try (final Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath)) {
            ResultSet rs = getStatement(connection).executeQuery(FIREFOX_SELECT);
            while (rs.next()) {
                Field field = new Field(
                        rs.getString("fieldname"),
                        rs.getBytes("value"),
                        rs.getLong("timesUsed"),
                        rs.getLong("firstUsed"),
                        rs.getLong("lastUsed")
                );
                fields.add(field);
                count++;
            }
        } catch(SQLException e) {
            // if the error message is "out of memory", it probably means no database file is found
            System.err.println("Retrieve input fields failed. " + e.getMessage());
        }
        System.out.println(" " + count);
    }

    public static void retrieveMultilineFields(String profilePath, List<EditorField> fields) {
        System.out.print("reading multilinefields:   ");

        final String dbPath = profilePath + File.separator + FHC_DATABASE;
        if (!ExportFileUtil.fileExistAndNotEmpty(dbPath)) {
            System.out.println(" skipped, non existing or empty db (" + FHC_DATABASE + ")");
            return;
        }

        long count = 0;
        try (final Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath)) {
            ResultSet rs = getStatement(connection).executeQuery(FHC_SELECT);
            while (rs.next()) {
                EditorField field = new EditorField(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("type"),
                        rs.getString("formid"),
                        rs.getBytes("content"),
                        rs.getString("host"),
                        rs.getString("url"),
                        rs.getLong("firstsaved"),
                        rs.getLong("lastsaved")
                );
                fields.add(field);
                count++;
            }
        } catch(SQLException e) {
            // if the error message is "out of memory", it probably means no database file is found
            System.err.println("Retrieve multiline fields failed. " + e.getMessage());
        }
        System.out.println(" " + count);
    }

    private static Statement getStatement(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        statement.setQueryTimeout(10);
        return statement;
    }

}
