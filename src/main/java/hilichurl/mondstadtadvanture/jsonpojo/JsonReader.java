package hilichurl.mondstadtadvanture.jsonpojo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import hilichurl.mondstadtadvanture.Program;
import hilichurl.mondstadtadvanture.jsonpojo.plots.Plot;
import hilichurl.mondstadtadvanture.jsonpojo.spots.Spots;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

//从json文件中读取出文本描述和选项
public class JsonReader {
    ObjectMapper mapper = new ObjectMapper();
    private Spots spots;
    private static final JsonReader instance = new JsonReader();

    public static JsonReader getInstance(){return instance;}

    private JsonReader(){}

    public Spots getSpots() throws IOException {
        if(spots==null)
            readSpots();

        return spots;
    }

    private void readSpots() throws IOException {
        File file= new File(Objects.requireNonNull(Program.class.getResource("json/spots.json")).getPath());
        spots=mapper.readValue(file,Spots.class);
    }

    //读取某段剧情
    public Plot readPlots(String key) throws IOException {
        File file=new File(Objects.requireNonNull(Program.class.getResource("json/plots.json")).getPath());
        JsonNode rootNode = mapper.readTree(file);
        JsonNode node = rootNode.get(key);

        return mapper.treeToValue(node,Plot.class);
    }
}
