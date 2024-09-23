package com.movie.test.Member;

import com.movie.test.Member.dto.ProfileImgDto;
import com.movie.test.hobby.HobbyDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class MemberService {

    final private MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberMapper memberMapper, PasswordEncoder passwordEncoder) {
        this.memberMapper = memberMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public List<HobbyDto> getHobbys() {
        log.info("getHobbys");
        return memberMapper.getHobbys();
    }

    public int createAccountConfirm(MemberDto memberDto, String[] hobbys) {
        log.info("createAccountConfirm()");

        int result = -1;
        boolean isMember = memberMapper.isMember(memberDto.getM_id());
        if (!isMember) {
            memberDto.setM_pw(passwordEncoder.encode(memberDto.getM_pw()));
            memberDto.setM_hobby(String.join(",", hobbys));

            result = memberMapper.addNewMember(memberDto);
            if (memberDto.getM_profile_thumbnail() != null) {
                memberMapper.addNewProfile(memberDto.getM_id(), memberDto.getM_profile_thumbnail());
            }

        }

        return result;
    }

    public MemberDto loginConfirm(MemberDto memberDto) {
        log.info("loginConfirm()");

        MemberDto dto = memberMapper.getMember(memberDto.getM_id());

        if (!passwordEncoder.matches(memberDto.getM_pw(), dto.getM_pw())) {
            dto = null;
        }

        return dto;
    }

    public MemberDto modifyForm(String loginedMemberID) {
        log.info("modifyForm()");
        return memberMapper.getMember(loginedMemberID);
    }

    public int deleteMember(String loginedMemberID) {
        log.info("deleteMember()");
        return memberMapper.deleteMember(loginedMemberID);
    }

    public int updateMember(MemberDto memberDto, String[] hobbys) {
        log.info("updateMember()");
        memberDto.setM_hobby(String.join(",", hobbys));

        int result = memberMapper.updateMember(memberDto);
        if (memberDto.getM_profile_thumbnail() != null) {
            memberMapper.addNewProfile(memberDto.getM_id(), memberDto.getM_profile_thumbnail());

        }
        return result;
    }

    public List<ProfileImgDto> getProfile(String loginedMemberID) {
        log.info("getProfile");
        return memberMapper.getProfile(loginedMemberID);
    }
}


