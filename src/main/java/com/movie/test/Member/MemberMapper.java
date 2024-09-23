package com.movie.test.Member;

import com.movie.test.Member.dto.ProfileImgDto;
import com.movie.test.hobby.HobbyDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MemberMapper {
    List<HobbyDto> getHobbys();

    boolean isMember(String m_id);

    int addNewMember(MemberDto memberDto);

    void addNewProfile(@Param("m_id") String m_id, @Param("m_profile_thumbnail") String m_profile_thumbnail);

    MemberDto getMember(String m_id);

    int deleteMember(String m_id);

    int updateMember(MemberDto memberDto);

    List<ProfileImgDto> getProfile(String m_id);
}
