package io.codelex.flightplanner.domain;

import java.util.List;
import java.util.Objects;

public class PageResult<T> {
    private int page;
    private int totalItems;
    private List<T> items;

    public PageResult(int page, int totalItems, List<T> items) {
        this.page = page;
        this.totalItems = totalItems;
        this.items = items;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PageResult<?> that = (PageResult<?>) o;
        return page == that.page && totalItems == that.totalItems && Objects.equals(items, that.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(page, totalItems, items);
    }

    @Override
    public String toString() {
        return "PageResult{" +
                "page=" + page +
                ", totalItems=" + totalItems +
                ", items=" + items +
                '}';
    }
}


