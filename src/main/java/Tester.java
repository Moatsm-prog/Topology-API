import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import java.io.IOException;

public class Tester {
    public static void main(String[] args){


        try {
            JSONObject o = Topology_API.ReadJSON("D:\\Projects\\Topology-API\\JSONFiles\\topology.json");
            Topology_API.writeJSON(Topology.getTopologies().get(0) , "D:\\Projects\\Topology-API\\JSONFiles\\out.json" );

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
