package com.movie.test.Member;


import com.movie.test.Member.dto.ProfileImgDto;
import com.movie.test.Member.util.UploadFileService;
import com.movie.test.hobby.HobbyDto;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Controller
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final UploadFileService uploadFileService;

    public MemberController(MemberService memberService, UploadFileService uploadFileService) {
        this.memberService = memberService;
        this.uploadFileService = uploadFileService;
    }

    // CREATE ACCOUNT FORM
    @GetMapping("/create_account_form")
    public String createAccountForm(Model model) {
        log.info("createAccountForm()");

        List<HobbyDto> hobbyDtos = memberService.getHobbys();
        model.addAttribute("hobbyDtos", hobbyDtos);
        String nextPage = "member/create_account_form";

        return nextPage;
    }

    // CREATE ACCOUNT CONFIRM
    @PostMapping("/create_account_confirm")
    public String createAccountConfirm(MemberDto memberDto,
                                       @RequestParam("hobbys") String[] hobbys,
                                       @RequestParam(value = "profile" , required = false) MultipartFile file) {
        log.info("createAccountConfirm()");

        String nextPage = "member/create_account_ok";
        log.info("file" + file);
        if (!file.isEmpty()) {
            String savedFileName= uploadFileService.upload(memberDto.getM_id(), file);
            if(savedFileName!=null) {
                memberDto.setM_profile_thumbnail(savedFileName);
            }
        }
        int result = memberService.createAccountConfirm(memberDto, hobbys);
        if (result <= 0) {
            nextPage = "member/create_account_ng";
        }

        return nextPage;
    }

    // LOGIN FORM
    @GetMapping("/login_form")
    public String loginForm() {
        log.info("loginForm()");

        String nextPage = "member/login_form";

        return nextPage;
    }

    // login confirm
    @PostMapping("/login_confirm")
    public String loginConfirm(MemberDto memberDto, HttpSession session) {
        log.info("loginConfirm()");

        String nextPage = "member/login_ok";

        MemberDto loginedMemberDto = memberService.loginConfirm(memberDto);
        if (loginedMemberDto != null) {
            session.setAttribute("loginedMemberID", loginedMemberDto.getM_id());

        } else {
            nextPage = "member/login_ng";
        }

        return nextPage;
    }

    // modify form
    @GetMapping("/modify_form")
    public String modifyForm(HttpSession session, Model model) {
        log.info("modifyForm()");

        String nextPage = "member/modify_form";

        Object object = session.getAttribute("loginedMemberID");
        String loginedMemberID = null;
        if (object != null) {
            loginedMemberID = String.valueOf(object);

            MemberDto loginedMemberDto = memberService.modifyForm(loginedMemberID);
            model.addAttribute("loginedMemberDto", loginedMemberDto);

            List<HobbyDto>hobbyDtos = memberService.getHobbys();
            model.addAttribute("hobbyDtos", hobbyDtos);
        }


        return nextPage;

    }

    // modify confirm
    @PostMapping("/modify_confirm")
    public String modifyConfirm(MemberDto memberDto,
                                @RequestParam("hobbys") String[] hobbys,
                                @RequestParam(value = "profile", required = false) MultipartFile file,
                                HttpSession session) {
        log.info("modifyConfirm()");

        String nextPage = "member/modify_ok";
        Object object = session.getAttribute("loginedMemberID");
        String loginedMemberID = null;
        if (object != null) {
            loginedMemberID = String.valueOf(object);
        }
        if (!file.isEmpty()) {
            String savedFileName = uploadFileService.upload(loginedMemberID, file);
            if (savedFileName != null) {
                memberDto.setM_profile_thumbnail(savedFileName);
            }

        }
        memberDto.setM_id(loginedMemberID);

        int result = memberService.updateMember(memberDto, hobbys);
        if (result <= 0) {
            nextPage = "member/modify_ng";
        }
        return nextPage;
    }
    // logout
    @GetMapping("/logout_confirm")
    public String logoutConfirm(HttpSession session) {
        log.info("logoutConfirm()");

        session.invalidate();

        String nextPage = "redirect:/";

        return nextPage;

    }
    // delete Confirm
    @GetMapping("/delete_confirm")
    public String deleteConfirm(HttpSession session) {
        log.info("deleteConfirm()");

        String nextPage = "redirect:/";

        Object object = session.getAttribute("loginedMemberID");
        String loginedMemberID = null;
        if (object != null) {
            loginedMemberID = String.valueOf(object);

            int result = memberService.deleteMember(loginedMemberID);
            if (result > 0) {
                nextPage = "redirect:/member/logout_confirm";
            }
        }

        return nextPage;

    }

    @PostMapping("/getProfile")
    @ResponseBody
    public Object getProfile(HttpSession session) {
        log.info("getProfile()");

        List<ProfileImgDto> profileImgDtos = null;
        Map<String, Object> resultMap = new HashMap<String, Object>();

        Object object = session.getAttribute("loginedMemberID");
        String loginedMemberID = null;
        if (object != null) {
            loginedMemberID = String.valueOf(object);
            profileImgDtos = memberService.getProfile(loginedMemberID);
            if (profileImgDtos != null) {
                resultMap.put("profileImgDtos", profileImgDtos);
            }
        }
        return resultMap;
    }
}
