/**
 * 
 */
package cmu.webserver.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author apurv
 *
 */
public class HTTPRequestDetails {
	private HTTPMethod method;
	private String relativePath;
	private String webPage;
	private Map<String,String> HTTPHeaders = new HashMap<>();
	
	public HTTPMethod getMethod() {
		return method;
	}
	
	public void setMethod(HTTPMethod method) {
		this.method = method;
	}
	
	public String getRelativePath() {
		return relativePath;
	}
	
	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
	}
	
	public String getWebPage() {
		return webPage;
	}
	
	public void setWebPage(String webPage) {
		this.webPage = webPage;
	}
	
	public Map<String, String> getHTTPHeaders() {
		return HTTPHeaders;
	}
	
	public void addHTTPHeader(String key, String value) {
		this.HTTPHeaders.put(key, value);
	}
	
	public void setHTTPHeaders(Map<String, String> hTTPHeaders) {
		HTTPHeaders = hTTPHeaders;
	}
	
	@Override
	public String toString() {
		return "HTTPRequestDetails [method=" + method + ", relativePath=" + relativePath + ", webPage=" + webPage
				+ ", HTTPHeaders=" + HTTPHeaders + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((HTTPHeaders == null) ? 0 : HTTPHeaders.hashCode());
		result = prime * result + ((method == null) ? 0 : method.hashCode());
		result = prime * result + ((relativePath == null) ? 0 : relativePath.hashCode());
		result = prime * result + ((webPage == null) ? 0 : webPage.hashCode());
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
		HTTPRequestDetails other = (HTTPRequestDetails) obj;
		if (method != other.method)
			return false;
		if (relativePath == null) {
			if (other.relativePath != null)
				return false;
		} else if (!relativePath.equals(other.relativePath))
			return false;
		if (webPage == null) {
			if (other.webPage != null)
				return false;
		} else if (!webPage.equals(other.webPage))
			return false;
		return true;
	}
	
}
