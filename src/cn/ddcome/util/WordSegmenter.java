package cn.ddcome.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apdplat.word.recognition.StopWord;
import org.apdplat.word.segmentation.SegmentationAlgorithm;
import org.apdplat.word.segmentation.SegmentationFactory;
import org.apdplat.word.segmentation.Word;
import org.apdplat.word.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * APDPlat - Application Product Development Platform
 * Copyright (c) 2013, ���д�, yang-shangchuan@qq.com
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
public class WordSegmenter {

	private static final Logger LOGGER = LoggerFactory.getLogger(WordSegmenter.class);    
    /**
     * ���ı����зִʣ�����ͣ�ô�
     * ��ָ�������ִ��㷨
     * @param text �ı�
     * @param segmentationAlgorithm �ִ��㷨
     * @return �ִʽ��
     */
    public static List<Word> segWithStopWords(String text, SegmentationAlgorithm segmentationAlgorithm){
        return SegmentationFactory.getSegmentation(segmentationAlgorithm).seg(text);
    }
    /**
     * ���ı����зִʣ�����ͣ�ô�
     * ʹ��˫�����ƥ���㷨
     * @param text �ı�
     * @return �ִʽ��
     */
    public static List<Word> segWithStopWords(String text){
        return SegmentationFactory.getSegmentation(SegmentationAlgorithm.MaxNgramScore).seg(text);
    }
    /**
     * ���ı����зִʣ��Ƴ�ͣ�ô�
     * ��ָ�������ִ��㷨
     * @param text �ı�
     * @param segmentationAlgorithm �ִ��㷨
     * @return �ִʽ��
     */
    public static List<Word> seg(String text, SegmentationAlgorithm segmentationAlgorithm){        
        List<Word> words = SegmentationFactory.getSegmentation(segmentationAlgorithm).seg(text);
        //ͣ�ôʹ���
        StopWord.filterStopWords(words);
        return words;
    }
    /**
     * ���ı����зִʣ��Ƴ�ͣ�ô�
     * ʹ��˫�����ƥ���㷨
     * @param text �ı�
     * @return �ִʽ��
     */
    public static List<Word> seg(String text){
        List<Word> words = SegmentationFactory.getSegmentation(SegmentationAlgorithm.MaxNgramScore).seg(text);
        //ͣ�ôʹ���
        StopWord.filterStopWords(words);
        return words;
    }
    /**
     * ���ļ����зִʣ�����ͣ�ô�
     * ��ָ�������ִ��㷨
     * @param input �����ļ�
     * @param output ����ļ�
     * @param segmentationAlgorithm �ִ��㷨
     * @throws Exception 
     */
    public static void segWithStopWords(File input, File output, SegmentationAlgorithm segmentationAlgorithm) throws Exception{
        Utils.seg(input, output, false, segmentationAlgorithm);
    }
    /**
     * ���ļ����зִʣ�����ͣ�ô�
     * ʹ��˫�����ƥ���㷨
     * @param input �����ļ�
     * @param output ����ļ�
     * @throws Exception 
     */
    public static void segWithStopWords(File input, File output) throws Exception{
        Utils.seg(input, output, false, SegmentationAlgorithm.MaxNgramScore);
    }
    /**
     * ���ļ����зִʣ��Ƴ�ͣ�ô�
     * ��ָ�������ִ��㷨
     * @param input �����ļ�
     * @param output ����ļ�
     * @param segmentationAlgorithm �ִ��㷨
     * @throws Exception 
     */
    public static void seg(File input, File output, SegmentationAlgorithm segmentationAlgorithm) throws Exception{
        Utils.seg(input, output, true, segmentationAlgorithm);
    }
    /**
     * ���ļ����зִʣ��Ƴ�ͣ�ô�
     * ʹ��˫�����ƥ���㷨
     * @param input �����ļ�
     * @param output ����ļ�
     * @throws Exception 
     */
    public static void seg(File input, File output) throws Exception{
        Utils.seg(input, output, true, SegmentationAlgorithm.MaxNgramScore);
    }
    private static void demo(){
        long start = System.currentTimeMillis();
        List<String> sentences = new ArrayList<>();
        sentences.add("���д���APDPlatӦ�ü���Ʒ����ƽ̨������");
        sentences.add("��˵��ȷʵ����");
        sentences.add("�����������ˮƽ");
        sentences.add("������̸�����Ǵ�ͷ��Ԫ�¿�ʼ��");
        sentences.add("�����������ʩ�ͷ�����һ����");
        sentences.add("�ͷ��������պ������ϣ������ͽ�������");
        sentences.add("�о���������Դ");
        sentences.add("����������ȥ����");
        sentences.add("����Щ��ҵ�й�����ҵ��ʮ��");
        sentences.add("��վ������");
        sentences.add("�����������̩ײ���Ǽ��µ�");
        sentences.add("��������Ĳ��̩Զ������");
        sentences.add("�����г������´�");
        sentences.add("��������ˤ������,����������һ��");
        sentences.add("ƹ������������");
        sentences.add("ҧ�����˵Ĺ�");
        sentences.add("������˺���ѩ");
        sentences.add("�⼸���������治С");
        sentences.add("��ѧ�������ֽ");
        sentences.add("��ϳɷ���ʽ");
        sentences.add("���������");
        sentences.add("��չ�й����õļƻ�");
        sentences.add("��������������");
        sentences.add("˰���ƶȽ����������");
        sentences.add("����Ⱥ�ڲ������ù���");
        sentences.add("������ʩչ���ܵĺû���");
        sentences.add("���־�����");
        sentences.add("������ÿ�");
        sentences.add("����Ű��ֻ���");
        sentences.add("�豭�İ��ֶ���");
        sentences.add("����������һ���н�");
        sentences.add("���������н���������");
        sentences.add("���µ���̬���������綫��");
        sentences.add("ʹ��Լ��ʳ��һ���γɷ���");
        sentences.add("��ӳ��һ���˵ľ�����ò");
        sentences.add("�������ݴ�ѧ�Ŀ�ѧ�ҷ���");
        sentences.add("�Һò�ͦ��");
        sentences.add("ľ��"); 
        sentences.add("�����������������Ҳ���");
        sentences.add("��������������Ҳ������");
        sentences.add("�������");
        sentences.add("ѧ����д����");
        sentences.add("��Ҵ����¾�");
        sentences.add("��Ҵ��������");
        sentences.add("�л����񹲺͹���������������");
        sentences.add("word��һ�����ķִ���Ŀ�����������д������д���Ӣ������ysc");
        sentences.add("����ë�ĳ������˱��ֵ�Ա�Ƚ��԰�������ѧϰС��,�ڽ�����·�����Э����,ͨ��������ʿ��˾,����ţ�����鷿ɽ����������˿����Խ���ʽ��������, ��Ϊgoogle�˳��й��¼������������˵۹�������Ļ�����,�����˰��������Ⱥ���Ը߳���");
        sentences.add("���Ŵ�Ů����ÿ�¾����������Ҷ�Ҫ�׿ڽ���24�ڽ������ȼ����������İ�װ����");
        sentences.add("��Ʒ�ͷ���");
        sentences.add("���ĺ���δ����");
        sentences.add("��ˮ��Ȼ��������԰");
        sentences.add("�й����׶��Ǳ���");
        sentences.add("��ʦ˵����������Ϣ");
        sentences.add("��������");
        int i=1;
        for(String sentence : sentences){
            List<Word> words = segWithStopWords(sentence);
            LOGGER.info((i++)+"���з־���: "+sentence);
            LOGGER.info("    �зֽ����"+words);
        }
        long cost = System.currentTimeMillis() - start;
        LOGGER.info("��ʱ: "+cost+" ����");
    }
    public static void processCommand(String... args) {
        if(args == null || args.length < 1){
            LOGGER.info("�����ȷ");
            return;
        }
        try{
            switch(args[0].trim().charAt(0)){
                case 'd':
                    demo();
                    break;
                case 't':
                    if(args.length < 2){
                        showUsage();
                    }else{
                        StringBuilder str = new StringBuilder();
                        for(int i=1; i<args.length; i++){
                            str.append(args[i]).append(" ");
                        }
                        List<Word> words = segWithStopWords(str.toString());
                        LOGGER.info("�з־��ӣ�"+str.toString());
                        LOGGER.info("�зֽ����"+words.toString());
                    }
                    break;
                case 'f':
                    if(args.length != 3){
                        showUsage();
                    }else{
                        segWithStopWords(new File(args[1]), new File(args[2]));
                    }
                    break;
                default:
                    StringBuilder str = new StringBuilder();
                    for(String a : args){
                        str.append(a).append(" ");
                    }
                    List<Word> words = segWithStopWords(str.toString());
                    LOGGER.info("�з־��ӣ�"+str.toString());
                    LOGGER.info("�зֽ����"+words.toString());
                    break;
            }
        }catch(Exception e){
            showUsage();
        }
    }
    private static void run(String encoding) {
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, encoding))){
            String line = null;
            while((line = reader.readLine()) != null){
                if("exit".equals(line)){
                    System.exit(0);
                    LOGGER.info("�˳�");
                    return;
                }
                if(line.trim().equals("")){
                    continue;
                }
                processCommand(line.split(" "));
                showUsage();
            }
        } catch (IOException ex) {
            LOGGER.error("�����жϣ�", ex);
        }
    }
    private static void showUsage(){
        LOGGER.info("");
        LOGGER.info("********************************************");
        LOGGER.info("�÷�: command [text] [input] [output]");
        LOGGER.info("����command�Ŀ�ѡֵΪ��demo��text��file");
        LOGGER.info("�����ʹ����дd t f���粻ָ�������Ĭ��Ϊtext�����������ı��ִ�");
        LOGGER.info("demo");
        LOGGER.info("text ���д���APDPlatӦ�ü���Ʒ����ƽ̨������");
        LOGGER.info("file d:/text.txt d:/word.txt");
        LOGGER.info("exit");
        LOGGER.info("********************************************");
        LOGGER.info("���������س�ȷ�ϣ�");
    }
    public static void main(String[] args) {
        String encoding = "utf-8";
        if(args==null || args.length == 0){
            showUsage();
            run(encoding);
        }else if(Charset.isSupported(args[0])){
            showUsage();
            run(args[0]);
        }else{
            processCommand(args);
            //�ǽ���ģʽ���˳�JVM
            System.exit(0);
        }
    }
}
