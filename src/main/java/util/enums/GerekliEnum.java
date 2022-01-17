package util.enums;

import util.BaysResource;

public enum GerekliEnum {
	GEREKLI (1, BaysResource.get("gerekli")), 
	GEREKSIZ (2, BaysResource.get("gereksiz"));

	private final int value;
	private final String label;

	GerekliEnum(int value, String label) {
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

		for (GerekliEnum gerekliEnum : GerekliEnum.values()) {
			if (gerekliEnum.getValue() == val) {
				label = gerekliEnum.getLabel();
				break;
			}
		}

		return label;
	}

}