package skinschanger.shared.format;

public class SkinProperty {
	private String name;
	private String value;
	private String signature;

	public SkinProperty(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public SkinProperty(String name, String value, String signature) {
		this(name, value);
		this.signature = signature;
	}

	public String getName() {
		return this.name;
	}

	public String getValue() {
		return this.value;
	}

	public boolean hasSignature() {
		return this.signature != null;
	}

	public String getSignature() {
		return this.signature;
	}
}