package com.example.socialmedia.service;


import com.example.socialmedia.User;
import com.example.socialmedia.auth.SessionManager;
import com.example.socialmedia.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public boolean registerUser(String userName, String email, String phoneNumber) {
        //檢查email是否存在
        if (userRepository.existsByEmail(email)) {
            return false;
        }

        //建立新使用者
        User newUser = new User();
        newUser.setUserName(userName);
        newUser.setEmail(email);
        newUser.setPhoneNumber(phoneNumber);
        newUser.setPassword(passwordEncoder.encode(phoneNumber));

        //儲存到資料庫
        userRepository.save(newUser);

        return true;
    }


    public String login(String phoneNumber){
        User user = userRepository.findByPhoneNumber(phoneNumber);
        if(user == null)return null;

        if (passwordEncoder.matches(phoneNumber, user.getPassword())) {
            return SessionManager.generateToken(user.getUserId()); // 登入成功回傳 token
        }

        return null;
    }

}
