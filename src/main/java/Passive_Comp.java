import java.util.ArrayList;

public class Passive_Comp extends Component{

    private ArrayList<String> values;
    private ArrayList<String> netlist;

    public Passive_Comp(String id, String type) {
        super(id, type);
    }

    public ArrayList<String> getValues() {
        return values;
    }

    public ArrayList<String> getNetlist() {
        return netlist;
    }


    public void setValues(ArrayList<String> values) {
        this.values = values;
    }

    public void setNetlist(ArrayList<String> netlist) {
        this.netlist = netlist;
    }







}
