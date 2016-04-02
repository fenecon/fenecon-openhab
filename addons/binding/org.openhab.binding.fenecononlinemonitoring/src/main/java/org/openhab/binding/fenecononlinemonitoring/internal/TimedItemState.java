package org.openhab.binding.fenecononlinemonitoring.internal;

import java.io.Serializable;
import java.util.Calendar;

public class TimedItemState<T> implements Serializable {
    private static final long serialVersionUID = 12L;

    private Long time;
    private String itemName;
    private T state;

    /**
     * T ideally is a java.util class, which is directly supported by MapDB serializer
     *
     * @param itemName
     * @param state
     */
    public TimedItemState(String itemName, T state) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        this.time = calendar.getTimeInMillis() / 1000;
        this.itemName = itemName;
        this.state = state;
    }

    public String getItemName() {
        return itemName;
    }

    public T getState() {
        return state;
    }

    public Long getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "TimedItemState [time=" + time + ", itemName=" + itemName + ", state=" + state + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((itemName == null) ? 0 : itemName.hashCode());
        result = prime * result + ((state == null) ? 0 : state.hashCode());
        result = prime * result + ((time == null) ? 0 : time.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TimedItemState<?> other = (TimedItemState<?>) obj;
        if (itemName == null) {
            if (other.itemName != null)
                return false;
        } else if (!itemName.equals(other.itemName))
            return false;
        if (state == null) {
            if (other.state != null)
                return false;
        } else if (!state.equals(other.state))
            return false;
        if (time == null) {
            if (other.time != null)
                return false;
        } else if (!time.equals(other.time))
            return false;
        return true;
    }
}
