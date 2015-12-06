package weixin.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CallbackIP implements Serializable {
	private String[] ip_list;
	private int errcode;
	private String errmsg;

	private static final long serialVersionUID = 1L;

	public String[] getIp_list() {
		return ip_list;
	}

	public void setIp_list(String[] ip_list) {
		this.ip_list = ip_list;
	}

	public int getErrcode() {
		return errcode;
	}

	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
}
