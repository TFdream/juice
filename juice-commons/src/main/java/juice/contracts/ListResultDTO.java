package juice.contracts;

import java.io.Serializable;
import java.util.List;

/**
 * @author Ricky Fung
 */
public class ListResultDTO<T> implements Serializable {
    private static final long serialVersionUID = -1L;

    /**
     * 默认实例
     */
    public static final ListResultDTO DEFAULT = new ListResultDTO();

    private List<T> list;

    public ListResultDTO() {}

    public ListResultDTO(List<T> list) {
        this.list = list;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
