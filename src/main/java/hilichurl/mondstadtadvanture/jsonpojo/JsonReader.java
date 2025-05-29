package hilichurl.mondstadtadvanture.jsonpojo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import hilichurl.mondstadtadvanture.Program;
import hilichurl.mondstadtadvanture.enums.GameScenes;
import hilichurl.mondstadtadvanture.jsonpojo.plots.Dialogue;
import hilichurl.mondstadtadvanture.jsonpojo.plots.Plot;
import hilichurl.mondstadtadvanture.jsonpojo.spots.Spots;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//从json文件中读取出文本描述和选项
public class JsonReader {
    ObjectMapper mapper = new ObjectMapper();
    private Spots spots;
    private static final JsonReader instance = new JsonReader();

    public static JsonReader getInstance(){return instance;}

    private JsonReader(){}

    //获取场景数据
    public Spots getSpots() throws IOException {
        if(spots==null)
            readSpots();

        return spots;
    }

    //读取场景文件
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

    //从json中查找可触发剧情
    public String getPlotTarget(GameScenes gameScenes,String currentPlot) throws IOException {
        File file=new File(Objects.requireNonNull(Program.class.getResource("json/plotTarget.json")).getPath());
        JsonNode root=mapper.readTree(file);
        JsonNode node = root.get(gameScenes.toString());
        if(node.get(currentPlot)!=null)
            return node.get(currentPlot).asText();
        else
            return null;
    }

    //查找NPC的聊天选项
    public List<JsonNode> getNPCChat(String name, GameScenes gameScenes) throws IOException {
        File file=new File(Objects.requireNonNull(Program.class.getResource("json/NPCChat.json")).getPath());
        JsonNode root=mapper.readTree(file);
        JsonNode sceneNode = root.get(gameScenes.toString());
        JsonNode node = sceneNode.get(name);

        if(node.isArray()){
            return mapper.convertValue(node, new TypeReference<>() {
            });
        }else
            return null;
    }

    //查找聊天选项对应的文本
    public ArrayList<Dialogue> getChatConnect(String connect, GameScenes gameScenes) throws IOException {
        File file = new File(Objects.requireNonNull(Program.class.getResource("Json/chatConnect.json")).getPath());
        JsonNode root=mapper.readTree(file);
        JsonNode sceneNode=root.get(gameScenes.toString());
        JsonNode node = sceneNode.get(connect);

        return mapper.convertValue(node, new TypeReference<ArrayList<Dialogue>>(){});
    }

    public JsonNode readPlayerData() throws IOException {
        File file=new File(Objects.requireNonNull(Program.class.getResource("json/playerData.json")).getPath());
        JsonNode root=mapper.readTree(file);

        return root;
    }
}
