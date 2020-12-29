package com.example.veggietracker.service;

import com.example.veggietracker.dto.AuthenticationResponse;
import com.example.veggietracker.dto.LoginRequest;
import com.example.veggietracker.dto.RegisterRequest;
import com.example.veggietracker.exceptions.VeggieTrackerException;
import com.example.veggietracker.model.NotificationEmail;
import com.example.veggietracker.model.User;
import com.example.veggietracker.model.VerificationToken;
import com.example.veggietracker.repository.UserRepository;
import com.example.veggietracker.repository.VerificationTokenRepository;
import com.example.veggietracker.security.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @Transactional  // as we are using relational db & want to guarantee consistency
    public void signup(RegisterRequest registerRequest) throws DataIntegrityViolationException {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword1()));
        user.setCreated(Instant.now());
        user.setEnabled(false);

        userRepository.save(user);

        String token = generateVerificationToken(user);
        mailService.sendMail(new NotificationEmail(
                "Please activate your account",
                user.getEmail(),
                "Thank you for signing up to VeggieTracker, " +
                        "please click on the below url to activate your account: " +
                        "http://veggietracker-env.eba-jpegpwk2.ap-south-1.elasticbeanstalk.com/api/v1/auth/accountVerification/" + token
        ));
    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUserRef(user);

        verificationTokenRepository.save(verificationToken);
        return token;
    }

    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        verificationToken.orElseThrow(() -> new VeggieTrackerException("Invalid Token"));
        fetchUserAndEnable(verificationToken.get());
    }

    @Transactional
    private void fetchUserAndEnable(VerificationToken verificationToken) {
        String username = verificationToken.getUserRef().getUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new VeggieTrackerException("User not found with name - " + username));
        user.setEnabled(true);
        userRepository.save(user);
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
            )
        );

        // To check if a user has logged in or not, we can just look the
        // SecurityContext for the authenticate object for the user.
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        String token = jwtProvider.generateToken(authenticate);
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .username(loginRequest.getUsername())
                .build();
    }
}
