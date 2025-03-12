package com.yb.exp.onnx;

import ai.onnxruntime.OrtException;
import ai.onnxruntime.OrtSession;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.model.scoring.onnx.OnnxScoringModel;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

@Service
public class OnnxService {


  public static void main(String[] args) throws OrtException {
    OrtSession.SessionOptions options = new OrtSession.SessionOptions();
    options.addCUDA(1);

    OnnxScoringModel scoringModel = new OnnxScoringModel("model/bge-reranker-base/model.onnx",
        options,
        "model/bge-reranker-base/tokenizer.json");

    String text = """
        北京市，简称“京”，中华人民共和国的首都及直辖市，是中国的政治、文化、科技、教育、军事和国际交往中心，曾称“北平”。北京是一座全球城市，是世界人口第三多的城市和人口最多的首都。北京位于华北平原的北部边缘，背靠燕山，毗邻天津市、河北省，为京津冀城市群的重要组成部分[2]。
        
        北京是拥有三千余年建城历史、八百六十余年建都史的历史文化名城。辽、金、元、明、清五个朝代在此定都。北京荟萃了自元明清以来的中华文化，拥有众多历史名胜古迹和人文景观，拥有8项世界遗产，是世界上拥有文化遗产项目数最多的城市。北京古迹众多，著名的有紫禁城、天坛、颐和园、圆明园、北海公园等；胡同和四合院作为北京老城的典型民居形式，已经是北京历史重要的文化符号[3]。北京是中国重要的旅游城镇，被《米其林指南》评为“三星级旅游推荐”（最高级别）目的地[4]。市人民政府驻通州区运河东大街57号。
        
        北京在中国大陆的政治、经贸、文化、教育和科技领域拥有显著影响力，为中国绝大多数国有企业总部所在地，并且拥有全球最多的财富世界500强企业总部[5]，北京商务中心区聚集了大量国际性金融商务办公设施及摩天大楼，中关村地区则集中了中国大陆重要的高新技术产业。北京是中国大陆铁路运输和公路交通运输的枢纽城市，拥有北京首都国际机场和北京大兴国际机场两座机场飞行区等级最高级别(4F级)的枢纽机场，北京地铁则是世界上运营里程第二、客运量最多的城市轨道交通系统[6][7][8]。北京是中国大陆拥有最多高等院校的城市[9]，校址位于北京的北京大学和清华大学被视为中国顶尖大学的代表[10]。北京在全球政治、经济等社会活动中处于重要地位并具有主导辐射带动能力，在GaWC全球城市排行中排名第6位，与中国的其他城市相比，仅次于香港和上海[11]。北京被世界城市研究机构GaWC评为世界一线城市[12]，联合国报告指出北京人类发展指数居中国城市第二位。北京也是世界上唯一的“双奥之城”，即既主办过夏季奥运会，也主办过冬季奥运会的城市。
        
        《北京城市总体规划（2016年—2035年）》将北京市定位为“全国政治中心、文化中心、国际交往中心、科技创新中心”，并计划加强这“四个中心”功能建设[13]。首都北京在高速发展的过程中，出现了一系列“城市病”，同时考虑到中国经济存在发展不充分不平衡的问题，创新性地成为中国第一个推行减量发展的城市[14]。北京开始疏解中心城区的非首都功能，将不符合“四个中心”的单位迁出中心城区[15]，支持河北雄安新区规划建设，高水平规划建设北京城市副中心[16]。
        """;
    Response<Double> response;

    StopWatch watch = new StopWatch();
    watch.start("first");
    response = scoringModel.score(text, "chinese ？");
    System.out.println(response.content());
    watch.stop();

    watch.start("2");
    response = scoringModel.score(text, "中国的首都是哪里？");
    System.out.println(response.content());
    watch.stop();

    watch.start("3");
    response = scoringModel.score(text, "中国的首都是哪里？");
    System.out.println(response.content());
    watch.stop();

    watch.start("4");
    response = scoringModel.score(text, "中国的首都是哪里？");
    System.out.println(response.content());
    watch.stop();

    watch.start("r");
    response = scoringModel.score(text, "日本的首都是哪里？");
    System.out.println(response.content());
    watch.stop();

    System.out.println(watch.prettyPrint());

  }
}
