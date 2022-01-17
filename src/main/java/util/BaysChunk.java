package util;

import com.itextpdf.text.Chunk;

public class BaysChunk extends Chunk {
	public BaysChunk(Chunk ch) {
		super(ch);
	}
	public void setContent(String content) {
		this.content = new StringBuffer(content);
	}
}
