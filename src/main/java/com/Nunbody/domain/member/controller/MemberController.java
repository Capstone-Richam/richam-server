package com.Nunbody.domain.member.controller;


import com.Nunbody.domain.member.dto.MemberRegisterRequestDto;
import com.Nunbody.domain.member.dto.SignInRequestDto;
import com.Nunbody.domain.member.dto.SignInResponseDto;
import com.Nunbody.domain.member.dto.ValidateRequestDto;
import com.Nunbody.domain.member.service.MemberService;
import com.Nunbody.global.common.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;


    @PostMapping("/signup")
    public ResponseEntity<SuccessResponse<?>> create(@RequestBody MemberRegisterRequestDto dto) {
        memberService.register(dto);

        return SuccessResponse.ok(null);
    }

    @PostMapping("/signin")
    public ResponseEntity<SuccessResponse<?>> signIn(@RequestBody SignInRequestDto dto) {
        SignInResponseDto signInResponseDto = memberService.signIn(dto.getAccount(), dto.getPassword());
        return SuccessResponse.ok(signInResponseDto);

    }

    @PostMapping("/validate")
    public ResponseEntity<SuccessResponse<?>> validateMember(@RequestBody ValidateRequestDto validateRequestDto) {
        if (validateRequestDto.getAorn().equals("account"))
            memberService.validateAccount(validateRequestDto.getContent());
        if (validateRequestDto.getAorn().equals("nickname"))
            memberService.validateName(validateRequestDto.getContent());
        return SuccessResponse.ok(null);
    }

}
