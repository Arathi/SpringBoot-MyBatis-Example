package com.undsf.example.service.impl;

import com.google.gson.Gson;
import com.undsf.example.entity.Member;
import com.undsf.example.mapper.MemberMapper;
import com.undsf.example.service.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Arathi on 2016-03-25.
 */
@Service
public class MemberServiceImpl implements IMemberService {
    @Autowired
    private MemberMapper memberMapper;

    @Override
    public String getMemberInfo(int id) {
        Gson gson = new Gson();
        Member member = memberMapper.getMemberById(id);
        String json = gson.toJson(member);
        return json;
    }
}
