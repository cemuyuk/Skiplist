
public class Visualization {
	private Skiplist slist;
	private String[][] visualizationArray;
	private int startBoundaryForSearchStars;

	public Visualization(Skiplist slist) {
		// TODO Auto-generated constructor stub
		this.slist=slist;
	}

	/*
	 * I have a visualization matrix in this class. It is basically in the shape of a skiplist.
	 * for instance visualizationMatrix[0][0]=minusInf, visualizationMatrix[0][1]=-- etc.
	 * I print the skiplist with this matrix.
	 * This method below just fills the last row of the matrix that is level S0 in skiplist.
	 */
	public void fillTheLastRowOfVisArray(){
		int rowLength=(slist.lengthOfLastLevel()*2)-1;
		visualizationArray=new String[slist.getHeight()+1][rowLength];
		Node temp=slist.getMinusInfAt(visualizationArray.length-1);
		for(int i=0; i<rowLength;i=i+2){
			if(temp.getKey().equals("-1000")){
				visualizationArray[visualizationArray.length-1][i]="[minusInf]";
				visualizationArray[visualizationArray.length-1][i+1]="--";
				temp=temp.getRight();
			}else if(temp.getKey().equals("1000")){
				visualizationArray[visualizationArray.length-1][i]="[plusInf]";
				temp=temp.getRight();
			}else{
				visualizationArray[visualizationArray.length-1][i]=temp.getKey();
				visualizationArray[visualizationArray.length-1][i+1]="--";
				temp=temp.getRight();
			}
		}
	}
	/*
	 * This method below is the main method to fill the visualizationMatrix. In order to understand
	 * it, you should read the commentary of the method above. Once I fill the last row of the 
	 * visualization matrix that of the same with the S0 level of the skiplist. I get the minusInf
	 * node from the one level above with the method minusInfAt(int j). Then I start comparing
	 * each node's key with the last row of the visualizationMatrix. I continue this recursively 
	 * till the visualization matrix is filled.
	 */
	public void fillTheVisArray(){
		fillTheLastRowOfVisArray();
		int rowLength=(slist.lengthOfLastLevel()*2)-1;
		for(int j=visualizationArray.length-1;j>0;j--){
			Node temp=slist.getMinusInfAt(j-1);
			for(int i=0; i<rowLength; i++){
				if(visualizationArray[j][i].equals("[minusInf]")){
					visualizationArray[j-1][i]="[minusInf]";
					temp=temp.getRight();
				}else if(visualizationArray[j][i].equals("--")){
					visualizationArray[j-1][i]="--";
				}else if(temp.getKey().equals(visualizationArray[j][i])){
					visualizationArray[j-1][i]=temp.getKey();
					temp=temp.getRight();
				}else if(visualizationArray[j][i].equals("[plusInf]")){
					visualizationArray[j-1][i]="[plusInf]";
				}else{
					visualizationArray[j-1][i]="--";
				}	
			}
		}
	}	

	/*
	 * Just prints the visualization matrix. In order to understand, you should read the commentary
	 * in the above methods: fillVisArray.
	 */
	public void printSkiplist(){
		fillTheVisArray();
		for(int j=0;j<visualizationArray.length;j++){
			String result="";
			for(int i=0;i<visualizationArray[0].length;i++){
				result=result+visualizationArray[j][i];
			}
			System.out.println(result);
		}
	}
	
	/*
	 * Once performed search, I keep an int array that keeps the right shift numbers.
	 * When I perform search print, I search the key and get that number of shift rights.
	 * According to that number, I change the "--" with "**".
	 */
	public void printSearch(String key){
		fillTheVisArray();
		String[][] temp=visualizeTheSearch(key);
		System.out.println("\n");
		for(int j=0;j<temp.length;j++){
			String result="";
			for(int i=0;i<temp[0].length;i++){
				result=result+temp[j][i];
			}
			System.out.println(result);
		}
	}
	
	/*
	 * The detail of this is explained on the method just above.
	 */
	public String[][] visualizeTheSearch(String key){
		String[][] searchVisArray=this.visualizationArray;
		slist.skipSearch(key);
		this.startBoundaryForSearchStars=1;
		for(int i=0; i<searchVisArray.length;i++){
			int count=0;
			while(count!=(slist.getRights()[i])){
				for(int j=this.startBoundaryForSearchStars; j<searchVisArray[0].length;j++){
					if(!searchVisArray[i][j].equals("--")){
						this.startBoundaryForSearchStars=j+1;
						break;
					}
					searchVisArray[i][j]="**";
				}
				count++;
			}
		}
		return searchVisArray;
	}

	public Skiplist getSlist() {
		return slist;
	}

	public void setSlist(Skiplist slist) {
		this.slist = slist;
	}
}
