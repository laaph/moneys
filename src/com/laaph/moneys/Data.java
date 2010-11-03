
import java.util.ArrayList;
import java.util.Date;

class Data {
    ArrayList<Date>      dates = new ArrayList<Date>();
    ArrayList<String>   labels = new ArrayList<String>();
    ArrayList<Integer>   items = new ArrayList<Integer>();
    ArrayList<String> comments = new ArrayList<String>();

    public void addEntry(Date d, String l, Integer i, String c) {
	dates.add(d); labels.add(l); items.add(i); comments.add(c);
    }

}
