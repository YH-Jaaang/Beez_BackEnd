package com.blockb.beez.service;

import com.blockb.beez.dao.PassCheckDao;
import com.blockb.beez.dto.PassCheckDto;
import com.blockb.beez.exception.LoginFailedException;
import com.blockb.beez.exception.UserNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PassCheckService {
    private final PassCheckDao passCheckDao;
    private final PasswordEncoder passwordEncoder;

    //회원 보안 Password 여부 확인
    public PassCheckDto findByUserPassConfirm(Long userId) {
        return passCheckDao.findByUserPassConfirm(userId)
                .orElseThrow(() -> new UserNotFoundException("없는 유저입니다."));
    }
    //회원 PasswordCheck
    public PassCheckDto findByUserPassCheck(Long userId, String password) {
        PassCheckDto passCheck = passCheckDao.findByUserPassCheck(userId)
                .orElseThrow(() -> new UserNotFoundException("없는 유저입니다."));

        //passwordEncoder.matches를 하면 디비에 저장시켜주고 throw를 보내줘야함. 근데 그게 잘 안됨.
        if (!passwordEncoder.matches(password, passCheck.getPasswordCheck())) {
            passCheckDao.passCount(userId);
            System.out.println("dd1");
        }

        if (!passwordEncoder.matches(password, passCheck.getPasswordCheck())) {
            System.out.println("dd2");
            throw new UserNotFoundException("비밀번호가 틀립니다."); 
        }
        return passCheck;
    }
    
    //회원 PasswordStorage
    public void findByUserPassStorage(PassCheckDto passCheckDto) {
        passCheckDto.setPasswordCheck(passwordEncoder.encode(passCheckDto.getPasswordCheck()));
        passCheckDao.save(passCheckDto);
    }
}
