package hilichurl.mondstadtadvanture.jsonpojo.plots;

import java.util.ArrayList;

public class Plot {
    private ArrayList<Dialogue> dialogue;
    private String group;

    public ArrayList<Dialogue> getDialogue(){return dialogue;}
    public void setDialogue(ArrayList<Dialogue> dialogue){this.dialogue=dialogue;}

    public String getGroup(){return group;}
    public void setGroup(String group){this.group=group;}
}