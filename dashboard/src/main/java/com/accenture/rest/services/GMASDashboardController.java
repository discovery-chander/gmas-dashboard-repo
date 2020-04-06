package com.accenture.rest.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.dao.entity.TDhhSquadLeverScore;
import com.accenture.dao.service.GMASTaskDao;
import com.accenture.gmas.constants.MessageConstant;
import com.accenture.gmas.dto.DocumentAnsDto;
import com.accenture.gmas.dto.LeverMaturityScoreDto;
import com.accenture.gmas.dto.LeverScoreDto;
import com.accenture.gmas.dto.LeverSquadScoresDto;
import com.accenture.gmas.dto.QuestionAnsDto;
import com.accenture.gmas.dto.ResultMapper;
import com.accenture.gmas.dto.Resultreport;
import com.accenture.gmas.dto.ScoreCalcsDto;
import com.accenture.gmas.dto.SquadScoreDto;
import com.accenture.gmas.repo.TDhhDocumentMstRepo;
import com.accenture.gmas.repo.TDhhKpiMstRepo;
import com.accenture.gmas.repo.TDhhLeverMstRepo;
import com.accenture.gmas.repo.TDhhQuestionMstRepo;
import com.accenture.gmas.repo.TDhhSquadLeverScoreRepo;
import com.accenture.gmas.repo.TDhhSquadMstRepo;
import com.accenture.gmas.repo.TDhhSurveyMstRepo;
import com.accenture.gmas.repo.TDhhusersueveydocumentansRepo;
import com.accenture.gmas.repo.TDhhusersurveyansRepo;
import com.accenture.gmas.repo.TDhhusersurveykpiansRepo;
import com.accenture.utility.AWSUtility;
import com.accenture.utility.ReportUtility;

@RestController
@ComponentScan("com.accenture.dao.service,com.accenture.gmas.*,com.accenture.utility")
@EnableJpaRepositories("com.accenture.dao.config")
@EntityScan("com.accenture.dao.entity")	
@RequestMapping(value = "/maturity-dashboard")
public class GMASDashboardController {
	
	private static final Logger logger = LoggerFactory.getLogger(GMASDashboardController.class);

	
	@Autowired
	private GMASTaskDao gmasTaskDao;

	@Autowired
	private TDhhusersurveyansRepo tdhhusersurveyansRepo;

	@Autowired
	private TDhhusersurveykpiansRepo tdhhusersurveykpiansRepo;

	@Autowired
	private TDhhusersueveydocumentansRepo tdhhusersueveydocumentansRepo;

	@Autowired
	private TDhhQuestionMstRepo tdhhquestionmstRepo;

	@Autowired
	private TDhhKpiMstRepo tDhhKpiMstRepo;	

	@Autowired
	private TDhhSurveyMstRepo tdhhsurveymstRepo;
	
	@Autowired
	private TDhhDocumentMstRepo tdhhDocMstRepo;
	
	@Autowired
	private TDhhLeverMstRepo tdhhlevermstRepo;
	
	@Autowired
	private TDhhSquadMstRepo tdhhSquadMstRepo;
	
	@Autowired
	private TDhhSquadLeverScoreRepo tdhhSquadLeverScoreRepo;
	
	@Autowired
	AWSUtility awsUtility;

	@Value("${download.file.title}")
	private String downloadFileTitle;

	@Value("${generated.file.path}")
	private String generatedFilePath;

	@Value("${dashboard.file.name}")
	private String dashboardFileName;
	
	@Value("${kpi.scores.filename}")
	private String kpiscoresfilename;
	
	@Value("${questions.scores.filename}")
	private String questionsscoresfilename;
	
	@Value("${document.answers.filename}")
	private String docansfilename;
	
	@GetMapping(value = "/maturityDashboardTest")
	public String hello() {
		return "{\"startmsg\":\"GMAC maturity Dashboard...up and running\"}";
	}	
	
	
	@GetMapping(value = "/getScoreCalcs")
	public ResultMapper getScoreCalcs() {
		try {
		
		List<Object> ans=tdhhusersurveyansRepo.fetchAllAnswers();
		getQuesScore(ans);
		
		
		//Document score calcs per lever per squad
		List<Object> docans=tdhhusersueveydocumentansRepo.fetchAllAnswers();
		getDocsScore(docans);
		
		//KPI score calcs per lever per squad
		
		
		List<Object> kpians=tdhhusersurveykpiansRepo.fetchAllKPIAnswers();
		getKpiScoreCalcs(kpians);
		
		
		ResultMapper resultMapper = new ResultMapper();
		resultMapper.setErrorCode("0");
		resultMapper.setMessage("OK");
		return resultMapper;
		}
		catch (Exception e) {
			return catchException(e);
		}
		
	}
	
	private ResultMapper catchException(Exception e) {
		logger.error(e.getMessage());
		StackTraceElement[] st = e.getStackTrace();
		StringBuilder stackTraceMsg = new StringBuilder(e.getCause() + "\n" + e.getMessage() + "\n");
		for (StackTraceElement s : st) {
			stackTraceMsg.append(s + "\n");
		}
		logger.error(stackTraceMsg.toString());
		ResultMapper resultMapper = new ResultMapper();
		resultMapper.setResultObject(null);
		resultMapper.setMessage(MessageConstant.NOT_OK_CODE);
		resultMapper.setErrorCode(MessageConstant.ERROR_STATUS);
		return resultMapper;
	}
	
	private void getQuesScore(List<Object> ans) {
		
		List<LeverSquadScoresDto> perleverscores=new ArrayList<LeverSquadScoresDto>();
		List<List<ScoreCalcsDto>> allperleveranswers=getaggregateAnswers(ans);
		for(List<ScoreCalcsDto> p:allperleveranswers) {
			int y=0,pa=0,na=0,qthr=0;
			int qadj=0;
			int sid=0,lid=0;
			LeverSquadScoresDto leverSquadScoresDto=new LeverSquadScoresDto();
			for(int i=0;i<p.size();i++) {
				if(p.get(i).getQans()==4)
					y++;
				else if(p.get(i).getQans()==3)
					pa++;
				else if(p.get(i).getQans()==1)
					na++;
				leverSquadScoresDto.setLeverid(p.get(i).getLeverid());
				leverSquadScoresDto.setSquadid(p.get(i).getSquadid());
				lid=p.get(i).getLeverid();
				sid=p.get(i).getSquadid();
			}
			int tuplaid=tdhhsurveymstRepo.fetchTuplaId(sid);
			int totques=(tdhhquestionmstRepo.fetchparentquescount(lid, tuplaid)+tdhhquestionmstRepo.fetchchildquescount(lid, tuplaid))-na;
			Object[] a=(Object[])tdhhSquadMstRepo.fetchaquesadj(sid);
			if(null != a[0])
				qadj=((Double)a[0]).intValue();
			if(null != a[1])
				qthr=((Double)a[1]).intValue();
			float lscore=0;
			if(totques != 0)
			 lscore= (float) ((y+(pa* 0.5))/totques);
			
			if(pa>((qthr/100)*totques)) {
				leverSquadScoresDto.setSq_le_score_adj(lscore*((float)qadj/100));
			}else {
				leverSquadScoresDto.setSq_le_score_adj(lscore);
			}
			
			leverSquadScoresDto.setSq_le_score(lscore);
			perleverscores.add(leverSquadScoresDto);
		}
		List<TDhhSquadLeverScore> tdhhSquadLeverScorelist=new ArrayList<TDhhSquadLeverScore>();
		for(LeverSquadScoresDto l:perleverscores) {
			TDhhSquadLeverScore tDhhSquadLeverScore=new TDhhSquadLeverScore();
			if(tdhhSquadLeverScoreRepo.fetchsquadlever( l.getLeverid(),l.getSquadid()) == null) {
			tDhhSquadLeverScore.setTDhhSquadMst(tdhhSquadMstRepo.findById(Long.valueOf(l.getSquadid())).get());
			tDhhSquadLeverScore.setTDhhLeverMst(tdhhlevermstRepo.findById(Long.valueOf(l.getLeverid())).get());
			tDhhSquadLeverScore.setQuesScore(l.getSq_le_score());
			tDhhSquadLeverScore.setQuesScoreAdj(l.getSq_le_score_adj());
			
			tDhhSquadLeverScore.setActiveFlag("Y");
			}
			else {
				tDhhSquadLeverScore=tdhhSquadLeverScoreRepo.fetchsquadlever( l.getLeverid(),l.getSquadid());
				tDhhSquadLeverScore.setQuesScore(l.getSq_le_score());
				tDhhSquadLeverScore.setQuesScoreAdj(l.getSq_le_score_adj());
			}
			tdhhSquadLeverScorelist.add(tDhhSquadLeverScore);
		}
		
		tdhhSquadLeverScoreRepo.saveAll(tdhhSquadLeverScorelist);
	}
	
	private void getDocsScore(List<Object> docans) {
		List<TDhhSquadLeverScore> tdhhSquadLeverScorelist=new ArrayList<TDhhSquadLeverScore>();
		List<LeverSquadScoresDto> perleversdoccores=new ArrayList<LeverSquadScoresDto>();
		List<List<ScoreCalcsDto>> allperleverdocanswers=getaggregateAnswers(docans);
		for(List<ScoreCalcsDto> p:allperleverdocanswers) {
			int y=0;
			int sid=0,lid=0,qadj=0,qthr=0;
			LeverSquadScoresDto leverSquadScoresDto=new LeverSquadScoresDto();
			for(int i=0;i<p.size();i++) {
				if(p.get(i).getQans()==4)
					y++;
				leverSquadScoresDto.setLeverid(p.get(i).getLeverid());
				leverSquadScoresDto.setSquadid(p.get(i).getSquadid());
				lid=p.get(i).getLeverid();
				sid=p.get(i).getSquadid();
			}
			int tuplaid=tdhhsurveymstRepo.fetchTuplaId(sid);
			int totques=tdhhDocMstRepo.fetchparentquescount(lid, tuplaid);
			float lscore=0;
			if(totques != 0)
			 lscore= (float) (y/(float)totques);
			Object[] a=(Object[])tdhhSquadMstRepo.fetchakpiadj(sid);
			if(null != a[0])
				qadj=((Double)a[0]).intValue();
			if(null != a[1])
				qthr=((Double)a[1]).intValue();
			if(tdhhSquadLeverScoreRepo.fetchsquadlever(lid, sid) !=null) {
			if((lscore*100)<qthr)
				leverSquadScoresDto.setSq_le_score_adj(tdhhSquadLeverScoreRepo.fetchsquadlever(lid, sid).getQuesScore()*((float)qadj/100));
			else
				leverSquadScoresDto.setSq_le_score_adj(tdhhSquadLeverScoreRepo.fetchsquadlever(lid, sid).getQuesScore());}
			
			leverSquadScoresDto.setSq_le_score(lscore);
			perleversdoccores.add(leverSquadScoresDto);
		}
		tdhhSquadLeverScorelist=new ArrayList<TDhhSquadLeverScore>();
		for(LeverSquadScoresDto l:perleversdoccores) {
			TDhhSquadLeverScore tDhhSquadLeverScore=new TDhhSquadLeverScore();
			if(tdhhSquadLeverScoreRepo.fetchsquadlever(l.getLeverid(),l.getSquadid() ) == null) {
			tDhhSquadLeverScore.setTDhhSquadMst(tdhhSquadMstRepo.findById(Long.valueOf(l.getSquadid())).get());
			tDhhSquadLeverScore.setTDhhLeverMst(tdhhlevermstRepo.findById(Long.valueOf(l.getLeverid())).get());
			tDhhSquadLeverScore.setDocScore(l.getSq_le_score());
			tDhhSquadLeverScore.setDocScoreAdj(l.getSq_le_score_adj());
			tDhhSquadLeverScore.setActiveFlag("Y");
			
			}
			else {
				tDhhSquadLeverScore=tdhhSquadLeverScoreRepo.fetchsquadlever(l.getLeverid(),l.getSquadid());
				tDhhSquadLeverScore.setDocScore(l.getSq_le_score());
				tDhhSquadLeverScore.setDocScoreAdj(l.getSq_le_score_adj());
			}
			tdhhSquadLeverScorelist.add(tDhhSquadLeverScore);
		}
		
		tdhhSquadLeverScoreRepo.saveAll(tdhhSquadLeverScorelist);
	}
	
	private void getKpiScoreCalcs(List<Object> kpians) {
		List<TDhhSquadLeverScore> tdhhSquadLeverScorelist=new ArrayList<TDhhSquadLeverScore>();
		List<LeverSquadScoresDto> perleverskpisores=new ArrayList<LeverSquadScoresDto>();
		List<List<ScoreCalcsDto>> allperleverkpianswers=getaggregateAnswers(kpians);
		for(List<ScoreCalcsDto> p:allperleverkpianswers) {
			int sid=0,lid=0,qadj=0,qthr=0;
			float kpiscore = 0;
			LeverSquadScoresDto leverSquadScoresDto=new LeverSquadScoresDto();
			for(int i=0;i<p.size();i++) {
				kpiscore+=p.get(i).getQans();
				leverSquadScoresDto.setLeverid(p.get(i).getLeverid());
				leverSquadScoresDto.setSquadid(p.get(i).getSquadid());
				lid=p.get(i).getLeverid();
				sid=p.get(i).getSquadid();
			}
			int tuplaid=tdhhsurveymstRepo.fetchTuplaId(sid);
			int totques=tDhhKpiMstRepo.fetchkpiquescount(lid, tuplaid);
			float lscore=0;
			if(totques != 0)
			 lscore= (float) (kpiscore/(float)totques);
			
			Object[] a=(Object[])tdhhSquadMstRepo.fetchakpiadj(sid);
			if(null != a[0])
				qadj=((Double)a[0]).intValue();
			if(null != a[1])
				qthr=((Double)a[1]).intValue();
			if(tdhhSquadLeverScoreRepo.fetchsquadlever(lid, sid) !=null) {
			if((lscore*100)<qthr)
				leverSquadScoresDto.setSq_le_score_adj(tdhhSquadLeverScoreRepo.fetchsquadlever(lid, sid).getQuesScore()*((float)qadj/100));
			else
				leverSquadScoresDto.setSq_le_score_adj(tdhhSquadLeverScoreRepo.fetchsquadlever(lid, sid).getQuesScore());}
			leverSquadScoresDto.setSq_le_score(lscore);
			perleverskpisores.add(leverSquadScoresDto);
		}
		tdhhSquadLeverScorelist=new ArrayList<TDhhSquadLeverScore>();
		for(LeverSquadScoresDto l:perleverskpisores) {
			TDhhSquadLeverScore tDhhSquadLeverScore=new TDhhSquadLeverScore();
			if(tdhhSquadLeverScoreRepo.fetchsquadlever( l.getLeverid(),l.getSquadid()) == null) {
			tDhhSquadLeverScore.setTDhhSquadMst(tdhhSquadMstRepo.findById(Long.valueOf(l.getSquadid())).get());
			tDhhSquadLeverScore.setTDhhLeverMst(tdhhlevermstRepo.findById(Long.valueOf(l.getLeverid())).get());
			tDhhSquadLeverScore.setKpiScore(l.getSq_le_score());
			tDhhSquadLeverScore.setKpiScoreAdj(l.getSq_le_score_adj());
			tDhhSquadLeverScore.setActiveFlag("Y");
			
			}
			else {
				tDhhSquadLeverScore=tdhhSquadLeverScoreRepo.fetchsquadlever( l.getLeverid(),l.getSquadid());
				tDhhSquadLeverScore.setKpiScore(l.getSq_le_score());
				tDhhSquadLeverScore.setKpiScoreAdj(l.getSq_le_score_adj());
			}
			tdhhSquadLeverScorelist.add(tDhhSquadLeverScore);
		}
		
		tdhhSquadLeverScoreRepo.saveAll(tdhhSquadLeverScorelist);
		
		tdhhSquadLeverScorelist=new ArrayList<TDhhSquadLeverScore>();
		for(LeverSquadScoresDto l:perleverskpisores) {
			TDhhSquadLeverScore tDhhSquadLeverScore=new TDhhSquadLeverScore();
			if(tdhhSquadLeverScoreRepo.fetchsquadlever(l.getLeverid(),l.getSquadid()) != null) {
				tDhhSquadLeverScore=tdhhSquadLeverScoreRepo.fetchsquadlever(l.getLeverid(),l.getSquadid());
				if(tDhhSquadLeverScore.getKpiScoreAdj()<tDhhSquadLeverScore.getQuesScoreAdj() && tDhhSquadLeverScore.getKpiScoreAdj()<tDhhSquadLeverScore.getDocScoreAdj()) {
					tDhhSquadLeverScore.setSingleSquadMaturityLevel(tDhhSquadLeverScore.getKpiScoreAdj());
					int totFTE=tdhhSquadMstRepo.fetchallsquadfte();
					float digiscore= (float) ((tDhhSquadLeverScore.getKpiScoreAdj())*(tdhhSquadMstRepo.findById(Long.valueOf(l.getSquadid())).get().getFte()))/(totFTE);
					tDhhSquadLeverScore.setDigitalHubMaturityLevel(digiscore);}
				else if(tDhhSquadLeverScore.getQuesScoreAdj()<tDhhSquadLeverScore.getKpiScoreAdj() && tDhhSquadLeverScore.getQuesScoreAdj()<tDhhSquadLeverScore.getDocScoreAdj()) {
					tDhhSquadLeverScore.setSingleSquadMaturityLevel(tDhhSquadLeverScore.getQuesScoreAdj());
					int totFTE=tdhhSquadMstRepo.fetchallsquadfte();
					float digiscore= (float) ((tDhhSquadLeverScore.getQuesScoreAdj())*(tdhhSquadMstRepo.findById(Long.valueOf(l.getSquadid())).get().getFte()))/(totFTE);
					tDhhSquadLeverScore.setDigitalHubMaturityLevel(digiscore);}
				else {
					tDhhSquadLeverScore.setSingleSquadMaturityLevel(tDhhSquadLeverScore.getDocScoreAdj());
					int totFTE=tdhhSquadMstRepo.fetchallsquadfte();
					float digiscore= (float) ((tDhhSquadLeverScore.getDocScoreAdj())*(tdhhSquadMstRepo.findById(Long.valueOf(l.getSquadid())).get().getFte()))/(totFTE);
					tDhhSquadLeverScore.setDigitalHubMaturityLevel(digiscore);}
			}
			tdhhSquadLeverScorelist.add(tDhhSquadLeverScore);
		}
		
		tdhhSquadLeverScoreRepo.saveAll(tdhhSquadLeverScorelist);
	}
	
	private List<List<ScoreCalcsDto>> getaggregateAnswers(List<Object> ans) {
		List<ScoreCalcsDto> allanswers=new ArrayList<ScoreCalcsDto>();
		if(ans!=null) {
			for(int i=0;i<ans.size();i++) {
				Object[] a=(Object[]) ans.get(i);
				ScoreCalcsDto scoreCalcsDto=new ScoreCalcsDto();
				scoreCalcsDto.setQid((int)a[0]);
				if(!String.valueOf(a[1]).equalsIgnoreCase("null"))
				scoreCalcsDto.setQans(Float.valueOf(String.valueOf(a[1])));
				else
					scoreCalcsDto.setQans(0);
				scoreCalcsDto.setSquadid((int)a[2]);
				scoreCalcsDto.setLeverid((int)a[3]);
				allanswers.add(scoreCalcsDto);
			}
			}
			//ques score clacs
			for(int i=0;i<allanswers.size();i++) {
				for(int j=i+1;j<allanswers.size();j++) {
					if(allanswers.get(i).getQid()==allanswers.get(j).getQid()) {
						if(allanswers.get(i).getLeverid()==allanswers.get(j).getLeverid()) {
							if(allanswers.get(i).getSquadid()==allanswers.get(j).getSquadid()) {
								if(allanswers.get(i).getQans()<allanswers.get(j).getQans()) {
									allanswers.remove(j);
								}
								else {
									allanswers.get(i).setQans(allanswers.get(j).getQans());
									allanswers.remove(j);
								}
								
								
							}
							
						}
					}
				}
				
			}
			
			List<ScoreCalcsDto> perleveranswers=new ArrayList<ScoreCalcsDto>();
			List<List<ScoreCalcsDto>> allperleveranswers=new ArrayList<List<ScoreCalcsDto>>();
			for(int i=0;i<allanswers.size();i++) {
				perleveranswers.add(allanswers.get(i));
				for(int j=i+1;j<allanswers.size();j++) {
					if((allanswers.get(i).getSquadid()==allanswers.get(j).getSquadid())&&(allanswers.get(i).getLeverid()==allanswers.get(j).getLeverid())){
						perleveranswers.add(allanswers.get(j));
						allanswers.remove(j);
						j--;
					}
					
				}
				allanswers.remove(i);
				i--;
				allperleveranswers.add(perleveranswers);
				perleveranswers=new ArrayList<ScoreCalcsDto>();
			}
			return allperleveranswers;
	}
	@GetMapping(value = "/fetchScoreByLeverSquad", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResultMapper fetchPersona(@RequestParam Long surveyId) {
		ResultMapper resultMapper = new ResultMapper();
		try {
			LeverMaturityScoreDto leverMaturityScore = gmasTaskDao.getScoreByLeverandSquad(surveyId);
			if (null != leverMaturityScore) {
				resultMapper.setResultObject(leverMaturityScore);
				resultMapper.setMessage(MessageConstant.OK_CODE);
				resultMapper.setErrorCode(MessageConstant.SUCCESS_STATUS);
			} else {
				resultMapper.setResultObject(leverMaturityScore);
				resultMapper.setMessage(MessageConstant.NO_DATA_FOUND);
				resultMapper.setErrorCode(MessageConstant.ERROR_STATUS);
			}
	
		} catch (Exception e) {
			return catchException(e);
	}	
		return resultMapper;
	}
	@GetMapping(value = "/generate-maturitylevelreport-xls", produces = MediaType.APPLICATION_JSON_VALUE)
	public Resultreport generateRecommendedactionsrXls(@RequestParam Long surveyId) {
		String urlToDownload = "";
		Resultreport report = new Resultreport();
		try {
			awsUtility.createBucket();	
			LeverMaturityScoreDto leverMaturityScore = gmasTaskDao.getScoreByLeverandSquad(surveyId);
			
			urlToDownload = generateXLStoDownload(leverMaturityScore,surveyId);
			report.setMessage(urlToDownload);
			report.setFileName(dashboardFileName + ".xlsx");
			report.setErrorcode("0");
			return report;
		} catch (Exception e) {
			return catchResultreportException(e);
		}
	}
	private Resultreport catchResultreportException(Exception e) {
		logger.error(e.getMessage());
		StackTraceElement[] st = e.getStackTrace();
		StringBuilder stackTraceMsg = new StringBuilder(e.getCause() + "\n" + e.getMessage() + "\n");
		for (StackTraceElement s : st) {
			stackTraceMsg.append(s + "\n");
		}
		logger.error(stackTraceMsg.toString());
		Resultreport resultreportpojo = new Resultreport();
		resultreportpojo.setErrorcode("1");
		resultreportpojo.setMessage("KO");
		return resultreportpojo;

	}
	private String generateXLStoDownload(LeverMaturityScoreDto leverMaturityScore, Long surveyId)
			throws IOException, NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet workingSheet = wb.createSheet(downloadFileTitle);
		int headerrow = 1;

		headerrow = createExcel(wb, workingSheet, headerrow,leverMaturityScore);
		File xlsPath = new File(generatedFilePath);
		xlsPath.mkdirs();
		String downloadFile = generatedFilePath + dashboardFileName + ".xlsx";
		File localXlsFilePath = new File(downloadFile);
		FileOutputStream outputStream = new FileOutputStream(localXlsFilePath);
		wb.write(outputStream);
		wb.close();
		String objectKey = "downloads/excel/" + surveyId + "/" + dashboardFileName + ".xlsx";
		awsUtility.uploadDirectoryOrFile(new File(downloadFile), objectKey);
		return awsUtility.getS3FileDownloadUrl(objectKey);
		
	}
	private int createExcel(XSSFWorkbook wb, XSSFSheet workingSheet, int headerrow, LeverMaturityScoreDto leverMaturityScore) {
		Row row = workingSheet.createRow(1);
		Row rowTwo = workingSheet.createRow(2);
		
		Row row1 = workingSheet.createRow(4);
		
		Row row2 = workingSheet.createRow(5);
		
		createDataCellAlignLeft(wb,"All Levers",1,row);		
		
		createDataCellAlignLeft(wb,"Digital Hub Maturity Level",2,row);	
		createBlackDataCellAlignLeft(wb,"Level 1",5,row);
		createBlackDataCellAlignLeft(wb,"Level 2",6,row);
		createBlackDataCellAlignLeft(wb,"Level 3",7,row);
		createBlackDataCellAlignLeft(wb,"Level 4",8,row);
		createBlackDataCellAlignLeft(wb,"Level 5",9,row);
		
		createDataCellAlignLeft(wb,"0% - 25%",5,rowTwo);
		createDataCellAlignLeft(wb,"25% - 50%",6,rowTwo);
		createDataCellAlignLeft(wb,"50% - 75%",7,rowTwo);
		createDataCellAlignLeft(wb,"75% - 95%",8,rowTwo);
		createDataCellAlignLeft(wb,"95% - 100%",9,rowTwo);
		
		createDataCellAlignLeft(wb,"",1,rowTwo);
		createDataCellAlignLeft(wb,"",2,rowTwo);
		createDataCellAlignLeft(wb,"",3,rowTwo);		
		
		workingSheet.addMergedRegion(new CellRangeAddress(row.getRowNum(), rowTwo.getRowNum(), 1, 1));
		workingSheet.addMergedRegion(new CellRangeAddress(row.getRowNum(), rowTwo.getRowNum(), 2, 2));
		workingSheet.addMergedRegion(new CellRangeAddress(row.getRowNum(), rowTwo.getRowNum(), 3, 3));
		
		createDataCellAlignLeft(wb,"Squads",2,row1);	
		
		createDataCellAlignLeft(wb,"#FTE (as declared by the users belonging to the squad)",2,row2);	
		
		createDataCellAlignLeft(wb,leverMaturityScore.getMaturityLevel()+"%",3,row);
		getGreenDataCell(wb,3,row);
		getLevel(leverMaturityScore.getMaturityLevel(),wb,row,rowTwo);
		Integer totalfte = 0;
		
		populateData(workingSheet,wb,7,leverMaturityScore.getLeverScoreList(), totalfte);
		return headerrow;
	}
	public void getLevel(Double maturity,XSSFWorkbook wb, Row row,Row rowTwo){
		if(maturity>=0 && maturity<25){
			getGreenDataCell(wb,5,row);
			getGreenDataCell(wb,5,rowTwo);
		}else if(maturity>=25 && maturity<50){
			getGreenDataCell(wb,6,row);
			getGreenDataCell(wb,6,rowTwo);
		}else if(maturity>=50 && maturity<75){
			getGreenDataCell(wb,7,row);
			getGreenDataCell(wb,7,rowTwo);
		}else if(maturity>=75 && maturity<95){
			getGreenDataCell(wb,8,row);
			getGreenDataCell(wb,8,rowTwo);
		}else if(maturity>=95 && maturity<100){
			getGreenDataCell(wb,9,row);
			getGreenDataCell(wb,9,rowTwo);
		}
	}
	private int populateData(XSSFSheet workingSheet, XSSFWorkbook wb, int headerrow, List<LeverScoreDto> leverScoreList,Integer totalfte){
		
		for(LeverScoreDto leverScore : leverScoreList){
			Row row = workingSheet.createRow(headerrow++);
			createDataCellAlignLeft(wb,leverScore.getLeverName(),1,row);			
			
			createDataCellAlignLeft(wb,"Single squad",2,row);			
			
			Row row1 = workingSheet.createRow(row.getRowNum()+1);
			createDataCellAlignLeft(wb,"Digital Hub",2,row1);
			
			createDataCellAlignLeft(wb,"",1,row1);
			
			workingSheet.addMergedRegion(new CellRangeAddress(row.getRowNum(), row1.getRowNum(), 1, 1));
			List<SquadScoreDto> squadList = leverScore.getSquadScoreList();
			
			for(SquadScoreDto squadDto:squadList){
				Row row5 = workingSheet.getRow(4);
				
				Boolean flag = false;
				int cellCount = row5.getLastCellNum();
				int count = 0;
				if(cellCount != 3){
					while(cellCount>3){
						if(row5.getCell(cellCount-1).getStringCellValue().equals(squadDto.getSquadName())){
							flag = true;
							count = cellCount-1;
							break;
						}
						cellCount--;
					}
				}
				
				if(!flag){
					createDataCellAlignLeft(wb,squadDto.getSquadName(),row5.getLastCellNum(),row5);
					
					Row row6 = workingSheet.getRow(5);
					createVioletDataCellAlignLeft(wb,""+squadDto.getFte(),row6.getLastCellNum(),row6);
					totalfte=totalfte+squadDto.getFte();
					count = row5.getLastCellNum()-1;
				}
				createRedDataCellAlignLeft(wb,""+squadDto.getSingleSquadMaturityLevel()+"%",count,row);
				createDataCellAlignLeft(wb,""+squadDto.getDigitalHubMaturityLevel()+"%",count,row1);
				workingSheet.autoSizeColumn(count);
			}
			Row row11 = workingSheet.getRow(4);
			Row row55 = workingSheet.getRow(5);
			if(!row11.getCell(row11.getLastCellNum()-1).getStringCellValue().equals("Aggregation")){
				createDataCellAlignLeft(wb,"Aggregation",row11.getLastCellNum(),row11);
			}
			createVioletDataCellAlignLeft(wb,""+totalfte,row11.getLastCellNum()-1,row55);
			if(null != leverScore.getAggregation()){
				createBlackDataCellAlignLeft(wb,""+leverScore.getAggregation()+"%",row1.getLastCellNum(),row1);
			}
			workingSheet.autoSizeColumn(row1.getLastCellNum());
			headerrow+=2;
			
			workingSheet.autoSizeColumn(2);
			workingSheet.autoSizeColumn(3);
		}
		workingSheet.setColumnWidth(1, 30 * 256);
		return headerrow;
		
	}

	private void createDataCellAlignLeft(XSSFWorkbook wb, String value, int cellCount, Row row) {
		Cell cell = row.createCell(cellCount++);
		cell.setCellStyle(leftAlignActionDataCellStyle(wb));
		cell.setCellValue(value);
	}
	
	
	private XSSFCellStyle leftAlignActionDataCellStyle(final XSSFWorkbook wb) {
		XSSFCellStyle style;
		style = wb.createCellStyle();
		XSSFFont defaultFont = wb.createFont();
		defaultFont.setBold(true);
		style.setFont(defaultFont);
		style.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style.setAlignment(HorizontalAlignment.LEFT);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);

		return style;
	}
	
	private void createBlackDataCellAlignLeft(XSSFWorkbook wb, String value, int cellCount, Row row) {
		Cell cell = row.createCell(cellCount++);
		cell.setCellStyle(blackDataCellStyle(wb));
		cell.setCellValue(value);
	}
	
	
	private XSSFCellStyle blackDataCellStyle(final XSSFWorkbook wb) {
		XSSFCellStyle style;
		style = wb.createCellStyle();
		Font font = wb.createFont();
		font.setColor(IndexedColors.WHITE.getIndex());
		font.setBold(true);
		style.setFont(font);
		style.setFillForegroundColor(IndexedColors.BLACK.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style.setAlignment(HorizontalAlignment.LEFT);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);

		return style;
	}
	
	private void createRedDataCellAlignLeft(XSSFWorkbook wb, String value, int cellCount, Row row) {
		Cell cell = row.createCell(cellCount++);
		cell.setCellStyle(redDataCellStyle(wb));
		cell.setCellValue(value);
	}
	
	
	private XSSFCellStyle redDataCellStyle(final XSSFWorkbook wb) {
		XSSFCellStyle style;
		style = wb.createCellStyle();
		Font font = wb.createFont();
		font.setColor(IndexedColors.WHITE.getIndex());
		font.setBold(true);
		style.setFont(font);
		style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);

		return style;
	}
	private void createVioletDataCellAlignLeft(XSSFWorkbook wb, String value, int cellCount, Row row) {
		Cell cell = row.createCell(cellCount++);
		cell.setCellStyle(violetDataCellStyle(wb));
		cell.setCellValue(value);
	}
	
	
	private XSSFCellStyle violetDataCellStyle(final XSSFWorkbook wb) {
		XSSFCellStyle style;
		style = wb.createCellStyle();
		XSSFFont defaultFont = wb.createFont();
		defaultFont.setBold(true);
		style.setFont(defaultFont);
		style.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style.setAlignment(HorizontalAlignment.LEFT);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);

		return style;
	}
	private void getGreenDataCell(XSSFWorkbook wb, int cellCount, Row row) {
		Cell cell = row.getCell(cellCount);
		cell.setCellStyle(dataCellStyle(wb));
	}
	
	
	private XSSFCellStyle dataCellStyle(final XSSFWorkbook wb) {
		XSSFCellStyle style;
		style = wb.createCellStyle();
		XSSFFont defaultFont = wb.createFont();
		defaultFont.setBold(true);
		style.setFont(defaultFont);
		style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style.setAlignment(HorizontalAlignment.LEFT);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);

		return style;
	}
	
	private XSSFCellStyle blackColorDataCellStyle(final XSSFWorkbook wb) {
		XSSFCellStyle style;
		style = wb.createCellStyle();
		XSSFFont defaultFont = wb.createFont();		
		style.setFont(defaultFont);
		style.setAlignment(HorizontalAlignment.LEFT);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);

		return style;
	}
	private XSSFCellStyle centreAlignBlackColorDataCellStyle(final XSSFWorkbook wb) {
		XSSFCellStyle style;
		style = wb.createCellStyle();
		XSSFFont defaultFont = wb.createFont();		
		style.setFont(defaultFont);
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		return style;
	}
	
	private XSSFCellStyle centreAlignCommentBlackColorDataCellStyle(final XSSFWorkbook wb) {
		XSSFCellStyle style;
		style = wb.createCellStyle();
		XSSFFont defaultFont = wb.createFont();		
		style.setFont(defaultFont);
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setWrapText(true);
		return style;
	}
	@GetMapping(value = "/generate-kpiscores-xls", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResultMapper generaterecommendedactionsxls(@RequestParam Long surveyId) {
		ResultMapper resultMapper = new ResultMapper();
		String urlToDownload = "";
		try {
			awsUtility.createBucket();
			List<Object> kpians=tdhhusersurveykpiansRepo.fetchAllKPISuvreyAnswers(surveyId);
			List<List<ScoreCalcsDto>> allperleverkpianswers=getaggregateKPIExcelAnswers(kpians);
			
			//Sorting by kpi id
			for(List<ScoreCalcsDto> a:allperleverkpianswers) {
			for(int i=0;i<a.size();i++) {
				for(int j=i+1;j<a.size();j++) {
					if(a.get(i).getQid()>a.get(j).getQid()) {
						ScoreCalcsDto temp=new ScoreCalcsDto();
						temp=a.get(j);
						a.set(j, a.get(i));
						a.set(i, temp);
					}
				}
			}	
			}
			XSSFWorkbook wb = new XSSFWorkbook();
			ReportUtility reportUtility=new ReportUtility();
			for(List<ScoreCalcsDto> a:allperleverkpianswers) {
				int rownum=5;
				XSSFSheet sheet=wb.createSheet(tdhhlevermstRepo.findById((long) a.get(0).getLeverid()).get().getLeverName().replaceAll("[.,/]",""));
				reportUtility.createExcelColumns(tdhhsurveymstRepo.findById(surveyId).get(), wb, sheet,"KPI VALUE");
				int cellnum = 1;
				boolean rowexist=false;
				Row	headerRow = sheet.getRow(3);
				createRedDataCellAlignLeft(wb, "KPI METRICS", 0, headerRow);
				for(ScoreCalcsDto c:a) {
					cellnum = 0;
				
					Row row=null;
					for(int i=0;i<=sheet.getPhysicalNumberOfRows()+1;i++) {
						if(sheet.getRow(i) !=null && sheet.getRow(i).getCell(0) !=null &&tDhhKpiMstRepo.findById(new Long(c.getQid())).get().getMetricName().equalsIgnoreCase(sheet.getRow(i).getCell(0).getStringCellValue())) {
							 row=sheet.getRow(i);
							rowexist=true;
							break;
							}
					}
					if(!rowexist) {
							row = sheet.createRow(rownum++);
							rowexist=false;
					}
				Cell cell = row.createCell(cellnum++);
				cell.setCellValue(tDhhKpiMstRepo.findById(new Long(c.getQid())).get().getMetricName());
				cell.setCellStyle(blackColorDataCellStyle(wb));
				Row row1=sheet.getRow(2);
				for(int i=0;i<row1.getPhysicalNumberOfCells();i++) {
					if(row1.getCell(i)!=null &&c.getUsername().equalsIgnoreCase(row1.getCell(i).getStringCellValue()))
						cellnum=i;
				}
				
				Cell cell1 = row.createCell(cellnum++);
				cell1.setCellValue(c.getQans());
				cell1.setCellStyle(centreAlignBlackColorDataCellStyle(wb));
				if(!c.getComment().equals("null")){
					Cell cell2 = row.createCell(cellnum++);
					cell2.setCellValue(c.getComment());
					cell2.setCellStyle(centreAlignCommentBlackColorDataCellStyle(wb));
				}
				
				sheet.autoSizeColumn(0);
				rowexist=false;
				}
			}
			/*FileOutputStream out = new FileOutputStream(new File("C:\\Users\\desu.koteswara.rao\\KPISCORES.xlsx"));
            wb.write(out);*/
           // wb.close();			
            File xlsPath = new File(generatedFilePath);
    		xlsPath.mkdirs();
    		String downloadFile = generatedFilePath + kpiscoresfilename + ".xlsx";
    		File localXlsFilePath = new File(downloadFile);
    		FileOutputStream outputStream = new FileOutputStream(localXlsFilePath);
    		wb.write(outputStream);
    		//wb.close();
    		String objectKey = "downloads/excel/" + surveyId + "/" + kpiscoresfilename + ".xlsx";
    		awsUtility.uploadDirectoryOrFile(new File(downloadFile), objectKey);
    		 
			
			//urlToDownload = generateKPIXLStoDownload(allsquadsListscores);
			resultMapper.setMessage(awsUtility.getS3FileDownloadUrl(objectKey));
            //resultMapper.setMessage(urlToDownload);
			resultMapper.setErrorCode("0");
			
		 }catch (Exception e) {
			return catchException(e);
		}
		return resultMapper;
	}
	
	
	private List<List<ScoreCalcsDto>> getaggregateKPIExcelAnswers(List<Object> ans) {
		List<ScoreCalcsDto> allanswers=new ArrayList<ScoreCalcsDto>();
		if(ans!=null) {
			for(int i=0;i<ans.size();i++) {
				Object[] a=(Object[]) ans.get(i);
				ScoreCalcsDto scoreCalcsDto=new ScoreCalcsDto();
				scoreCalcsDto.setQid((int)a[0]);
				if(!String.valueOf(a[1]).equalsIgnoreCase("null"))
				scoreCalcsDto.setQans(Float.valueOf(String.valueOf(a[1])));
				else
					scoreCalcsDto.setQans(0);
				
				scoreCalcsDto.setSquadid((int)a[2]);
				scoreCalcsDto.setLeverid((int)a[3]);
				scoreCalcsDto.setUsername(String.valueOf(a[4]));
				scoreCalcsDto.setComment(String.valueOf(a[5]));
				allanswers.add(scoreCalcsDto);
			}
			}
			
			List<ScoreCalcsDto> perleveranswers=new ArrayList<ScoreCalcsDto>();
			List<List<ScoreCalcsDto>> allperleveranswers=new ArrayList<List<ScoreCalcsDto>>();
			for(int i=0;i<allanswers.size();i++) {
				perleveranswers.add(allanswers.get(i));
				for(int j=i+1;j<allanswers.size();j++) {
					if((allanswers.get(i).getLeverid()==allanswers.get(j).getLeverid())){
						perleveranswers.add(allanswers.get(j));
						allanswers.remove(j);
						j--;
					}
					
				}
				allanswers.remove(i);
				i--;
				allperleveranswers.add(perleveranswers);
				perleveranswers=new ArrayList<ScoreCalcsDto>();
			}
			return allperleveranswers;
	}
	
	@GetMapping(value = "/generate-questionsscores-xls", produces = MediaType.APPLICATION_JSON_VALUE)
	public Resultreport generateQuestionsScorexls(@RequestParam Long surveyId) {
		Resultreport report = new Resultreport();
		try {
			awsUtility.createBucket();
			List<Object> ans = gmasTaskDao.getQuestionAnsBysurveyId(surveyId);
			List<List<QuestionAnsDto>> allperleverDocanswers=getAggregateQuestionExcelAnswers(ans);
			
			XSSFWorkbook wb = new XSSFWorkbook();
			ReportUtility reportUtility=new ReportUtility();
			for(List<QuestionAnsDto> questionAnsDtos : allperleverDocanswers) {
				for(int i=0;i<questionAnsDtos.size();i++) {
					for(int j=i+1;j<questionAnsDtos.size();j++) {
						if(questionAnsDtos.get(i).getQid()>questionAnsDtos.get(j).getQid()) {
							QuestionAnsDto temp=new QuestionAnsDto();
							temp=questionAnsDtos.get(j);
							questionAnsDtos.set(j, questionAnsDtos.get(i));
							questionAnsDtos.set(i, temp);
						}
					}
				}		
				int rownum=5;
				XSSFSheet sheet=wb.createSheet(gmasTaskDao.getLevername((long) questionAnsDtos.get(0).getLeverid()).replaceAll("[.,/]",""));
				reportUtility.createExcelColumns(gmasTaskDao.getSurveyMst(surveyId), wb, sheet,"RESPONSES");
				int cellnum = 1;
				boolean rowexist=false;
				Row	headerRow = sheet.getRow(3);
				createRedDataCellAlignLeft(wb, "QUESTIONS", 0, headerRow);
				Map<Long,String> questionsMap = gmasTaskDao.getQuestionMst((long) questionAnsDtos.get(0).getLeverid());
				for(QuestionAnsDto questionAnsDto : questionAnsDtos) {
					cellnum = 0;
					Row	row = null;
					for(int i=0;i<=sheet.getPhysicalNumberOfRows()+1;i++) {
						if(sheet.getRow(i) !=null && sheet.getRow(i).getCell(0) !=null && questionsMap.get(new Long(questionAnsDto.getQid())).equals(sheet.getRow(i).getCell(0).getStringCellValue())) {
							 row=sheet.getRow(i);
							 rowexist=true;
							 break;
							}
					}
					if(!rowexist) {
							row = sheet.createRow(rownum++);
							rowexist=false;
					}
					Cell cell = row.createCell(cellnum++);
					cell.setCellValue(questionsMap.get(new Long(questionAnsDto.getQid())));
					cell.setCellStyle(blackColorDataCellStyle(wb));
					Row row1=sheet.getRow(2);
					for(int i=0;i<row1.getPhysicalNumberOfCells();i++) {
						if(row1.getCell(i)!=null &&questionAnsDto.getUsername().equalsIgnoreCase(row1.getCell(i).getStringCellValue()))
							cellnum=i;
					}
					Cell cell1 = row.createCell(cellnum++);
					cell1.setCellValue(questionAnsDto.getQans());
					cell1.setCellStyle(centreAlignBlackColorDataCellStyle(wb));
					
					if(!questionAnsDto.getComment().equals("null")){
						Cell cell2 = row.createCell(cellnum++);
						cell2.setCellValue(questionAnsDto.getComment());
						cell2.setCellStyle(centreAlignCommentBlackColorDataCellStyle(wb));
					}
					
					
					sheet.autoSizeColumn(0);
					rowexist=false;
				}
			} 
            File xlsPath = new File(generatedFilePath);
    		xlsPath.mkdirs();
    		String downloadFile = generatedFilePath + questionsscoresfilename + ".xlsx";
    		File localXlsFilePath = new File(downloadFile);
    		FileOutputStream outputStream = new FileOutputStream(localXlsFilePath);
    		wb.write(outputStream);
    		//wb.close();
    		String objectKey = "downloads/excel/" + surveyId + "/" + questionsscoresfilename + ".xlsx";
    		awsUtility.uploadDirectoryOrFile(new File(downloadFile), objectKey);

    		report.setMessage(awsUtility.getS3FileDownloadUrl(objectKey));
    		report.setFileName(questionsscoresfilename + ".xlsx");
    		report.setErrorcode("0");
			
		}catch (Exception e) {
			return catchResultreportException(e);
		}
		return report;
	}
	
	private List<List<QuestionAnsDto>> getAggregateQuestionExcelAnswers(List<Object> ans) {
		List<QuestionAnsDto> allanswers=new ArrayList<>();
		if(ans!=null) {
			for(int i=0;i<ans.size();i++) {
				Object[] a=(Object[]) ans.get(i);
				QuestionAnsDto questionAnsDto=new QuestionAnsDto();
				questionAnsDto.setQid((int)a[0]);
				if(null != (String)a[1])
					questionAnsDto.setQans(String.valueOf(a[1]));
				questionAnsDto.setSquadid((int)a[2]);
				questionAnsDto.setLeverid((int)a[3]);
				questionAnsDto.setUsername(String.valueOf(a[4]));
				questionAnsDto.setComment(String.valueOf(a[5]));
				allanswers.add(questionAnsDto);
			}
			}
			
			List<QuestionAnsDto> perleveranswers=new ArrayList<QuestionAnsDto>();
			List<List<QuestionAnsDto>> allperleveranswers=new ArrayList<List<QuestionAnsDto>>();
			for(int i=0;i<allanswers.size();i++) {
				perleveranswers.add(allanswers.get(i));
				for(int j=i+1;j<allanswers.size();j++) {
					if((allanswers.get(i).getLeverid()==allanswers.get(j).getLeverid())){
						perleveranswers.add(allanswers.get(j));
						allanswers.remove(j);
						j--;
					}
					
				}
				allanswers.remove(i);
				i--;
				allperleveranswers.add(perleveranswers);
				perleveranswers=new ArrayList<QuestionAnsDto>();
			}
			return allperleveranswers;
	}
	
	@GetMapping(value = "/generate-DocumentAns-xls", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResultMapper generatereDocumentAnsXls(@RequestParam Long surveyId) {
		ResultMapper resultMapper = new ResultMapper();
		try {
			awsUtility.createBucket();
			List<Object> docAns = gmasTaskDao.getDocumentAns(surveyId);
			List<List<DocumentAnsDto>> allperleverDocanswers=getAggregateDocExcelAnswers(docAns);
			
			//Sorting by document id
			for(List<DocumentAnsDto> a:allperleverDocanswers) {
			for(int i=0;i<a.size();i++) {
				for(int j=i+1;j<a.size();j++) {
					if(a.get(i).getDocumentId()>a.get(j).getDocumentId()) {
						DocumentAnsDto temp=new DocumentAnsDto();
						temp=a.get(j);
						a.set(j, a.get(i));
						a.set(i, temp);
					}
				}
			}	
			}
			XSSFWorkbook wb = new XSSFWorkbook();
			ReportUtility reportUtility=new ReportUtility();
			for(List<DocumentAnsDto> documentAnsDtos : allperleverDocanswers) {
				int rownum=5;
				XSSFSheet sheet=wb.createSheet(gmasTaskDao.getLevername((long) documentAnsDtos.get(0).getLeverId()).replaceAll("[.,/]",""));
				reportUtility.createExcelColumns(gmasTaskDao.getSurveyMst(surveyId), wb, sheet,"UPLOADED");
				int cellnum = 1;
				boolean rowexist=false;
				Row	headerRow = sheet.getRow(3);
				createRedDataCellAlignLeft(wb, "DOCUMENTS", 0, headerRow);
				for(DocumentAnsDto documentAnsDto : documentAnsDtos) {
					cellnum = 0;
					Row	row = null;
					for(int i=0;i<=sheet.getPhysicalNumberOfRows()+1;i++) {
						if(sheet.getRow(i) !=null && sheet.getRow(i).getCell(0) !=null &&gmasTaskDao.getDocumentName(new Long(documentAnsDto.getDocumentId())).equalsIgnoreCase(sheet.getRow(i).getCell(0).getStringCellValue())){
							 row=sheet.getRow(i);
							 rowexist=true;
							 break;
						}
					}
					if(!rowexist) {
						row = sheet.createRow(rownum++);
						rowexist=false;
					}
					Cell cell = row.createCell(cellnum++);
					cell.setCellValue(gmasTaskDao.getDocumentName(new Long(documentAnsDto.getDocumentId())));
					cell.setCellStyle(blackColorDataCellStyle(wb));
					Row row1=sheet.getRow(2);
					for(int i=0;i<row1.getPhysicalNumberOfCells();i++) {
						if(row1.getCell(i)!=null &&documentAnsDto.getUserName().equalsIgnoreCase(row1.getCell(i).getStringCellValue()))
							cellnum=i;
					}
					Cell cell1 = row.createCell(cellnum++);
					cell1.setCellValue(documentAnsDto.getAnswer());
					cell1.setCellStyle(centreAlignBlackColorDataCellStyle(wb));
					
					if(!documentAnsDto.getComment().equals("null")){
						Cell cell2 = row.createCell(cellnum++);
						cell2.setCellValue(documentAnsDto.getComment());
						cell2.setCellStyle(centreAlignCommentBlackColorDataCellStyle(wb));
					}
					
					sheet.autoSizeColumn(0);
					rowexist=false;
				}
			}			
            File xlsPath = new File(generatedFilePath);
    		xlsPath.mkdirs();
    		String downloadFile = generatedFilePath + docansfilename + ".xlsx";
    		File localXlsFilePath = new File(downloadFile);
    		FileOutputStream outputStream = new FileOutputStream(localXlsFilePath);
    		wb.write(outputStream);
    		//wb.close();
    		String objectKey = "downloads/excel/" + surveyId + "/" + docansfilename + ".xlsx";
    		awsUtility.uploadDirectoryOrFile(new File(downloadFile), objectKey);

    		resultMapper.setMessage(awsUtility.getS3FileDownloadUrl(objectKey));
			resultMapper.setErrorCode("0");
			
		}catch (Exception e) {
			return catchException(e);
		}
		return resultMapper;
	}
	
	private List<List<DocumentAnsDto>> getAggregateDocExcelAnswers(List<Object> ans) {
		List<DocumentAnsDto> allanswers=new ArrayList<>();
		if(ans!=null) {
			for(int i=0;i<ans.size();i++) {
				Object[] a=(Object[]) ans.get(i);
				DocumentAnsDto documentAnsDto=new DocumentAnsDto();
				documentAnsDto.setDocumentId((int)a[0]);
				if(null != (String)a[1])
					documentAnsDto.setAnswer(String.valueOf(a[1]));
				documentAnsDto.setSquadId((int)a[4]);
				documentAnsDto.setLeverId((int)a[3]);
				documentAnsDto.setUserName(String.valueOf(a[2]));
				documentAnsDto.setComment(String.valueOf(a[5]));
				allanswers.add(documentAnsDto);
			}
			}
			
			List<DocumentAnsDto> perleveranswers=new ArrayList<DocumentAnsDto>();
			List<List<DocumentAnsDto>> allperleveranswers=new ArrayList<List<DocumentAnsDto>>();
			for(int i=0;i<allanswers.size();i++) {
				perleveranswers.add(allanswers.get(i));
				for(int j=i+1;j<allanswers.size();j++) {
					if((allanswers.get(i).getLeverId()==allanswers.get(j).getLeverId())){
						perleveranswers.add(allanswers.get(j));
						allanswers.remove(j);
						j--;
					}
					
				}
				allanswers.remove(i);
				i--;
				allperleveranswers.add(perleveranswers);
				perleveranswers=new ArrayList<DocumentAnsDto>();
			}
			return allperleveranswers;
	}
	
	}	
