import Components.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.*;
import java.util.ArrayList;


/**
 * @author Moatsm Eldeeb
 * @version 1.0
 */
public class Topology_API {

    /**
     *
     * @param s The path of the JSON file we want to read
     * @return JSONObject which contains the entered Topology
     * @throws IOException if input or output exception occurred
     * @throws ParseException if an error occurred during parsing
     */
    static public JSONObject ReadJSON(String s) throws IOException, ParseException {

        JSONParser jsonparser = new JSONParser();
        FileReader reader = new FileReader(s);
        Object obj =jsonparser.parse(reader);
        JSONObject myobject = (JSONObject) obj;
        Topology mytopology = new Topology(myobject.get("id").toString() , myobject);
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
            return myobject;
        }

    /**
     * @param str The unformated JSON string
     * @return String containing the formatted JSON to be printed
     */
    static private String Format(String str){
            String to_be_printed ="";
            int indent = 0;
            String space = " ";
            for (int i=0 ; i < str.length();i++){
                Character c = str.charAt(i);
                if(c == Character.valueOf('{')){
                    indent = indent + 2;
                    String repeated = space.repeat(indent);
                    to_be_printed = to_be_printed + c + '\n' + repeated;

                }
                else if(c == Character.valueOf(':')){
                    to_be_printed = to_be_printed + c + ' ';
                }
                else if(c == Character.valueOf('[')){
                    indent = indent + 2;
                    String repeated = space.repeat(indent);
                    to_be_printed = to_be_printed + c + '\n' + repeated;


                }
                else if(c == Character.valueOf(',')){
                    String repeated = space.repeat(indent);
                    to_be_printed = to_be_printed + c + '\n' + repeated;
                }
                else if(c == Character.valueOf('}')){
                    indent = indent - 2;
                    String repeated = space.repeat(indent);
                    to_be_printed = to_be_printed + '\n' + repeated + c  ;
                }
                else if(c == Character.valueOf(']')){
                    indent = indent - 2;
                    String repeated = space.repeat(indent);
                    to_be_printed = to_be_printed + '\n' + repeated + c  ;
                }


                else{
                    to_be_printed = to_be_printed + c;
                }
            }
            return to_be_printed;
        }
    /**
     *
     * @param mytopology The topology we want to write to the file
     * @param dest The path of the file we want to write the topology in;
     * @throws IOException if input or output exception occurred
     */
        static public void writeJSON(Topology mytopology , String dest) throws IOException {
            FileWriter file = new FileWriter(dest);
            String str = mytopology.getObject().toJSONString();
            String to_be_printed = Format(str);
            file.write(to_be_printed);
            file.close();

        }

    /**
     *
     * @return Arraylist of the list of topologies stored in the memory
     */
    static public ArrayList<Topology> queryTopologies(){
            return Topology.topologies;
        }

    /**
     *
     * @param id The id of the Topology we want to delete
     * @return The topology removed from the List of topologies (if there is no such element it returns null)
     */
    static public Topology deleteTopology(String id){
            for(int i=0 ; i < Topology.topologies.size() ; i++){
                if(Topology.topologies.get(i).getId().equals(id)){
                    return Topology.topologies.remove(i);
                }
            }
            return null;
        }

    /**
     *
     * @param id The id of the Topology we search for
     * @return The list of devices we search for in the List of topologies by the topology id (if there is no such element it returns null)
     */
    static public ArrayList<Component> queryDevices(String id){
            for(int i=0 ; i < Topology.topologies.size() ; i++){
                if(Topology.topologies.get(i).getId().equals(id)){
                    return Topology.topologies.get(i).getMycomp();
                }
            }
            return null;
        }

    /**
     *
     * @param Tid The id of the Topology we search for
     * @param Nid The id of the Netlist we search for
     * @return The list of devices we search for in the List of topologies by the topology id and Netlist is
     *         (if there is no such elements it returns null)
     */
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

