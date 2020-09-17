package hr.fer.java.zemris.hw07.observer2;

public class IntegerStorageChange {

	private IntegerStorage storage;
	private int beforeChange;
	private int afterChange;
	
	public IntegerStorageChange(IntegerStorage storage, int beforeChange, int afterChange) {
		super();
		this.storage = storage;
		this.beforeChange = beforeChange;
		this.afterChange = afterChange;
	}

	public IntegerStorage getStorage() {
		return storage;
	}

	public int getBeforeChange() {
		return beforeChange;
	}

	public int getAfterChange() {
		return afterChange;
	}
}
