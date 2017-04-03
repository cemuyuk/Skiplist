import java.util.ArrayList;

/*
 * Not every method is commented. The ones that are unique are commented.
 */
public class Skiplist {
	private Node header;
	private Node trailer;
	private int maxCapacity;
	private int height;
	private int numberOfElements;
	private ArrayList<String> insertedElements;
	private int[] rights;

	public Skiplist(int maxCapacity) {
		// TODO Auto-generated constructor stub
		header=new Node("-1000");
		trailer=new Node("1000");
		header.setRight(trailer);
		trailer.setLeft(header);
		height=0;
		numberOfElements=0;
		this.maxCapacity=maxCapacity;
		insertedElements=new ArrayList<String>();
	}

	public void skipInsert(String key){

		Node floor=skipSearch(key);
		Node nodeToInsert=new Node(key);
		int towerCount=0;

		int towerHeight=determineTowerHeight(key);

		//If the key already exists in the skiplist:
		if(floor.getKey().equals(key)){
			System.out.println("The key: "+key+" already exists in the skiplist.");
			//If the key does not exist in the skiplist yet:
		}else{
			insertedElements.add(key);
			nodeToInsert.setLeft(floor);
			nodeToInsert.setRight(floor.getRight());
			nodeToInsert.getRight().setLeft(nodeToInsert);
			floor.setRight(nodeToInsert);

			for(int i=0;i<towerHeight;i++){
				towerCount++;
				if(towerCount>=height)
					this.addLayer();
				while(floor.getUp()==null)
					floor=floor.getLeft();

				floor=floor.getUp();
				Node newToInsert=new Node(key);
				newToInsert.setLeft(floor);			
				newToInsert.setRight(floor.getRight());
				newToInsert.setDown(nodeToInsert);
				newToInsert.getRight().setLeft(newToInsert);
				floor.setRight(newToInsert);
				nodeToInsert.setUp(newToInsert);
				nodeToInsert=newToInsert;
			}
		}
		numberOfElements++;
	}

	/*
	 * This is an additional method for load. It does not calculate the tower height
	 * with the function provided, but it takes it as input since it is provided in the saved .txt.
	 */
	public void skipInsertWithHeight(String key, int towerHeight){

		Node floor=skipSearch(key);
		Node nodeToInsert=new Node(key);
		int towerCount=0;

		//If the key already exists in the skiplist:
		if(floor.getKey().equals(key)){
			System.out.println("The key: "+key+" already exists in the skiplist.");
			//If the key does not exist in the skiplist yet:
		}else{
			insertedElements.add(key);
			nodeToInsert.setLeft(floor);
			nodeToInsert.setRight(floor.getRight());
			nodeToInsert.getRight().setLeft(nodeToInsert);
			floor.setRight(nodeToInsert);

			for(int i=0;i<towerHeight;i++){
				towerCount++;
				if(towerCount>=height)
					this.addLayer();
				while(floor.getUp()==null)
					floor=floor.getLeft();

				floor=floor.getUp();
				Node newToInsert=new Node(key);
				newToInsert.setLeft(floor);			
				newToInsert.setRight(floor.getRight());
				newToInsert.setDown(nodeToInsert);
				newToInsert.getRight().setLeft(newToInsert);
				floor.setRight(newToInsert);
				nodeToInsert.setUp(newToInsert);
				nodeToInsert=newToInsert;
			}
		}
		numberOfElements++;

	}

	public int determineTowerHeight(String key){
		int result;
		int temp=Integer.parseInt(key);
		int m= (int) (Math.log(maxCapacity) / Math.log(2));
		result=temp % m;
		return result;
	}

	public Node skipSearch(String key){
		//		rightsAndDowns[i][0] is the rights [i][1] is the downs.
		this.rights=new int[height+1];
		for(int i=0;i<height+1;i++){
			this.rights[i]=0;
		}
		int count=0;
		Node result=header;
		while(result.getDown()!=null){
			result=result.getDown();
			count++;
			//			int keyInt = Integer.parseInt(key);
			//			int rightInt = Integer.parseInt(result.getRight().getKey());
			while(result.getRight().getKey().compareTo(key) <= 0 && 
					result.getRight().getKey().compareTo("1000")!=0){
				result=result.getRight();
				this.rights[count]++;
			}
		}
		return result;
	}

	public Node remove(String key){

		Node searchResult=skipSearch(key);
		Node temp=searchResult;

		if(!searchResult.getKey().equals(key)){
			System.out.println("The key you have entered for remove method does not exist in the skiplist.");
			return null;
		}

		while(temp!=null){
			temp.getLeft().setRight(temp.getRight());
			temp.getRight().setLeft(temp.getLeft());
			temp.setLeft(null);
			temp.setRight(null);
			temp=temp.getUp();
		}
		return searchResult;
	}

	/*
	 * When we are inserting, we might reach the highest level. Then we have to add an empty layer 
	 * above all. This method guarantees that.
	 */
	public void addLayer(){
		Node minusInfNode = new Node("-1000");
		Node plusInfNode = new Node("1000");

		minusInfNode.setRight(plusInfNode);
		minusInfNode.setDown(header);

		plusInfNode.setLeft(minusInfNode);
		plusInfNode.setDown(trailer);

		header.setUp(minusInfNode);
		trailer.setUp(plusInfNode);

		header=minusInfNode;
		trailer=plusInfNode;

		height++;
	}

	public int lengthOfLastLevel(){
		Node temp=header;
		int count=0;
		for(int i=0;i<getHeight();i++){
			//if(temp.getDown()==null) break;
			temp=temp.getDown();
		}
		while(true){
			if(temp==null) break;
			temp=temp.getRight();
			count++;
		}
		return count;
	}
	public Node getMinusInfAt(int j){
		Node result=header;
		for (int i=0; i<j; i++){
			result=result.getDown();
		}
		return result;
	}

	public Node getHeader() {
		return header;
	}

	public void setHeader(Node header) {
		this.header = header;
	}

	public Node getTrailer() {
		return trailer;
	}

	public void setTrailer(Node trailer) {
		this.trailer = trailer;
	}

	public int getMaxCapacity() {
		return maxCapacity;
	}

	public void setMaxCapacity(int maxCapacity) {
		this.maxCapacity = maxCapacity;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getNumberOfElements() {
		return numberOfElements;
	}

	public void setNumberOfElements(int numberOfElements) {
		this.numberOfElements = numberOfElements;
	}

	public ArrayList<String> getInsertedElements() {
		return insertedElements;
	}

	public void setInsertedElements(ArrayList<String> insertedElements) {
		this.insertedElements = insertedElements;
	}

	public int[] getRights() {
		return rights;
	}

	public void setRights(int[] rights) {
		this.rights = rights;
	}
	
}
