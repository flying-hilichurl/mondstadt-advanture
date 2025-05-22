package hilichurl.mondstadtadvanture.jsonpojo.plots;

import hilichurl.mondstadtadvanture.enums.CharaPosition;
import hilichurl.mondstadtadvanture.enums.DialogueType;

public class Dialogue {
    private int id;
    private DialogueType type;
    private String name;
    private CharaPosition position;
    private String text;
    private int next;

    public int getId(){return id;}
    public void setId(int id){this.id=id;}

    public DialogueType getType(){return type;}
    public void setType(DialogueType type){this.type=type;}

    public String getName() {return name;}
    public void setName(String name){this.name=name;}

    public CharaPosition getPosition(){return position;}
    public void setPosition(CharaPosition position){this.position=position;}

    public String getText() {return text;}
    public void setText(String text){this.text=text;}

    public int getNext(){return next;}
    public void setNext(int next){this.next=next;}
}
