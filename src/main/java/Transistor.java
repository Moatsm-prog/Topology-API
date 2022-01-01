import java.util.ArrayList;

public class Transistor extends Component{

    private ArrayList<String> values;
    private ArrayList<String> netlist;

    public void setValues(ArrayList<String> values) {
        this.values = values;
    }

    public void setNetlist(ArrayList<String> netlist) {
        this.netlist = netlist;
    }

    public Transistor(String id, String type) {
        super(id, type);
        this.values = null;
        this.netlist = null;
    }

    public ArrayList<String> getValues() {
        return values;
    }

    public ArrayList<String> getNetlist() {
        return netlist;
    }
}
