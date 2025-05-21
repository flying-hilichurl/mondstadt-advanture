package hilichurl.mondstadtadvanture.jsonpojo.plots;

import hilichurl.mondstadtadvanture.enums.PlotEndWay;

import java.util.ArrayList;

public class Plot {
    private ArrayList<Dialogue> dialogue;
    private PlotEndWay endWay;
    private ArrayList<Branch> branches;
    private String next;
    private String group;

    public ArrayList<Dialogue> getDialogue(){return dialogue;}
    public void setDialogue(ArrayList<Dialogue> dialogue){this.dialogue=dialogue;}

    public PlotEndWay getEndWay(){return endWay;}
    public void setEndWay(PlotEndWay endWay){this.endWay=endWay;}

    public ArrayList<Branch> getBranches(){return branches;}
    public void setBranches(ArrayList<Branch> branches){this.branches=branches;}

    public String getNext(){return next;}
    public void setNext(String next){this.next=next;}

    public String getGroup(){return group;}
    public void setGroup(String group){this.group=group;}
}