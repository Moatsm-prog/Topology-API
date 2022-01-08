import Components.*;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class Topology{
    private String id;
    private JSONObject object;
    private  ArrayList<Component> mycomp;
    static ArrayList<Topology> topologies;

    public JSONObject getObject() {
        return object;
    }

    public String getId() {
        return id;
    }
    public ArrayList<Component> getMycomp() {
        return mycomp;
    }

    public static ArrayList<Topology> getTopologies() {
        return topologies;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMycomp(ArrayList<Component> mycomp) {
        this.mycomp = mycomp;
    }



    public Topology(String id , JSONObject o) {
        this.id = id;
        this.topologies = new ArrayList<Topology>();
        this.object = o;
    }


    public void addComponent(Component c){
        this.mycomp.add(c);
    }
    public void print() {
        System.out.println("id:" + this.id);
        if (mycomp != null) {
            for (int i = 0; i < mycomp.size(); i++) {
                System.out.println("Component");
                System.out.println(mycomp.get(i).getId());
                System.out.println(mycomp.get(i).getType());
                if (mycomp.get(i) instanceof Passive_Comp) {
                    Passive_Comp m = (Passive_Comp) mycomp.get(i);
                    for (int j = 0; j < m.getValues().size(); j++) {
                        System.out.println("Resistance:");
                        System.out.println("Default: " + m.getValues().get(0));
                        System.out.println("Min: " + m.getValues().get(1));
                        System.out.println("Max" + m.getValues().get(2));
                    }
                }
            }

        }
    }
}
