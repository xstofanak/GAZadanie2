package stuba.fiit;

import java.util.ArrayList;

public class worker {
	
	public int ID;
	public ArrayList<Integer>  linearPriority;
	
	public int getID() {
		return ID;
	}
	
	public void setID(int iD) {
		ID = iD;
	}
	
	public ArrayList<Integer>  getLinearPriority() {
		return linearPriority;
	}
	
	public void setLinearPriority(ArrayList<Integer>  linearPriority) {
		this.linearPriority = linearPriority;
	}
}
