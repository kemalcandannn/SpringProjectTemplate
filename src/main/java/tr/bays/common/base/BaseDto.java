package tr.bays.common.base;

import java.io.Serializable;

import lombok.Data;

@SuppressWarnings("serial")
@Data
public abstract class BaseDto implements Serializable {
	private Long id;
}
