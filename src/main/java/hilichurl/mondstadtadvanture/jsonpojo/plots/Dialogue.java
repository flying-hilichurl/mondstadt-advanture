package hilichurl.mondstadtadvanture.jsonpojo.plots;

import hilichurl.mondstadtadvanture.enums.CharaPosition;

public class Dialogue {
    private String name;
    private CharaPosition position;
    private String text;

    public String getName() {return name;}
    public void setName(String name){this.name=name;}

    public CharaPosition getPosition(){return position;}
    public void setPosition(CharaPosition position){this.position=position;}

    public String getText() {return text;}
    public void setText(String text){this.text=text;}
}
