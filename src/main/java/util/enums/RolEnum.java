package util.enums;

import util.BaysResource;

public enum RolEnum {
	SISTEM_YONETICISI (-1L, BaysResource.get("sistem_yoneticisi"));

	private final long value;
	private final String label;

	RolEnum(long value, String label) {
		this.value = value;
		this.label = label;
	}

	public long getValue() {
		return value;
	}

	public String getLabel() {
		return label;
	}

	public static String getLabel(int val) {
		String label = "";

		for (RolEnum rolEnum : RolEnum.values()) {
			if (rolEnum.getValue() == val) {
				label = rolEnum.getLabel();
				break;
			}
		}

		return label;
	}

}