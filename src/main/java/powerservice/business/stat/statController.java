package powerservice.business.stat;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import powerservice.business.stat.service.statService;
import powerservice.business.stat.service.ctiService;

@EnableScheduling
@Controller
public class statController {
	
	@Resource
    private statService statService;
	
	@Resource
    private ctiService ctiService;
	
	// 매일 오전 6시 실행
	@Scheduled(cron = "0 0 6 * * *")
	// 테스트용
//	@Scheduled(cron = "*/30 * * * * *")
	public void infoData() throws Exception{
		
		try {
			
			// PostgreSQL에서 전날 00:00:00 ~ 23:59:59데이터 가져오기 -- TB_CTI_MAIN_INFO 에 들어갈 데이터
			List<Map<String, Object>> selectInfoData = statService.selectInfoData();
			
			// PostgreSQL에서 전날 00:00:00 ~ 23:59:59데이터 가져오기 -- TB_CTI_QUE_INFO 에 들어갈 데이터
			List<Map<String, Object>> selectQueData = statService.selectQueData();
			
			System.out.println("selectInfoData : " + selectInfoData);
			System.out.println("selectQueData : " + selectQueData);
			
			// TB_CTI_MAIN_INFO에 insert
			for(int i = 0; i < selectInfoData.size(); i++) {
				
				ctiService.insertInfoData(selectInfoData.get(i));
			}
			
			// TB_CTI_QUE_INFO에 insert
			for(int i = 0; i < selectQueData.size(); i++) {
				
				ctiService.insertQueData(selectQueData.get(i));
			}
			
			System.out.println("데이터 insert 완료!!! =======================================");
			
		} catch (Exception e) {
			System.out.println("\ngimController.infoData 에러 ===============\n");
		}
		
	}
}



































