import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Operations {
	private Skiplist slist;
	private String inputFile;
	private FileWriter frForSave;
	private Visualization visualize;

	public Operations(String inputFile) throws IOException {	
		// TODO Auto-generated constructor stub
		this.inputFile=inputFile;
		System.out.println("\nLoading the skiplist from input text file given as input to operations:");
		//Fills the skip list reading from the input.txt file
		this.fillTheSkiplistFromInputFile();
		//initializes the visualization instance of operations that is used to print on console
		visualize=new Visualization(this.slist);
		this.visualize.printSkiplist();
		performInputOutputOperations();
	}

	/*
	 * This method below just initializes the skiplist field of this class.
	 * Since in my skiplist implementation a skiplist takes the maxCapacity as argument,
	 * this method below reads it from the input.txt then creates just constructs the skiplist.
	 * It does not insert anything yet.
	 */
	public void initializeTheSkiplist() throws IOException{
		try {
			BufferedReader in = new BufferedReader(new FileReader(inputFile));
			String str;
			str = in.readLine();
			String[] values=str.split("\\W+");
			if ((str = in.readLine()) != null)		
				values =str.split("\\W+");
			slist = new Skiplist(Integer.parseInt(values[0]));
			in.close();
		} catch (IOException e) {
			System.out.println("File Read Error");
		}
	}

	/*
	 * Using the initialized skiplist field of this class, 
	 * fill the skiplist from input file method below inserts each entry
	 * one by one to the skiplist.
	 */
	public void fillTheSkiplistFromInputFile() throws IOException{
		this.initializeTheSkiplist();
		try {
			BufferedReader in = new BufferedReader(new FileReader(inputFile));
			String str;
			str = in.readLine();
			String[] values=str.split("\\W+");
			for(int i=0;i<values.length;i++){
				this.slist.skipInsert(values[i]);
			}
			in.close();
		} catch (IOException e) {
			System.out.println("File Read Error reading the skiplist from"+inputFile);
		}
	}
	public Skiplist getSlist() {
		return slist;
	}
	public void setSlist(Skiplist slist) {
		this.slist = slist;
	}
	/*
	 * Saves the skiplist and writes to list1.txt that is load or save directory.
	 */
	public int saveSkiplist(String storingTxt) throws IOException{
		try{
			File fl=new File(storingTxt);
			if(!fl.exists()){
				fl.createNewFile();
				frForSave=new FileWriter(fl);
				Collections.sort(this.slist.getInsertedElements());
				for(int i=0; i<this.slist.getInsertedElements().size(); i++){
					String temp=this.slist.getInsertedElements().get(i);
					int tower=this.slist.determineTowerHeight(temp);
					frForSave.write(temp+" "+tower+"\n");
				}
				frForSave.close();
				return 1;
			}else{
				frForSave=new FileWriter(fl);
				Collections.sort(this.slist.getInsertedElements());
				for(int i=0; i<this.slist.getInsertedElements().size(); i++){
					String temp=this.slist.getInsertedElements().get(i);
					int tower=this.slist.determineTowerHeight(temp);
					frForSave.write(temp+" "+tower+"\n");
				}
				frForSave.close();
				return 2;
			}
		}catch (IOException e) {
			System.out.println("File Write Error in saving Skiplist");
		}
		return 3;
	}

	/*
	 * loads the skiplist from the input file. the input must have .txt extension
	 */
	public int loadSkiplist(String loadingTxt) throws IOException{
		try {
			BufferedReader in = new BufferedReader(new FileReader(loadingTxt));
			String str;
			String[] values;
			ArrayList<Integer> levels=new ArrayList<Integer>();
			ArrayList<String> elements=new ArrayList<String>();
			while((str = in.readLine()) != null){
				values=str.split("\\W+");
				levels.add(Integer.parseInt(values[1]));
				elements.add(values[0]);
			}
			loadSkiplistHelper(elements, levels);
			in.close();
			return 1;
		} catch (IOException e) {
			System.out.println("File Read Error! The .txt file enter to load does not exist in the"
					+ "directory. \nThe skiplist remains the same and shown below. Please enter a valid file.");
			return -1;
		}
	}

	/*
	 * This is just an auxilary method for the one just above.
	 */

	public void loadSkiplistHelper(ArrayList<String> elements, ArrayList<Integer> levels){
		int maxCapacity = (Integer.parseInt(elements.get(0))-levels.get(0))*  (int) Math.log(2);
		Skiplist newList=new Skiplist(maxCapacity);
		for(int i=0;i<elements.size();i++){
			newList.skipInsertWithHeight(elements.get(i), levels.get(i));
		}
		this.setSlist(newList);
	}

	/*
	 * This method below constantly listens for user input to the console.
	 * It checks all the inputs and does what it is necessary according to the cases.
	 * Does not terminate until user commands to do so.
	 */
	public void performInputOutputOperations() throws IOException{
		BufferedReader rd=new BufferedReader(new InputStreamReader(System.in));
		int counter=0;
		while(true){
			System.out.println("\n\n-----OPERATION"+counter+"-----");
			System.out.println("AVAILABLE COMMANDS are given below. After executing perations 1, 2, 3 and 4 the skiplist will be printed:"
					+ "\n-1- Insert a key: Write 'insert*key' to insert a key. Case sensitive do not use upper case letters."
					+ "\n-2- Remove a key: Write 'remove*key' to remove a key. Case sensitive do not use upper case letters."
					+ "\n-3- Search a key: Write 'search*key' to search a key. Case sensitive do not use upper case letters."
					+ "\n-4- Load a skiplist: Write 'load .txt' to load a skiplist. DO NOT forget to include .txt extension."
					+ "\n-5- Save the current skiplist: Write 'save .txt' to save the current skiplist. "
					+ "\nDO NOT forget to include .txt extension. If the .txt file you enter does not exist, "
					+ "\na new one with the name you entered will be"
					+ "created and the skiplist will be saved into that."
					+ "\n-6- Terminate the program: Write 'Terminate' to quit.");
			String str = rd.readLine();
			int txtEnd=str.length();
			int txtStart=txtEnd-4;
			String testString=str.substring(txtStart,txtEnd);
			if(str.length()>=8){
				if(str.substring(0, 8).toLowerCase().equals("terminat")){
					System.out.println("-----OPERATION"+counter+" over.-----");
					break;
				}else if(str.substring(0, 5).equals("load ")){
					if(testString.equals(".txt")){
						if(this.loadSkiplist(str.substring(5))==1){
							this.visualize.setSlist(this.slist);
							System.out.println("Performed below---------->");
							System.out.println("The new skiplist after load operation:");
							this.visualize.printSkiplist();
						}
					}else{
						System.out.println("Enter a file with the .txt extension. "
								+ "What you entered is: "+str.substring(5));
					}
				}else if(str.substring(0, 5).equals("save ")){
					if(testString.equals(".txt")){
						System.out.println("Performed below---------->");
						if(this.saveSkiplist(str.substring(5))==1){
							System.out.println("The file "+str.substring(5)+" is now created"
									+ " and the skiplist is saved into it in the project directory.");
						}else{
							System.out.println("The .txt file "+str.substring(5)+" had already existed in the"
									+ " directory.\nNow it is overwritten with the data of the current skiplist.");
						}
					}else{
						System.out.println("Enter a file with the .txt extension. "
								+ "What you entered is: "+str.substring(5));
					}
				}else if(str.substring(0, 7).equals("insert*")){
					if(!isNumeric(str.substring(7))){
						System.out.println("Please enter a numeric value to the insert function.");
					}else{
						if(str.substring(7).length()!=2){
							System.out.println("Insert error:"+str.substring(7)+" is not a valid key. "
									+ "You should enter a valid key"
									+ " between 10-99");
						}else{
							System.out.println("Performed below---------->");
							System.out.println(str.substring(7)+" inserted:");
							this.slist.skipInsert(str.substring(7));
							this.visualize.setSlist(this.slist);
							this.visualize.printSkiplist();
						}
					}
				}else if(str.substring(0, 7).equals("remove*")){
					if(!isNumeric(str.substring(7))){
						System.out.println("Please enter a numeric value to the insert function.");
					}else{
						if(str.substring(7).length()!=2){
							System.out.println("Remove error:"+str.substring(7)+" is not a valid key. "
									+ "You should enter a valid key"
									+ " between 10-99");
						}else{
							System.out.println("Performed below---------->");
							System.out.println(str.substring(7)+" removed.");
							this.slist.remove(str.substring(7));
							this.visualize.setSlist(this.slist);
							this.visualize.printSkiplist();
						}
					}
				}else if(str.substring(0, 7).equals("search*")){
					if(!isNumeric(str.substring(7))){
						System.out.println("Please enter a numeric value to the search function.");
					}else{
						if(str.substring(7).length()!=2){
							System.out.println("Search error:"+str.substring(7)+" is not a valid key. "
									+ "You should enter a valid key"
									+ " between 10-99");
						}else{
							System.out.println("Performed below---------->");
							System.out.println("Searching "+str.substring(7));
							this.visualize.setSlist(this.slist);
							this.visualize.printSearch(str.substring(7));
						}
					}
				}else{
					System.out.println("Please enter an valid command from the available commands listed.");
				}
			}else{
				System.out.println("Please enter an valid command from the available commands listed.");
			}
			System.out.println("-----OPERATION"+counter+" over.-----");
			counter++;
		}
		rd.close();
	}
	private boolean isNumeric(String str){
		try{
			int m=Integer.parseInt(str);
		}catch(NumberFormatException nfe){
			return false;
		}
		return true;
	}

}
