package cn.ddcome.test;

import java.util.List;

import org.apdplat.word.segmentation.Word;
import org.junit.Test;

import cn.ddcome.util.WordSegmenter;

public class TestWordSegmenter {

	@Test
	public void test() {
		List<Word> words = WordSegmenter.segWithStopWords("�����Ұ��㵫����Ҫ�뿪��Ϊʲô��ϧ��");
		System.out.println("====================================");
		System.out.println(words);
		for(int i=0; i<words.size(); i++) {
			System.out.println(words.get(i));
		}
	}
}
