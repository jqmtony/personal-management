package cn.xt.pmc.management.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 *
 * Created by xtao on 2018-1-21.
 */
public class BlogType implements Serializable{
	/**
     * 
     */
	private Long id;
	/**
     * 
     */
	private Long pid;
	/**
     * 
     */
	private String name;

	List<BlogType> children;

	public void setId(Long id){
		this.id = id;
	}

	public Long getId(){
		return this.id;
	}
	public void setPid(Long pid){
		this.pid = pid;
	}

	public Long getPid(){
		return this.pid;
	}
	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}

	public List<BlogType> getChildren() {
		return children;
	}

	public void setChildren(List<BlogType> children) {
		this.children = children;
	}
}