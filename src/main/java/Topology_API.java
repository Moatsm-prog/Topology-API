import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;

public class Topology_API {


    static public Topology ReadJSON(String s) throws IOException, ParseException {

        JSONParser jsonparser = new JSONParser();
        FileReader reader = new FileReader(s);
        Object obj =jsonparser.parse(reader);
        JSONObject myobject = (JSONObject) obj;
        Topology mytopology = new Topology(myobject.get("id").toString());
        JSONArray mycomp = (JSONArray) myobject.get("components");
            for(int i = 0; i < mycomp.size(); i++){
                JSONObject comp = (JSONObject) mycomp.get(i);
                // if component is resistor or conductor or inductor
                if(comp.get("type").toString() == "resistor"
                        || comp.get("type").toString() == "capacitor"
                        || comp.get("type").toString() == "inductor"  ){
                    Passive_Comp passive = new Passive_Comp("",comp.get("type").toString());
                    passive.setId(comp.get("id").toString());
                    JSONObject netlist = (JSONObject) comp.get("netlist");
                    ArrayList<String> net = null;
                    net.add(netlist.get("t1").toString());
                    net.add(netlist.get("t2").toString());
                    passive.setNetlist(net);
                    String value = null;
                    if(passive.getType() == "resistor") value = "resistance";
                    else if(passive.getType() == "capacitor") value = "inductance";
                    else if(passive.getType() == "inductor") value = "capacitance";
                    JSONObject values = (JSONObject) comp.get(value);
                    ArrayList<String> res = null;
                    res.add(values.get("default").toString());
                    res.add(values.get("min").toString());
                    res.add(values.get("max").toString());
                    passive.setValues(res);
                    mytopology.addComponent(passive);
                }
                // if component is Transistor
                if(comp.get("type").toString() == "nmos"
                        || comp.get("type").toString() == "pmos"){
                    Transistor trans = new Transistor("",comp.get("type").toString());
                    trans.setId(comp.get("id").toString());
                    JSONObject netlist = (JSONObject) comp.get("netlist");
                    ArrayList<String> net = null;
                    net.add(netlist.get("drain").toString());
                    net.add(netlist.get("gate").toString());
                    net.add(netlist.get("source").toString());
                    trans.setNetlist(net);
                    JSONObject values = (JSONObject) comp.get("m(l)");
                    ArrayList<String> tra = null;
                    tra.add(values.get("default").toString());
                    tra.add(values.get("min").toString());
                    tra.add(values.get("max").toString());
                    trans.setValues(tra);
                    mytopology.addComponent(trans);
                }

            }
            Topology.topologies.add(mytopology);
            return mytopology;
        }

        static public void writeJSON(JSONObject o) throws IOException {
            FileWriter file = new FileWriter("C:\\Users\\moats\\IdeaProjects\\Topology_API\\src\\JSONFiles\\out.json");
            file.write(o.toJSONString());
            file.close();

        }

        static public ArrayList<Topology> queryTopologies(){
            return Topology.topologies;
        }


        static public Topology deleteTopology(String id){
            for(int i=0 ; i < Topology.topologies.size() ; i++){
                if(Topology.topologies.get(i).getId().equals(id)){
                    return Topology.topologies.remove(i);
                }
            }
            return null;
        }

        static public ArrayList<Component> queryDevices(String id){
            for(int i=0 ; i < Topology.topologies.size() ; i++){
                if(Topology.topologies.get(i).getId().equals(id)){
                    return Topology.topologies.get(i).getMycomp();
                }
            }
            return null;
        }

        static public ArrayList<Component> queryDevicesWithNetlistNode(String Tid , String Nid){
            ArrayList<Component> arr = null;
            for(int i=0 ; i < Topology.topologies.size() ; i++){
                Topology t = Topology.topologies.get(i);
                if(t.getId().equals(Tid)){
                    for (int j=0 ; j < t.getMycomp().size() ; j++){
                        if(t.getMycomp().get(i).getId().equals(Nid)){
                                arr.add(t.getMycomp().get(i));
                    }
                }

            }
        }
        return arr;
    }




    }

