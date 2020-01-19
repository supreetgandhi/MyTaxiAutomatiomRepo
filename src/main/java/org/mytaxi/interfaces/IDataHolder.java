package org.mytaxi.interfaces;

import java.util.List;

public interface IDataHolder {
	
	Integer getUserID();
	void setUserID(int userid);
	List<Integer> getPostid();
	void setPostId(List<Integer> postid);
}
