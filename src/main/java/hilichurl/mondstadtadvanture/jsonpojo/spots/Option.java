package hilichurl.mondstadtadvanture.jsonpojo.spots;

import hilichurl.mondstadtadvanture.enums.GameScenes;
import hilichurl.mondstadtadvanture.enums.Interacter;

public class Option {
    private String text;
    private Interacter type;
    private GameScenes target;

    public String getText() {return text;}
    public void setText(String text){this.text=text;}

    public Interacter getType(){return type;}
    public void setType(Interacter type){this.type=type;}

    public GameScenes getTarget(){return target;}
    public void setTarget(GameScenes scenes){this.target=scenes;}
}
