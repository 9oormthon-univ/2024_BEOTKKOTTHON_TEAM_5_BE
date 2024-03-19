package io.festival.distance.auth.service;

import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.member.repository.MemberRepository;
import io.festival.distance.domain.member.validlogin.ValidLogin;
import io.festival.distance.exception.DistanceException;
import io.festival.distance.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Set;

@Component("userDetailsService")
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final ValidLogin validLogin;
    @Override
    // 로그인시에 DB에서 유저정보와 권한정보를 가져와서 해당 정보를 기반으로 userdetails.User 객체를 생성해 리턴
    public UserDetails loadUserByUsername(final String loginId) {
        Member member = memberRepository.findOneWithAuthoritiesByLoginId(loginId)
                .orElseThrow(() -> new DistanceException(ErrorCode.NOT_EXIST_MEMBER));
        if(validLogin.checkLogin(member))
            throw new DistanceException(ErrorCode.NOT_NULL_MEMBER_INFO);
        return createAdmin(loginId,member);
    }

    private User createAdmin(String id, Member member) {
        if (!member.isActivated()) {
            throw new RuntimeException(id + " -> 활성화되어 있지 않습니다.");
        }
        GrantedAuthority grantedAuthorities = new SimpleGrantedAuthority(member.getAuthority().toString());

        return new User(member.getLoginId(),
                member.getPassword(),
                Set.of(grantedAuthorities));
    }
}