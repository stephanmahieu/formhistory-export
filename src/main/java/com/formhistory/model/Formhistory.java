package com.formhistory.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.ArrayList;
import java.util.List;

@XStreamAlias("formhistory")
public class Formhistory {
    Header header = new Header();
    List<Field> fields = new ArrayList<Field>();
    List<EditorField> editorFields = new ArrayList<EditorField>();

    public Header getHeader() {
        return header;
    }

    public List<Field> getFields() {
        return fields;
    }

    public List<EditorField> getEditorFields() {
        return editorFields;
    }
}
