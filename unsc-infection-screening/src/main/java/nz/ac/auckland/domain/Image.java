package nz.ac.auckland.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Image {
	
	@Column(nullable=false)
	private String _title;
	
	@Column(nullable=false)
	private String _filename;
	
	private int _sizeX;
	private int _sizeY;
	
	protected Image() {}
	
	public Image(String title, String filename, int sizeX, int sizeY) {
		_title = title;
		_filename = filename;
		_sizeX = sizeX;
		_sizeY = sizeY;
	}
	
	public String getTitle() {
		return _title;
	}
	
	public String getFilename() {
		return _filename;
	}
	
	public int getSizeX() {
		return _sizeX;
	}
	
	public int getSizeY() {
		return _sizeY;
	}
}
