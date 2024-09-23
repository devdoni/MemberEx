package com.movie.test.Member;

import lombok.Data;

@Data
public class MemberDto {
    private int m_no;
    private String m_id;
    private String m_pw;
    private String m_mail;
    private String m_phone;
    private String m_gender;
    private String m_hobby;
    private String m_profile_thumbnail;
    private String m_reg_date;
    private String m_mod_date;
}
