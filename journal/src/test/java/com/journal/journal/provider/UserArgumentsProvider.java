//package com.journal.journal.provider;
//
//import org.junit.jupiter.api.extension.ExtensionContext;
//import org.junit.jupiter.params.provider.Arguments;
//import org.junit.jupiter.params.provider.ArgumentsProvider;
//import org.springframework.security.core.userdetails.User;
//
//import java.util.stream.Stream;
//
//public class UserArgumentsProvider implements ArgumentsProvider {
//
//    @Override
//    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception{
//        return Stream.of(
//                Arguments.of(User.builder().username("ram").password("123").build()),
//                Arguments.of(User.builder().username("rahul").password("123").build())
//        );
//    }
//}
