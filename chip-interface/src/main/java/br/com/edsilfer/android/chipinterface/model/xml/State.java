package br.com.edsilfer.android.chipinterface.model.xml;

import com.google.gson.Gson;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.ElementMap;

import java.util.List;
import java.util.Map;

/**
 * Created by User on 13/12/2016.
 */

public class State {
    @Attribute
    public String type;

    @ElementList(inline = true)
    public List<Text> text;
    @ElementMap(entry = "background", key = "type", attribute = true, inline = true)
    public Map<String, String> background;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}