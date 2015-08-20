package skinschanger.shared.format;

public class SkinProfile {
	private long timestamp;
	private SkinProperty playerSkinData;

	public SkinProfile(SkinProperty skinData) {
		this.timestamp = System.currentTimeMillis();
		this.playerSkinData = skinData;
	}

	public SkinProfile(SkinProperty skinData, long creationTime) {
		this(skinData);
		this.timestamp = creationTime;
	}

	public boolean isTooDamnOld() {
		return System.currentTimeMillis() - this.timestamp > 864000000L;
	}

	public long getCreationDate() {
		return this.timestamp;
	}

	public SkinProperty getPlayerSkinProperty() {
		return this.playerSkinData;
	}
}