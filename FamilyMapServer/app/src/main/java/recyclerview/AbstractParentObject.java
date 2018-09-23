package recyclerview;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.List;

public abstract class AbstractParentObject implements ParentObject {
    private String parentTitleText;
    private List<Object> childrenList;

    public AbstractParentObject() {}
    public AbstractParentObject(String parentTitleText) {
        this.parentTitleText = parentTitleText;
    }

    @Override
    public List<Object> getChildObjectList() {
        return childrenList;
    }

    @Override
    public void setChildObjectList(List<Object> list) {
        childrenList = list;
    }

    public String getParentTitleText() {
        return parentTitleText;
    }

    public void setParentTitleText(String parentTitleText) {
        this.parentTitleText = parentTitleText;
    }
}
