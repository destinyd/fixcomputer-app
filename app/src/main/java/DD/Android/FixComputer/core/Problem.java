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
public class Problem implements Serializable {
    private static final long serialVersionUID = 7102057195843891457L;

    public String _id;
    String phone,name,address,address_plus,desc,status,uuid;
    Double lat,lng,price;
    Date created_at,updated_at;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress_plus() {
        return address_plus;
    }

    public void setAddress_plus(String address_plus) {
        this.address_plus = address_plus;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getStatusStr(){
        if(status == null)
            return "";
        if(status.equals("order"))
            return "故障提交";
        else if(status.equals("contacted"))
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
