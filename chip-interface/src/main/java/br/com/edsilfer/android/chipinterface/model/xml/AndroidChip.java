package br.com.edsilfer.android.chipinterface.model.xml;

import com.google.gson.Gson;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

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










