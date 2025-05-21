package hilichurl.mondstadtadvanture.jsonpojo.plots;

import hilichurl.mondstadtadvanture.enums.PlotEndWay;

import java.util.List;

public class Plot {
    private List<Dialogue> dialogue;
    private PlotEndWay endWay;
    private List<Branch> branches;
    private String next;
    private String group;

    public List<Dialogue> getDialogue(){return dialogue;}
    public void setDialogue(List<Dialogue> dialogue){this.dialogue=dialogue;}

    public PlotEndWay getEndWay(){return endWay;}
    public void setEndWay(PlotEndWay endWay){this.endWay=endWay;}

    public List<Branch> getBranches(){return branches;}
    public void setBranches(List<Branch> branches){this.branches=branches;}

    public String getNext(){return next;}
    public void setNext(String next){this.next=next;}

    public String getGroup(){return group;}
    public void setGroup(String group){this.group=group;}
}