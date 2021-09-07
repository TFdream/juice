package juice.commons.range;

import juice.util.DateUtils;
import java.util.Date;

/**
 * 时间范围
 * @author Ricky Fung
 */
public class TimeRange {
    /**
     * 开始时间（包含）
     */
    private final Date startTime;
    
    /**
     * 结束时间（包含）
     */
    private final Date endTime;

    public TimeRange(Date startTime, Date endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static TimeRange of(Date start, Date end) {
        if (end.before(start)) {
            throw new IllegalArgumentException("结束时间必须在开始时间之后");
        }
        return new TimeRange(start, end);
    }

    //=======

    /**
     * 判断与指定的时间段是否有重叠
     * @param slot
     * @return
     */
    public boolean isIntersection(TimeRange slot) {
        if (slot == null) {
            return false;
        }
        return !(le(this, slot) || ge(this, slot));
    }

    /**
     * s1在s2的左侧
     * @param s1
     * @param s2
     * @return
     */
    public boolean le(TimeRange s1, TimeRange s2) {
        return lt(s1, s2) || s1.getEndTime().equals(s2.getStartTime());
    }
    public boolean lt(TimeRange s1, TimeRange s2) {
        return s1.getEndTime().before(s2.getStartTime());
    }

    /**
     * s1在s2的右侧
     * @param s1
     * @param s2
     * @return
     */
    public boolean ge(TimeRange s1, TimeRange s2) {
        return gt(s1, s2) || s1.getStartTime().equals(s2.getEndTime());
    }
    public boolean gt(TimeRange s1, TimeRange s2) {
        return s1.getStartTime().after(s2.getEndTime());
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        return String.format("[%s, %s]", DateUtils.format(startTime), DateUtils.format(endTime));
    }
}