package stuba.fiit.entity;

import java.util.ArrayList;
import java.util.Objects;

public class Worker implements Entity {
	public int ID;
	public ArrayList<Integer> linearPriority;

	@Override
	public int getID() {
		return ID;
	}
	
	public void setID(int iD) {
		ID = iD;
	}

	@Override
	public ArrayList<Integer>  getLinearPriority() {
		return linearPriority;
	}
	
	public void setLinearPriority(ArrayList<Integer>  linearPriority) {
		this.linearPriority = linearPriority;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Worker worker = (Worker) o;
		return ID == worker.ID;
	}

	@Override
	public int hashCode() {

		return Objects.hash(ID);
	}
}
