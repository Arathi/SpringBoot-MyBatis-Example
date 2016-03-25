package com.undsf.example.controller;

import com.undsf.example.service.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Arathi on 2016-03-25.
 */
@RestController
@RequestMapping(value="/member")
public class MemberController {
    @Autowired
    private IMemberService memberService;

    @RequestMapping(value="/get_member_info/{member_id}", method=RequestMethod.GET)
    public String get_member_info(
            @PathVariable(value="member_id") int id
    ) {
        return memberService.getMemberInfo(id);
    }
}
