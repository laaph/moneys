


import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.util.logging.Logger;

class Data {
    private static final Logger LOG = Logger.getLogger(Data.class.getName());

    ArrayList<Date>      dates = new ArrayList<Date>();
    ArrayList<String>   labels = new ArrayList<String>();
    ArrayList<Integer>   items = new ArrayList<Integer>();
    ArrayList<String> comments = new ArrayList<String>();

    /**
     * Adds an entry to the table of data.  An entry consists of a monetary transaction.
     * We compose it of the date it happened, a label (a short text description, such as
     * "paycheck" or "car payment"), the amount (use a negative number for paying out and
     * a positive number for income), and an (optional) comment (use null for the comment
     * if you wish to omit it).
     * @param d The date of transaction 
     * @param l The label
     * @param i The amount in the transaction
     * @param c Any commentary you may care to add
     */
    public void addEntry(Date d, String l, Integer i, String c) {
	dates.add(d); labels.add(l); items.add(i); comments.add(c);
    }

    /**
     * Changes the format of the date string to allow for strange
     * dates to be entered.
     * @param df The format of the new date string
     */

    public void setDateFormat(String df) {
	dateFormat = df;
    }

    protected String dateFormat = "yyyy-MM-dd";
    /**
     * Loads a file from disk to memory.
     * @param filename
     * @throws java.io.IOException
     * @throws java.io.FileNotFoundException
     */

    public void loadFile(String filename) throws 
	java.io.IOException,
	java.io.FileNotFoundException {
	String strLine;
	BufferedReader buf = new BufferedReader(new FileReader(filename));

	int lineNum = 0;
	Date d; String l; Integer i; String c;
	String tmp[];
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

	while ((strLine = buf.readLine()) != null) {
	    lineNum++;
	    try {
		// parse stuffs here

		tmp = strLine.split("\\s+");
		d = sdf.parse(tmp[0]);
		l = tmp[1];
		i = Integer.parseInt(tmp[2]);
		c = tmp[3];
	    } catch (java.text.ParseException e) {
		LOG.warning("Unable to parse line " + lineNum);
		continue;
	    }
	    dates.add(d);
	    labels.add(l);
	    items.add(i);
	    comments.add(c);
	}
	buf.close();
    }
    /**
     * Saves data from memory to disk.
     * @param filename
     * @throws java.io.IOException
     */

    public void saveFile(String filename) throws java.io.IOException {

	PrintWriter out = new PrintWriter(new FileWriter(filename));

	for(int i = 0; i < items.size(); i++) {
	    out.println(dates.get(i) + "\t" + labels.get(i) + "\t" +
			items.get(i) + "\t" + comments.get(i));
	}
	out.close();
    }
    
    // what we will want to return is a slope, offset, whether we are
    // getting richer or poorer, and number of days until bust/millionaire
    /**
     * calculateTrends
     * Calculates the trend to riches or poverty.  Given a number of days
     * in the past, it will look at the trend line from that day.
     * 
     * @param  days  Number of days in the past to calculate trend line from
     * @return slope The slope of the trend line that you are increasing or 
     * 				 decreasing by.  Null indicates an error.
     */
    public Double  calculateTrend(int days) {
	// Calculate days

	Date today    = new Date();
	Date firstDay = new Date(today.getTime() - ((24*60*60*1000) * days));

	// sanity check
	if(firstDay.before(dates.get(0)) || firstDay.after(today)) {
	    // return error?;
	    return null; // What do I return to indicate error?
	}

	int moneyAtStart = 0;
	int indexFrom    = 0;
	int moneyAtEnd   = 0;
	for(int i = 0; i < items.size(); i++) {
	    if(dates.get(i).before(firstDay)) {
		moneyAtStart = moneyAtStart + items.get(i);
	    } else {
		// We can either calculate it here, or making a new loop 
		// may be easier to read
		indexFrom = i;
		break;
	    }
	}
	moneyAtEnd = moneyAtStart; // we'll add in the rest too, relax
	for(int i = indexFrom; i < items.size(); i++) {
	    moneyAtEnd = moneyAtEnd + items.get(i);
	}

	double slope = (double)(moneyAtEnd - moneyAtStart)/(double)(days);
	return slope;


    }
    
}
