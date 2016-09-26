package vacuum;

/** One square in the vacuum world. */
public class Square {

	/** True if there is dirt in this square. */
	private boolean dirty;
	
	/** True if there is an obstacle that prevents an agent from entering this square. */
	private boolean obstacle;

	/** Returns true if this square is dirty. */
	public boolean isDirty() {
		return dirty;
	}

	/** Returns true if this square is an obstacle. */
	public boolean isObstacle() {
		return obstacle;
	}

	/** Sets the dirtiness of this square. */
	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}

	/** Sets whether there is an obstacle in this square. */
	public void setObstacle(boolean obstacle) {
		this.obstacle = obstacle;
	}

}
