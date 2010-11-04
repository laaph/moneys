
package com.laaph.moneys;

class CommandLine {

    Data  myData = new Data();

    public static void main(String[] args) {

	CommandLine myCommandLine = new CommandLine();

	myCommandLine.parseOptions(args);

	while(true) {
	    System.out.print("moneys> ");
	    String input = myCommandLine.getInput();
	    System.out.print("debug - input = " + input + "\n");
	    if(input == null) {
		continue;
	    }
	    boolean iShouldQuit = myCommandLine.parse(input);
	    if(iShouldQuit) {
		System.exit(0);
	    }
	}

    }

    java.util.Scanner in = new java.util.Scanner(System.in);

    String getInput() {
	String snoot = in.nextLine();
	return snoot;
    }

    boolean parse(String in) {
	String[] pieces = in.split("\\s+");
	//print("debug - pieces[0] = " + pieces[0] + "\n");
	if(pieces[0].equals("print")) {
	    // For now, ignore options to print
	    for(int i = 0; i < myData.items.size(); i++) {
		println(myData.dates.get(i) + "\t" + 
			myData.labels.get(i) + "\t" +
			myData.items.get(i) + "\t" + 
			myData.comments.get(i));
	    }
	    return false;
	}
	if(pieces[0].equals("load")) {
	    try {
		String file = pieces[1];  // Need error checking
		myData.loadFile(file);
	    } catch (Exception ex) {
		println("Unable to load file: " + ex.getMessage());
	    }
	    return false;
	}
	if(pieces[0].equals("save")) {
	    try {
		String file = pieces[1];
		myData.saveFile(file);
	    } catch (Exception ex) {
		println("Unable to write file: " + ex.getMessage());
	    }
	    return false;
	}
	if(pieces[0].equals("quit")) {
	    return true;
	}
	println("Unable to parse input");
	return false;
    }


    void parseOptions(String[] args) {
	// cycle through options, do stuff as necessary

	for(int i = 0; i < args.length; i++) {
	    // if ... do ...
	    String opt = args[i];
	    if(opt == "-f") {
		if(i+1 == args.length) {
		    // error
		    System.out.println("No filename specified, file not loaded");
		}
		i = i + 1;
		try {
		    myData.loadFile(args[i]);
		} catch (Exception ex) {
		    System.out.println("Unable to load file " + args[i] + 
				       ":" + ex.getMessage() );
		}
	    }
	}
    }

    void println(String ln) {
	// Clearly java was not built for commandline programs
	System.out.println(ln);
    }
    void print(String ln) {
	System.out.print(ln);
    }
}
