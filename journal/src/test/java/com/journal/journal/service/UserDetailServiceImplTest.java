//package com.journal.journal.service;
//
//import com.journal.journal.repository.UserRepository;
//import com.journal.journal.services.UserDetailServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.params.provider.Arguments;
//import org.mockito.ArgumentMatcher;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.ArrayList;
//
//import static org.mockito.Mockito.*;
//
//
//// @SpringBootTest
//public class UserDetailServiceImplTest {
//
////    in case of SpringBoot Test use @Autowired
//
//    @InjectMocks
//    private UserDetailServiceImpl userDetailService;
//
////    In case of SpringBoot Test use @MockBean
//
//    @Mock
//    private UserRepository userRepository;
//
//    @BeforeEach
//    void setUp(){
//        MockitoAnnotations.initMocks(this);
//    }
//
//    void loadUserByUsernameTest(){
//        when(userRepository.findByUsername(ArgumentMatcher.anyString())).thenReturn(User.builder().username("ram").password("jhhjhjs").roles(new ArrayList<>()).build());
//        UserDetails user=userDetailService.loadUserByUsername("ram");
//        Ass
//    }
//
//}
