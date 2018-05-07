package cn.ddcome.test;

import java.util.List;

import org.apdplat.word.segmentation.Word;
import org.junit.Test;

import cn.ddcome.util.WordSegmenter;

public class TestWordSegmenter {

	@Test
	public void test() {
		List<Word> words = WordSegmenter.segWithStopWords("广州我爱你但是我要离开你为什么可惜了");
		System.out.println("====================================");
		System.out.println(words);
		for(int i=0; i<words.size(); i++) {
			System.out.println(words.get(i));
		}
	}
}
