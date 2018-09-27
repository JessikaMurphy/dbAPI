package com.example.polls.service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.polls.model.Choice;
import com.example.polls.model.Paint;
import com.example.polls.model.Poll;
import com.example.polls.model.User;
import com.example.polls.payload.AddPaintRequest;
import com.example.polls.payload.PollRequest;
import com.example.polls.repository.PaintRepository;
import com.example.polls.repository.UserRepository;
import com.example.polls.security.UserPrincipal;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.json.*;

@Service
public class PaintService {
	
	@Autowired
    private PaintRepository paintRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(PollService.class);
	
	public Paint addPaint(AddPaintRequest addPaintRequest, User user) {
		Paint paint = new Paint();
    	paint.setBrand(addPaintRequest.getBrand());
    	paint.setPaintName(addPaintRequest.getPaintName());
    	paint.setRgb(addPaintRequest.getRgb());
    	paint.setId(addPaintRequest.getId());
    	paint.setPaintId(addPaintRequest.getPaintId());
    	paint.setUser(user);
    	
    	return paintRepository.save(paint);
    }
	public void removePaint(AddPaintRequest addPaintRequest, User user) {
		paintRepository.deleteByUserAndPaintId(user.getId(), addPaintRequest.getId());
	}
	public List<Paint> getUsersPaints(Long id) {
		List<Paint> userPaints = paintRepository.findByUserId(id);
		
 
		return userPaints;
	}
		
	

}
