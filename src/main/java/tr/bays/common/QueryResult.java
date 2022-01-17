package tr.bays.common;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QueryResult<T> {
    private List<T> list;
    private int count;
}
