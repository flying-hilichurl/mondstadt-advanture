package hilichurl.mondstadtadvanture.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import hilichurl.mondstadtadvanture.Program;

import java.io.File;
import java.io.IOException;

//从json文件中读取出文本描述和选项
public class JsonReader {
    ObjectMapper mapper = new ObjectMapper();
    private Spots spots;

    public Spots getSpots() throws IOException {
        if(spots==null)
            readSpots();

        return spots;
    }

    private void readSpots() throws IOException {
        File file= new File(Program.class.getResource("json/spots.json").getPath());
        spots=mapper.readValue(file,Spots.class);
    }
}
