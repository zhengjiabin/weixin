package weixin.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 凭证对象
 * 
 * @author Administrator
 *
 */
@XmlRootElement
public class AccessToken implements Serializable {
	private String access_token;
	private int expires_in;
	private long get_access_token_time;
	private int errcode;
	private String errmsg;

	private static final long serialVersionUID = 1L;

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public int getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
	}

	public long getGet_access_token_time() {
		return get_access_token_time;
	}

	public void setGet_access_token_time(long get_access_token_time) {
		this.get_access_token_time = get_access_token_time;
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
