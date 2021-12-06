package com.ssangyong.GreenMarket.service;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

@Service
public class TradeChatService {
	
	/**
	 * JSON 형식의 String -> JSONObject로 변환
	 */
	public JSONObject parseJsonToObject(String jsonStr) {
		JSONParser parser = new JSONParser();
		JSONObject obj = null;
		try {
			obj = (JSONObject) parser.parse(jsonStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}
}
