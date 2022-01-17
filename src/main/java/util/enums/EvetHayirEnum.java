package util.enums;

import util.BaysResource;

public enum EvetHayirEnum {
	EVET (1, BaysResource.get("evet")), 
	HAYIR (2, BaysResource.get("hayir"));

	private final int value;
	private final String label;

	EvetHayirEnum(int value, String label) {
		this.value = value;
		this.label = label;
	}

	public int getValue() {
		return value;
	}

	public String getLabel() {
		return label;
	}

	public static String getLabel(int val) {
		String label = "";

		for (EvetHayirEnum evetHayirEnum : EvetHayirEnum.values()) {
			if (evetHayirEnum.getValue() == val) {
				label = evetHayirEnum.getLabel();
				break;
			}
		}

		return label;
	}

}