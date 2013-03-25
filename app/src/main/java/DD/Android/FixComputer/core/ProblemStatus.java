package DD.Android.FixComputer.core;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: dd
 * Date: 13-3-20
 * Time: 上午10:18
 * To change this template use File | Settings | File Templates.
 */
public class ProblemStatus implements Serializable {

    Problem last;
    Date last_changed_at;

    public Problem getLast() {
        return last;
    }

    public void setLast(Problem last) {
        this.last = last;
    }

    public Date getLast_changed_at() {
        return last_changed_at;
    }

    public void setLast_changed_at(Date last_changed_at) {
        this.last_changed_at = last_changed_at;
    }
}
