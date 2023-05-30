package study.spring.helloworld.controllers;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import study.spring.helloworld.helpers.UploadHelper;
import study.spring.helloworld.helpers.WebHelper;
import study.spring.helloworld.models.UploadItem;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UploadController {

    private final WebHelper webHelper;
    private final UploadHelper uploadHelper;

    @Value("${upload.dir}")
    private String uploadDir;

    @Value("${upload.url}")
    private String uploadUrl;

    /** 업로드 폼을 구성하는 페이지*/
    @GetMapping("/fileupload/upload.do")
    public String upload(){
        return "fileupload/upload";
    }

    /**
     * Spring 순정 업로드 기능 구현
     * - 업로드가 되는 과정에서 다루어야 하는 값들을 확인하기 위한 예제
     */
    @PostMapping("/fileupload/upload_ok.do")
    public ModelAndView uploadOk(Model model,
        @RequestParam(required = false) String subject,
        @RequestParam(required = false) MultipartFile photo){

            /** 1) 업로드 파일 저장하기 */
            //업로드 된 파일이 존재하는지 확인한다.
            if(photo.getOriginalFilename().isEmpty()){
                return webHelper.badRequest("업로드 된 파일이 없습니다.");
            }

            //업로드 된 파일의 정보를 로그로 기록
            log.debug("===============================");
            log.debug("원본파일이름: " + photo.getOriginalFilename());
            log.debug("<input type='file'>의 이름: " + photo.getName());
            log.debug("파일형식: " + photo.getContentType());
            log.debug("파일크기: " + photo.getSize());

            //업로드 된 파일이 저장될 경로 정보를 생성한다.
            File targetFile = new File(uploadDir, photo.getOriginalFilename());
            
            try {
                photo.transferTo(targetFile);
            } catch (Exception e) {
                return webHelper.serverError(e);
            } 

            /** 2) 업도르 경로 정보 처리하기 */
            //복사된 파일의 정대 경로를 추출한다.
            //--> 운영체제 호환을 위해 역슬래시를 슬래시로 변환한다.
            String absPath = targetFile.getAbsolutePath().replace("\\", "/");
            log.debug("업로드 된 파일의 경로: " + absPath);

            //업로드된 파일의 절대경로(absPath)에서 환경설정 파일이 명시된 폴더까지의 위치는 삭제하여
            //환경설정 파일에 명시된 upload.dir 이후의 위치만 추출한다.(윈도우만...ㅜㅜ)
            String filePath = null;
            
            if(absPath.substring(0, 1).equals("/")){
                //Mac, Linux 용 경로 처리
                filePath = absPath.replace(uploadDir, "");
            }
            else{
                //Window용 경로 처리 --> 설정 파일에 명시한 첫 글자(/)를 제거해야 함
                filePath = absPath.replace(uploadDir.substring(1), "");
            }

            log.debug("업로드 폴더 내에서의 최종 경로: " + filePath);

            /** 3) 업로드 결과를 Beans에 저장 */
            UploadItem item = new UploadItem();
            item.setContentType(photo.getContentType());
            item.setFieldName(photo.getName());
            item.setFileSize(photo.getSize());
            item.setOriginName(photo.getOriginalFilename());
            item.setFilePath(filePath);

            //업로드 경로를 웹 상에서 접근 가능한 경로 문자열로 변환하여 Beans에 추가한다.
            String fileUrl = String.format("%s%s", uploadUrl, filePath);
            item.setFileUrl(fileUrl);
            log.debug("파일의 URL: " + fileUrl);
            log.debug("===============================");

            /** 4) View 처리 */
            //텍스트 정보를 View로 전달한다.
            model.addAttribute("subject", subject);
            //업로드 정보를 View로 전달한다.
            model.addAttribute("item", item);

            //뷰 호출
            return new ModelAndView("fileupload/upload_ok");
        }

        /** 헬퍼를 사용한 업로드 폼을 구성하는 페이지 */
        @GetMapping("/fileupload/use_helper.do")
        public String useHelper(){
            return "fileupload/use_helper";
        }

        /** 헬퍼를 활용한 업로드 처리 */
        @PostMapping("/fileupload/use_helper_ok.do")
        public ModelAndView useHelperOk(Model model,
            @RequestParam(required = false) MultipartFile photo){

                UploadItem item = null;

                try {
                    item = uploadHelper.saveMultipartFile(photo);
                }catch (Exception e) {
                    return webHelper.serverError(e);
                }

                model.addAttribute("item", item);

                return new ModelAndView("fileupload/use_helper_ok");
        }
        
        /** 헬퍼를 사용한 업로드 폼을 구성하는 페이지 */
        @GetMapping("/fileupload/multiple.do")
        public String multiple(){
            return "fileupload/multiple";
        }
        
        /** 헬퍼를 활용한 업로드 처리 */
        @PostMapping("/fileupload/multiple_ok.do")
        public ModelAndView multipleOk(Model model,
            @RequestParam(required = false) MultipartFile[] photo){
                List<UploadItem> list = null;

                try {
                    list = uploadHelper.saveMultipartFile(photo);
                } catch (Exception e) {
                    return webHelper.serverError(e);
                }

                model.addAttribute("list", list);

                return new ModelAndView("fileupload/multiple_ok");
            }
    
}
