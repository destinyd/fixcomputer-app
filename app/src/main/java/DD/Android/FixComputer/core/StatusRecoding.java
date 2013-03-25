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
public class StatusRecoding implements Serializable {
    private static final long serialVersionUID = 7102057455843123457L;

    public String _id;
    String status,plus,problem_id;
    Date created_at,updated_at;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public String getPlus() {
        return plus;
    }

    public void setPlus(String plus) {
        this.plus = plus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public String getProblem_id() {
        return problem_id;
    }

    public void setProblem_id(String problem_id) {
        this.problem_id = problem_id;
    }

    public String getStatusStr(){
        if(status == null)
            return "";
        if(status.equals("contacted"))
            return "已联系";
        else if(status.equals("visited"))
            return "已上门";
        else if(status.equals("token"))
            return "带回维修";
        else if(status.equals("repaired"))
            return "维修完毕";
        else if(status.equals("paid"))
            return "已付款";
        else if(status.equals("finish"))
            return "完毕";
        else if(status.equals("cancel"))
            return "取消";
        else
            return "";
    }
}
