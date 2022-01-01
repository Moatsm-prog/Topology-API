import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

public class writeJSON {

    public writeJSON(JSONObject Obj) throws IOException {
        FileWriter file = new FileWriter("C:\\Users\\moats\\IdeaProjects\\Topology_API\\src\\JSONFiles\\out.json");
        file.write(Obj.toJSONString());
        file.close();
        System.out.println("Done");
        String s = "";
        Obj.keySet().forEach(keyStr ->
        {
            Object keyvalue = Obj.get(keyStr);
            System.out.println("key: "+ keyStr + " value: " + keyvalue);



        });


    }



}
