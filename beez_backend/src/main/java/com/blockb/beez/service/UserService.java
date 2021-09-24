package com.blockb.beez.service;

import java.util.Collections;

import com.blockb.beez.dao.UserMapper;
import com.blockb.beez.dto.LoginDto;
import com.blockb.beez.dto.UserDto;
import com.blockb.beez.exception.DuplicatedUsernameException;
import com.blockb.beez.exception.LoginFailedException;
import com.blockb.beez.exception.UserNotFoundException;
import com.blockb.beez.utils.JwtTokenProvider;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

//비즈니스 로직을 가진 Service생성.
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserMapper userMapper;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    
    //로그인
    public String login(LoginDto loginDto) {
        //db에 아이디랑 비교하고 존재하지 않을 경우, 노티 출력
        UserDto userDto = userMapper.findUserByUsername(loginDto.getUsername())
                .orElseThrow(() -> new LoginFailedException("잘못된 아이디입니다"));
        //db에 아이디가 존재하고 비밀번호가 잘못 됐을 경우, 노티 출력
        if (!passwordEncoder.matches(loginDto.getPassword(), userDto.getPassword())) {
            //System.out.println(passwordEncoder.encode("6003wkd"));  //비밀번호 암호화해서 확인. $2a$10$mr8eta5WTsGvrdZELOGX7OWHUIdJMR2Fdab0RuXD3AtCzIpLFoKQ. - 암호화
            throw new LoginFailedException("잘못된 비밀번호입니다");
        }
        //jwtTokenProvider - 토큰을 생성하기 위해
        return jwtTokenProvider.createToken(userDto.getUserId(), Collections.singletonList(userDto.getRole()));
        
    }
    //회원 가입
    @Transactional
    public UserDto join(UserDto userDto) {
        if (userMapper.findUserByUsername(userDto.getUsername()).isPresent()) {
            throw new DuplicatedUsernameException("이미 가입된 유저입니다");
            
        }

        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userMapper.save(userDto);

        return userMapper.findUserByUsername(userDto.getUsername()).get();
    }
    //회원 조회
    public UserDto findByUserId(Long userId) {
        return userMapper.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException("없는 유저입니다."));
    }
}
