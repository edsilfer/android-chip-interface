package br.com.edsilfer.android.chipinterface.model.xml;

import com.google.gson.Gson;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Root;

import java.util.List;
import java.util.Map;

@Root(name = "chip")
public class AndroidChip {
    @Attribute(name = "id")
    public String id;
    @ElementList(inline = true)
    public List<State> state;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}


class State {
    @Attribute
    public String type;

    @ElementList(inline = true)
    public List<Text> text;
    @ElementMap(entry = "background", key = "type", attribute = true, inline = true)
    private Map<String, String> map;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}


class Text {
    @Attribute(required = false)
    public String type;
    @Attribute(required = false)
    public String id;

    @Element
    public String font;
    @Element
    public Integer size;
    @Element
    public String style;
    @Element
    public String color;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}







