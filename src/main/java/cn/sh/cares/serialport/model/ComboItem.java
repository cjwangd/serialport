package cn.sh.cares.serialport.model;

public class ComboItem {
    private String label;
    private Object value;

    public ComboItem(String label, Object value) {
        this.label = label;
        this.value = value;
    }

    @Override
    public String toString() {
        return label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
