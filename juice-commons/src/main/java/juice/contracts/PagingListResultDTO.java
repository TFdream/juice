package juice.contracts;

import juice.util.CollectionUtils;

import java.io.Serializable;
import java.util.List;

/**
 * 分页方式返回结果
 * @author Ricky Fung
 */
public class PagingListResultDTO<T> extends ListResultDTO<T> implements Serializable {
    private static final long serialVersionUID = -1L;

    /**
     * 默认实例
     */
    public static final PagingListResultDTO DEFAULT = new PagingListResultDTO();

    /**
     * 总条数
     */
    private long total;
    /**
     * 总页数
     */
    private int pages;

    //当前页
    private int pageNum;
    //每页的数量
    private int pageSize;

    //当前页的数量
    private int size;

    private String pageOffset;

    //是否有下一页
    private boolean hasNextPage;

    public PagingListResultDTO() {}

    public PagingListResultDTO(long total, List<T> dataList, int pageNum, int pageSize) {
        this.total = total;
        this.list = dataList;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        //计算分页
        this.calculatePages();
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getPageOffset() {
        return pageOffset;
    }

    public void setPageOffset(String pageOffset) {
        this.pageOffset = pageOffset;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    //===========
    /**
     * 计算总页数
     * @return
     */
    public int calculatePages() {
        if (total < 1) {
            total = 0;
        }
        if (pageSize > 0) {
            this.pages = (int) ((total + pageSize - 1) / pageSize);
        } else {
            this.pages = 1;
        }

        //判断是否有下一页
        if (pageNum < pages) {
            this.hasNextPage = true;
        }

        //当前页的数量
        this.size = CollectionUtils.size(getList());
        return pages;
    }
}
