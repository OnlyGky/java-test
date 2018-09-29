package Blob;

public class ImageHolder {
	private int id;//封装图片的ID
	private String name;//封装图片的名字
	public ImageHolder() {
	}
	public ImageHolder(int id,String name) {
		this.id=id;
		this.name=name; 
	}
	
	public int getId() {
		return this.id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String toString(){
		return name;
	}

}
