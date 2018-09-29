package Blob;

import java.io.File;
import java.util.ArrayList;

import javax.swing.filechooser.FileFilter;

public class ExtensionFileFilter extends FileFilter{//实现文件过滤功能
private String description="";
private ArrayList<String>extensions=new ArrayList<>();
public void addExtension(String extension) {//用于添加文件扩展名
	if(extension.startsWith(".")) {
		extension="."+extension;
		extensions.add(extension.toLowerCase());
	}
}
public void setDescription(String aDescription) {
	description=aDescription;
}
public String getDescription() {
	return description;
}
public boolean accept(File f) {//继承FileFilter必须实现的方法，判断该文件过滤器是否接受该文件
	if(f.isDirectory())	return true;//判断该文件是否是路径
	String name=f.getName().toLowerCase();
	for(String extension :extensions) {//判断扩展名是否相同，该文件可接受
		if(name.endsWith(extension)) {
			return true;
		}
	}
	return false;
}
}
