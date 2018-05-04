package stuba.fiit.entity;

import java.util.ArrayList;
import java.util.Objects;

public class Boss implements Entity {
	private int ID;
	private ArrayList<Integer> linearPriority;

	@Override
	public int getID() {
		return ID;
	}
	
	public void setID(int iD) {
		ID = iD;
	}

	@Override
	public ArrayList<Integer> getLinearPriority() {
		return linearPriority;
	}
	
	public void setLinearPriority(ArrayList<Integer>  linearPriority) {
		this.linearPriority = linearPriority;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Boss boss = (Boss) o;
		return ID == boss.ID;
	}

	@Override
	public int hashCode() {

		return Objects.hash(ID);
	}
}
