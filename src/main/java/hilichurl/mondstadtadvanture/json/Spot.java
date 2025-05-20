package hilichurl.mondstadtadvanture.json;

import java.util.List;

public class Spot {
    private String name;
    private String text;
    private String background;  //背景图的路径
    private List<String> optionals;

    public String getName() {return name;}
    public void setName(String name){this.name=name;}

    public String getText() {return text;}
    public void setText(String text){this.text=text;}

    public String getBackground() {return background;}
    public void setBackground(String background){this.background=background;}

    public List<String> getOptionals() {return optionals;}
    public void setOptionals(List<String> optionals){this.optionals=optionals;}
}
