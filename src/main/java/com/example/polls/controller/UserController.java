package com.example.polls.controller;

import com.example.polls.exception.ResourceNotFoundException;
import com.example.polls.model.Paint;
import com.example.polls.model.User;
import com.example.polls.payload.*;
import com.example.polls.repository.PaintRepository;
import com.example.polls.repository.PollRepository;
import com.example.polls.repository.UserRepository;
import com.example.polls.repository.VoteRepository;
import com.example.polls.security.UserPrincipal;
import com.example.polls.service.PaintService;
import com.example.polls.service.PollService;
import com.example.polls.security.CurrentUser;
import com.example.polls.util.AppConstants;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
    private UserRepository userRepository;

    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private PaintRepository paintRepository;

    @Autowired
    private PollService pollService;
    @Autowired
    private PaintService paintService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    
    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getName());
        return userSummary;
    }
    
    @PutMapping("/user/me/add")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> addColor(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody AddPaintRequest addPaintRequest){
    	Optional<User> userOptional = userRepository.findById(currentUser.getId());
    	if (!userOptional.isPresent())
    		return ResponseEntity.notFound().build();
    	User user = userOptional.get();
    	paintService.addPaint(addPaintRequest, user);
    	user.setLastPaintAdded(addPaintRequest.toString());
    	userRepository.save(user);
    	
    	return ResponseEntity.ok()
                .body(new ApiResponse(true, "Paint added"));   
    }
    @DeleteMapping("/user/me/remove")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> removeColor(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody AddPaintRequest addPaintRequest){
    	Optional<User> userOptional = userRepository.findById(currentUser.getId());
    	if (!userOptional.isPresent())
    		return ResponseEntity.notFound().build();
    	User user = userOptional.get();
    	Optional<Paint> paintOptional = paintRepository.findById(addPaintRequest.getId());
    	if (!paintOptional.isPresent())
    		return ResponseEntity.notFound().build();
    	paintService.removePaint(addPaintRequest, user);
    	
    	
    	
    	return ResponseEntity.ok()
                .body(new ApiResponse(true, "Paint removed"));   
    }
    
    
    @GetMapping("/user/me/mypaints")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> getMyPaints(@CurrentUser UserPrincipal currentUser) {
    	Optional<User> userOptional = userRepository.findById(currentUser.getId());
    	if (!userOptional.isPresent())
    		return ResponseEntity.notFound().build();
    	
        List<Paint> userPaints = paintService.getUsersPaints(currentUser.getId());
        return ResponseEntity.ok()
                .body(new PaintResponse(true, userPaints));
    }
    

    @GetMapping("/user/checkUsernameAvailability")
    public UserIdentityAvailability checkUsernameAvailability(@RequestParam(value = "username") String username) {
        Boolean isAvailable = !userRepository.existsByUsername(username);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/user/checkEmailAvailability")
    public UserIdentityAvailability checkEmailAvailability(@RequestParam(value = "email") String email) {
        Boolean isAvailable = !userRepository.existsByEmail(email);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/users/{username}")
    public UserProfile getUserProfile(@PathVariable(value = "username") String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        long pollCount = pollRepository.countByCreatedBy(user.getId());
        long voteCount = voteRepository.countByUserId(user.getId());

        UserProfile userProfile = new UserProfile(user.getId(), user.getUsername(), user.getName(), user.getCreatedAt(), pollCount, voteCount);

        return userProfile;
    }

    @GetMapping("/users/{username}/polls")
    public PagedResponse<PollResponse> getPollsCreatedBy(@PathVariable(value = "username") String username,
                                                         @CurrentUser UserPrincipal currentUser,
                                                         @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                         @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return pollService.getPollsCreatedBy(username, currentUser, page, size);
    }


    @GetMapping("/users/{username}/votes")
    public PagedResponse<PollResponse> getPollsVotedBy(@PathVariable(value = "username") String username,
                                                       @CurrentUser UserPrincipal currentUser,
                                                       @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                       @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return pollService.getPollsVotedBy(username, currentUser, page, size);
    }
}
