package com.accenture.dao.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.accenture.dao.entity.TDhhLeverMst;
import com.accenture.dao.entity.TDhhQuestionMst;
import com.accenture.dao.entity.TDhhSquadLeverScore;
import com.accenture.dao.entity.TDhhSquadMst;
import com.accenture.dao.entity.TDhhSurveyMst;
import com.accenture.gmas.dto.LeverMaturityScoreDto;
import com.accenture.gmas.dto.LeverScoreDto;
import com.accenture.gmas.dto.SquadScoreDto;
import com.accenture.gmas.repo.TDhhDocumentMstRepo;
import com.accenture.gmas.repo.TDhhLeverMstRepo;
import com.accenture.gmas.repo.TDhhQuestionMstRepo;
import com.accenture.gmas.repo.TDhhSquadLeverScoreRepo;
import com.accenture.gmas.repo.TDhhSquadMstRepo;
import com.accenture.gmas.repo.TDhhSurveyMstRepo;
import com.accenture.gmas.repo.TDhhusersueveydocumentansRepo;
import com.accenture.gmas.repo.TDhhusersurveyansRepo;

@Service
@ComponentScan("com.accenture.dao.config")
public class GMASTaskDao {
	@Autowired
	private TDhhSquadLeverScoreRepo tdhhsquadleverscoreRepo;
	
	@Autowired
	private TDhhQuestionMstRepo tdhhQuestionMstRepo;
	
	@Autowired
	private TDhhusersurveyansRepo tdhhusersurveyansRepo;
	
	@Autowired
	private TDhhusersueveydocumentansRepo tdhhusersueveydocumentansRepo;	

	@Autowired
	private TDhhSurveyMstRepo tdhhsurveymstRepo;
	
	@Autowired
	private TDhhDocumentMstRepo tdhhDocMstRepo;
	
	@Autowired
	private TDhhLeverMstRepo tdhhlevermstRepo;
	
	@Autowired
	private TDhhSquadMstRepo tdhhSquadMstRepo;
	
	private Map<TDhhLeverMst, List<SquadScoreDto>> leverMap;
	private Map<Long, Double> leverAggregation;
	
	public LeverMaturityScoreDto getScoreByLeverandSquad(Long surveyId){
		List<TDhhSquadLeverScore> suqdLeverScore = tdhhsquadleverscoreRepo.fetchSquadLeverScoreBySurveyId(surveyId);
		List<SquadScoreDto> squadSocreDtoList = new ArrayList<>();
		List<LeverScoreDto> leverScoreDtoList = new ArrayList<>();
		LeverMaturityScoreDto leverScoreDto = new LeverMaturityScoreDto();
		leverMap = new HashMap<>();
		leverAggregation = new HashMap<>();
		Double aggregation = null;
		for(TDhhSquadLeverScore squadLeverScore : suqdLeverScore){					
			if(!leverMap.containsKey(squadLeverScore.getTDhhLeverMst())){
				squadSocreDtoList = new ArrayList<>();
				aggregation = null;
			}
			SquadScoreDto squadScore = getSquadScore(squadLeverScore,tdhhSquadMstRepo.fetchSurveyfte(surveyId));
			if(null == aggregation){
				aggregation = squadScore.getDigitalHubMaturityLevel();
			}else{
				aggregation =(Math.round((Double.sum(aggregation,squadScore.getDigitalHubMaturityLevel())*10))/10.0);
			}
			squadSocreDtoList.add(squadScore);
			leverMap.put(squadLeverScore.getTDhhLeverMst(),squadSocreDtoList);
			leverAggregation.put(squadLeverScore.getTDhhLeverMst().getLeverId(), aggregation);
		}
		Double maturityScore = null;
		for(Map.Entry<TDhhLeverMst, List<SquadScoreDto>> entry : leverMap.entrySet()){
			TDhhLeverMst leverMst = entry.getKey();
			LeverScoreDto leverScore = new LeverScoreDto();	
			maturityScore = getLeverScore(maturityScore, leverMst, leverScore);
			leverScoreDtoList.add(leverScore);
		}
		Iterable<TDhhLeverMst>  levers = tdhhlevermstRepo.findAll();
		squadSocreDtoList = new ArrayList<>();
		aggregation = null;
		for(TDhhLeverMst lever : levers){
			if(!leverMap.containsKey(lever)){
				LeverScoreDto leverScore = new LeverScoreDto();
				leverScore.setLeverId(lever.getLeverId());
				leverScore.setLeverName(lever.getLeverName());				
				leverScore.setSquadScoreList(squadSocreDtoList);
				leverScore.setAggregation(aggregation);
				leverScoreDtoList.add(leverScore);
			}
		}
		//Sorting by lever id
		for(int i=0;i<leverScoreDtoList.size();i++) {
			for(int j=i+1;j<leverScoreDtoList.size();j++) {
				if(leverScoreDtoList.get(i).getLeverId()>leverScoreDtoList.get(j).getLeverId()) {
					LeverScoreDto temp=new LeverScoreDto();
					temp=leverScoreDtoList.get(j);
					leverScoreDtoList.set(j, leverScoreDtoList.get(i));
					leverScoreDtoList.set(i, temp);
				}
			}
		}		
		leverScoreDto.setLeverScoreList(leverScoreDtoList);
		
		if(leverMap.size()>0){
			leverScoreDto.setMaturityLevel(Math.round((maturityScore/leverMap.size())*10)/10.0);
		}
		
		return leverScoreDto;
	}

	private Double getLeverScore(Double maturityScore, TDhhLeverMst leverMst, LeverScoreDto leverScore) {
		Double aggregation;
		leverScore.setLeverId(leverMst.getLeverId());
		leverScore.setLeverName(leverMst.getLeverName());
		aggregation = leverAggregation.get(leverMst.getLeverId());
		leverScore.setAggregation(aggregation);
		leverScore.setSquadScoreList(leverMap.get(leverMst));
		if(null == maturityScore){
			maturityScore = aggregation;
		}else{
			maturityScore = maturityScore+aggregation;
		}
		return maturityScore;
	}

	private SquadScoreDto getSquadScore(TDhhSquadLeverScore squadLeverScore,int totfte) {
		SquadScoreDto squadScore = new SquadScoreDto();
		TDhhSquadMst squadMst = squadLeverScore.getTDhhSquadMst();
		squadScore.setSquadId(squadMst.getSquadId());
		squadScore.setSquadName(squadMst.getSquadName());
		squadScore.setFte(squadMst.getFte());
		squadScore.setSingleSquadMaturityLevel( Math.round((squadLeverScore.getSingleSquadMaturityLevel()*100)*10)/10.0);
		Double digiscore=0.0;
		if(totfte!=0)
		 digiscore=((squadLeverScore.getSingleSquadMaturityLevel())*(squadMst.getFte()))/(totfte);
		squadScore.setDigitalHubMaturityLevel(Math.round((digiscore*100)*10)/10.0);
		squadScore.setQuesScore(squadLeverScore.getQuesScore());
		squadScore.setKpiScore(squadLeverScore.getKpiScore());
		squadScore.setDocScore(squadLeverScore.getDocScore());
		squadScore.setQuesScoreAdj(squadLeverScore.getQuesScoreAdj());
		squadScore.setKpiScoreAdj(squadLeverScore.getKpiScoreAdj());
		squadScore.setDocScoreAdj(squadLeverScore.getDocScoreAdj());
		return squadScore;
	}
	
	public List<Object> getQuestionAnsBysurveyId(Long surveyid){
		
		return tdhhusersurveyansRepo.fetchSurveyQuestionAns(surveyid);		
	}
	public String getQuestionById(Long questionId){
		return tdhhQuestionMstRepo.findById(questionId).get().getQuestion();
	}
	
	public List<Object> getDocumentAns(Long surveyId) {
		return tdhhusersueveydocumentansRepo.getDocumentAns(surveyId);
	}

	public String getLevername(long leverId) {
		return tdhhlevermstRepo.findById(leverId).get().getLeverName();
	}

	public TDhhSurveyMst getSurveyMst(Long surveyId) {
		return tdhhsurveymstRepo.findById(surveyId).get();
	}

	public String getDocumentName(Long documentId) {
		return tdhhDocMstRepo.findById(documentId).get().getDocumentDesc();
	}

	public Map<Long, String> getQuestionMst(long leverId) {
		Map<Long, String> quesMap = new HashMap<>();
		List<TDhhQuestionMst> tDhhQuestionMstList = tdhhQuestionMstRepo.getQuestionMst(leverId);
		tDhhQuestionMstList.forEach(tDhhQuestionMst->{
			quesMap.put(tDhhQuestionMst.getQuestionId(), tDhhQuestionMst.getQuestion());
		});
		return quesMap;
	}
}
