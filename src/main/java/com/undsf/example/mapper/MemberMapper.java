package com.undsf.example.mapper;

import com.undsf.example.entity.Member;

import java.util.List;

/**
 * Created by Arathi on 2016-03-25.
 */
public interface MemberMapper {
    Member getMemberById(int id);
    Member getMemberByName(String name);
    List<Member> getMembers();
}
