package br.com.edsilfer.android.chipinterface.model.xml;

import com.google.gson.Gson;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

/**
 * Created by User on 13/12/2016.
 */

public class Text {
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