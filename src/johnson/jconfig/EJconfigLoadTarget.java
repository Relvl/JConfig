package johnson.jconfig;

/**
 * @author Johnson on 07.11.2015.
 */
public enum EJconfigLoadTarget {
	RESOURCES(false),
	FILESYSTEM(true),
	URL(false);

	private final boolean canStore;

	EJconfigLoadTarget(boolean canStore) {
		this.canStore = canStore;
	}

	public boolean isCanStore() {
		return canStore;
	}
}
